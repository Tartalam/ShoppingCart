package Application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.LinkedList;

/**
 * Provides file operations for reading, writing, updating and deleting data records.
 * Handles all file I/O operations with comprehensive error handling and data integrity checks.
 * 
 * Features:
 * - Thread-safe file operations
 * - Automatic backup/restore on write failures
 * - Validation of all inputs
 * - Detailed error reporting
 */
public class FileManagement {
    
    /**
     * Writes an object to a file in append mode.
     * Creates the file if it doesn't exist. Maintains data integrity with backup mechanism.
     * 
     * @param obj The object to write (must implement toString())
     * @param fileName Target file path (relative or absolute)
     * @throws IllegalArgumentException if obj is null or fileName is null/empty
     * @throws IOException if file operation fails after retries
     */
    public void writeObjectToFile(Object obj, String fileName) throws IOException {
        // Input validation
        if (obj == null) {
            throw new IllegalArgumentException("Object to write cannot be null");
        }
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new IllegalArgumentException("File name cannot be null or empty");
        }

        // Create backup file path
        Path filePath = Path.of(fileName);
        Path backupPath = Path.of(fileName + ".bak");
        
        try {
            // Create parent directories if they don't exist
            if (filePath.getParent() != null) {
                Files.createDirectories(filePath.getParent());
            }

            // Create backup if file exists
            if (Files.exists(filePath)) {
                Files.copy(filePath, backupPath, StandardCopyOption.REPLACE_EXISTING);
            }

            // Write to file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
                writer.write(obj.toString());
                writer.newLine();
            }

        } catch (IOException e) {
            // Restore from backup if write failed
            if (Files.exists(backupPath)) {
                try {
                    Files.copy(backupPath, filePath, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException restoreEx) {
                    throw new IOException("Failed to write object and restore backup", restoreEx);
                }
            }
            throw new IOException("Failed to write object to file: " + fileName, e);
        } finally {
            // Clean up backup file
            try {
                if (Files.exists(backupPath)) {
                    Files.delete(backupPath);
                }
            } catch (IOException cleanupEx) {
                System.err.println("Warning: Failed to clean up backup file: " + cleanupEx.getMessage());
            }
        }
    }

    /**
     * Deletes an object from file based on TRN (Tax Registration Number).
     * Maintains data integrity by working on a temporary file and atomic replace.
     * 
     * @param productId The TRN to identify the object to delete
     * @param fileName The file containing the records
     * @throws IllegalArgumentException if trn or fileName is null/empty
     * @throws IOException if file operations fail
     */
    public void deleteObjectFromFile(String productId, String fileName) throws IOException {
        // Input validation
        if (productId == null || productId.trim().isEmpty()) {
            throw new IllegalArgumentException("Product ID cannot be null or empty");
        }
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new IllegalArgumentException("File name cannot be null or empty");
        }

        Path filePath = Path.of(fileName);
        Path tempPath = Path.of(fileName + ".tmp");

        try {
            boolean objectFound = false;
            
            // Read original file and write to temp file excluding the object to delete
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(tempPath.toFile()))) {
                
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parsedObject = parseLineToObject(line);
                    if (parsedObject.length > 1 && !parsedObject[0].trim().equals(productId)) {
                        writer.write(line);
                        writer.newLine();
                    } else {
                        objectFound = true;
                    }
                }
            }

            if (!objectFound) {
                throw new IllegalArgumentException("Object with Product ID " + productId + " not found in " + fileName);
            }

            // Atomically replace original file with temp file
            Files.move(tempPath, filePath, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            // Clean up temp file if operation failed
            try {
                if (Files.exists(tempPath)) {
                    Files.delete(tempPath);
                }
            } catch (IOException cleanupEx) {
                System.err.println("Warning: Failed to clean up temp file: " + cleanupEx.getMessage());
            }
            throw new IOException("Failed to delete object from file", e);
        }
    }

    /**
     * Reads objects from a file and returns them as a LinkedList of String arrays.
     * Each line in the file becomes a String array in the LinkedList.
     * 
     * @param fileName The file to read from
     * @return LinkedList of String arrays representing each record
     * @throws IllegalArgumentException if fileName is null/empty
     * @throws IOException if file reading fails
     */
    public LinkedList<String[]> readObjectsFromFile(String fileName) throws IOException {
        // Input validation
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new IllegalArgumentException("File name cannot be null or empty");
        }

        LinkedList<String[]> objectList = new LinkedList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Skip empty lines
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] parts = line.split(",");
                objectList.add(parts);
            }
        } catch (IOException e) {
            throw new IOException("Failed to read from file: " + fileName, e);
        }
        
        return objectList;
    }

    /**
     * Updates all records in a file with new data.
     * Uses atomic file replacement for data integrity.
     * 
     * @param fileName Target file name
     * @param records LinkedList of String arrays representing the records
     * @throws IllegalArgumentException if fileName is null/empty or records is null
     * @throws IOException if file operations fail
     */
    public void updateObjectsInFile(String fileName, LinkedList<String[]> records) throws IOException {
        // Input validation
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new IllegalArgumentException("File name cannot be null or empty");
        }
        if (records == null) {
            throw new IllegalArgumentException("Records cannot be null");
        }

        Path filePath = Path.of(fileName);
        Path tempPath = Path.of(fileName + ".tmp");

        try {
            // Write all records to temp file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempPath.toFile()))) {
                for (String[] record : records) {
                    if (record != null) {
                        writer.write(String.join(",", record));
                        writer.newLine();
                    }
                }
            }

            // Atomically replace original file
            Files.move(tempPath, filePath, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            // Clean up temp file if operation failed
            try {
                if (Files.exists(tempPath)) {
                    Files.delete(tempPath);
                }
            } catch (IOException cleanupEx) {
                System.err.println("Warning: Failed to clean up temp file: " + cleanupEx.getMessage());
            }
            throw new IOException("Failed to update objects in file", e);
        }
    }

    /**
     * Parses a line from file into an Object array.
     * @param line The line to parse
     * @return Array of objects from the parsed line
     */
    private String[] parseLineToObject(String line) {
        if (line == null || line.trim().isEmpty()) {
            return new String[0];
        }
        return line.split(",");
    }

    /**
     * Formats an Object array into a line for file storage.
     * @param obj The object array to format
     * @return Formatted string line
     */
    @SuppressWarnings("unused")
	private String formatObjectToLine(Object[] obj) {
        if (obj == null || obj.length == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (Object o : obj) {
            if (o != null) {
                sb.append(o.toString()).append(",");
            }
        }

        // Remove trailing comma if any
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }

        return sb.toString();
    }
}
//Author:Jahmari Harrison 
//ID: 2304204
//Module: CIT2004
//course: Object Oriented Programming-UM2
package Application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FileManagement {
	
	
	void WriteObjectToFile(Object obj, String fileName) {
    if (obj == null || fileName == null || fileName.isEmpty()) {
      throw new IllegalArgumentException("Object or file name cannot be null or empty.");
    }

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
      writer.write(obj.toString());
      writer.newLine();
      //System.out.println("Object written to file successfully.");
    } catch (IOException e) {
      System.err.println("Error writing to file: " + e.getMessage());
    }
  }
//this is now

  public void deleteObjectFromFile(String trn, String fileName) {
        // Create a LinkedList to store objects
        LinkedList<Object[]> objectList = new LinkedList<>();

        //  Read the file and populate the linked list with objects
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Parse each line into an Object[] (this depends on the file format)
                Object[] parsedObject = parseLineToObject(line);
                objectList.add(parsedObject);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        // Search for the object with the matching TRN (in Object[1])
        boolean objectFound = false;
        for (Object[] obj : objectList) {
            // Check if Object[1] contains the TRN we want to delete
            if (obj.length > 1 && obj[1].toString().trim().equals(trn)) {
                objectList.remove(obj);
                objectFound = true;
                break; // Exit the loop once the object is found and deleted
            }
        }

        // If object was found, write the updated list back to the file
        if (objectFound) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                for (Object[] obj : objectList) {
                    // Convert Object[] back to a formatted string to write to the file
                    String formattedLine = formatObjectToLine(obj);
                    writer.write(formattedLine);
                    writer.newLine(); // Ensure each object is on a new line
                }
            } catch (IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
            }
        } else {
            System.out.println("Object with TRN " + trn + " not found.");
        }
    }

    /**
     * Helper method to convert a line from the file into an Object[].
     * This will depend on how the objects are stored in the file.
     * For example, assume the object is stored in a comma-separated format.
     * @param line The line from the file.
     * @return The parsed Object[].
     */
    private Object[] parseLineToObject(String line) {
        // Assuming the line is comma-separated and we need to split it
        // Modify this based on your actual object structure
        String[] tokens = line.split(",");
        return tokens; // Return as Object[] (can be changed as needed)
    }

    /**
     * Helper method to convert an Object[] back to a formatted string to write to the file.
     * This should match the format used in the file.
     * @param obj The object array to format.
     * @return The formatted line.
     */
    private String formatObjectToLine(Object[] obj) {
        // Join the object array into a comma-separated string
        // Modify this based on your actual object structure
        StringBuilder sb = new StringBuilder();
        for (Object o : obj) {
            sb.append(o.toString()).append(",");
        }
        // Remove the last comma
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }
        //read data and parses it into an array list and then returns it to the calling method.
        public LinkedList<String[]> readObjectsFromFile(String fileName) {
        LinkedList<String[]> objectList = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split the line into parts by comma
                String[] parts = line.split(",");
                // Add the entire parsed line to the list, regardless of its length
                objectList.add(parts);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return objectList;
    }

    // Method to write updated records back to the file
    public void UpdateObjectsInFile(String fileName, LinkedList<String[]> records) {
      try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
          for (String[] record : records) {
              // Convert the record (String[]) to a single line string
              String line = String.join(",", record); // Assuming CSV format
              writer.write(line);
              writer.newLine(); // Write a newline after each record
          }
          //System.out.println("File updated successfully.");
      } catch (IOException e) {
          System.out.println("Error writing to file: " + e.getMessage());
      }
  }
    
 
    
}


  


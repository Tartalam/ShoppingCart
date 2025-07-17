// AVLFileManager.java
package Application;

import java.io.*;

public class AVLFileManager {
    private static final String AVL_TREE_FILE = "avl_tree.ser";

    /**
     * Serializes and saves the AVL tree to a file
     * @param avl The AVL tree to save
     * @throws IOException If there's an error during file operations
     */
    public void saveAVLTree(AVL avl) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(AVL_TREE_FILE))) {
            oos.writeObject(avl);
            System.out.println("AVL tree successfully saved to file.");
        }
    }

    /**
     * Deserializes and loads the AVL tree from file
     * @return The loaded AVL tree
     * @throws IOException If there's an error during file operations
     * @throws ClassNotFoundException If the serialized class is not found
     */
    public AVL loadAVLTree() throws IOException, ClassNotFoundException {
        File file = new File(AVL_TREE_FILE);
        if (!file.exists()) {
            System.out.println("No existing AVL tree file found. Creating new AVL tree.");
            return new AVL();
        }

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(AVL_TREE_FILE))) {
            AVL avl = (AVL) ois.readObject();
            System.out.println("AVL tree successfully loaded from file.");
            return avl;
        }
    }
}
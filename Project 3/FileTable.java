/*
    Leon Lam
    CS4348 Project 3

    FileTable class for project3
*/

// Used for serializing and deserializing the objects of this class
import java.io.Serializable;

public class FileTable implements Serializable {
    
    // FileEntry array
    public FileEntry[] table;

    // Constructor initializes the file table
    public FileTable() {
        table = new FileEntry[10];
    }
}
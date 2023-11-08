import java.util.Scanner;

public class main{
public static void main(String[] args) {
    // storage for disk
    int storage [][] = new int[256][512];
    Scanner in = new Scanner(System.in);

    System.out.println("1) Display a file");
    System.out.println("2) Display the file table");
    System.out.println("3) Display the free space bitmap");
    System.out.println("4) Display a disk block");
    System.out.println("5) Copy a file from the simulation to a file on the real system");
    System.out.println("6) Copy a file from the real system to a file on the simulation");
    System.out.println("7) Delete a file");
    System.out.println("8) Exit");
    

        while(in.nextInt() != 8){
            System.out.println("1) Display a file");
            System.out.println("2) Display the file table");
            System.out.println("3) Display the free space bitmap");
            System.out.println("4) Display a disk block");
            System.out.println("5) Copy a file from the simulation to a file on the real system");
            System.out.println("6) Copy a file from the real system to a file on the simulation");
            System.out.println("7) Delete a file");
            System.out.println("8) Exit");
        }
    }
}
//follow ivy_babycat on Instagram 

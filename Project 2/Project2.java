import java.util.concurrent.Semaphore;


public class Project2 {
    public static void main(String[] args) {
        Threads t1 = new Threads("Hello");
        Threads t2 = new Threads("world");
        Thread myThread = new Thread(t1);
        Thread myThread2 = new Thread(t2);
        myThread.start();
        myThread2.start();

        try {
            myThread.join();
            myThread2.join();
        } catch (Exception e) {
            System.err.print("Thread not working");
        }
    }    
}

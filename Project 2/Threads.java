public class Threads implements Runnable{
    private String message;
  
    // first thread
    Threads( String msg )
    {
       message = msg;
    }
 
    // function will take in the message and run the code
    public void run()
    {
        System.out.print(message + " ");
    }
}

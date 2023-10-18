package Examples;

// implements Runnable to create thread
public class JavaThread1 implements Runnable
{
   private String message;
  
   // first thread
   JavaThread1( String msg )
   {
      message = msg;
   }

   // function will take in the message and run the code
   public void run()
   {
      System.out.print(message);
   }

   public static void main(String args[])
   {
      String message_main ="Hello, ";   
      String message_thread = "thread";

      // print hello 
      System.out.print(message_main);

      // create thread
      JavaThread1 thr = new JavaThread1(message_thread);
      Thread myThread = new Thread( thr );
      myThread.start();

      try
      {
         myThread.join();
      }
      catch (InterruptedException e)
      {
      }
   }
}


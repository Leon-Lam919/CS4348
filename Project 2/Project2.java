import java.util.concurrent.Semaphore;


public class Project2 {
    // all semaphores
    private static Semaphore front_desk_employee[] = {new Semaphore(0, true), new Semaphore(0, true)};
    private static Semaphore bellhop[] = {new Semaphore(0, true), new Semaphore(0, true)};
    public static void main(String[] args) {
        System.out.println("Simulation starts");

        // runs front desk threads
        front_desk front_desk_employee_1 = new front_desk(1);
        front_desk front_desk_employee_2 = new front_desk(2);
        Thread frontThread = new Thread(front_desk_employee_1);
        Thread frontThread2 = new Thread(front_desk_employee_2);
        frontThread.start();
        frontThread2.start();

        bellhop bellhop1 = new bellhop(1);
        bellhop bellhop2 = new bellhop(2);
        Thread bell1 = new Thread(bellhop1);
        Thread bell2 = new Thread(bellhop2);
        bell1.start();
        bell2.start();

        Thread guest[] = new Thread[5];
        for (int i = 0; i < 5; i++){
            guest[i] = new Thread(new guests(i));
        }

        for (int i = 0; i < 5; i++){
            guest[i].start();
        }

        // try catch block to check for errors
        try {
            frontThread.join();
            frontThread2.join();
            bell1.join();
            bell2.join();
            for (int i = 0; i< 5; i++){
                guest[i].join();
            }

        } catch (Exception e) {
            System.err.print("Thread not working");
        }
    }

    // front desk thread
    public static class front_desk implements Runnable{
        private int id;
  
        // first thread
        public front_desk( int id )
        {
            this.id = id;
        }
     
        // runs the thread from main and spits out the id rn
        @Override
        public void run()
        {
            System.out.println("Front desk employee " + id + " created.");
        }
    }

    // bellhop thread
    public static class bellhop implements Runnable{
        private int id;

        public bellhop(int id){
            this.id = id;
        }

        // runs thread and spits out id rn
        @Override
        public void run(){
            System.out.println("Bellhop " + id + " created.");
        }
    }

    //guest thread
    public static class guests implements Runnable{
        private int id;

        public guests(int id){
            this.id = id;
        }

        @Override
        public void run(){
            System.out.println("Guest " + id + " created.");
        }
    }
}


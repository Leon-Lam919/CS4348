import java.util.concurrent.Semaphore;
import java.util.Random;

public class Project2 {
    // all semaphores
    private static Semaphore front_desk_employee[] = {new Semaphore(1), new Semaphore(1)};
    private static Semaphore bellhop[] = {new Semaphore(0), new Semaphore(0)};
    private static Semaphore guestSemaphore[] = {new Semaphore(0), new Semaphore(0)};
    public static void main(String[] args) {
        System.out.println("Simulation starts");

        Random rand = new Random();
        
        // runs front desk threads
        front_desk front_desk_employee_1 = new front_desk(1);
        Thread frontThread = new Thread(front_desk_employee_1);
        Thread guest[] = new Thread[5];
        Thread guestBag[] = new Thread[5];
        frontThread.start();
        bellhop bellhop1 = new bellhop(1);
        Thread bell1 = new Thread(bellhop1);
        bell1.start();
        
        // creates guest threads and runs them
        for (int i = 0; i < 5; i++){
            guest[i] = new Thread(new guests(i));
            guest[i].start();
        }
        
        // creating guest thread with bags
        for(int i = 0; i < 5; i++){
            int randBag = rand.nextInt(10);
            guestBag[i] = new Thread(new guestBag(i, randBag));
            guestBag[i].start();
        }
        
        // try catch block to check for errors
        try {
            frontThread.join();
            bell1.join();
            // guest threads join
            for(int i = 0; i < 5; i++){
                guest[i].join();
            }
            // guest bag thread joins
            for(int i = 0; i < 5; i++){
                guestBag[i].join();
            }
            // guest retire for the evening, therefore end of program
            for (int i = 0; i< 5; i++){
                System.out.println("Guest " + i + " retires for the evening");
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
            try{
                for (int i = 0; i < 2; i++){
                front_desk_employee[i].acquire();
                System.out.println("Front desk employee " + i + " created.");
            }
            front_desk_employee[0].release();
            front_desk_employee[1].release();
            bellhop[0].release();
            bellhop[1].release();
            }catch(Exception e){
                System.out.println(e);
            }
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
            try{
                for (int j = 0; j < 2; j++){
                bellhop[j].acquire();
                System.out.println("Bellhop " + j + " created.");
                }
                bellhop[0].release();
                guestSemaphore[0].release();
            }catch(Exception e){
                System.out.println(e);
            }
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
            try{
                // guest are created
                guestSemaphore[0].acquire();
                System.out.println("Guest " + id + " created.");
                guestSemaphore[0].release();
                guestSemaphore[1].release();
            }catch(Exception e){
                System.out.println(e);
            }
        }
    }

    // guest bag that shows how many bags each guest has
    public static class guestBag implements Runnable{
        private int bag;
        private int id;

        public guestBag(int id, int bag){
            this.id = id;
            this.bag = bag;
        }

        @Override
        public void run(){
            try {
                guestSemaphore[1].acquire();
                System.out.println("Guest " + id + " enters hotel with " + bag + " bags");
                guestSemaphore[1].release();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

}


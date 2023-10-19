import java.util.concurrent.Semaphore;


public class Project2 {
    // all semaphores
    private static Semaphore front_desk_employee[] = {new Semaphore(1), new Semaphore(1)};
    private static Semaphore bellhop[] = {new Semaphore(0), new Semaphore(0)};
    private static Semaphore guestSemaphore[] = {new Semaphore(0), new Semaphore(0)};
    public static void main(String[] args) {
        System.out.println("Simulation starts");

        // runs front desk threads
        front_desk front_desk_employee_1 = new front_desk(1);
        Thread frontThread = new Thread(front_desk_employee_1);
        Thread guest[] = new Thread[5];
        
        for (int i = 0; i < 5; i++){
            guest[i] = new Thread(new guests(i));
        }
        
        frontThread.start();
        bellhop bellhop1 = new bellhop(1);
        Thread bell1 = new Thread(bellhop1);
        bell1.start();
        
        for (int i = 0; i < 5; i++){
            guest[i].start();
        }

        // try catch block to check for errors
        try {
            frontThread.join();
            bell1.join();
            for (int i = 0; i< 5; i++){
                guest[i].join();
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
                guestSemaphore[0].acquire();
                System.out.println("Guest " + id + " created.");
                guestSemaphore[0].release();
            }catch(Exception e){
                System.out.println(e);
            }
        }
    }
}


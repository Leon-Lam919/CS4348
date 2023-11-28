import java.util.concurrent.Semaphore;
import java.util.Random;
import java.util.LinkedList;
import java.util.Queue;

public class Project2 {
    // all semaphores
    private static Semaphore front_desk_employee[] = {new Semaphore(1), new Semaphore(1)};
    private static Semaphore bellhop[] = {new Semaphore(0), new Semaphore(0)};
    private static Semaphore guestSemaphore[] = {new Semaphore(0), new Semaphore(0)};
    public static void main(String[] args) {
        final int NUMGUEST = 25;
        System.out.println("Simulation starts");

        Random rand = new Random();
        
        // runs front desk threads
        front_desk front_desk_employee_1 = new front_desk(1);
        Thread frontThread = new Thread(front_desk_employee_1);
        Thread guest[] = new Thread[5];
        frontThread.start();
        bellhop bellhop1 = new bellhop(1);
        Thread bell1 = new Thread(bellhop1);
        bell1.start();
        
        // creates guest threads and runs them
        for (int i = 0; i < 5; i++){
            guest[i] = new Thread(new guests(i, rand.nextInt(10)));
            guest[i].start();
        }
        
        
        // try catch block to check for errors
        try {
            frontThread.join();
            bell1.join();
            // guest threads join
            for(int i = 0; i < 5; i++){
                guest[i].join();
            }
            // guest retire for the evening, therefore end of program
            for (int i = 0; i< 5; i++){
                System.out.println("Guest " + i + " retires for the evening");
            }

            for(int i = 0; i < 5; i++){
                System.out.println("Guest " + i + " joined");
            }



        } catch (Exception e) {
            System.err.print("Thread not working");
        }
    }

    // front desk thread
    public static class front_desk implements Runnable{
        private int id;

        front_desk(){}
  
        // first thread
        public front_desk( int id )
        {
            this.id = id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
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

        public bellhop(){}

        public bellhop(int id){
            this.id = id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        // runs thread and spits out id rn
        @Override
        public void run(){
            try{
                for (int j = 0; j < 2; j++){
                bellhop[j].acquire();
                System.out.println("Bellhop " + j + " created.");
                }
                guestSemaphore[0].release();
                bellhop[0].acquire();
                guests guest = new guests();
                System.out.println("Bellhop " + getId() + " recieves bags from guest " + guest.getId());
                bellhop[0].release();

                bellhop[1].acquire();
                System.out.println("bellhop "+ getId() + " recieves bags from guest " + guest.getId());
                guestSemaphore[0].release();
            }catch(Exception e){
                System.out.println(e);
            }
        }
    }

    //guest thread
    public static class guests implements Runnable{
        private int id;
        private int bags;
        
        public guests(){}
        
        public guests(int id, int bags){
            this.id = id;
            this.bags = bags;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setBags(int bags) {
            this.bags = bags;
        }

        public int getBags() {
            return bags;
        }

        @Override
        public void run(){
            try{
                // guest are created
                guestSemaphore[0].acquire();
                System.out.println("Guest " + getId() + " created.");
                guestSemaphore[0].release();
                System.out.println("Guest " + getId() + " enters hotel with " + getBags() + " bags");

                Queue<Integer> queue = new LinkedList<>();

                // if bags are greater than 2, call first bellhop to get bags
                if(getBags() > 2){
                    queue.add(getId());
                    bellhop[0].release();
                }
                front_desk_employee[0].release();
                guestSemaphore[1].release();
            }catch(Exception e){
                System.out.println(e);
            }
        }
    }

}


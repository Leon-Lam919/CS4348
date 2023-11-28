import java.util.Random;
import java.util.concurrent.*;

class Hotel {
    private final int numRooms;
    private final Semaphore frontDeskSemaphore;
    private final Semaphore bellhopSemaphore;
    private final BlockingQueue<Integer> availableRooms;
    private final Random random;

    public Hotel(int numRooms) {
        this.numRooms = numRooms;
        this.frontDeskSemaphore = new Semaphore(2);
        this.bellhopSemaphore = new Semaphore(2);
        this.availableRooms = new LinkedBlockingQueue<>();
        this.random = new Random();

        for (int i = 1; i <= numRooms; i++) {
            availableRooms.offer(i);
        }
    }

    public int checkInGuest() throws InterruptedException {
        frontDeskSemaphore.acquire();
        int roomNumber = availableRooms.take();
        frontDeskSemaphore.release();
        return roomNumber;
    }

    public void checkOutGuest(int roomNumber) throws InterruptedException {
        availableRooms.offer(roomNumber);
    }

    public void dropOffBags(int numBags) throws InterruptedException {
        bellhopSemaphore.acquire();
        System.out.println("Bellhop dropping off " + numBags + " bags.");
        Thread.sleep(numBags * 100); // Simulate time taken to drop off bags
        bellhopSemaphore.release();
    }

    public void pickUpBags(int numBags) throws InterruptedException {
        bellhopSemaphore.acquire();
        System.out.println("Bellhop picking up " + numBags + " bags.");
        Thread.sleep(numBags * 100); // Simulate time taken to pick up bags
        bellhopSemaphore.release();
    }

    public void giveTip(int numBags) {
        System.out.println("Guest giving tip for " + numBags + " bags.");
    }
}

class Guest implements Runnable {
    private final int id;
    private final Hotel hotel;
    private final int numBags;
    private final int roomNumber;

    public Guest(int id, Hotel hotel, int numBags) throws InterruptedException {
        this.id = id;
        this.hotel = hotel;
        this.numBags = numBags;
        this.roomNumber = hotel.checkInGuest();
    }

    @Override
    public void run() {
        try {
            System.out.println("Guest " + id + " checked in. Got room number: " + roomNumber);

            if (numBags > 2) {
                hotel.dropOffBags(numBags);
            }

            System.out.println("Guest " + id + " entering room " + roomNumber);

            if (numBags > 2) {
                hotel.pickUpBags(numBags);
                hotel.giveTip(numBags);
            }

            System.out.println("Guest " + id + " retiring for the evening.");
            hotel.checkOutGuest(roomNumber);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class FrontDeskEmployee implements Runnable {
    private final Hotel hotel;

    public FrontDeskEmployee(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(hotel.getRandom().nextInt(3000) + 1000); // Simulate time taken to process a guest
                // Front desk employee is available to check in a guest
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

class Bellhop implements Runnable {
    private final Hotel hotel;

    public Bellhop(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(hotel.getRandom().nextInt(3000) + 1000); // Simulate time taken to process a guest
                // Bellhop is available to handle bags
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Hotel hotel = new Hotel(25);
        
        // Start front desk employees
        Thread frontDeskEmployee1 = new Thread(new FrontDeskEmployee(hotel));
        Thread frontDeskEmployee2 = new Thread(new FrontDeskEmployee(hotel));
        
        frontDeskEmployee1.start();
        frontDeskEmployee2.start();

        // Start bellhops
        Thread bellhop1 = new Thread(new Bellhop(hotel));
        Thread bellhop2 = new Thread(new Bellhop(hotel));
        
        bellhop1.start();
        bellhop2.start();

        // Start guests
        for (int i = 1; i <= 25; i++) {
            int numBags = hotel.getRandom().nextInt(6);
            Thread guestThread = new Thread(new Guest(i, hotel, numBags));
            guestThread.start();
        }
    }
}

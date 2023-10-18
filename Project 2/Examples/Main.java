
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class SharedResource {
    private int sharedData = 0;
    private Lock lock = new ReentrantLock();

    public void increment() {
        lock.lock();
        try {
            sharedData++;
        } finally {
            lock.unlock();
        }
    }

    public int getValue() {
        lock.lock();
        try {
            return sharedData;
        } finally {
            lock.unlock();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        SharedResource resource = new SharedResource();

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                resource.increment();
                System.out.println("Thread 1: Incremented");
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                int value = resource.getValue();
                System.out.println("Thread 2: Value = " + value);
            }
        });

        thread1.start();
        thread2.start();
    }
}


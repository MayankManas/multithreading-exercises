import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Philosopher implements Runnable {
    private final ReentrantLock leftChopstick, rightChopstick;
    private final String id;
    private int hunger = 5;
    private int retryDelay = 1000;

    public Philosopher(String id, ReentrantLock leftChopstick, ReentrantLock rightChopstick) {
        this.id = id;
        this.rightChopstick = rightChopstick;
        this.leftChopstick = leftChopstick;
    }

    public void run() {
        while (hunger > 0) {
            try {
                boolean acquiredLeft, acquiredRight = false;
                acquiredLeft = leftChopstick.tryLock(retryDelay, TimeUnit.MILLISECONDS);
                if (acquiredLeft) {
                    acquiredRight = rightChopstick.tryLock(retryDelay, TimeUnit.MILLISECONDS);
                }
                if ((acquiredLeft && acquiredRight)) {
                    hunger--;
                    Thread.sleep(100);
                    System.out.println("Philosopher " + id + " ate, hunger : " + hunger);
                    rightChopstick.unlock();
                    leftChopstick.unlock();
                    retryDelay = 1000; // reset after successful bite
                } else {
                    // exponential back-off
                    System.out.println("Philosopher " + id + " sleeping for " + retryDelay + "ms");
                    retryDelay = Math.min(retryDelay * 2, 8000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } finally {
                if (rightChopstick.isHeldByCurrentThread()) {
                    rightChopstick.unlock();
                }
                if (leftChopstick.isHeldByCurrentThread()) {
                    leftChopstick.unlock();
                }
            }
        }
    }

}

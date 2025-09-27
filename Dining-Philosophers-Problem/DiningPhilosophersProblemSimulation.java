import java.util.concurrent.locks.ReentrantLock;

public class DiningPhilosophersProblemSimulation {
    public static void main(String[] args) throws InterruptedException {
        Philosopher philosophers[] = new Philosopher[100];

        ReentrantLock chopsticks[] = new ReentrantLock[philosophers.length];

        for (int i = 0; i < chopsticks.length; i++) {
            chopsticks[i] = new ReentrantLock(true);
        }

        for (int i = 0; i < philosophers.length; i++) {
            philosophers[i] = new Philosopher(String.valueOf(i + 1), chopsticks[i], chopsticks[(i + 1) % philosophers.length]);
        }

        Thread t[] = new Thread[philosophers.length];
        for (int i = 0; i < t.length; i++) {
            t[i] = new Thread(philosophers[i]);
            t[i].start();
        }

        for (int i = 0; i < t.length; i++) {
            t[i].join();
        }

        System.out.println("Dining done");
    }
}
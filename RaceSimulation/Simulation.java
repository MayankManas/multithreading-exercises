import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

public class Simulation {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch finishLatch = new CountDownLatch(10);

        ConcurrentLinkedQueue<String> rankings = new ConcurrentLinkedQueue<>();
        Racer racer[] = new Racer[10];

        for (int i = 0; i < racer.length; i++) {
            racer[i] = new Racer("" + (i + 1), startLatch, finishLatch, rankings);
        }
        Thread t[] = new Thread[racer.length];

        for (int i = 0; i < t.length; i++) {
            t[i] = new Thread(racer[i]);
            t[i].start();
        }
        startLatch.countDown();

        finishLatch.await();

        System.out.println("Race completed!");
        int k = 1;
        while (!rankings.isEmpty()) {
            System.out.println("Player " + rankings.poll() + ", rank : " + k++);
        }

    }
}

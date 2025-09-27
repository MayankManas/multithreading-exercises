import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

public class Racer implements Runnable {
    private final CountDownLatch startLatch;
    private final String playerId;
    private final CountDownLatch finishLatch;
    private ConcurrentLinkedQueue<String> rankings;

    public Racer(String id, CountDownLatch latch, CountDownLatch finishLatch, ConcurrentLinkedQueue<String> rankings) {
        this.startLatch = latch;
        this.playerId = id;
        this.rankings = rankings;
        this.finishLatch = finishLatch;
    }

    public void run() {
        try {
            // wait til countdown
            startLatch.await();
            int distance = 0;
            // start running
            while (distance < 1000) {
                int dis = (int) (Math.random() * 10.0);
                distance += dis;
                if (distance < 1000) {
                    System.out.printf("Player %s at %d metres mark %n", this.playerId, distance);
                } else {
                    rankings.add(playerId);
                    finishLatch.countDown();
                    System.out.printf("Player %s finished the race \n", this.playerId);
                    return;
                }
                int duration = (int) (Math.random() * 100.0);
                System.out.printf("Player %s resting for %dms\n", this.playerId, duration);
                Thread.sleep(duration);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

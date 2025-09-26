import BoundedQueue.MTBoundedQueue;
import java.util.List;

public class Producer<T> implements Runnable {
    private final List<T> list;
    private final int produceInterval;
    private final MTBoundedQueue<T> queue;
    private volatile boolean running = true;

    public Producer(List<T> list, int interval, MTBoundedQueue<T> queue) {
        this.list = list;
        this.produceInterval = interval;
        this.queue = queue;
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        try {
            while (running) {
                for (T item : list) {
                    if (!running) break;
                    queue.enqueue(item);
                    System.out.println("Producer added: " + item);
                    Thread.sleep(produceInterval);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

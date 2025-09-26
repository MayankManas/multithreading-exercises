import BoundedQueue.MTBoundedQueue;

public class Consumer<T> implements Runnable {
    private final MTBoundedQueue<T> queue;
    private volatile boolean running = true;

    public Consumer(MTBoundedQueue<T> queue) {
        this.queue = queue;
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        try {
            while (running) {
                T item = queue.dequeue();
                System.out.println("Consumer consumed: " + item);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

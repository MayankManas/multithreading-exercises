import BoundedQueue.MTBoundedQueue;

import java.util.ArrayList;

public class Driver {
    public static void main(String[] args) throws InterruptedException {
        MTBoundedQueue mtBoundedQueue = new MTBoundedQueue(20);
        ArrayList<Integer> list = new ArrayList<>();
        for(int i = 0; i < 100; i++) {
            list.add(i);
        }
        Producer<Integer> producer = new Producer<>(list, 0, mtBoundedQueue);
        Consumer<Integer> consumer = new Consumer<>(mtBoundedQueue);

        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);

        producerThread.start();
        consumerThread.start();

        // Let it run for 5 seconds
        Thread.sleep(5000);

        producer.stop();
        consumer.stop();

        producerThread.join();
        consumerThread.join();

        System.out.println("Finished gracefully!");

    }
}

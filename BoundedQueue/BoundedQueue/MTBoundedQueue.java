package BoundedQueue;

public class MTBoundedQueue<T> {
    private final T[] arr;
    private final int capacity;
    private int head;
    private int tail;
    private volatile int size;

    public MTBoundedQueue(int capacity) {
        this.capacity = capacity;
        this.head = this.tail = 0;
        this.arr = (T[]) new Object[capacity];
    }

    public synchronized void enqueue(T item) throws InterruptedException {
        while (size == capacity) { // wait for dequeuing
            wait();
        }
        // increment size
        size++;

        // enqueue
        arr[tail++] = item;
        // mod to make it behave as a circular array
        tail %= capacity;

        notifyAll();
    }

    public synchronized T dequeue() throws InterruptedException {
        while (size == 0) { // nothing to dequeue, make the consumer wait
            wait();
        }
        size--;
        T item = arr[head++];
        head %= capacity;

        // notify waiting producers
        notifyAll();
        return item;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getSize() {
        return size;
    }
}
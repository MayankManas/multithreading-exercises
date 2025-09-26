## Problem Statement

* You have a queue with limited capacity (say size = 5).

* Multiple producer threads will generate numbers and put them into the queue.

* Multiple consumer threads will remove numbers and process them.

* If the queue is full, producers must wait.

* If the queue is empty, consumers must wait.


## Producer-Consumer Pattern in Java

This project demonstrates a **thread-safe, generic, and stoppable Producer-Consumer pattern** in Java using a custom bounded queue (`MTBoundedQueue`). It is designed to showcase multithreading concepts with **graceful shutdown** and **type-safe generics**.

---

## Features

- **Generic Bounded Queue** (`MTBoundedQueue<T>`)
    - Supports any type of object.
    - Thread-safe using `synchronized`, `wait()`, and `notifyAll()`.
    - Implements a circular buffer for O(1) enqueue/dequeue operations.

- **Producer**
    - Produces items from a given list at a configurable interval.
    - Can be stopped gracefully using a `stop()` method.

- **Consumer**
    - Continuously consumes items from the queue.
    - Can also be stopped gracefully using a `stop()` method.

- **Graceful Shutdown**
    - Threads can terminate cleanly without forcing abrupt interruption.
    - Uses a `volatile boolean running` flag to control execution.

---


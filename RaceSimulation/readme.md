# Multithreaded Race Simulation – Java

## 📖 Problem Statement
Simulate a race with multiple runners (threads):

- All runners start **simultaneously**.
- Each runner progresses with a random speed (simulated via random distance increments and sleep).
- Record the **winner** and the **order of finishers** (ranking).

This simulation demonstrates **thread coordination** and **synchronization** in Java.

---

## 🛠️ Concepts Used
- **Multithreading**: Each runner runs in its own thread to simulate parallel execution.
- **CountDownLatch**:
    - **Start latch** ensures all runners start at the same time.
    - **Finish latch** allows the main thread to wait until all runners finish.
- **Thread-safe collections**:
    - `ConcurrentLinkedQueue` to store the finish order safely.
- **Randomized execution** to simulate variable speeds.

---
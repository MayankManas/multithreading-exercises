# Dining Philosophers Problem – Java Simulation

## 📖 Problem Statement
Five philosophers sit around a circular table. Each philosopher alternates between **thinking** and **eating**. To eat, a philosopher needs **two chopsticks** (one to the left and one to the right). Chopsticks are shared between neighbors.

### Challenges:
- **Deadlock**: If all philosophers pick up their left chopstick at the same time, no one can proceed.
- **Starvation**: Some philosophers may never get a chance to eat if scheduling is unfair.
- **Fairness**: Ensure that each philosopher eventually gets to eat.

---

## 🛠️ Approach Used
This implementation uses:
- **Threads**: Each philosopher runs as an independent thread.
- **`ReentrantLock` with fairness enabled**: Ensures FIFO order of chopstick access.
- **`tryLock` with timeout**: Prevents deadlock (a philosopher will give up after waiting).
- **Exponential backoff**: If chopsticks aren’t available, the philosopher waits progressively longer before retrying, reducing contention.
- **`finally` block**: Guarantees that locks (chopsticks) are always released, even if an exception occurs.

---

## References
https://en.wikipedia.org/wiki/Dining_philosophers_problem
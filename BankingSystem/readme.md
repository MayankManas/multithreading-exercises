# 🏦 Thread-Safe Bank System Simulation

## 📌 Overview
This project simulates a simple **banking system** with multiple accounts and concurrent money transfers.  
It demonstrates how to make transactions **thread-safe** using `ReentrantLock` and how to avoid **deadlocks** by enforcing a **global lock ordering**.

---

## 🚀 Features
- Multiple accounts with balances.
- Money transfers between accounts in **parallel threads**.
- Use of `ReentrantLock` for safe concurrent updates.
- Deadlock prevention using **consistent lock ordering**.
- `CountDownLatch` to wait until all transfer threads complete.
- Ensures **total system balance remains constant**.

---

## 🧑‍💻 Key Classes

### `Account`
- Holds account ID and balance.
- Provides `ReentrantLock` for safe concurrent access.

### `Bank`
- Contains static `transfer(Account from, Account to, int amount)` method.
- Locks both accounts in a **fixed global order** (based on `System.identityHashCode`) to prevent deadlocks.
- Updates balances safely.

### `BankSimulation`
- Creates two accounts with initial balances.
- Spawns two threads:
    - One transferring money **A → B**
    - One transferring money **B → A**
- Uses a `CountDownLatch` to wait until both threads finish.
- Prints final balances and verifies total balance remains unchanged.

---
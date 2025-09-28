import java.util.concurrent.locks.ReentrantLock;

public class Account {
    private int balance;
    private ReentrantLock lock;
    private String id;

    public String getId() {
        return id;
    }

    public Account(String id, int balance) {
        this.id = id;
        lock = new ReentrantLock(true);
        this.balance = balance;
    }

    public ReentrantLock getLock() {
        return lock;
    }

    public int getBalance() {
        return balance;
    }

    public boolean withdraw(int amount) {
        if (this.balance >= amount) {
            this.balance -= amount;
            return true;
        } else { //insufficient funds
            return false;
        }

    }

    public boolean deposit(int amount) {
        this.balance += amount;
        return true;
    }

    @Override
    public String toString() {
        return "Account[" + id + ", balance=" + balance + "]";
    }
}

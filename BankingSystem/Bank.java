import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Bank {
    static boolean transfer(Account from, Account to, int amount) throws InterruptedException {

        ReentrantLock lock1 = from.getLock();
        ReentrantLock lock2 = to.getLock();

        ReentrantLock firstLock = System.identityHashCode(lock1) < System.identityHashCode(lock2) ? lock1 : lock2;
        ReentrantLock secondLock = firstLock == lock1 ? lock2 : lock1;

        if (firstLock.tryLock(1, TimeUnit.SECONDS)) {
            try {
                if (secondLock.tryLock(1, TimeUnit.SECONDS)) {
                    try {
                        if (from.getBalance() >= amount) {
                            from.withdraw(amount);
                            System.out.printf("Amount %d withdrawn from account : %s\n", amount, from.getId());
                            to.deposit(amount);
                            System.out.printf("Amount %d deposited to account : %s\n", amount, to.getId());
                            return true;
                        } else {
                            System.out.printf("Insufficient funds in account : %s\n", from.getId());
                            return false;
                        }
                    } finally {
                        secondLock.unlock();
                    }
                } else {
                    System.out.println("Couldn't acquire lock on second account");
                    return false;
                }
            } finally {
                firstLock.unlock();
            }
        } else {
            System.out.println("Couldn't acquire lock on first account");
            return false;
        }
    }

}


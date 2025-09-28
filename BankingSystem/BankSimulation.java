import java.util.concurrent.CountDownLatch;

public class BankSimulation {
    public static void main(String[] args) throws InterruptedException {
        Account acc1 = new Account("A", 1000);
        Account acc2 = new Account("B", 1000);

        CountDownLatch latch = new CountDownLatch(2);

        // Thread 1: transfer from A → B
        new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    Bank.transfer(acc1, acc2, 100);
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        }).start();

        // Thread 2: transfer from B → A
        new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    Bank.transfer(acc2, acc1, 50);
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        }).start();

        latch.await();

        System.out.println("Final balances:");
        System.out.println(acc1);
        System.out.println(acc2);
        System.out.println("Total balance = " + (acc1.getBalance() + acc2.getBalance()));
    }
}
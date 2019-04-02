import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/*
    Modified listing 30.6 from Introduction to java programming... Currently there will only be added money while there
    are below 30 in the balance, but there will be added from 1 to 100 due to DepositTask. Withdraw will happen as long
    as there is money enough. also added 1 more of each task in the ThreadPool. To check if it worked even if there was
    two Withdraws awaiting. it did, and they wait for each other. so they wont take so there will be a negative amount.
*/
public class UnderstandLocksAndThreadCOOP {
    private static Account account = new Account();

    public static void main(String[] args) {
        ExecutorService excecute = Executors.newFixedThreadPool(4);
        excecute.execute(new DepositTask());
        excecute.execute(new WithdrawTask());
        excecute.execute(new DepositTask());
        excecute.execute(new WithdrawTask());
    }

    public static class DepositTask implements Runnable{
        @Override
        public void run() {try {
        while (true){
            account.deposit((int)(Math.random()*100)+1);
            Thread.sleep(1000);
        }}catch (InterruptedException ex){ex.printStackTrace();}
        }
    }

    public static class WithdrawTask implements Runnable{
        @Override
        public void run() {try {
            while (true){
                account.withdraw((int)(Math.random()*10)+1);
                Thread.sleep(1000);
            }}catch (InterruptedException ex){ex.printStackTrace();}
        }
    }

    private static class Account {
        private static Lock lock = new ReentrantLock();

        private static Condition newDeposit = lock.newCondition();

        private int balance = 0;

        public int getBalance() {
            return balance;
        }

        public void withdraw(int amount) {
            lock.lock();
            try {
                while (balance < amount) {
                    System.out.println("\t\t\twait for a deposit");
                    newDeposit.await();
                }
                balance -= amount;
                System.out.println("\t\t\tWithdraw " + amount + "\t\t" + getBalance());
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void deposit(int amount) {
            lock.lock();
            try {
                while (balance < 30) {
                    balance += amount;
                    System.out.println("Deposit " + amount + "\t\t\t\t\t" + getBalance());
                    newDeposit.signalAll();
                }
            } finally {
                lock.unlock();
            }
        }
    }
}
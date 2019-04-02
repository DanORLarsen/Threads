public class Chapter30_11 {
    public static Object object1 = new Object();
    public static Object object2 = new Object();
//Simple deadlock, both objects waiting to get the other object so they can do the sleep 100 after.
    public static void main(String[] args) {
        new Thread(new RunnableO1()).start();
        new Thread(new RunnableO2()).start();
    }
}

class RunnableO1 implements Runnable {
    @Override
    public void run() {
        synchronized (Chapter30_11.object2) {
            System.out.println("Runnable1 got object 2");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Runnable1 want objecet1");
            synchronized (Chapter30_11.object1) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
class RunnableO2 implements Runnable {
    @Override
    public void run() {
        synchronized (Chapter30_11.object1) {
            System.out.println("Runnable2 got object 1");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Runnable2 want object 2");
            synchronized (Chapter30_11.object2) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
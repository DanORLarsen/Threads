public class Chapter30_4 {
    private static int number = 0;

    public synchronized void addSync(){
        number+=1;
        System.out.println(number);
    }
    public void add(){
        number+=1;
        System.out.println(number);
    }
}
class RunnableTest implements Runnable{
    private static Chapter30_4 chapter30Assignments = new Chapter30_4();
    private boolean sync = false;

    public RunnableTest(boolean sync){
        this.sync = sync;
    }

    @Override
    public void run() {
        //No error if no loop, because the thread is most likely executed before the new Thread is started.
        //Each thread add one to number 10 times.
        if (!sync)
            for (int i = 0; i <10 ; i++) {
                chapter30Assignments.add();
            }

        else
            for (int i = 0; i <10 ; i++) {
                chapter30Assignments.addSync();
            }

    }

    public static void main(String[] args) {
        //Change to use Synchronized methoed or not.
        boolean sync = true;
        //When sync is false some we miss a few numbers, and the order is not right.
        for (int i = 0; i < 1000; i++) {
            {
                new Thread(new RunnableTest(sync)).start();
            }
        }
    }
}

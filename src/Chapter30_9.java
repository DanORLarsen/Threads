import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Chapter30_9 {
    public static HashSet<Integer> hashSet = new HashSet<>();
public static final int repeat = 5000000;

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.execute(new Thread(new Runnable1()));
        executor.execute(new Thread(new Runnable2()));
    }
}
class Runnable1 implements Runnable{
    @Override
    public void run() {
        try {
        for (int i = 0; i < Chapter30_9.repeat ; i++) {
            Chapter30_9.hashSet.add(i);
                Thread.sleep(1000);
        } } catch (InterruptedException e) {
        e.printStackTrace();
    }
    }
}
class Runnable2 implements Runnable{
    @Override
    public void run() {
        Iterator<Integer> iterator = Chapter30_9.hashSet.iterator();
        try {
            while (iterator.hasNext()){
                Chapter30_9.hashSet.iterator().next();
                Thread.sleep(1000);
            }
        }catch (InterruptedException e){e.printStackTrace();}
        
    }
}
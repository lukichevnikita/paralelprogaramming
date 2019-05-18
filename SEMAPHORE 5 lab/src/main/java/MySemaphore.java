import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MySemaphore extends Semaphore {
    private int permits;
    private int capacity;
    private ReentrantLock lock;
    private Condition condition;

    public MySemaphore(int permits) {
        super(permits);
        this.permits = permits;
        this.capacity = 0;
        this.lock = new ReentrantLock();
        this.condition = lock.newCondition();
    }

    public MySemaphore(int permits, boolean fair) {
        super(permits, fair);
    }

    @Override
    public void acquire() {
        try {
            lock.lock();
            while (capacity >= permits){
                condition.await();
            }
            capacity++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void release(){
        lock.lock();
        try {
            capacity--;
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }


}

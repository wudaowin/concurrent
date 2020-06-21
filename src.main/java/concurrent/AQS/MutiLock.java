package concurrent.AQS;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 自定义同步组件MutiLock，允许同一时刻有capacity个线程获取到锁，超过capacity个线程的访问将被阻塞。
 *
 * @author tr.wang
 */
public class MutiLock {
    private final Sync sync;

    public MutiLock(int capacity) {
        this.sync = new Sync(2);
    }

    //静态内部类，重写tryAcquireShared（）以及tryReleaseShared（）方法
    private static class Sync extends AbstractQueuedSynchronizer {
        public Sync(int count) {
            if (count < 0) {
                throw new IllegalArgumentException("count must be more than 0!");
            }
            super.setState(count);
        }

        public int tryAcquireShared(int reduceCount) {
            for (; ; ) {
                int current = getState();
                int newCount = current - reduceCount;
                if (newCount < 0 || compareAndSetState(current, newCount)) {
                    return newCount;
                }
            }
        }

        public boolean tryReleaseShared(int returnCount) {
            for (; ; ) {
                int current = getState();
                int newCount = current + returnCount;
                if (compareAndSetState(current, newCount)) {
                    return true;
                }
            }
        }

    }

    public void lock() {
        sync.acquireShared(1);
    }

    public void unLock() {
        sync.releaseShared(1);
    }

}

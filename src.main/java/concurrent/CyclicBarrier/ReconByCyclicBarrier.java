package concurrent.CyclicBarrier;

import java.util.concurrent.*;

/**
 * 使用 getPOrders() 和 getDOrders()与check（），save（）并行。
 *
 * @param <T>
 */
public class ReconByCyclicBarrier<T> {
    //由于可能存在多线程同时操作pos/dos队列，所以需要使用线程安全的集合
    private LinkedBlockingDeque<T> pos;
    private LinkedBlockingDeque<T> dos;

    // 创建 2 个线程的线程池
    Executor executor =
            Executors.newFixedThreadPool(2);

    // 执行回调的线程池
    Executor executorBack =
            Executors.newFixedThreadPool(1);
    final CyclicBarrier barrier =
            new CyclicBarrier(2, () -> {
                executorBack.execute(this::check);
            });

    //回调函数。注意：CyclicBarrier的回调函数执行在一个回合里最后执行await()的线程上，而且同步调用回调函数check()，
    // 调用完check之后，才会开始第二回合。所以check如果不另开一线程异步执行，就起不到性能优化的作用了。
    void check() {
        T p = pos.pop();
        T d = dos.pop();
        // 执行对账操作
        T diff = check(p, d);
        // 差异写入差异库
        save(diff);
    }

    private void doRecon() throws InterruptedException {
        executor.execute(() -> {
            while (!isPosDone()) {
                //每次获取1000条
                for (int i = 0; i < 1000; i++) {
                    //获取待对账的订单
                    pos.add(getPOrder());
                    if (isPosDone()) {
                        break;
                    }
                }
                // 等待
                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        });

        executor.execute(() -> {
            while (!isDosDone()) {
                //每次获取1000条
                for (int i = 0; i < 1000; i++) {
                    //获取待对账的运单
                    dos.add(getDOrder());
                    if (isDosDone()) {
                        break;
                    }
                }
                // 等待
                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 判断待对账的订单是否全部获取
     *
     * @return
     */
    private boolean isPosDone() {
        System.out.println("判断待对账的订单是否全部获取");
        return true;
    }

    /**
     * 判断待对账的派送单单是否全部获取
     *
     * @return
     */
    private boolean isDosDone() {
        System.out.println("判断待对账的派送单单是否全部获取");
        return true;
    }

    private T getPOrder() {
        T t = null;
        System.out.println("获取未对账订单");
        return t;
    }

    private T getDOrder() {
        T t = null;
        System.out.println("获取未对账订单");
        return t;
    }

    private T check(T p, T d) {
        T t = null;
        System.out.println("对账，找出差异订单");
        return t;
    }

    private void save(T diff) {
        System.out.println("将差异订单保存至差异库中");
    }
}

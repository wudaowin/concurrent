package concurrent.CyclicBarrier;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 通过CountDownLaunch实现获取未对账订单以及派送单的并行，优化对账效率。
 *
 * @param <T>
 */
public class ReconByCountDownLaunch<T> {
    private List<T> pos = new LinkedList<>();
    private List<T> dos = new LinkedList<>();
    private List<T> diff = new LinkedList<>();
    // 创建 2 个线程的线程池
    Executor executor =
            Executors.newFixedThreadPool(2);

    private void doRecon() throws InterruptedException {
        while (!isDone()) {
            // 计数器初始化为 2
            CountDownLatch latch =
                    new CountDownLatch(2);
            // 查询未对账订单
            executor.execute(() -> {
                pos = getPOrders();
                latch.countDown();
            });
            // 查询派送单
            executor.execute(() -> {
                dos = getDOrders();
                latch.countDown();
            });

            // 等待两个查询操作结束
            latch.await();

            // 执行对账操作
            diff = check(pos, dos);
            // 差异写入差异库
            save(diff);
        }
    }


    /**
     * 判断对账时否结束
     *
     * @return
     */
    private boolean isDone() {
        System.out.println("判断是否完成对账");
        return true;
    }

    private List<T> getPOrders() {
        System.out.println("获取未对账订单");
        return new LinkedList<>();
    }

    private List<T> getDOrders() {
        System.out.println("获取未对账订单");
        return new LinkedList<>();
    }

    private List<T> check(List<T> pos, List<T> dos) {
        System.out.println("对账，找出差异订单");
        return new LinkedList<>();
    }

    private void save(List<T> diff) {
        System.out.println("将差异订单保存至差异库中");
    }
}

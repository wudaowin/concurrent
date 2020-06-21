package concurrent.AQS;

/**
 * TwinsLock测试，定义MutiLock的容量为2，可以看出线程成对的输出，一次只能有两个线程获取到锁。
 */
public class MutiLockTest {
    public static void main(String[] args) throws InterruptedException {
        final MutiLock mutiLock = new MutiLock(2);
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                while (true) {
                    mutiLock.lock();
                    try {
                        Thread.sleep(1000);
                        System.out.println("当前线程名：" + Thread.currentThread().getName());
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        mutiLock.unLock();
                    }
                }
            });

            thread.setDaemon(true);
            thread.start();
        }
        //每隔一秒换一行
        for (int i = 0; i < 10; i++) {
            Thread.sleep(1000);
            System.out.println();
        }
    }
}

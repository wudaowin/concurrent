package concurrent.CountDownLaunch;

import java.util.concurrent.CountDownLatch;

/**
 * @Description 抽象类
 * @Author tr.wang
 * @Date 2019/11/28 19:34
 * @Version 1.0
 */
public abstract class AbstractHealthCheck implements Runnable{
    private CountDownLatch latch;
    private String serviceName;
    private boolean serviceUp;

    public AbstractHealthCheck(String serviceName, CountDownLatch latch)
    {
        super();
        this.latch = latch;
        this.serviceName = serviceName;
        this.serviceUp = false;
    }

    @Override
    public void run() {
        try {
            verifyService();
            serviceUp = true;
        } catch (Throwable t) {
            t.printStackTrace(System.err);
            serviceUp = false;
        } finally {
            if(latch != null) {
                latch.countDown();
            }
        }
    }

    public String getServiceName() {
        return serviceName;
    }

    public boolean isServiceUp() {
        return serviceUp;
    }

    public abstract void verifyService();
}

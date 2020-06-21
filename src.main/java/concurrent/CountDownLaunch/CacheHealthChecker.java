package concurrent.CountDownLaunch;

/**
 * @Description TODO
 * @Author tr.wang
 * @Date 2019/11/28 19:50
 * @Version 1.0
 */
import java.util.concurrent.CountDownLatch;

public class CacheHealthChecker extends AbstractHealthCheck
{
    public CacheHealthChecker (CountDownLatch latch)
    {
        super("Cache Service", latch);
    }

    @Override
    public void verifyService()
    {
        System.out.println("Checking " + this.getServiceName());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(this.getServiceName() + " is UP");
    }
}

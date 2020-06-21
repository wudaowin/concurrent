package concurrent.CountDownLaunch;

/**
 * @Description 网络检查
 * @Author tr.wang
 * @Date 2019/11/28 19:47
 * @Version 1.0
 */
import java.util.concurrent.CountDownLatch;

public class NetworkHealthChecker extends AbstractHealthCheck
{
    public NetworkHealthChecker (CountDownLatch latch)
    {
        super("Network Service", latch);
    }

    @Override
    public void verifyService()
    {
        System.out.println("Checking " + this.getServiceName());
        try
        {
            Thread.sleep(7000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        System.out.println(this.getServiceName() + " is UP");
    }
}
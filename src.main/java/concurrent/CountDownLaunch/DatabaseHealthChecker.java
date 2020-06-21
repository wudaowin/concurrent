package concurrent.CountDownLaunch;

/**
 * @Description 数据库检查
 * @Author tr.wang
 * @Date 2019/11/28 19:50
 * @Version 1.0
 */
import java.util.concurrent.CountDownLatch;

public class DatabaseHealthChecker extends AbstractHealthCheck
{
    public DatabaseHealthChecker (CountDownLatch latch)
    {
        super("Database Service", latch);
    }

    @Override
    public void verifyService()
    {
        System.out.println("Checking " + this.getServiceName());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(this.getServiceName() + " is UP");
    }
}

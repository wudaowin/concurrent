import concurrent.CountDownLaunch.AbstractHealthCheck;
import concurrent.CountDownLaunch.CacheHealthChecker;
import concurrent.CountDownLaunch.DatabaseHealthChecker;
import concurrent.CountDownLaunch.NetworkHealthChecker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @Description TODO
 * @Author tr.wang
 * @Date 2019/11/29 10:10
 * @Version 1.0
 */
public class CountDownLanchTest {
    private static List<AbstractHealthCheck> services;
    private static CountDownLatch latch;
    public static boolean checkExternalServices() throws Exception
    {
        latch = new CountDownLatch(3);
        services = new ArrayList<AbstractHealthCheck>();
        services.add(new NetworkHealthChecker(latch));
        services.add(new CacheHealthChecker(latch));
        services.add(new DatabaseHealthChecker(latch));

        Executor executor = Executors.newFixedThreadPool(services.size());

        for(final AbstractHealthCheck v : services)
        {
            executor.execute(v);
        }

        latch.await();

        for(final AbstractHealthCheck v : services)
        {
            if( ! v.isServiceUp())
            {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args)
    {
        boolean result = false;
        try {
            result = checkExternalServices();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("External services validation completed !! Result was :: "+ result);
    }
}

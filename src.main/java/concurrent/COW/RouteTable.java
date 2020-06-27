package concurrent.COW;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 使用cow实现一个路由中心的注册表
 */
public class RouteTable {
    // Key 是接口名，Value 是路由集合
    private final ConcurrentHashMap<String, CopyOnWriteArraySet<Router>> routingTable = new ConcurrentHashMap<>();

    // 获取路由
    public Set<Router> get(String interfaceName) {
        return routingTable.get(interfaceName);
    }

    // 增加路由
    public void add(Router router) {
        CopyOnWriteArraySet<Router> set = routingTable.computeIfAbsent(router.getInterfaceName(),
                interfaceName -> new CopyOnWriteArraySet<>());
        set.add(router);
    }

    // 删除路由
    public void remove(Router router) {
        CopyOnWriteArraySet<Router> set = routingTable.get(router.getInterfaceName());
        if (set != null) {
            set.remove(router);
        }
    }
}

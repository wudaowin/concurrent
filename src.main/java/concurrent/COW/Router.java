package concurrent.COW;

import java.util.Objects;

/**
 * 路由信息
 */
public class Router {
    private final String ip;
    private final Integer port;
    private final String interfaceName;

    public Router(String ip, Integer port, String interfaceName) {
        this.ip = ip;
        this.port = port;
        this.interfaceName = interfaceName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Router router = (Router) o;
        return Objects.equals(ip, router.ip) &&
                Objects.equals(port, router.port) &&
                Objects.equals(interfaceName, router.interfaceName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip, port, interfaceName);
    }

    public String getInterfaceName() {
        return interfaceName;
    }
}
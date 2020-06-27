package concurrent.ReadWriteLock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 使用读写锁实现一个简单的本地缓存
 *
 * @param <K>
 * @param <V>
 */
public class Cache<K, V> {
    private final Map<K, V> hashMap =
            new HashMap<>();
    private final ReadWriteLock rwl =
            new ReentrantReadWriteLock();
    private final Lock readLock = rwl.readLock();
    private final Lock writeLock = rwl.writeLock();

    /**
     * 获取key对应的value值
     *
     * @param key
     * @return
     */
    public V get(K key) {
        V value = null;
        // 读缓存
        readLock.lock();
        try {
            value = hashMap.get(key);
            if (value != null) {
                return value;
            }
        } finally {
            readLock.unlock();
        }


        readLock.unlock();
        // 缓存中不存在，查询数据库
        writeLock.lock();
        try {
            // 再次验证
            // 其他线程可能已经查询过数据库
            value = hashMap.get(key);
            if (value == null) {
                // 查询数据库
                value = getDateFromDb();
                hashMap.put(key, value);
            }
            // 释放写锁前，降级为读锁。
            // 降级是可以的。
            // 主要是为了保证数据的可见性，如果当前线程不获取读锁，直接释放写锁的话，假设此刻有另一个线程B获取了写锁并修改了数据，
            // 那么当前线程无法感知到线程B的数据更新。如果线程获取了读锁，那么线程B将会阻塞，知道当前线程获取了数据并释放读锁之后，
            // 线程B才能获取到写锁并更新数据
            readLock.lock();
        } finally {
            writeLock.unlock();
        }

        try {
            return value;
        } finally {
            readLock.unlock();
        }
    }

    /**
     * 设置key对应的value值，并返回旧的value
     *
     * @param key
     * @param value
     * @return
     */
    public V put(K key, V value) {
        writeLock.lock();
        try {
            return hashMap.put(key, value);
        } finally {
            writeLock.unlock();
        }
    }

    private V getDateFromDb() {
        V value = null;
        System.out.println("查询数据库");
        return value;
    }
}


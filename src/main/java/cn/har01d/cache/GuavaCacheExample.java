package cn.har01d.cache;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class GuavaCacheExample {
    public static void main(String[] args) {
        log("Started");
        Cache<String, Connection> cache = CacheBuilder.newBuilder()
                .expireAfterAccess(5, TimeUnit.MINUTES)
                .removalListener(new MyRemovalListener())
                .build();

        cache.put("key0", new Connection(123));
        log("Cache size: " + cache.size());

        Executors.newSingleThreadExecutor().submit(() -> {
            Connection connection = cache.getIfPresent("key0");
            for (int i = 0; i < 100; i++) {
                log("Connection: " + connection);
                if (connection == null || connection.getId() == 0) {
                    break;
                }
                sleep(30);
            }
            log("Cache get: key0 -> " + cache.getIfPresent("key0"));
            log("Cache size: " + cache.size());
        });

        var threadpool = new ThreadPoolExecutor(10, 50, 1, TimeUnit.MINUTES, new ArrayBlockingQueue<>(1000));
        for (int i = 0; i < 1000; i++) {
            threadpool.submit(() -> {
                int id = ThreadLocalRandom.current().nextInt(100) + 1;
                String key = "key" + id;
                try {
                    Connection connection = cache.get(key, () -> new Connection(id));
                    log("New connection: " + connection);
                } catch (ExecutionException e) {
                    log(e.getMessage());
                }
                sleep(id);
            });

            log("Cache size: " + cache.size());
        }

//        sleep(10);
//        for (int i = 0; i < 3; i++) {
//            log("Cache get: key2 -> " + cache.getIfPresent("key2"));
//            sleep(20);
//        }
        //cache.cleanUp();

//        log("Cache get: key0 -> " + cache.getIfPresent("key0"));
//        log("Cache size: " + cache.size());
    }

    private static void sleep(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void log(String msg) {
        System.out.println(Instant.now().truncatedTo(ChronoUnit.MILLIS) + " [" + Thread.currentThread().getName() + "] " + msg);
    }
}

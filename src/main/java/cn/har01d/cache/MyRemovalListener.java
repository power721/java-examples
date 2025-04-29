package cn.har01d.cache;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

public class MyRemovalListener implements RemovalListener<String, Connection> {
    @Override
    public void onRemoval(RemovalNotification<String, Connection> removalNotification) {
        try {
            System.out.println(Instant.now().truncatedTo(ChronoUnit.MILLIS) + " [" + Thread.currentThread().getName()
                    + "] Removed entry: " + removalNotification.getKey() + " -> " + removalNotification.getValue()
                    + ", cause: " + removalNotification.getCause());
            removalNotification.getValue().setId(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

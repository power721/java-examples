package cn.har01d;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.time.Instant;

public class DownloadExample {
    public static void main(String[] args) throws Exception {
        RangeDownloader downloader = new RangeDownloader.Builder()
                .concurrency(10)
                .partSize(4 * 1024 * 1024)
                .build();

        RangeDownloader.DownloadParams params = new RangeDownloader.DownloadParams(
                "http://10.121.235.6/test/numbers.txt",
                new RangeDownloader.Range(0, -1),
                -1
        );

        params = new RangeDownloader.DownloadParams("http://10.121.235.6/test/ecdm-kickstart-15.4.0-1-20250316.070319-157.ova");

        long start = System.currentTimeMillis();
        try (InputStream in = downloader.download(params);
             FileOutputStream out = new FileOutputStream("ecdm-kickstart.ova")) {

            byte[] buffer = new byte[32 * 1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
        }
        System.out.println("Time: " + (System.currentTimeMillis() - start) + "ms");
    }
}

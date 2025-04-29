package cn.har01d;

import java.net.URI;
import java.net.URL;
import java.security.cert.Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

public class CertificateTest {
    public static void main(String[] args) {
        fetchSSLCertificate("https://10.246.155.101:443");
    }

    private static void fetchSSLCertificate(String httpsUrl) {
        try {
            URL url = new URL(httpsUrl);
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, null, new java.security.SecureRandom());
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setSSLSocketFactory(sslContext.getSocketFactory());
            connection.connect();

            Certificate[] certificates = connection.getServerCertificates();
            for (Certificate certificate : certificates) {
                System.out.println("Certificate Details:");
                System.out.println("-------------------");
                System.out.println("certificate: " + certificate);
                System.out.println("Type: " + certificate.getType());
                System.out.println("Public Key: " + certificate.getPublicKey());
                System.out.println("Encoded: " + certificate.getEncoded());
                System.out.println("Principal: " + connection.getPeerPrincipal().getName());
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

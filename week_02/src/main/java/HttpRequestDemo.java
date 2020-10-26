import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @ClassName HttpRequestDemo
 * @Description http客户端demo
 * @Author Shaldon
 * @Date 2020/10/25 10:25
 * @Version 1.0
 */
public class HttpRequestDemo {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger("demo");
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpGet httpGet = new HttpGet("http://localhost:8801/");
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpGet);
            System.out.println("http statusLine = " + httpResponse.getStatusLine());
            if (200 == httpResponse.getStatusLine().getStatusCode()){
                HttpEntity response = httpResponse.getEntity();
                if (response != null){
                    logger.info("response content = [" + EntityUtils.toString(response) +"]");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (httpResponse != null){
                    httpResponse.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}

package io.github.kimmking.gateway.outbound.httpclient;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.rmi.runtime.Log;

/**
 * @ClassName HttpOutboundHandler
 * @Description TODO
 * @Author Shaldon
 * @Date 2020/11/3 16:06
 * @Version 1.0
 **/
public class HttpOutboundHandler {
  private static Logger logger = LoggerFactory.getLogger(HttpOutboundHandler.class);
  private CloseableHttpClient httpClient;
  private String backendUrl;

  public HttpOutboundHandler(String backendUrl) {
    this.backendUrl = backendUrl;
    httpClient = HttpClients.createDefault();
  }

  public void handle(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx){
    RequestConfig config = RequestConfig.custom().setSocketTimeout(5000)
        .setConnectTimeout(5000)
        .setConnectionRequestTimeout(5000)
        .build();
    final String url = this.backendUrl + fullRequest.uri();
    final HttpGet httpGet = new HttpGet(url);
    httpGet.setConfig(config);
    try {
      CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
      logger.info("httpStatusLine={}", httpResponse.getStatusLine().toString());
      logger.info(httpResponse.getStatusLine().toString());
      handleResponse(fullRequest,ctx,httpResponse);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  private void handleResponse(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, final HttpResponse endpointResponse) throws Exception {
    FullHttpResponse response = null;
    try {
//            String value = "hello,kimmking";
//            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(value.getBytes("UTF-8")));
//            response.headers().set("Content-Type", "application/json");
//            response.headers().setInt("Content-Length", response.content().readableBytes());


      byte[] body = EntityUtils.toByteArray(endpointResponse.getEntity());
//            System.out.println(new String(body));
//            System.out.println(body.length);
      response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(body));

      response = new DefaultFullHttpResponse(HTTP_1_1,
          HttpResponseStatus.valueOf(endpointResponse.getStatusLine().getStatusCode()),
          Unpooled.wrappedBuffer(body));
      response.headers().set("Content-Type", "application/json");
      response.headers().setInt("Content-Length", Integer.parseInt(endpointResponse.getFirstHeader("Content-Length").getValue()));

//            for (Header e : endpointResponse.getAllHeaders()) {
//                //response.headers().set(e.getName(),e.getValue());
//                System.out.println(e.getName() + " => " + e.getValue());
//            }

    } catch (Exception e) {
      e.printStackTrace();
      response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
      exceptionCaught(ctx, e);
    } finally {
      if (fullRequest != null) {
        if (!HttpUtil.isKeepAlive(fullRequest)) {
          ctx.write(response).addListener(ChannelFutureListener.CLOSE);
        } else {
          //response.headers().set(CONNECTION, KEEP_ALIVE);
          ctx.write(response);
        }
      }
      ctx.flush();
      //ctx.close();
    }

  }

  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    cause.printStackTrace();
    ctx.close();
  }

}

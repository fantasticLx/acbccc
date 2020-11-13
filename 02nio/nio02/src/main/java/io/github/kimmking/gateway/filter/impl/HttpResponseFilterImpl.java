package io.github.kimmking.gateway.filter.impl;

import io.github.kimmking.gateway.filter.HttpResponseFilter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpResponse;

/**
 * @ClassName HttpResponseFilterImpl
 * @Description 设置响应头 Content-Type
 * @Author Shaldon
 * @Date 2020/11/4 11:37
 * @Version 1.0
 **/
public class HttpResponseFilterImpl implements HttpResponseFilter {
  private String filterName;
  // http headers key
  private final String contentType = "Content-Type";

  public HttpResponseFilterImpl(String filterName) {
    this.filterName = filterName;
  }

  @Override
  public void filter(FullHttpResponse httpResponse, ChannelHandlerContext cxt) {
    switch (filterName){
      case "json" :
        httpResponse.headers().set(contentType, "application/json");
        break;
      case "html" :
        httpResponse.headers().set(contentType, "text/html");
        break;
      case "xml" :
        httpResponse.headers().set(contentType, "text/xml");
        break;
    }


  }
}

package io.github.kimmking.gateway.filter.impl;

import io.github.kimmking.gateway.filter.HttpRequestFilter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @ClassName HttpRequestFilterImpl
 * @Description TODO
 * @Author Shaldon
 * @Date 2020/11/3 15:53
 * @Version 1.0
 **/
public class HttpRequestFilterImpl implements HttpRequestFilter {

  private String filterName;

  public HttpRequestFilterImpl(String filterName) {
    this.filterName = filterName;
  }

  @Override
  public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
    fullRequest.headers().add("nio", filterName);
  }
}

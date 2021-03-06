package io.github.kimmking.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpResponse;

public interface HttpResponseFilter {
  void filter(FullHttpResponse httpResponse, ChannelHandlerContext cxt);
}

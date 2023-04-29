package io.lonmstalker.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class LogHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) {
        String response;
        HttpResponseStatus status;

        if (Objects.equals(msg.uri(), "/")) {
            response = "Found";
            status = HttpResponseStatus.OK;
        } else {
            response = "Not Found";
            status = HttpResponseStatus.NOT_FOUND;
        }

        this.sendResponse(ctx, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, Unpooled.copiedBuffer(response, StandardCharsets.UTF_8)), response.length());
    }

    private void sendResponse(ChannelHandlerContext ctx, FullHttpResponse rp, int length) {
        rp.headers().set(HttpHeaderNames.CONTENT_LENGTH, length);
        ctx.writeAndFlush(rp).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
    }
}

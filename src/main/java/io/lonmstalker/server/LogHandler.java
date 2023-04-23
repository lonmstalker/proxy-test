package io.lonmstalker.server;

import io.lonmstalker.data.LoggedResponse;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class LogHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(final ChannelHandlerContext ctx, final Object msg) {
        ctx
                .writeAndFlush(new LoggedResponse("logged"))
                .addListener(ChannelFutureListener.CLOSE);
        System.err.println("catch data");
    }
}

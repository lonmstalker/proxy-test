package io.lonmstalker.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

public class NettyHandler extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(final SocketChannel socketChannel) {
        socketChannel.pipeline().addLast("codes", new HttpServerCodec());
        socketChannel.pipeline().addLast("encoder", new HttpObjectAggregator(1024 * 10));
        socketChannel.pipeline().addLast("handler", new LogHandler());
    }
}

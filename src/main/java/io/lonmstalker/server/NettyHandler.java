package io.lonmstalker.server;

import io.lonmstalker.serde.RpDecoder;
import io.lonmstalker.serde.RpEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class NettyHandler extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(final SocketChannel socketChannel) {
        socketChannel.pipeline()
                .addLast(
                        new RpDecoder(),
                        new RpEncoder(),
                        new LogHandler()
                );
    }
}

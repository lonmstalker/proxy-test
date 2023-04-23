package io.lonmstalker.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.kqueue.KQueue;
import io.netty.channel.kqueue.KQueueEventLoopGroup;
import io.netty.channel.kqueue.KQueueServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.internal.SystemPropertyUtil;

public class NettyServer {
    private final int port;

    public NettyServer() {
        this.port = SystemPropertyUtil.getInt("netty.server.port", 8080);
    }

    public void run() throws InterruptedException {
        final var bossLoopGroup = this.createLoopGroup();
        final var workerLoopGroup = this.createLoopGroup();

        try {
            final var bootstrap = new ServerBootstrap()
                    .group(bossLoopGroup, workerLoopGroup)
                    .channel(this.getChannelClass())
                    .childHandler(new NettyHandler())
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            System.err.println("Started app");

            bootstrap.bind(this.port)
                    .sync()
                    .channel()
                    .closeFuture()
                    .sync();
        } finally {
            workerLoopGroup.shutdownGracefully();
            bossLoopGroup.shutdownGracefully();
        }
    }

    private EventLoopGroup createLoopGroup() {
        if (Epoll.isAvailable()) {
            return new EpollEventLoopGroup();
        }
        if (KQueue.isAvailable()) {
            return new KQueueEventLoopGroup();
        }
        return new NioEventLoopGroup();
    }

    private Class<? extends ServerChannel> getChannelClass() {
        if (Epoll.isAvailable()) {
            return EpollServerSocketChannel.class;
        }
        if (KQueue.isAvailable()) {
            return KQueueServerSocketChannel.class;
        }
        return NioServerSocketChannel.class;
    }
}

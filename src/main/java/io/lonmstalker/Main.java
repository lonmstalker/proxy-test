package io.lonmstalker;

import io.lonmstalker.server.NettyServer;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        new NettyServer().run();
    }
}
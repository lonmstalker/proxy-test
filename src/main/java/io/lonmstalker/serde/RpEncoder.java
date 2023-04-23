package io.lonmstalker.serde;

import io.lonmstalker.data.LoggedResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.StandardCharsets;

public class RpEncoder extends MessageToByteEncoder<LoggedResponse> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, LoggedResponse loggedResponse, ByteBuf bf) {
        bf.writeCharSequence(loggedResponse.response(), StandardCharsets.UTF_8);
    }
}

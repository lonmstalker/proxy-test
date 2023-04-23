package io.lonmstalker.serde;

import io.lonmstalker.data.LoggedResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class RpDecoder extends ReplayingDecoder<LoggedResponse> {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf bf, List<Object> list) throws Exception {
        if (bf.isReadable()) {
            list.add(new LoggedResponse(bf.toString(0, bf.readableBytes(), StandardCharsets.UTF_8)));
        }
    }
}

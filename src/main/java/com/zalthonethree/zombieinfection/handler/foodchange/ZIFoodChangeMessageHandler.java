package com.zalthonethree.zombieinfection.handler.foodchange;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.minecraftforge.fml.client.FMLClientHandler;

public class ZIFoodChangeMessageHandler extends SimpleChannelInboundHandler<ZIFoodChangeMessage> {
	@Override protected void channelRead0(ChannelHandlerContext ctx, ZIFoodChangeMessage msg) throws Exception {
		FMLClientHandler.instance().getClientPlayerEntity().getFoodStats().setFoodLevel(msg.foodLevelChange);
		FMLClientHandler.instance().getClientPlayerEntity().getFoodStats().setFoodSaturationLevel(msg.saturationChange);
	}
}
package com.zalthonethree.zombieinfection.handler;

import io.netty.channel.ChannelFutureListener;

import java.util.EnumMap;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;

import com.zalthonethree.zombieinfection.handler.foodchange.ZIFoodChangeMessage;
import com.zalthonethree.zombieinfection.handler.foodchange.ZIFoodChangeMessageHandler;
import com.zalthonethree.zombieinfection.handler.timeinfected.ZITimeInfectedMessage;
import com.zalthonethree.zombieinfection.handler.timeinfected.ZITimeInfectedMessageHandler;
import com.zalthonethree.zombieinfection.utility.Utilities;

import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.FMLOutboundHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public enum PacketHandler /*extends EntityDragon*/ {
	INSTANCE;
	
	private EnumMap<Side, FMLEmbeddedChannel> channels;
	
	private PacketHandler() {
		this.channels = NetworkRegistry.INSTANCE.newChannel("ZombieInfection", new ZombieInfectionCodec());
		if (Utilities.isClientSide()) addClientHandler();
		if (Utilities.isServerSide()) addServerHandler();
	}
	
	@SideOnly(Side.CLIENT) private void addClientHandler() {
		FMLEmbeddedChannel clientChannel = this.channels.get(Side.CLIENT);
		
		String codecName = clientChannel.findChannelHandlerNameForType(ZombieInfectionCodec.class);
		clientChannel.pipeline().addAfter(codecName, "ZITimeInfectedHandler", new ZITimeInfectedMessageHandler());
		clientChannel.pipeline().addAfter(codecName, "ZIFoodChangeHandler", new ZIFoodChangeMessageHandler());
	}
	
	@SideOnly(Side.SERVER) private void addServerHandler() {
		// FMLEmbeddedChannel serverChannel = this.channels.get(Side.SERVER);
		
		// String codecName = serverChannel.findChannelHandlerNameForType(ZombieInfectionCodec.class);
		// Add any messages that the server gets from the client. Shouldn't really be needed. Uncomment lines above if this is needed.
	}
	
	public static Packet getTimeInfectedUpdatePacket(int Seconds) {
		ZITimeInfectedMessage msg = new ZITimeInfectedMessage();
		msg.index = 0;
		msg.secondsInfected = Seconds;
		
		return INSTANCE.channels.get(Side.SERVER).generatePacketFrom(msg);
	}
	
	public static Packet getFoodChangePacket(int food, float saturation) {
		ZIFoodChangeMessage msg = new ZIFoodChangeMessage();
		msg.index = 1;
		msg.foodLevelChange = food;
		msg.saturationChange = saturation;
		
		return INSTANCE.channels.get(Side.SERVER).generatePacketFrom(msg);
	}
	
	public void sendTo(Packet message, EntityPlayerMP player) {
		this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
		this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(player);
		this.channels.get(Side.SERVER).writeAndFlush(message);
	}
	
	public void sendToAll(Packet message) {
		this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALL);
		this.channels.get(Side.SERVER).writeAndFlush(message);
	}
	
	public void sendToAllAround(Packet message, NetworkRegistry.TargetPoint point) {
		this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALLAROUNDPOINT);
		this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(point);
		this.channels.get(Side.SERVER).writeAndFlush(message);
	}
	
	public void sendToServer(Packet message) {
		this.channels.get(Side.CLIENT).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
		this.channels.get(Side.CLIENT).writeAndFlush(message).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
	}
}
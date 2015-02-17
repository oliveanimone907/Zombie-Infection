package com.zalthonethree.zombieinfection.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelChicken;
import net.minecraft.client.model.ModelCow;
import net.minecraft.client.model.ModelPig;
import net.minecraftforge.common.MinecraftForge;

import com.zalthonethree.zombieinfection.client.gui.GuiEyeInfection;
import com.zalthonethree.zombieinfection.entity.*;
import com.zalthonethree.zombieinfection.event.InfectedPlayerTooltipEncryptEvent;
import com.zalthonethree.zombieinfection.model.*;
import com.zalthonethree.zombieinfection.render.*;
import com.zalthonethree.zombieinfection.updatechecker.UpdateChecker;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;

public class ClientProxy extends CommonProxy/*, EntityDragon*/ {
	private boolean encrytionRegistered = false;
	private boolean updateEventRegistered = false;
	
	@Override public void init() {
		super.init();
		MinecraftForge.EVENT_BUS.register(new GuiEyeInfection(Minecraft.getMinecraft()));
		if (!updateEventRegistered) {
			MinecraftForge.EVENT_BUS.register(new UpdateChecker());
			FMLCommonHandler.instance().bus().register(new UpdateChecker());
			updateEventRegistered = true;
		}
		if (!encrytionRegistered) MinecraftForge.EVENT_BUS.register(new InfectedPlayerTooltipEncryptEvent());
		encrytionRegistered = true;
	}
	
	public void registerRenderInformation(){}
	
	public void registerRenderers(){
		float shadowSize = 0.5F;
		
		RenderingRegistry.registerEntityRenderingHandler(EntityZombieCow.class, new RenderZombieCow(new ModelCow(), shadowSize));
		
		RenderingRegistry.registerEntityRenderingHandler(EntityZombiePig.class, new RenderZombiePig(new ModelPig(), shadowSize));
		
		RenderingRegistry.registerEntityRenderingHandler(EntityZombieChicken.class, new RenderZombieChicken(new ModelChicken(), shadowSize));
		
		RenderingRegistry.registerEntityRenderingHandler(EntityZombieSheep.class, new RenderZombieSheep(new ModelZombieSheep(), shadowSize));
		}
}
package com.zalthonethree.zombieinfection.client.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

import com.zalthonethree.zombieinfection.Reference;
import com.zalthonethree.zombieinfection.utility.TimeInfectedTrackingClient;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class GuiEyeInfection extends Gui/*, EntityDragon*/ {
	private Minecraft minecraftInstance;
	private ResourceLocation uncracked = new ResourceLocation(Reference.MOD_ID.toLowerCase(), "textures/gui/eyeinfection.png");
	private ResourceLocation cracked = new ResourceLocation(Reference.MOD_ID.toLowerCase(), "textures/gui/eyeinfectioncracked.png");
	
	public GuiEyeInfection(Minecraft MC) {
		super();
		minecraftInstance = MC;
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL) public void onRender(RenderGameOverlayEvent.Pre event) {
		int timeInfected = TimeInfectedTrackingClient.getSecondsInfected();
		if (event.isCanceled() || event.type != ElementType.HOTBAR || timeInfected < 60) return;
		ScaledResolution sr = new ScaledResolution(minecraftInstance, minecraftInstance.displayWidth, minecraftInstance.displayHeight);
		
		GL11.glPushAttrib( GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_ENABLE_BIT);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDepthMask(false);
		minecraftInstance.renderEngine.bindTexture(uncracked);
		drawModalRectWithCustomSizedTexture(0, 0, 0, 0, sr.getScaledWidth(), sr.getScaledHeight(), sr.getScaledWidth(), sr.getScaledHeight());
		if (timeInfected >= 120) {
			minecraftInstance.renderEngine.bindTexture(cracked);
			drawModalRectWithCustomSizedTexture(0, 0, 0, 0, sr.getScaledWidth(), sr.getScaledHeight(), sr.getScaledWidth(), sr.getScaledHeight());
		}
		GL11.glPopAttrib();
	}
}
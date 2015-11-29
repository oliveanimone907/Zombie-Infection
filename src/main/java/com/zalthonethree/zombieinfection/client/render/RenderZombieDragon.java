package com.zalthonethree.zombieinfection.client.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelDragon;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.zalthonethree.zombieinfection.Reference;
import com.zalthonethree.zombieinfection.entity.zend.EntityZombieDragon;
import com.zalthonethree.zombieinfection.utility.LogHelper;

@SideOnly(Side.CLIENT) public class RenderZombieDragon extends RenderLiving {
	private static final ResourceLocation eggTexture = new ResourceLocation(Reference.MOD_ID.toLowerCase(), "textures/entity/dragonegg.png");
	private static final ResourceLocation dragonTexture = new ResourceLocation(Reference.MOD_ID.toLowerCase(), "textures/entity/zombiedragon.png");
	protected ModelDragon modelDragon;
	protected ModelBase modelEgg;
	
	public RenderZombieDragon(RenderManager manager, ModelDragon dragon, ModelBase egg, float shadow) {
		super(manager, egg, shadow);
		this.modelDragon = dragon;
		this.modelEgg = egg;
	}
	
	@Override protected ResourceLocation getEntityTexture(Entity entity) {
		if (!(entity instanceof EntityZombieDragon)) return null;
		EntityZombieDragon zombieDragon = (EntityZombieDragon) entity;
		return zombieDragon.isEgg() ? eggTexture : dragonTexture;
	}
	
	@Override public void doRender(EntityLiving entity, double x, double y, double z, float entityYaw, float partialTicks) {
		this.doRender((EntityZombieDragon) entity, x, y, z, entityYaw, partialTicks);
	}
	
	@Override public void doRender(EntityLivingBase entity, double x, double y, double z, float entityYaw, float partialTicks) {
		this.doRender((EntityZombieDragon) entity, x, y, z, entityYaw, partialTicks);
	}
	
	@Override public void doRender(Entity entity, double x, double y, double z, float entityYaw, float partialTicks) {
		this.doRender((EntityZombieDragon) entity, x, y, z, entityYaw, partialTicks);
	}
	
	public void doRender(EntityZombieDragon entity, double x, double y, double z, float entityYaw, float partialTicks) {
		BossStatus.setBossStatus(entity, true);
		
		this.mainModel = entity.isEgg() ? this.modelEgg : this.modelDragon;
		
		super.doRender((EntityLiving) entity, x, y, z, entityYaw, partialTicks);
	}
}
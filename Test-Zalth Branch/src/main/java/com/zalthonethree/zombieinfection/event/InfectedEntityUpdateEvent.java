package com.zalthonethree.zombieinfection.event;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import com.zalthonethree.zombieinfection.ZombieInfection;
import com.zalthonethree.zombieinfection.potion.PotionHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class InfectedEntityUpdateEvent {
	
	/** Villager */
	
	@SubscribeEvent public void onAttack(LivingHurtEvent event) {
		EntityLivingBase entityLivingIn = event.entityLiving;
		if (event.source instanceof EntityDamageSource) {
			EntityDamageSource source = (EntityDamageSource) event.source;
			Entity attacker = source.getEntity();
			if (attacker instanceof EntityPlayer) {
				Entity target = event.entity;
				if (target instanceof EntityVillager) {
					EntityVillager attacked = (EntityVillager) target;
					if (((EntityPlayer) attacker).isPotionActive(ZombieInfection.potionInfection))
						if ((attacked.getRNG().nextInt(100) + 1) <= 25) {
							attacked.addPotionEffect(PotionHelper.createWither(0));
						}
				}
			}
		}
	}
	
	@SubscribeEvent public void onDead(LivingDeathEvent event) {
		
		EntityLivingBase entityLivingIn = event.entityLiving;
		if (event.source instanceof EntityDamageSource) {
			EntityDamageSource source = (EntityDamageSource) event.source;
			Entity attacker = source.getEntity();
			if (attacker instanceof EntityPlayer) {
				Entity target = event.entity;
				if (target instanceof EntityVillager) {
					EntityVillager attacked = (EntityVillager) target;
					if ((attacked.getRNG().nextInt(100) + 1) <= 25) {
						if (((EntityVillager) attacked).isPotionActive(Potion.wither) && ((EntityPlayer) attacker).isPotionActive(ZombieInfection.potionInfection)) {
							EntityZombie entityzombie = new EntityZombie(attacked.worldObj);
							entityzombie.copyLocationAndAnglesFrom(entityLivingIn);
							attacked.worldObj.removeEntity(entityLivingIn);
							entityzombie.onSpawnWithEgg((IEntityLivingData) null);
							entityzombie.setVillager(true);
							
							if (entityLivingIn.isChild()) {
								entityzombie.setChild(true);
							}
							
							attacked.worldObj.spawnEntityInWorld(entityzombie);
							attacked.worldObj.playAuxSFXAtEntity((EntityPlayer) null, 1016,(int) attacked.posX, (int) attacked.posY,(int) attacked.posZ, 0);
						}
					}
				}
			}
		}
	}
	
}

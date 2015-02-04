package com.zalthonethree.zombieinfection.event;

import java.util.Iterator;




//import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
//import net.minecraft.util.DamageSource;
//import net.minecraftforge.event.entity.living.LivingDeathEvent;


import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import com.zalthonethree.zombieinfection.ZombieInfection;
import com.zalthonethree.zombieinfection.api.CustomInfectionEffect;
import com.zalthonethree.zombieinfection.api.ZombieInfectionAPI;
import com.zalthonethree.zombieinfection.handler.ConfigurationHandler;
import com.zalthonethree.zombieinfection.potion.PotionHelper;
import com.zalthonethree.zombieinfection.utility.FoodTracking;
import com.zalthonethree.zombieinfection.utility.TimeInfectedTracking;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;

public class InfectedPlayerUpdateEvent /*extends EntityDragon*/ {

	/*@SubscribeEvent
	public void LivingDeathEvent(LivingDeathEvent event) {
		EntityVillager villager = (net.minecraft.entity.passive.EntityVillager) event.entity;
		DamageSource source = event.source;
		//if (villager.isPotionActive(Potion.wither))
		if (villager.isDead);
		{
            EntityZombie entityzombie = new EntityZombie(this.worldObj);
            entityzombie.copyLocationAndAnglesFrom(villager);
            entityzombie.setVillager(true);
		}
	}*/
	
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
						if ((attacked.getRNG().nextInt(100) + 1) <= 10)
						{
							 EntityZombie entityzombie = new EntityZombie(attacked.worldObj);
					         entityzombie.copyLocationAndAnglesFrom(entityLivingIn);
					         attacked.worldObj.removeEntity(entityLivingIn);
					         entityzombie.onSpawnWithEgg((IEntityLivingData)null);
					         entityzombie.setVillager(true);
					         
					            if (entityLivingIn.isChild())
					            {
					                entityzombie.setChild(true);
					            }
					            
					         attacked.worldObj.spawnEntityInWorld(entityzombie);
					         attacked.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1016, (int)attacked.posX, (int)attacked.posY, (int)attacked.posZ, 0);
							
						}
					}
				}
			}
		}
	
	@SuppressWarnings("rawtypes") @SubscribeEvent
	public void onPlayerUpdate(TickEvent.PlayerTickEvent event) {
		EntityPlayer player = event.player;
		if (player.isPotionActive(ZombieInfection.potionInfection) && !player.isPotionActive(ZombieInfection.potionCure)) {
			int timeInfected = TimeInfectedTracking.getTicksInfected(player);
			for (CustomInfectionEffect customEffect : ZombieInfectionAPI.getCustomInfectionEffects()) {
				customEffect.run(player, timeInfected);
			}
			player.addPotionEffect(PotionHelper.createInfection(timeInfected < (20 * 60 * 2) ? 0 : 1));
			player.addPotionEffect(PotionHelper.createHunger(timeInfected < (20 * 60 * 2) ? 0 : 1));
			player.addPotionEffect(PotionHelper.createSlowness(timeInfected < (20 * 60 * 2) ? 0 : 1));
			player.addPotionEffect(PotionHelper.createMiningFatigue(timeInfected < (20 * 60 * 2) ? 0 : 1));
			player.addPotionEffect(PotionHelper.createWeakness(timeInfected < (20 * 60 * 2) ? 0 : 1));
			if (player.getFoodStats().getFoodLevel() > FoodTracking.get(player)) {
				player.getFoodStats().addStats(FoodTracking.get(player) - player.getFoodStats().getFoodLevel(), 0);
			}
			TimeInfectedTracking.update(player);
			FoodTracking.put(player);
			
			if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
				
				
			if (player.worldObj.canBlockSeeTheSky((int) player.posX, (int) player.posY, (int) player.posZ) && player.worldObj.isDaytime() && player.inventory.armorInventory[3] == null){
					player.setFire(1);
					player.getBrightness(1.0F);
			    }	
				
			Iterator entities = player.worldObj.loadedEntityList.iterator();
			while (entities.hasNext()) {
				Object thing = entities.next();
				if (thing instanceof EntityVillager && !(thing instanceof EntityPlayer)) {
					EntityVillager villager = (EntityVillager) thing;
					if (villager.getDistanceToEntity(player) < ConfigurationHandler.getSpreadDistance())
						{
							if (!villager.isPotionActive(Potion.wither))
							villager.addPotionEffect(new PotionEffect(Potion.wither.id, 10 * 20, 0, true));
						}
					}
				}
			
			if (!FMLCommonHandler.instance().getMinecraftServerInstance().isPVPEnabled() && ConfigurationHandler.getSpreadEnabled()) {
				Iterator players = FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().playerEntityList.iterator();
					
				while (players.hasNext()) {
				 Object thing = players.next();
				  if (thing instanceof EntityPlayer) {
				   EntityPlayer anotherPlayer = (EntityPlayer) thing;
				    if (anotherPlayer.getDistanceToEntity(player) < ConfigurationHandler.getSpreadDistance()) {
					 if (anotherPlayer.getUniqueID() != player.getUniqueID()) {
					  anotherPlayer.addPotionEffect(PotionHelper.createInfection(0));
					 }
				   }
				 }
			   }
			 }
		   }
		} else {
			FoodTracking.remove(player);
			TimeInfectedTracking.remove(player);
		}
	}
}

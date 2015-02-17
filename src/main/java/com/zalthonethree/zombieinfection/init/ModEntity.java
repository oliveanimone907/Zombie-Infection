package com.zalthonethree.zombieinfection.init;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

import com.zalthonethree.zombieinfection.entity.*;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityList.EntityEggInfo;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.MinecraftForge;

import java.util.List;

public class ModEntity {
	
	public static void registerEntity(Class<? extends Entity> entityClass, String entityName, int bkEggColor, int fgEggColor) {
		int id = EntityRegistry.findGlobalUniqueEntityId();

		EntityRegistry.registerGlobalEntityID(entityClass, entityName, id);
		EntityList.entityEggs.put(Integer.valueOf(id), new EntityEggInfo(id, bkEggColor, fgEggColor));
		}

		public void addSpawn(Class<? extends EntityLiving> entityClass, int spawnProb, int min, int max, BiomeGenBase[] biomes) {
		if (spawnProb > 0) {
		EntityRegistry.addSpawn(entityClass, spawnProb, min, max, EnumCreatureType.creature, biomes);
		}
		}
	
		
	public void registerEntity()
	
	{
		EntityRegistry.registerModEntity(EntityZombieCow.class, "Zombie_Cow", 1, this, 80, 4, true);
	}
	
	{
		EntityRegistry.registerModEntity(EntityZombiePig.class, "Zombie_Pig", 2, this, 80, 4, true);
	}
	
	{
		EntityRegistry.registerModEntity(EntityZombieChicken.class, "Zombie_Chicken", 3, this, 80, 4, true);
	}
	
	{
		EntityRegistry.registerModEntity(EntityZombieSheep.class, "Zombie_Sheep", 4, this, 80, 4, true);
	}
	
	
    public static void init() {
        registerEntities();
    }

    private static void registerEntities() {
    	
    	registerEntity(EntityZombieCow.class, "Zombie_Cow", 0x3e2e09, 0x566c58);
    	
    	
    	registerEntity(EntityZombiePig.class, "Zombie_Pig", 0xc87e7e, 0x566c58);
    	
    	
    	registerEntity(EntityZombieChicken.class, "Zombie_Chicken", 0xa3a3a3, 0x566c58);
    	
    	
    	registerEntity(EntityZombieSheep.class, "Zombie_Sheep", 0xe8e8e8, 0x566c58);


    	for (int i = 0; i < BiomeGenBase.getBiomeGenArray().length; i++)
    	{
    	    if (BiomeGenBase.getBiomeGenArray()[i] != null)
    	    {
    	    	//Example of how to add mob spawning, DO NOT REMOVE, for now.
    	        //EntityRegistry.addSpawn(EntityZombieCow.class, 1, 1, 1, EnumCreatureType.monster, BiomeGenBase.getBiomeGenArray()[i]);
    	    }
    	}
    }
}
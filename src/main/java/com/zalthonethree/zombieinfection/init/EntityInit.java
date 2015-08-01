package com.zalthonethree.zombieinfection.init;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;

import com.zalthonethree.zombieinfection.ZombieInfection;
import com.zalthonethree.zombieinfection.entity.EntityZombieChicken;
import com.zalthonethree.zombieinfection.entity.EntityZombieCow;
import com.zalthonethree.zombieinfection.entity.EntityZombiePig;
import com.zalthonethree.zombieinfection.entity.EntityZombieSheep;

import cpw.mods.fml.common.registry.EntityRegistry;

public class EntityInit /*extends EntityDragon*/ {

	private static int nextEntityID = 1;

	private static int getNextEntityID() {
		nextEntityID ++;
		return nextEntityID - 1;
	}

	@SuppressWarnings("unused") private static void registerEntity(Class<? extends Entity> clazz) { registerEntity(clazz, 0, 0); }
	private static void registerEntity(Class<? extends Entity> clazz, int bkEggColor, int fgEggColor) { registerEntity(clazz, clazz.getName().toLowerCase(), bkEggColor, fgEggColor); }
	private static void registerEntity(Class<? extends Entity> clazz, String name, int bkEggColor, int fgEggColor) {
//	int id = EntityRegistry.findGlobalUniqueEntityId();
		
//		EntityRegistry.registerGlobalEntityID(clazz, name, id);
		EntityRegistry.registerModEntity(clazz, name, getNextEntityID(), ZombieInfection.instance, 80, 4, true);
		registerSpawnEgg(name, bkEggColor, fgEggColor);
//		EntityList.entityEggs.put(id, new EntityList.EntityEggInfo(id, bkEggColor, fgEggColor)); // From what I've read we need to use something other than global IDs and get the spawn eggs a different way
	}

	private static void registerSpawnEgg(String name, int bkEggColor, int fgEggColor) {
	}

	@SuppressWarnings("unused") private static void addSpawn(Class<? extends EntityLiving> entityClass, int spawnProb, int min, int max, BiomeGenBase[] biomes) {
		EntityRegistry.addSpawn(entityClass, spawnProb, min, max, EnumCreatureType.creature, biomes);
	}
	
	
	public static void init() {
		registerEntity();
	}
	
	private static void registerEntity() {
		registerEntity(EntityZombieChicken.class, "ZIZombieChicken", 0xa3a3a3, 0x566c58);
		registerEntity(EntityZombieCow.class, "ZIZombieCow", 0x3e2e09, 0x566c58);
		registerEntity(EntityZombiePig.class, "ZIZombiePig", 0xc87e7e, 0x566c58);
		registerEntity(EntityZombieSheep.class, "ZIZombieSheep", 0xe8e8e8, 0x566c58);
	}
}
package com.zalthonethree.zombieinfection.init;

import net.minecraft.entity.Entity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.Item;
import net.minecraft.world.biome.BiomeGenBase;

import com.zalthonethree.zombieinfection.ZombieInfection;
import com.zalthonethree.zombieinfection.entity.EntityZombieChicken;
import com.zalthonethree.zombieinfection.entity.EntityZombieCow;
import com.zalthonethree.zombieinfection.entity.EntityZombiePig;
import com.zalthonethree.zombieinfection.entity.EntityZombieSheep;

import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class EntityInit /*extends EntityDragon*/ {

	private static int nextEntityID = 1;

	private static int getNextEntityID() {
		nextEntityID ++;
		return nextEntityID - 1;
	}

	@SuppressWarnings("unused") private static void registerEntity(Class<? extends Entity> clazz) { registerEntity(clazz, 0, 0); }
	private static void registerEntity(Class<? extends Entity> clazz, int bkEggColor, int fgEggColor) { registerEntity(clazz, clazz.getName().toLowerCase(), bkEggColor, fgEggColor); }
	private static void registerEntity(Class<? extends Entity> clazz, String name, int bkEggColor, int fgEggColor) {
		
		EntityRegistry.registerModEntity(clazz, name, getNextEntityID(), ZombieInfection.instance, 80, 4, true);
		registerSpawnEgg(name, bkEggColor, fgEggColor);
	}

	private static void registerSpawnEgg(String name, int bkEggColor, int fgEggColor) {
	       Item itemSpawnEgg = new SpawnEgg(name, bkEggColor, fgEggColor).setUnlocalizedName("spawn_egg_"+name.toLowerCase()).setTextureName("ZombieInfection:spawn_egg");
	       GameRegistry.registerItem(itemSpawnEgg, "spawnEgg"+name);
	}

	@SuppressWarnings("unused") private static void addSpawn(Class<? extends EntityLiving> entityClass, int spawnProb, int min, int max, BiomeGenBase[] biomes) {
		EntityRegistry.addSpawn(entityClass, spawnProb, min, max, EnumCreatureType.creature, biomes);
	}
	
	
	public static void init() {
		registerEntity();
	}
	
	private static void registerEntity() {
		registerEntity(EntityZombieChicken.class, "Zombie Chicken (ZI)", 0xa3a3a3, 0x566c58);
		registerEntity(EntityZombieCow.class, "Zombie Cow (ZI)", 0x3e2e09, 0x566c58);
		registerEntity(EntityZombiePig.class, "Zombie Pig (ZI)", 0xc87e7e, 0x566c58);
		registerEntity(EntityZombieSheep.class, "Zombie Sheep (ZI)", 0xe8e8e8, 0x566c58);
	}
}
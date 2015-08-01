package com.zalthonethree.zombieinfection.init;

import net.minecraft.item.Item;

import com.zalthonethree.zombieinfection.item.ItemBase;
import com.zalthonethree.zombieinfection.item.ItemCure;
import com.zalthonethree.zombieinfection.item.ItemInfectedEgg;
import com.zalthonethree.zombieinfection.item.ItemInfectedMilk;
import com.zalthonethree.zombieinfection.item.ItemKnowledgeBook;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModItems /*extends EntityDragon*/ {
	public static final ItemBase cure = new ItemCure();
	public static final ItemBase infectedEgg = new ItemInfectedEgg();
	public static final ItemBase infectedMilk = new ItemInfectedMilk();
	public static final ItemBase knowledgeBook = new ItemKnowledgeBook();
	
	public static final Item itemSpawnEggChicken = new SpawnEgg("ZIZombieChicken", 0xa3a3a3, 0x566c58)
    .setUnlocalizedName("spawn_egg_")
    .setTextureName("ZombieInfection:spawn_egg");
	
	public static final Item itemSpawnEggCow = new SpawnEgg("ZIZombieCow", 0x3e2e09, 0x566c58)
    .setUnlocalizedName("spawn_egg_")
    .setTextureName("ZombieInfection:spawn_egg");
	
	public static final Item itemSpawnEggPig = new SpawnEgg("ZIZombiePig", 0xc87e7e, 0x566c58)
    .setUnlocalizedName("spawn_egg_")
    .setTextureName("ZombieInfection:spawn_egg");
	
	public static final Item itemSpawnEggSheep = new SpawnEgg("ZIZombieSheep", 0xe8e8e8, 0x566c58)
    .setUnlocalizedName("spawn_egg_")
    .setTextureName("ZombieInfection:spawn_egg");
	
	public static void init() {
		GameRegistry.registerItem(cure, "Cure");
		GameRegistry.registerItem(infectedEgg, "infectedEgg");
		GameRegistry.registerItem(infectedMilk, "infectedMilk");
		GameRegistry.registerItem(knowledgeBook, "knowledgeBook");
		GameRegistry.registerItem(itemSpawnEggChicken, "spawnEggChicken");
		GameRegistry.registerItem(itemSpawnEggCow, "spawnEggCow");
		GameRegistry.registerItem(itemSpawnEggPig, "spawnEggPig");
		GameRegistry.registerItem(itemSpawnEggSheep, "spawnEggSheep");
		
	}
}
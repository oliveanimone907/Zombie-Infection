package com.zalthonethree.zombieinfection;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import com.zalthonethree.zombieinfection.handler.ConfigurationHandler;
import com.zalthonethree.zombieinfection.handler.PacketHandler;
import com.zalthonethree.zombieinfection.init.BuiltInAPI;
import com.zalthonethree.zombieinfection.init.EasterEggs;
import com.zalthonethree.zombieinfection.init.EntityInit;
import com.zalthonethree.zombieinfection.init.ModBlocks;
import com.zalthonethree.zombieinfection.init.ModItems;
import com.zalthonethree.zombieinfection.init.Recipes;
import com.zalthonethree.zombieinfection.potion.PotionCure;
import com.zalthonethree.zombieinfection.potion.PotionInfection;
import com.zalthonethree.zombieinfection.proxy.IProxy;
import com.zalthonethree.zombieinfection.utility.LogHelper;
import com.zalthonethree.zombieinfection.world.BiomeGenZend;
import com.zalthonethree.zombieinfection.world.WorldProviderZend;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, acceptedMinecraftVersions = "[1.8,1.8.8]") public class ZombieInfection /*extends EntityDragon*/ {
	@Mod.Instance(Reference.MOD_ID) public static ZombieInfection instance;
	public static Potion potionInfection;
	public static Potion potionCure;
	public static BiomeGenBase biomeZend;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY) public static IProxy proxy;
	
	@Mod.EventHandler public void preInit(FMLPreInitializationEvent event) {
		ConfigurationHandler.init(event.getSuggestedConfigurationFile());
		FMLCommonHandler.instance().bus().register(new ConfigurationHandler());
		
		Potion[] potionTypes = null;
		biomeZend = new BiomeGenZend(ConfigurationHandler.getZendBiomeId());
		BiomeDictionary.registerBiomeType(biomeZend, BiomeDictionary.Type.SPOOKY);
		DimensionManager.registerProviderType(ConfigurationHandler.getZendDimensionId(), WorldProviderZend.class, false);
		DimensionManager.registerDimension(ConfigurationHandler.getZendDimensionId(), ConfigurationHandler.getZendDimensionId());
		
		for (Field f : Potion.class.getDeclaredFields()) {
			f.setAccessible(true);
			
			try {
				if (f.getName().equals("potionTypes") || f.getName().equals("field_76425_a")) {
					Field modfield = Field.class.getDeclaredField("modifiers");
					modfield.setAccessible(true);
					modfield.setInt(f, f.getModifiers() & ~Modifier.FINAL);
					potionTypes = (Potion[]) f.get(null);
					final Potion[] newPotionTypes = new Potion[256];
					System.arraycopy(potionTypes, 0, newPotionTypes, 0, potionTypes.length);
					f.set(null, newPotionTypes);
				}
			} catch (Exception e) {
				System.err.println("Severe error, please report this to the mod author:");
				System.err.println(e);
			}
		}
		
		PacketHandler.INSTANCE.ordinal();
		LogHelper.info("Pre-Init Complete");
	}
	
	@Mod.EventHandler public void init(FMLInitializationEvent event) {
		ModItems.init();
		ModBlocks.init();
		proxy.init();
		proxy.registerRenderers();
		
		potionInfection = (new PotionInfection(63, new ResourceLocation(Reference.MOD_ID.toLowerCase(), "infection"), true, 0)).setIconIndex(3, 1).setPotionName("potion.zombieinfection.infection");
		potionCure = (new PotionCure(64, new ResourceLocation(Reference.MOD_ID.toLowerCase(), "cure"), true, 0)).setIconIndex(2, 2).setPotionName("potion.zombieinfection.cure");
		
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
		EasterEggs.init();
		EntityInit.init();
		BuiltInAPI.init();
		Recipes.init();
		LogHelper.info("Init Complete");
	}
	
	@Mod.EventHandler public void postInit(FMLPostInitializationEvent event) {
		LogHelper.info("Post-Init Complete");
	}
	
	@Mod.EventHandler public void serverStarting(FMLServerStartingEvent event) {
		proxy.init();
	}
}

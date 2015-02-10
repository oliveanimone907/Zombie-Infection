package com.zalthonethree.zombieinfection.handler;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

import com.zalthonethree.zombieinfection.Reference;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ConfigurationHandler /*extends EntityDragon*/ {
	public static Configuration configuration;
	private static int spreadDistance = 3;
	private static int villagerInfectionChance = 25;
	private static boolean enableMessages = true;
	private static boolean enableWither = true;
	private static boolean enableHunger = true;
	private static boolean enableSlowness = true;
	private static boolean enableMiningFatigue = true;
	private static boolean enableWeakness = true;
	
	public static void init(File configFile) {
		if (configuration == null) {
			configuration = new Configuration(configFile);
			loadConfiguration();
		}
	}
	
	@SubscribeEvent public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.modID.equalsIgnoreCase(Reference.MOD_ID)) {
			loadConfiguration();
		}
	}
	
	private static void loadConfiguration() {
		spreadDistance = configuration.getInt("Spread Distance", Configuration.CATEGORY_GENERAL, 3, 0, 5, "Distance to be from someone to spread the infection when PVP is off. 0 = Disabled");
		villagerInfectionChance = configuration.getInt("Villager Infection Chance", Configuration.CATEGORY_GENERAL, 25, 0, 100, "Chance for infection to spread to villager upon attack.");
		enableWither = configuration.getBoolean("Wither Effect", "Effects", true, "Enable Wither effect after 8 minutes have passed with the Infection active");
		enableHunger = configuration.getBoolean("Hunger Effect", "Effects", true, "Enable Hunger effect when infected");
		enableSlowness = configuration.getBoolean("Slowness Effect","Effects", true, "Enable Slowness effect when infected");
		enableMiningFatigue = configuration.getBoolean("Mining Fatigue Effect", "Effects", true, "Enable MiningFatigue effect when infected");
		enableWeakness = configuration.getBoolean("Weakness Effect", "Effects", true, "Enable Weakness effect when infected");
		enableMessages = configuration.getBoolean("Chat Messages", Configuration.CATEGORY_GENERAL, true, "Enable the chat messages that appear when infected");
		if (configuration.hasChanged()) {
			configuration.save();
		}
	}
	
	public static int getSpreadDistance() { return spreadDistance; }
	public static int getVillagerInfectionChance() { return villagerInfectionChance; }
	public static boolean getSpreadEnabled() { return spreadDistance != 0; }
	public static boolean enableMessages() { return enableMessages != false; }
	public static boolean enableWither() { return enableWither != false; }
	public static boolean enableHunger() { return enableHunger != false; }
	public static boolean enableSlowness() { return enableSlowness != false; }
	public static boolean enableMiningFatigue() { return enableMiningFatigue != false; }
	public static boolean enableWeakness() { return enableWeakness != false; }
}
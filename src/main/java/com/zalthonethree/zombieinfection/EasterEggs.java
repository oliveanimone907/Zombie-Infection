package com.zalthonethree.zombieinfection;

import com.zalthonethree.zombieinfection.api.ZombieInfectionAPI;

public class EasterEggs {
	
	//SPOILERS!!
	
	public static void init() {
		ZombieInfectionAPI.registerEncryptionSwitch("item.mushroomStew", "easteregg.stew", "easteregg.description.stew");
		ZombieInfectionAPI.registerEncryptionSwitch("item.egg", "easteregg.egg", "easteregg.description.egg");
		
	}
	
}

package com.zalthonethree.zombieinfection.init;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.zalthonethree.zombieinfection.item.ItemBase;
import com.zalthonethree.zombieinfection.item.ItemCure;
import com.zalthonethree.zombieinfection.item.ItemInfectedEgg;
import com.zalthonethree.zombieinfection.item.ItemInfectedMilk;
import com.zalthonethree.zombieinfection.item.ItemKnowledgeBook;
import com.zalthonethree.zombieinfection.utility.Utilities;

public class ModItems {
	public static final ItemBase cure = new ItemCure();
	public static final ItemBase infectedEgg = new ItemInfectedEgg();
	public static final ItemBase infectedMilk = new ItemInfectedMilk();
	public static final ItemBase knowledgeBook = new ItemKnowledgeBook();
	
	public static void init() {
		GameRegistry.registerItem(cure, "Cure");
		GameRegistry.registerItem(infectedEgg, "infectedEgg");
		GameRegistry.registerItem(infectedMilk, "infectedMilk");
		GameRegistry.registerItem(knowledgeBook, "knowledgeBook");
		if (Utilities.isClientSide()) {
			ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
			mesher.register(cure, 0, new ModelResourceLocation("zombieinfection:Cure", "inventory"));
			mesher.register(infectedEgg, 0, new ModelResourceLocation("zombieinfection:infectedEgg", "inventory"));
			mesher.register(infectedMilk, 0, new ModelResourceLocation("zombieinfection:infectedMilk", "inventory"));
			mesher.register(knowledgeBook, 0, new ModelResourceLocation("zombieinfection:knowledgeBook", "inventory"));
		}
	}
}
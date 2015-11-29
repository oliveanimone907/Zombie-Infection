package com.zalthonethree.zombieinfection.utility;

import java.util.Collection;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import net.minecraft.block.properties.PropertyEnum;

public class PropertyPearlType extends PropertyEnum {
	public enum EnumPearl {
		NONE,
		BLOODY,
		INFECTED,
		CURED
	}
	
	protected PropertyPearlType(String name, Collection values) {
		super(name, EnumPearl.class, values);
	}
	
	public static PropertyPearlType create(String name) {
		return create(name, Predicates.alwaysTrue());
	}
	
	public static PropertyPearlType create(String name, Predicate filter) {
		return create(name, Collections2.filter(Lists.newArrayList(EnumPearl.values()), filter));
	}
	
	public static PropertyPearlType create(String name, Collection values) {
		return new PropertyPearlType(name, values);
	}
}
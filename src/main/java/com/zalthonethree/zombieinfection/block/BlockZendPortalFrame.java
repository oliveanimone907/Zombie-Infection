package com.zalthonethree.zombieinfection.block;

import java.util.Random;

import com.zalthonethree.zombieinfection.utility.PropertyPearlType;
import com.zalthonethree.zombieinfection.utility.PropertyPearlType.EnumPearl;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockZendPortalFrame extends BlockBase {
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	public static final PropertyPearlType EYE = PropertyPearlType.create("eye");
	
	public BlockZendPortalFrame() {
		super(Material.rock);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(EYE, EnumPearl.NONE));
	}
	
	@Override public boolean isOpaqueCube() { return false; }
	
	@Override public void setBlockBoundsForItemRender() {
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.8125F, 1.0F);
	}
	
	@Override public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity) {
		this.setBlockBounds(0F, 0F, 0F, 1F, 0.8125F, 1F);
		super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
		
		if (((EnumPearl) worldIn.getBlockState(pos).getValue(EYE)) != EnumPearl.NONE) {
			this.setBlockBounds(0.3125F, 0.8125F, 0.3125F, 0.6875F, 1.0F, 0.6875F);
			super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
		}
		
		this.setBlockBoundsForItemRender();
	}
	
	@Override public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return null;
	}
}
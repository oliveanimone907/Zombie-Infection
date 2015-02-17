package com.zalthonethree.zombieinfection.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityZombieChicken extends EntityMob{
	
    public float field_70886_e;
    public float destPos;
    public float field_70884_g;
    public float field_70888_h;
    public float field_70889_i = 1.0F;
	
	public EntityZombieChicken(World p_i1683_1_) {
		super(p_i1683_1_);
        this.setSize(0.9F, 1.3F);
	    this.getNavigator().setAvoidsWater(false);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
        this.tasks.addTask(3, new EntityAIAttackOnCollide(this, EntityChicken.class, 1.0D, true));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(4, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityChicken.class, 0, false));
	}
	
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
    }
	
    public boolean isAIEnabled()
    {
        return true;
    }
    
    public boolean attackEntityAsMob(Entity p_70652_1_)
    {
        boolean flag = super.attackEntityAsMob(p_70652_1_);

        if (flag)
        {
            int i = this.worldObj.difficultySetting.getDifficultyId();

            if (this.getHeldItem() == null && this.isBurning() && this.rand.nextFloat() < (float)i * 0.3F)
            {
                p_70652_1_.setFire(2 * i);
            }
        }

        return flag;
    }
    
    protected String getLivingSound()
    {
        return "mob.chicken.say";
    }


    protected String getHurtSound()
    {
        return "mob.chicken.hurt";
    }


    protected String getDeathSound()
    {
        return "mob.chicken.hurt";
    }
    

    protected void playStepSound(int x, int y, int z, Block blockIn)
    {
        this.playSound("mob.chicken.step", 0.15F, 1.0F);
    }


    protected float getSoundVolume()
    {
        return 0.4F;
    }
    
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
    }

    protected Item getDropItem()
    {
        return Items.rotten_flesh;
    }
    

   public void onLivingUpdate()
    {
        if (this.worldObj.isDaytime() && !this.worldObj.isRemote && !this.isChild())
        {
            float f = this.getBrightness(1.0F);
            
            if (f > 0.5F && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)))
            {
                this.setFire(8);
            }
        }
        
        super.onLivingUpdate();
        
        this.field_70888_h = this.field_70886_e;
        this.field_70884_g = this.destPos;
        this.destPos = (float)((double)this.destPos + (double)(this.onGround ? -1 : 4) * 0.3D);

        if (this.destPos < 0.0F)
        {
            this.destPos = 0.0F;
        }

        if (this.destPos > 1.0F)
        {
            this.destPos = 1.0F;
        }

        if (!this.onGround && this.field_70889_i < 1.0F)
        {
            this.field_70889_i = 1.0F;
        }

        this.field_70889_i = (float)((double)this.field_70889_i * 0.9D);

        if (!this.onGround && this.motionY < 0.0D)
        {
            this.motionY *= 0.6D;
        }

        this.field_70886_e += this.field_70889_i * 2.0F;
    }
   
   
   public void onKillEntity(EntityLivingBase entityLivingIn)
   {
       super.onKillEntity(entityLivingIn);

       if ((this.worldObj.difficultySetting == EnumDifficulty.NORMAL || this.worldObj.difficultySetting == EnumDifficulty.HARD && !entityLivingIn.isChild()) && entityLivingIn instanceof EntityChicken)
       {
           if (this.worldObj.difficultySetting != EnumDifficulty.HARD && this.rand.nextBoolean())
           {
               return;
           }

           EntityZombieChicken entityzombiechicken = new EntityZombieChicken(this.worldObj);
           entityzombiechicken.copyLocationAndAnglesFrom(entityLivingIn);
           this.worldObj.removeEntity(entityLivingIn);
           entityzombiechicken.onSpawnWithEgg((IEntityLivingData)null);

           this.worldObj.spawnEntityInWorld(entityzombiechicken);
           this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1016, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
       }
   }
   
   protected void fall(float distance) {}
}

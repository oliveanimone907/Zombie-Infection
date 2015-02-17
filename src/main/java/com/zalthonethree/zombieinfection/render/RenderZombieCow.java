package com.zalthonethree.zombieinfection.render;

import com.zalthonethree.zombieinfection.Reference;
import com.zalthonethree.zombieinfection.entity.EntityZombieCow;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderZombieCow extends RenderLiving
{
    private static final ResourceLocation zombiecowTextures = new ResourceLocation(Reference.MOD_ID, "textures/entity/zombiecow.png");

    public RenderZombieCow(ModelBase p_i1253_1_, float p_i1253_2_)
    {
        super(p_i1253_1_, p_i1253_2_);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityZombieCow p_110775_1_)
    {
        return zombiecowTextures;
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity p_110775_1_)
    {
        return this.getEntityTexture((EntityZombieCow)p_110775_1_);
    }
}
package divinerpg.objects.entities.entity.vanilla;

import divinerpg.objects.entities.ai.AIDivineFireballAttack;
import divinerpg.objects.entities.entity.EntityDivineGhast;
import divinerpg.objects.entities.entity.projectiles.EntityEnderTripletsFireball;
import divinerpg.registry.DRPGLootTables;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import javax.annotation.Nullable;


public class EntityEnderTriplets extends EntityDivineGhast {

    public EntityEnderTriplets(World worldIn) {
        super(worldIn);
        this.setSize(2.0F, 2.0F);
        this.isImmuneToFire = true;
        this.experienceValue = 5;
    }

    @Override
    public float getEyeHeight() {
        return 1.0F;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.27D);
    }

    @Nullable
    @Override
    protected AIDivineFireballAttack createShootAI() {
        return new AIDivineFireballAttack(this,
                (world1, parent, x, y, z, fireballStrength) -> new EntityEnderTripletsFireball(world, parent, x, y, z));
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float par2) {
        if (this.isEntityInvulnerable(damageSource)) {
            return false;
        } else if ("fireball".equals(damageSource.getDamageType())
                && damageSource.getTrueSource() instanceof EntityPlayer) {
            super.attackEntityFrom(damageSource, 1000.0F);
            // EntityPlayer player = (EntityPlayer)damageSource.getTrueSource();
            // player.triggerAchievement(DivineRPGAchievements.tripleDanger);
            return true;
        } else {
            return super.attackEntityFrom(damageSource, par2);
        }
    }

    @Override
    protected float getSoundVolume() {
        return 10.0F;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_GHAST_SCREAM;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_GHAST_DEATH;
    }

    @Override
    protected ResourceLocation getLootTable() {
        return DRPGLootTables.ENTITIES_ENDER_TRIPLETS;
    }

    @Override
    public boolean getCanSpawnHere() {
        return this.world.getDifficulty() != EnumDifficulty.PEACEFUL && world.provider.getDimension() == 1;
    }
}
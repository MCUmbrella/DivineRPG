package divinerpg.objects.entities.entity.twilight.cories;

import divinerpg.objects.entities.ai.AIDivineFireballAttack;
import divinerpg.objects.entities.ai.ILaunchThrowable;
import divinerpg.objects.entities.entity.EntityDivineGhast;
import divinerpg.objects.entities.entity.projectiles.EntityCoriShot;
import divinerpg.registry.DRPGLootTables;
import divinerpg.registry.ModSounds;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityAdvancedCori extends EntityDivineGhast {

    public EntityAdvancedCori(World worldIn) {
        super(worldIn);
        this.setSize(0.6F, 1.5F);
    }

    @Override
    protected AIDivineFireballAttack createShootAI() {
        return new AIDivineFireballAttack(this,
                new ILaunchThrowable() {

                    @Override
                    public float getInaccuracy(World world) {
                        return 0;
                    }

                    @Override
                    public EntityThrowable createThowable(World world, EntityLivingBase parent, double x, double y, double z) {
                        return new EntityCoriShot(world, parent, (float) parent.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
                    }
                },
                ModSounds.CORI_SHOOT);
    }

    @Override
    public float getEyeHeight() {
        return 0.8F;
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 1;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.CORI_IDLE;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.CORI_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.CORI_HURT;
    }

    @Override
    protected ResourceLocation getLootTable() {
        return DRPGLootTables.ENTITIES_ADVANCED_CORI;
    }
}
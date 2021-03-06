package divinerpg.objects.entities.entity.vethea;

import divinerpg.objects.entities.ai.AIDivineFireballAttack;
import divinerpg.objects.entities.entity.EntityDivineGhast;
import divinerpg.objects.entities.entity.projectiles.EntityZoragonBomb;
import divinerpg.registry.DRPGLootTables;
import divinerpg.registry.ModSounds;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityZoragon extends EntityDivineGhast {
    private static final int spawnLayer = 4;

    public EntityZoragon(World par1World) {
        super(par1World);
        this.setSize(4.0F, 4.0F);
    }

    @Override
    public boolean getCanSpawnHere() {
        return this.posY < 48.0D * spawnLayer && this.posY > 48.0D * (spawnLayer - 1) && super.getCanSpawnHere();
    }

    @Nullable
    @Override
    protected AIDivineFireballAttack createShootAI() {
        return new AIDivineFireballAttack(this,
                (world1, parent, x, y, z) -> new EntityZoragonBomb(this.world, this),
                null);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.ZORAGON;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.ZORAGON_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.ZORAGON_HURT;
    }

    @Override
    protected ResourceLocation getLootTable() {
        return DRPGLootTables.ENTITIES_ZORAGON;
    }

    @Override
    protected float getSoundVolume() {
        return 2.0F;
    }

}

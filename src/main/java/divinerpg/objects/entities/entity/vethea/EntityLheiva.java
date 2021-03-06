package divinerpg.objects.entities.entity.vethea;

import divinerpg.registry.DRPGLootTables;
import divinerpg.registry.ModItems;
import divinerpg.registry.ModSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityLheiva extends VetheaMob {

    public EntityLheiva(World worldIn) {
        super(worldIn);
        this.setSize(1F, 1.4f);
        this.addAttackingAI();
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();


    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float par2) {
        Entity var1 = source.getTrueSource();
        if (var1 != null && var1 instanceof EntityPlayer) {
            if (((EntityPlayer)var1).inventory.hasItemStack(new ItemStack(ModItems.bandOfHeivaHunting)))
                return super.attackEntityFrom(source, par2);
        } else if(source == DamageSource.OUT_OF_WORLD) return super.attackEntityFrom(source, par2);
        return false;
    }

    @Override
    public int getSpawnLayer() {
        return 3;
    }

    @Override
    protected ResourceLocation getLootTable()
    {
        return DRPGLootTables.ENTITIES_LHEIVA;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.LHEIVA;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.LHEIVA_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.LHEIVA_HURT;
    }
}
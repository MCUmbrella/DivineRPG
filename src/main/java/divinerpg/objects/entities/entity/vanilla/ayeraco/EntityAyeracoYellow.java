package divinerpg.objects.entities.entity.vanilla.ayeraco;

import divinerpg.registry.DRPGLootTables;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BossInfo.Color;
import net.minecraft.world.World;

public class EntityAyeracoYellow extends EntityAyeraco {

    public EntityAyeracoYellow(World worldIn) {
        super(worldIn);
    }

    public EntityAyeracoYellow(World world, BlockPos beam) {
        super(world, beam, Color.YELLOW, DRPGLootTables.ENTITIES_AYERACO_YELLOW);
    }
}

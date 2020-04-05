package divinerpg.utils.portals.description;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.block.state.pattern.BlockStateMatcher;
import net.minecraft.block.state.pattern.FactoryBlockPattern;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class NetherLikePortalDescription implements IPortalDescription {
    private final BlockPattern fullPattern;
    private final BlockPattern framePattern;
    private final Block frame;
    private final Block portal;

    public NetherLikePortalDescription(Block frame, Block portal) {
        this.frame = frame;
        this.portal = portal;

        fullPattern = FactoryBlockPattern.start()
                .aisle("?xx?")
                .aisle("x..x")
                .aisle("x..x")
                .aisle("x..x")
                .aisle("?xx?")
                .where('?', BlockWorldState.hasState(BlockStateMatcher.ANY))
                .where('x', BlockWorldState.hasState(BlockStateMatcher.forBlock(getFrame())))
                .where('.', BlockWorldState.hasState(BlockStateMatcher.forBlock(getPortal())))
                .build();

        framePattern = FactoryBlockPattern.start()
                .aisle("?xx?")
                .aisle("x??x")
                .aisle("x??x")
                .aisle("x??x")
                .aisle("?xx?")
                .where('?', BlockWorldState.hasState(BlockStateMatcher.ANY))
                .where('x', BlockWorldState.hasState(BlockStateMatcher.forBlock(getFrame())))
                .build();
    }

    @Override
    public Block getFrame() {
        return frame;
    }

    @Override
    public Block getPortal() {
        return portal;
    }

    @Override
    public BlockPattern.PatternHelper createPortal(World world, BlockPos pos) {
        // starting from botton right corener
        final BlockPos rightBottom = pos.east();
        final BlockPos leftTop = rightBottom.offset(EnumFacing.WEST, 3).offset(EnumFacing.UP, 4);

        for (int i = 0; i < 5; i++) {
            world.setBlockState(leftTop.down(i), frame.getDefaultState());
            world.setBlockState(rightBottom.up(i), frame.getDefaultState());
        }

        for (int i = 0; i < 4; i++) {
            world.setBlockState(leftTop.east(i), frame.getDefaultState());
            world.setBlockState(rightBottom.west(i), frame.getDefaultState());
        }

        // create platform is needed
        List<BlockPos> platformBlocks = Arrays.asList(
                rightBottom.west().north(),
                rightBottom.west().south(),

                rightBottom.west(2).north(),
                rightBottom.west(2).south()
        );

        for (BlockPos x : platformBlocks) {
            if (Stream.of(pos, pos.down()).allMatch(world::isAirBlock)) {
                world.setBlockState(x, frame.getDefaultState());
            }
        }

        lightPortal(world, leftTop, EnumFacing.SOUTH, EnumFacing.DOWN);
        return matchFrame(world, pos);
    }

    @Override
    public void lightPortal(World world, BlockPattern.PatternHelper frameMatch) {
        EnumFacing right = frameMatch.getUp();
        EnumFacing down = frameMatch.getForwards();
        BlockPos topLeft = frameMatch.getFrontTopLeft();

        lightPortal(world, topLeft, right, down);
    }

    @Nullable
    @Override
    public BlockPattern.PatternHelper matchWorkingPortal(World world, BlockPos pos) {
        return fullPattern.match(world, pos);
    }

    @Nullable
    @Override
    public BlockPattern.PatternHelper matchFrame(World world, BlockPos pos) {
        return framePattern.match(world, pos);
    }

    @Override
    public BlockPos getPlayerPortalPosition(World world, BlockPattern.PatternHelper fullMatch) {
        BlockPos result = fullMatch.getFrontTopLeft()
                .offset(fullMatch.getUp(), 1)
                .offset(fullMatch.getUp().getOpposite().rotateY(), 1)
                .offset(fullMatch.getForwards(), 3);

        return result;
    }

    private void lightPortal(World world, BlockPos topLeft, EnumFacing right, EnumFacing down) {
        right = right.rotateYCCW();
        topLeft = topLeft.offset(right, 1).offset(down, 1);

        IBlockState state = fillWithProps(portal.getDefaultState(), right);


        for (int i = 0; i < 3; i++) {
            world.setBlockState(topLeft.offset(down, i), state);
            world.setBlockState(topLeft.offset(down, i).offset(right), state);
        }
    }

    private IBlockState fillWithProps(IBlockState state, EnumFacing facing) {
        if (state.getPropertyKeys().contains(BlockPortal.AXIS)) {
            state = state.withProperty(BlockPortal.AXIS, facing.getAxis());
        }

        return state;
    }
}
package name.modid.util;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class BlockUtils {
    /**
     * Finds the highest non-air block at the given X and Z coordinates.
     *
     * @param world The world to search in.
     * @param x The X coordinate.
     * @param z The Z coordinate.
     * @return The BlockPos of the highest non-air block, or null if none is found.
     */
    public static BlockPos getHighestBlock(World world, int x, int z) {
        int maxHeight = world.getHeight(); // Get the world height limit.
        for (int y = maxHeight - 1; y >= world.getBottomY(); y--) { // Iterate from top to bottom.
            BlockPos pos = new BlockPos(x, y, z);
            BlockState state = world.getBlockState(pos);
            if (!state.isAir()) { // Check if the block is not air.
                return pos;
            }
        }
        return null; // No non-air block found.
    }

    /**
     * Finds the highest non-air block at the given X and Z coordinates.
     *
     * @param world The world to search in.
     * @param pos The BlockPos to search.
     * @return The BlockPos of the highest non-air block, or null if none is found.
     */
    public static BlockPos getHighestBlock(World world, BlockPos pos) {
        int maxHeight = world.getHeight(); // Get the world height limit.
        for (int y = maxHeight - 1; y >= world.getBottomY(); y--) { // Iterate from top to bottom.
            BlockPos checkPos = new BlockPos(pos.getX(), y, pos.getZ());
            if (!world.getBlockState(checkPos).isAir()) { // Check if the block is not air.
                return checkPos;
            }
        }
        return null; // No non-air block found.
    }

    /**
     * Finds the highest solid block at the given X and Z coordinates.
     *
     * @param world The world to search in.
     * @param x The X coordinate.
     * @param z The Z coordinate.
     * @return The BlockPos of the highest solid block, or null if none is found.
     */
    public static BlockPos getHighestSolidBlock(World world, int x, int z) {
        int maxHeight = world.getHeight();
        for (int y = maxHeight - 1; y >= world.getBottomY(); y--) {
            BlockPos pos = new BlockPos(x, y, z);
            BlockState state = world.getBlockState(pos);
            if (state.isSolidBlock(world, pos)) { // Check if the block is not air nor a solid block
                return pos;
            }
        }
        return null; // No non-air block found.
    }

    /**
     * Finds the highest solid block at the given X and Z coordinates.
     *
     * @param world The world to search in.
     * @param pos The BlockPos to search.
     * @return The BlockPos of the highest solid block, or null if none is found.
     */
    public static BlockPos getHighestSolidBlock(World world, BlockPos pos) {
        int maxHeight = world.getHeight();
        for (int y = maxHeight - 1; y >= world.getBottomY(); y--) {
            BlockPos checkPos = new BlockPos(pos.getX(), y, pos.getZ());
            if (world.getBlockState(checkPos).isSolidBlock(world, checkPos)) { // Check if the block is not air nor a solid block
                return checkPos;
            }
        }
        return null; // No non-air block found.
    }

    /**
     * Finds the block the player is looking at
     *
     * @param player The world to search in.
     * @param searchLength The amount of blocks to raycast
     * @return The BlockPos of the block a player is looking at
     */
    public static BlockPos getBlockPlayerIsLookingAt(PlayerEntity player, double searchLength) {
        Vec3d eyePos = player.getEyePos();
        Vec3d lookVec = player.getRotationVec(1.0F); // 1.0F is the partial tick for interpolation

        // Set the max distance
        Vec3d endPos = eyePos.add(lookVec.multiply(searchLength));

        BlockHitResult hitResult = player.getWorld().raycast(new RaycastContext(
                eyePos,
                endPos,
                RaycastContext.ShapeType.OUTLINE,
                RaycastContext.FluidHandling.NONE,
                player
        ));

        if (hitResult.getType() == HitResult.Type.BLOCK) {
            return hitResult.getBlockPos();
        }

        return null; // No block found
    }
}

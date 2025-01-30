package name.modid.enchantment.effect.midas;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import name.modid.util.BlockUtils;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.EnchantmentLocationBasedEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;


public record MidasTouchEnchantmentEffect(EnchantmentLevelBasedValue amount) implements EnchantmentLocationBasedEffect {
    public static final MapCodec<MidasTouchEnchantmentEffect> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    EnchantmentLevelBasedValue.CODEC.fieldOf("amount").forGetter(MidasTouchEnchantmentEffect::amount)
            ).apply(instance, MidasTouchEnchantmentEffect::new)
    );

    /*@Override
    public void remove(EnchantmentEffectContext context, Entity user, Vec3d pos, int level) {
        isServerTickOn = false;
    }*/

    private static boolean isServerTickOn = false;


    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity user, Vec3d pos, boolean newlyApplied) {
        BlockState goldBlockState = Blocks.GOLD_BLOCK.getDefaultState();
        Vec3d[] prevRotation = {user.getRotationVec(1.0f)};
        isServerTickOn = true;

        ServerTickEvents.START_WORLD_TICK.register(serverWorld -> {
            if(isServerTickOn) {
                Entity userEntity = serverWorld.getEntity(user.getUuid());
                if (userEntity instanceof PlayerEntity player) {
                    Vec3d currentRotation = player.getRotationVec(1.0f);

                    if (!player.isAlive()) {
                        isServerTickOn = false;
                    }

                    if (!currentRotation.equals(prevRotation[0])) {
                        prevRotation[0] = currentRotation;

                        BlockPos playerLookingBlock = BlockUtils.getBlockPlayerIsLookingAt((PlayerEntity) user, 75.0);

                        if (playerLookingBlock != null && !world.getBlockState(playerLookingBlock).equals(goldBlockState))
                            world.setBlockState(playerLookingBlock, goldBlockState);
                    }
                }
            }
        });
    }

    @Override
    public MapCodec<? extends EnchantmentLocationBasedEffect> getCodec() {
        return CODEC;
    }

}


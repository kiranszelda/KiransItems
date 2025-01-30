package name.modid.enchantment.effect.midas;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import name.modid.util.BlockUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.EnchantmentLocationBasedEffect;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public record MidasFeetEnchantmentEffect(EnchantmentLevelBasedValue amount) implements EnchantmentLocationBasedEffect {
    public static final MapCodec<MidasFeetEnchantmentEffect> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    EnchantmentLevelBasedValue.CODEC.fieldOf("amount").forGetter(MidasFeetEnchantmentEffect::amount)
            ).apply(instance, MidasFeetEnchantmentEffect::new)
    );

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity user, Vec3d pos, boolean newlyApplied) {
        BlockState goldBlockState = Blocks.GOLD_BLOCK.getDefaultState();
        BlockPos playerBlockPos = user.getBlockPos();
        BlockPos playerHighestBlock = BlockUtils.getHighestSolidBlock(world, playerBlockPos);

        if(!world.getBlockState(playerHighestBlock).equals(goldBlockState) && playerHighestBlock != null)
            world.setBlockState(playerHighestBlock, goldBlockState);
    }

    @Override
    public MapCodec<? extends EnchantmentLocationBasedEffect> getCodec() {
        return CODEC;
    }

}

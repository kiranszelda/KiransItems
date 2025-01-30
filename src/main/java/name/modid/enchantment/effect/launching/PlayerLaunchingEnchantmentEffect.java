package name.modid.enchantment.effect.launching;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public record PlayerLaunchingEnchantmentEffect(EnchantmentLevelBasedValue amount) implements EnchantmentEntityEffect {
    public static final MapCodec<PlayerLaunchingEnchantmentEffect> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    EnchantmentLevelBasedValue.CODEC.fieldOf("amount").forGetter(PlayerLaunchingEnchantmentEffect::amount)
            ).apply(instance, PlayerLaunchingEnchantmentEffect::new)
    );

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity target, Vec3d pos) {
        if (target instanceof LivingEntity victim) {
            if (context.owner() != null && context.owner() instanceof PlayerEntity player) {
                Vec3d playerRotationVec = player.getRotationVec(1.0f).multiply(0.2);
                victim.addVelocity(playerRotationVec.getX(), 0.5*this.amount.getValue(level) + playerRotationVec.getY(), playerRotationVec.getZ());
                player.addVelocity(playerRotationVec.getX(), 0.65*this.amount.getValue(level) + playerRotationVec.getY(), playerRotationVec.getZ());
                player.velocityModified = true;
            }
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}

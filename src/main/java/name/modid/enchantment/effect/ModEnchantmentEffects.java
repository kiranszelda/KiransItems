package name.modid.enchantment.effect;

import com.mojang.serialization.MapCodec;
import name.modid.enchantment.effect.launching.LaunchingEnchantmentEffect;
import name.modid.enchantment.effect.launching.PlayerLaunchingEnchantmentEffect;
import name.modid.enchantment.effect.midas.MidasFeetEnchantmentEffect;
import name.modid.enchantment.effect.midas.MidasTouchEnchantmentEffect;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.enchantment.effect.EnchantmentLocationBasedEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import name.modid.KiransItems;


public class ModEnchantmentEffects {

    public static final RegistryKey<Enchantment> MIDAS_TOUCH = of("midas_touch");
    public static MapCodec<MidasTouchEnchantmentEffect> MIDAS_TOUCH_EFFECT = registerLocation("midas_touch_effect", MidasTouchEnchantmentEffect.CODEC);

    public static final RegistryKey<Enchantment> MIDAS_FEET = of("midas_feet");
    public static MapCodec<MidasFeetEnchantmentEffect> MIDAS_FEET_EFFECT = registerLocation("midas_feet_effect", MidasFeetEnchantmentEffect.CODEC);

    public static final RegistryKey<Enchantment> LAUNCHING = of("launching");
    public static MapCodec<LaunchingEnchantmentEffect> LAUNCHING_EFFECT = registerEntity("launching_effect", LaunchingEnchantmentEffect.CODEC);

    public static final RegistryKey<Enchantment> PLAYER_LAUNCHING = of("player_launching");
    public static MapCodec<PlayerLaunchingEnchantmentEffect> PLAYER_LAUNCHING_EFFECT = registerEntity("player_launching_effect", PlayerLaunchingEnchantmentEffect.CODEC);

    private static RegistryKey<Enchantment> of(String path) {
        Identifier id = Identifier.of(KiransItems.MOD_ID, path);
        return RegistryKey.of(RegistryKeys.ENCHANTMENT, id);
    }

    private static <T extends EnchantmentLocationBasedEffect> MapCodec<T> registerLocation(String id, MapCodec<T> codec) {
        return Registry.register(Registries.ENCHANTMENT_LOCATION_BASED_EFFECT_TYPE, Identifier.of(KiransItems.MOD_ID, id), codec);
    }

    private static <T extends EnchantmentEntityEffect> MapCodec<T> registerEntity(String id, MapCodec<T> codec) {
        return Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, Identifier.of(KiransItems.MOD_ID, id), codec);
    }

    public static void registerModEnchantmentEffects() {
    }
}


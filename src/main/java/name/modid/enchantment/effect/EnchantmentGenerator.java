package name.modid.enchantment.effect;

import name.modid.enchantment.effect.midas.MidasFeetEnchantmentEffect;
import name.modid.enchantment.effect.midas.MidasTouchEnchantmentEffect;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class EnchantmentGenerator extends FabricDynamicRegistryProvider {
    public EnchantmentGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
        register(entries, ModEnchantmentEffects.MIDAS_TOUCH, Enchantment.builder(
                        Enchantment.definition(
                                registries.getOrThrow(RegistryKeys.ITEM).getOrThrow(ItemTags.CHEST_ARMOR_ENCHANTABLE),
                                10,
                                1,
                                Enchantment.leveledCost(1, 7),
                                Enchantment.leveledCost(1, 12),
                                4,
                                AttributeModifierSlot.CHEST
                        )
                ).addEffect(
                        EnchantmentEffectComponentTypes.LOCATION_CHANGED,
                        new MidasTouchEnchantmentEffect(EnchantmentLevelBasedValue.constant(1.0f))
                )
        );

        register(entries, ModEnchantmentEffects.MIDAS_FEET, Enchantment.builder(
                        Enchantment.definition(
                                registries.getOrThrow(RegistryKeys.ITEM).getOrThrow(ItemTags.FOOT_ARMOR_ENCHANTABLE),
                                10,
                                1,
                                Enchantment.leveledCost(1, 7),
                                Enchantment.leveledCost(1, 12),
                                4,
                                AttributeModifierSlot.FEET
                        )
                ).addEffect(
                        EnchantmentEffectComponentTypes.LOCATION_CHANGED,
                        new MidasFeetEnchantmentEffect(EnchantmentLevelBasedValue.constant(1.0f))
                )

        );
    }

    private void register(Entries entries, RegistryKey<Enchantment> key, Enchantment.Builder builder, ResourceCondition... resourceConditions) {
        entries.add(key, builder.build(key.getValue()), resourceConditions);
    }

    @Override
    public String getName() {
        return "KiransItemsEnchantmentGenerator";
    }
}


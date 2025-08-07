package me.tnoctua.soulbound.mixin;

import me.tnoctua.soulbound.Soulbound;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

import static me.tnoctua.soulbound.Soulbound.isStackSoulbound;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Inject(method = "appendTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;appendAttributeModifiersTooltip(Ljava/util/function/Consumer;Lnet/minecraft/component/type/TooltipDisplayComponent;Lnet/minecraft/entity/player/PlayerEntity;)V"))
    private void appendTooltip(Item.TooltipContext context, TooltipDisplayComponent displayComponent, PlayerEntity player, TooltipType type, Consumer<Text> textConsumer, CallbackInfo ci) {
        ItemStack stack = (ItemStack)(Object) this;
        if (isStackSoulbound(stack)) {
            textConsumer.accept(Text.translatable("tooltip.%s.soulbound".formatted(Soulbound.MOD_ID)).withColor(Soulbound.COLOR));
        }
    }

}

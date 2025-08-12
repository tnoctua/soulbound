package me.tnoctua.soulbound.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static me.tnoctua.soulbound.Soulbound.isStackSoulbound;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow @Nullable protected abstract ItemEntity createItemEntity(ItemStack stack, boolean atSelf, boolean retainOwnership);

    @Inject(method = "dropItem", at = @At("HEAD"), cancellable = true)
    private void dropItem(ItemStack stack, boolean dropAtSelf, boolean retainOwnership, CallbackInfoReturnable<ItemEntity> cir) {
        if (isStackSoulbound(stack)) {
            ItemEntity itemEntity = createItemEntity(stack, false, true);
            if (itemEntity != null) {
                itemEntity.setOwner(getUuid());
                this.getEntityWorld().spawnEntity(itemEntity);
            }

            cir.setReturnValue(itemEntity);
        }
    }

}

package me.tnoctua.soulbound.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;

import static me.tnoctua.soulbound.Soulbound.DEAD_PLAYERS;
import static me.tnoctua.soulbound.Soulbound.isStackSoulbound;

@Mixin(PlayerEntity.class)
public abstract class PlayerMixin extends Entity {

	private PlayerMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@Shadow public abstract PlayerInventory getInventory();

	@Inject(method = "dropInventory", at = @At("HEAD"))
	private void dropInventory(ServerWorld world, CallbackInfo ci) {
		ArrayList<ItemStack> soulboundStacks = new ArrayList<>();
		getInventory().forEach(stack -> {
			if (isStackSoulbound(stack)) {
				soulboundStacks.add(stack);
			}
		});
		DEAD_PLAYERS.put(getUuid(), soulboundStacks);
	}

}
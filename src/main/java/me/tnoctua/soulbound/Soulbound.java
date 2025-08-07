package me.tnoctua.soulbound;

import me.tnoctua.nmodutils.util.ModRegistry;
import me.tnoctua.soulbound.init.ModComponents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Soulbound implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("Soulbound Component");
	public static final String MOD_ID = "soulcomponent";
	public static final ModRegistry REGISTRY = new ModRegistry(MOD_ID);
	public static final int COLOR = new Color(4, 174, 174).getRGB();
	public static final HashMap<UUID, ArrayList<ItemStack>> DEAD_PLAYERS = new HashMap<>();

	@Override
	public void onInitialize() {
		ModComponents.init(); // Initialize components

		// Soulbound Component Event
		ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> {
			if (!alive && DEAD_PLAYERS.containsKey(oldPlayer.getUuid()) && DEAD_PLAYERS.get(oldPlayer.getUuid()) != null) {
				DEAD_PLAYERS.get(oldPlayer.getUuid()).forEach(newPlayer::giveItemStack);
				DEAD_PLAYERS.remove(oldPlayer.getUuid());
			}
		});
		// Prevent duplication glitch when exiting to the title screen after death
		ServerPlayerEvents.LEAVE.register(player -> DEAD_PLAYERS.remove(player.getUuid()));
	}

	/**
	 * Returns true if the provided item stack is a soulbound item.
	 * @param stack item stack to check
	 * @return {@code true} if the stack is soulbound, otherwise {@code false}
	 */
	public static boolean isStackSoulbound(ItemStack stack) {
		return stack.contains(ModComponents.SOULBOUND) && stack.get(ModComponents.SOULBOUND).booleanValue();
	}

}
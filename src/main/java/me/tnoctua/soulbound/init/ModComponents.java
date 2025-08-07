package me.tnoctua.soulbound.init;

import com.mojang.serialization.Codec;
import me.tnoctua.soulbound.Soulbound;
import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodecs;

import static me.tnoctua.nmodutils.util.Utils.addTranslation;
import static me.tnoctua.soulbound.Soulbound.REGISTRY;

public class ModComponents {

    public static final ComponentType<Boolean> SOULBOUND = REGISTRY.register("soulbound", (builder) -> builder.codec(Codec.BOOL).packetCodec(PacketCodecs.BOOLEAN));

    /**
     * Statically initializes mod components.
     */
    public static void init() {}

    static {
        addTranslation("tooltip.%s.soulbound".formatted(Soulbound.MOD_ID));
    }

}

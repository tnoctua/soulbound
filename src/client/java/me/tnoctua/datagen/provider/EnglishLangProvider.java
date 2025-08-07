package me.tnoctua.datagen.provider;

import me.tnoctua.nmodutils.datagen.NModLangProvider;
import me.tnoctua.soulbound.Soulbound;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class EnglishLangProvider extends NModLangProvider {

    public EnglishLangProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(Soulbound.REGISTRY, dataOutput, "en_us", registryLookup);
    }

}

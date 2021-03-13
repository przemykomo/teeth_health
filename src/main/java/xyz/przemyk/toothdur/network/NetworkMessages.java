package xyz.przemyk.toothdur.network;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import xyz.przemyk.toothdur.TeethHealthMod;

import java.util.Optional;

public class NetworkMessages {
    private static final String PROTOCOL_VERSION = "1";
    public static SimpleChannel INSTANCE;

    public static void register() {
        INSTANCE = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(TeethHealthMod.MODID, "main"),
                () -> PROTOCOL_VERSION,
                PROTOCOL_VERSION::equals,
                PROTOCOL_VERSION::equals);

        INSTANCE.registerMessage(0,
                PacketUpdateTeethHealth.class,
                PacketUpdateTeethHealth::toBytes,
                PacketUpdateTeethHealth::new,
                PacketUpdateTeethHealth::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));
    }
}

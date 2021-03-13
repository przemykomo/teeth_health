package xyz.przemyk.toothdur.network;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import xyz.przemyk.toothdur.capability.CapabilityTeethHealth;

import java.util.function.Supplier;

public class PacketUpdateTeethHealth {

    private final int durability;

    public PacketUpdateTeethHealth(int durability) {
        this.durability = durability;
    }

    public PacketUpdateTeethHealth(PacketBuffer buffer) {
        durability = buffer.readByte();
    }

    public void toBytes(PacketBuffer buffer) {
        buffer.writeByte(durability);
    }

    public void handle(Supplier<NetworkEvent.Context> ctxSup) {
        NetworkEvent.Context ctx = ctxSup.get();
        ctx.enqueueWork(() -> {
            PlayerEntity playerEntity = Minecraft.getInstance().player;
            if (playerEntity != null) {
                playerEntity.getCapability(CapabilityTeethHealth.TEETH_HEALTH).ifPresent(cap -> {
                    cap.health = durability;
                });
            }
        });
        ctx.setPacketHandled(true);
    }
}

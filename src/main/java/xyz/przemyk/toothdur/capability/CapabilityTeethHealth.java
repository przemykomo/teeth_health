package xyz.przemyk.toothdur.capability;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.network.PacketDistributor;
import xyz.przemyk.toothdur.TeethHealthMod;
import xyz.przemyk.toothdur.network.NetworkMessages;
import xyz.przemyk.toothdur.network.PacketUpdateTeethHealth;

public class CapabilityTeethHealth {

    public int health = 20;

    public void updateClient(ServerPlayerEntity playerEntity) {
        NetworkMessages.INSTANCE.send(PacketDistributor.PLAYER.with(() -> playerEntity), new PacketUpdateTeethHealth(health));
    }

    @CapabilityInject(CapabilityTeethHealth.class)
    public static final Capability<CapabilityTeethHealth> TEETH_HEALTH = null;
    public static final ResourceLocation ID = new ResourceLocation(TeethHealthMod.MODID, "teeth_health");

    public static void register() {
        CapabilityManager.INSTANCE.register(CapabilityTeethHealth.class, new IStorage<CapabilityTeethHealth>() {

            @Override
            public INBT writeNBT(Capability<CapabilityTeethHealth> capability, CapabilityTeethHealth instance, Direction side) {
                return IntNBT.valueOf(instance.health);
            }

            @Override
            public void readNBT(Capability<CapabilityTeethHealth> capability, CapabilityTeethHealth instance, Direction side, INBT nbt) {
                instance.health = ((IntNBT) nbt).getAsInt();
            }

        }, CapabilityTeethHealth::new);
    }
}

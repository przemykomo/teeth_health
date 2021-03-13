package xyz.przemyk.toothdur;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import xyz.przemyk.toothdur.capability.CapabilityTeethHealth;
import xyz.przemyk.toothdur.network.NetworkMessages;

@Mod(TeethHealthMod.MODID)
public class TeethHealthMod {
    public static final String MODID = "teeth_health";

    public TeethHealthMod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
    }

    public void commonSetup(FMLCommonSetupEvent event) {
        CapabilityTeethHealth.register();
        NetworkMessages.register();
    }
}

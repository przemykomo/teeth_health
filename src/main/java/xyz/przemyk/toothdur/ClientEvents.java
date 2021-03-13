package xyz.przemyk.toothdur;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.przemyk.toothdur.capability.CapabilityTeethHealth;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = TeethHealthMod.MODID)
public class ClientEvents {

    public static final int height = 49;
    public static final ResourceLocation TEXTURE = new ResourceLocation(TeethHealthMod.MODID, "textures/gui/hud.png");

    @SubscribeEvent
    public static void renderGameOverlay(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            Minecraft minecraft = Minecraft.getInstance();
            minecraft.player.getCapability(CapabilityTeethHealth.TEETH_HEALTH).ifPresent(cap -> {
                if (cap.health < 20) {
                    minecraft.getTextureManager().bind(TEXTURE);
                    MatrixStack matrixStack = event.getMatrixStack();

                    RenderSystem.enableBlend();
                    MainWindow mainWindow = minecraft.getWindow();
                    int left = mainWindow.getGuiScaledWidth() / 2 + 91;
                    int top = mainWindow.getGuiScaledHeight() - height;

                    for (int i = 0; i < 10; ++i) {
                        int idx = i * 2 + 1;
                        int x = left - i * 8 - 9;

                        AbstractGui.blit(matrixStack, x, top, -90, 16, 0, 9, 9, 256, 256);

                        if (idx < cap.health) {
                            AbstractGui.blit(matrixStack, x, top, -90, 52, 0, 9, 9, 256, 256);
                            continue;
                        } else if (idx == cap.health) {
                            AbstractGui.blit(matrixStack, x, top, -90, 61, 0, 9, 9, 256, 256);
                            continue;
                        }

                        break;
                    }
                    RenderSystem.disableBlend();
                }
            });
        }
    }
}

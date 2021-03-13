package xyz.przemyk.toothdur;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.przemyk.toothdur.capability.CapabilityTeethHealth;
import xyz.przemyk.toothdur.capability.TeethHealthProvider;

@Mod.EventBusSubscriber(modid = TeethHealthMod.MODID)
public class CommonEvents {

    @SubscribeEvent
    public static void onEat(LivingEntityUseItemEvent.Finish event) {
        Entity entity = event.getEntity();
        if (entity instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) entity;
            playerEntity.getCapability(CapabilityTeethHealth.TEETH_HEALTH).ifPresent(cap -> {
                Item item = event.getItem().getItem();
                if (item == Items.GOLDEN_APPLE) {
                    cap.health -= 3;
                    if (cap.health < 0) {
                        cap.health = 0;
                    }
                    if (!entity.level.isClientSide) {
                        cap.updateClient((ServerPlayerEntity) playerEntity);
                    }
                }
            });
        }
    }

    @SubscribeEvent
    public static void tickEat(LivingEntityUseItemEvent.Tick event) {
        Item item = event.getItem().getItem();
        Food food = item.getFoodProperties();
        if (food != null && event.getDuration() == 5) {
            Entity entity = event.getEntity();
            entity.getCapability(CapabilityTeethHealth.TEETH_HEALTH).ifPresent(cap -> {
                if (cap.health <= 0) {
                    entity.hurt(new EatingDamageSource(event.getItem()), 1.0f);
                    event.setCanceled(true);
                }
            });
        }
    }

    @SubscribeEvent
    public static void capabilityAttach(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity) {
            TeethHealthProvider provider = new TeethHealthProvider();
            event.addCapability(CapabilityTeethHealth.ID, provider);
            event.addListener(provider::invalidate);
        }
    }

    @SubscribeEvent
    public static void playerLoginEvent(PlayerEvent.PlayerLoggedInEvent event) {
        PlayerEntity playerEntity = event.getPlayer();
        if (!playerEntity.level.isClientSide) {
            playerEntity.getCapability(CapabilityTeethHealth.TEETH_HEALTH)
                    .ifPresent(cap -> cap.updateClient((ServerPlayerEntity) playerEntity));
        }
    }
}

package xyz.przemyk.toothdur;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class EatingDamageSource extends DamageSource {

    private final ItemStack itemStack;

    public EatingDamageSource(ItemStack itemStack) {
        super("death.hard_food");
        this.itemStack = itemStack;
    }

    @Override
    public ITextComponent getLocalizedDeathMessage(LivingEntity killedEntity) {
        return new TranslationTextComponent(msgId, killedEntity.getDisplayName(), itemStack.getDisplayName());
    }
}

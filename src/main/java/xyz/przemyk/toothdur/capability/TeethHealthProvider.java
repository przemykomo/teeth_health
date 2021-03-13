package xyz.przemyk.toothdur.capability;

import net.minecraft.nbt.IntNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class TeethHealthProvider implements ICapabilitySerializable<IntNBT> {

    private final CapabilityTeethHealth capability = new CapabilityTeethHealth();
    private final LazyOptional<CapabilityTeethHealth> lazyOptional = LazyOptional.of(() -> capability);

    public void invalidate() {
        lazyOptional.invalidate();
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (cap == CapabilityTeethHealth.TEETH_HEALTH) {
            return lazyOptional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public IntNBT serializeNBT() {
        return (IntNBT) CapabilityTeethHealth.TEETH_HEALTH.writeNBT(capability, null);
    }

    @Override
    public void deserializeNBT(IntNBT nbt) {
        CapabilityTeethHealth.TEETH_HEALTH.readNBT(capability, null, nbt);
    }
}

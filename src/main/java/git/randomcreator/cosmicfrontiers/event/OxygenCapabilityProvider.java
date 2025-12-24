package git.randomcreator.cosmicfrontiers.event;

import git.randomcreator.cosmicfrontiers.capability.OxygenCapability;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class OxygenCapabilityProvider implements ICapabilitySerializable<CompoundTag> {
    private final OxygenCapability oxygen = new OxygenCapability();
    private final LazyOptional<OxygenCapability> oxygenOptional = LazyOptional.of(() -> oxygen);

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == OxygenEventHandler.OXYGEN_CAPABILITY) {
            return oxygenOptional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        return oxygen.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        oxygen.deserializeNBT(nbt);
    }
}
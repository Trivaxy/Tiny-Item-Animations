package xyz.trivaxy.tia.mixin;

import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import xyz.trivaxy.tia.Animated;

@Mixin(Slot.class)
public class SlotMixin implements Animated {
    public float animationProgress = 0f;

    @Override
    public float getAnimationProgress() {
        return animationProgress;
    }

    @Override
    public void setAnimationProgress(float progress) {
        animationProgress = progress;
    }
}
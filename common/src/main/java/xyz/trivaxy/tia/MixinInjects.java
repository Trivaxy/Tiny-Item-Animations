package xyz.trivaxy.tia;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.inventory.Slot;

// just so hotswap works
public class MixinInjects {

    private static Slot currentlyRenderingSlot = null;
    private static float carriedAnimationProgress = 0f;

    public static void preRenderFloatingItem(GuiGraphics gui, int leftPos, int topPos, int mouseX, int mouseY, float partialTicks) {
        PoseStack poseStack = gui.pose();

        carriedAnimationProgress += partialTicks * ModConfigs.animationSpeed;
        if (carriedAnimationProgress > 1f)
            carriedAnimationProgress = 1f;

        poseStack.translate(-(leftPos - mouseX), -(topPos - mouseY - 4), 0);

        float scale = 1f + (ModConfigs.pickupScale - 1f) * (1 - (float)Math.pow(1 - carriedAnimationProgress, 5));
        poseStack.scale(scale, scale, scale);

        poseStack.translate(leftPos - mouseX, topPos - mouseY - 4, 0);
    }

    public static void postRenderSlot(Slot pSlot) {
        Animated slot = (Animated) pSlot;

        float progress = slot.getAnimationProgress();
        progress -= Minecraft.getInstance().getDeltaTracker().getRealtimeDeltaTicks() * ModConfigs.animationSpeed;
        if (progress < 0f)
            progress = 0f;

        slot.setAnimationProgress(progress);
        currentlyRenderingSlot = null;
    }

    public static void preRenderSlotItem(Slot slot) {
        currentlyRenderingSlot = slot;
    }

    public static void onRenderSlot(PoseStack pose) {
        if (currentlyRenderingSlot == null)
            return;

        Animated slot = (Animated) currentlyRenderingSlot;
        float scale = 1f + (ModConfigs.pickupScale - 1f) * (1 - (float)Math.pow(1 - slot.getAnimationProgress(), 5));
        pose.scale(scale, scale, scale);
    }

    public static void onSlotStackedOn(Slot pSlot) {
        Animated slot = (Animated) pSlot;
        slot.setAnimationProgress(1f);
        carriedAnimationProgress = 0f;
    }
}
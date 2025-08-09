package xyz.trivaxy.tia;

import net.minecraft.client.Minecraft;
import net.minecraft.world.inventory.Slot;
import org.joml.Matrix3x2fStack;

// just so hotswap works
public class MixinInjects {

    private static Slot currentlyRenderingSlot = null;
    private static float carriedAnimationProgress = 0f;
    private static boolean currenlyRenderingMouseItem = false;

    public static void preRenderItem(Matrix3x2fStack pose, int i, int j) {
        pose.pushMatrix();
        applyScaling(pose, i, j);
    }

    public static void postRenderItem(Matrix3x2fStack pose) {
        pose.popMatrix();
    }

    public static void preRenderItemDecorations(Matrix3x2fStack pose, int i, int j) {
        applyScaling(pose, i, j); // item decorations don't need a matrix push/pop
    }

    private static void applyScaling(Matrix3x2fStack pose, int i, int j) {
        float centerX = i + 8;
        float centerY = j + 8;

        pose.translate(centerX, centerY);

        if (currentlyRenderingSlot != null) {
            applyScaleForSlot(pose);
        }

        if (currenlyRenderingMouseItem) {
            applyScaleForMouseItem(pose);
        }

        pose.translate(-centerX, -centerY);
    }

    public static void applyScaleForMouseItem(Matrix3x2fStack pose) {
        carriedAnimationProgress += Minecraft.getInstance().getDeltaTracker().getGameTimeDeltaPartialTick(true) * ModConfigs.animationSpeed;
        if (carriedAnimationProgress > 1f)
            carriedAnimationProgress = 1f;

        float scale = 1f + (ModConfigs.pickupScale - 1f) * (1 - (float)Math.pow(1 - carriedAnimationProgress, 5));
        pose.scale(scale, scale);
    }

    public static void preRenderFloatingItem() {
        currenlyRenderingMouseItem = true;
    }

    public static void postRenderFloatingItem() {
        currenlyRenderingMouseItem = false;
    }

    public static void preRenderSlotItem(Slot slot) {
        currentlyRenderingSlot = slot;
    }

    public static void postRenderSlotItem(Slot pSlot) {
        Animated slot = (Animated) pSlot;

        float progress = slot.getAnimationProgress();
        progress -= Minecraft.getInstance().getDeltaTracker().getRealtimeDeltaTicks() * ModConfigs.animationSpeed;
        if (progress < 0f)
            progress = 0f;

        slot.setAnimationProgress(progress);
        currentlyRenderingSlot = null;
    }

    public static void applyScaleForSlot(Matrix3x2fStack pose) {
        if (currentlyRenderingSlot == null)
            return;

        Animated slot = (Animated) currentlyRenderingSlot;
        float scale = 1f + (ModConfigs.pickupScale - 1f) * (1 - (float)Math.pow(1 - slot.getAnimationProgress(), 5));
        pose.scale(scale, scale);
    }

    public static void onSlotStackedOn(Slot pSlot) {
        Animated slot = (Animated) pSlot;
        slot.setAnimationProgress(1f);
        carriedAnimationProgress = 0f;
    }
}
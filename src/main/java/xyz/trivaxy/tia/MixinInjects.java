package xyz.trivaxy.tia;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

// just so hotswap works
public class MixinInjects {
    private static Slot currentlyRenderingSlot = null;

    public static void preRenderFloatingItem(int leftPos, int topPos, int mouseX, int mouseY, float partialTicks, ItemStack draggingItem) {
        TiaMod.carriedAnimationProgress += partialTicks * TiaConfig.animationSpeed.get();
        if (TiaMod.carriedAnimationProgress > 1f)
            TiaMod.carriedAnimationProgress = 1f;

        PoseStack poseStack = RenderSystem.getModelViewStack();
        poseStack.translate(-(leftPos - mouseX), -(topPos - mouseY), 0);
        poseStack.translate(-4, -(draggingItem.isEmpty() ? 4 : 8), 0f);

        float scale = 1f + (TiaConfig.pickupScale.get().floatValue() - 1f) * (1 - (float)Math.pow(1 - TiaMod.carriedAnimationProgress, 5));
        poseStack.scale(scale, scale, scale);

        poseStack.translate((leftPos - mouseX), (topPos - mouseY), 0);
        poseStack.translate(4, (draggingItem.isEmpty() ? 4 : 8), 0f);
    }

    public static void postRenderSlot(Slot pSlot) {
        Animated slot = (Animated) pSlot;

        float progress = slot.getAnimationProgress();
        progress -= Minecraft.getInstance().getFrameTime() * TiaConfig.animationSpeed.get();
        if (progress < 0f)
            progress = 0f;

        slot.setAnimationProgress(progress);
    }

    public static void preRenderSlotItem(PoseStack pPoseStack, Slot pSlot, int leftPos, int topPos) {
        currentlyRenderingSlot = pSlot; // this is a bit hacky, but it works
    }

    public static void postRenderSlotItem(PoseStack pPoseStack) {
        currentlyRenderingSlot = null;
    }

    public static void whileRenderGuiItem(int pX, int pY) {
        if (currentlyRenderingSlot == null)
            return;

        Animated slot = (Animated) currentlyRenderingSlot;
        float scale = 1f + (TiaConfig.pickupScale.get().floatValue() - 1f) * (1 - (float)Math.pow(1 - slot.getAnimationProgress(), 5));
        RenderSystem.getModelViewStack().scale(scale, scale, scale);
    }

    public static void onSlotStackedOn(Slot pSlot) {
        Animated slot = (Animated) pSlot;
        slot.setAnimationProgress(1f);
        TiaMod.carriedAnimationProgress = 0f;
    }
}

package xyz.trivaxy.tia.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.trivaxy.tia.MixinInjects;

@Mixin(AbstractContainerScreen.class)
public class AbstractContainerScreenMixin {
    @Shadow protected int leftPos;

    @Shadow protected int topPos;

    @Shadow private ItemStack draggingItem;

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/AbstractContainerScreen;renderFloatingItem(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V"))
    private void injectRenderFloatingItem(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick, CallbackInfo ci) {
        MixinInjects.preRenderFloatingItem(pPoseStack, leftPos, topPos, pMouseX, pMouseY, pPartialTick, draggingItem);
    }

    @Inject(method = "renderSlot", at = @At("TAIL"))
    private void injectRenderSlotSetAnimation(PoseStack pPoseStack, Slot pSlot, CallbackInfo ci) {
        MixinInjects.postRenderSlot(pSlot);
    }

    @Inject(method = "renderSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/ItemRenderer;renderAndDecorateItem(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;III)V"))
    private void injectRenderSlotScale(PoseStack pPoseStack, Slot pSlot, CallbackInfo ci) {
        MixinInjects.preRenderSlotItem(pPoseStack, pSlot, leftPos, topPos);
    }

    @Inject(method = "renderSlot", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/client/renderer/entity/ItemRenderer;renderGuiItemDecorations(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V"))
    private void injectRenderSlotPopPose(PoseStack pPoseStack, Slot pSlot, CallbackInfo ci) {
        MixinInjects.postRenderSlotItem(pPoseStack);
    }
}
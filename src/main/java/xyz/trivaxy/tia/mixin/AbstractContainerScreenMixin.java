package xyz.trivaxy.tia.mixin;

import net.minecraft.client.gui.GuiGraphics;
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

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/AbstractContainerScreen;renderFloatingItem(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V"))
    private void injectRender(GuiGraphics gui, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        MixinInjects.preRenderFloatingItem(gui, leftPos, topPos, mouseX, mouseY, partialTicks);
    }

    @Inject(method = "renderSlot", at = @At("TAIL"))
    private void injectRenderSlotSetAnimation(GuiGraphics gui, Slot pSlot, CallbackInfo ci) {
        MixinInjects.postRenderSlot(pSlot);
    }

    @Inject(method = "renderSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;renderItem(Lnet/minecraft/world/item/ItemStack;III)V"))
    private void injectRenderSlotScale(GuiGraphics gui, Slot pSlot, CallbackInfo ci) {
        MixinInjects.preRenderSlotItem(gui, pSlot, leftPos, topPos);
    }
}

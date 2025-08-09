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

    @Inject(method = "renderFloatingItem", at = @At(value = "HEAD"))
    private void injectPreRenderFloatingItem(GuiGraphics gui, ItemStack $$1, int mouseX, int mouseY, String $$4, CallbackInfo ci) {
        MixinInjects.preRenderFloatingItem();
    }

    @Inject(method = "renderFloatingItem", at = @At(value = "TAIL"))
    private void injectPostRenderFloatingItem(GuiGraphics gui, ItemStack $$1, int mouseX, int mouseY, String $$4, CallbackInfo ci) {
        MixinInjects.postRenderFloatingItem();
    }

    @Inject(method = "renderSlot", at = @At("HEAD"))
    private void injectRenderSlotKeepTrack(GuiGraphics gui, Slot pSlot, CallbackInfo ci) {
        MixinInjects.preRenderSlotItem(pSlot);
    }

    @Inject(method = "renderSlot", at = @At("TAIL"))
    private void injectRenderSlotSetAnimation(GuiGraphics gui, Slot pSlot, CallbackInfo ci) {
        MixinInjects.postRenderSlotItem(pSlot);
    }
}
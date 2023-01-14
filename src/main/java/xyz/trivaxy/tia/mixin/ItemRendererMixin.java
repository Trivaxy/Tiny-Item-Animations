package xyz.trivaxy.tia.mixin;

import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.trivaxy.tia.MixinInjects;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
    @Inject(method = "renderGuiItem(Lnet/minecraft/world/item/ItemStack;IILnet/minecraft/client/resources/model/BakedModel;)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;applyModelViewMatrix()V", ordinal = 0))
    private void injectScaleItem(ItemStack pStack, int pX, int pY, BakedModel pBakedModel, CallbackInfo ci) {
        MixinInjects.whileRenderGuiItem(pX, pY);
    }
}

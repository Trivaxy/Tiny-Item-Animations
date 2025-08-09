package xyz.trivaxy.tia.mixin;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.joml.Matrix3x2fStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.trivaxy.tia.MixinInjects;

@Mixin(GuiGraphics.class)
public class GuiGraphicsMixin {

    @Shadow @Final private Matrix3x2fStack pose;

    @Inject(method = "renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;III)V", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/client/renderer/item/ItemModelResolver;updateForTopItem(Lnet/minecraft/client/renderer/item/ItemStackRenderState;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;I)V"))
    private void preRenderItem(LivingEntity $$0, Level $$1, ItemStack $$2, int i, int j, int $$5, CallbackInfo ci) {
        MixinInjects.preRenderItem(pose, i, j);
    }

    @Inject(method = "renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;III)V", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/client/gui/render/state/GuiRenderState;submitItem(Lnet/minecraft/client/gui/render/state/GuiItemRenderState;)V"))
    private void postRenderItem(LivingEntity $$0, Level $$1, ItemStack $$2, int $$3, int $$4, int $$5, CallbackInfo ci) {
        MixinInjects.postRenderItem(pose);
    }

    @Inject(method = "renderItemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lorg/joml/Matrix3x2fStack;pushMatrix()Lorg/joml/Matrix3x2fStack;"))
    private void preRenderItemDecorations(Font $$0, ItemStack $$1, int i, int j, String $$4, CallbackInfo ci) {
        MixinInjects.preRenderItemDecorations(pose, i, j);
    }
}

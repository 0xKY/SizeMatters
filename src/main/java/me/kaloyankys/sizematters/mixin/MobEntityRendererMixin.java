package me.kaloyankys.sizematters.mixin;

import me.kaloyankys.sizematters.SizeOfMob;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(MobEntityRenderer.class)
public abstract class MobEntityRendererMixin<T extends MobEntity, M extends EntityModel<T>> extends LivingEntityRenderer<T, M> implements SizeOfMob {

    public MobEntityRendererMixin(EntityRendererFactory.Context context, M entityModel, float f) {
        super(context, entityModel, f);
    }
    @Inject(at = @At("HEAD"), method = "render", cancellable = true)
    private void renderResized(T mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        //Rotate
        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(((SizeOfMob) mobEntity).getYaw()));
        matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(((SizeOfMob) mobEntity).getYaw()));
        matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(((SizeOfMob) mobEntity).getYaw()));
        //Scale
        matrixStack.scale(((SizeOfMob) mobEntity).getSize(), ((SizeOfMob) mobEntity).getSize(), ((SizeOfMob) mobEntity).getSize());
    }
}

package me.kaloyankys.sizematters.mixin;

import me.kaloyankys.sizematters.SizeMod;
import me.kaloyankys.sizematters.SizeOfMob;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin extends Entity implements SizeOfMob {

    private float mobSize = 1.0f;

    public MobEntityMixin(EntityType<?> entityType, World world) {
        super(entityType, world);
    }
    @Inject(at = @At("HEAD"), method = "interactMob", cancellable = true)
    private void interactWithSize(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (player.getStackInHand(hand).getItem() == SizeMod.ENLARGEMENT_PILL) {
            if (mobSize < 6.9f) {
                mobSize = mobSize + 0.1f;
                this.setSize(mobSize);
            }
            else {
                mobSize = 1.0f;
                this.getEntityWorld().createExplosion(this, this.getX(), this.getY(), this.getZ(),
                        3.5f, false, Explosion.DestructionType.BREAK);
                this.kill();
            }
            if (!player.isCreative()) {
                player.getStackInHand(hand).decrement(1);
            }
        }
        else if (player.getStackInHand(hand).getItem() == SizeMod.SHRINKING_PILL) {
            if(mobSize > 0.05f) {
                mobSize = mobSize - 0.025f;
                this.setSize(mobSize);
            }
            else {
                mobSize = 1.0f;
                this.getEntityWorld().createExplosion(this, this.getX(), this.getY(), this.getZ(),
                        3.5f, false, Explosion.DestructionType.BREAK);
                this.kill();
            }
            if(!player.isCreative()) {
                player.getStackInHand(hand).decrement(1);
            }
        }
    }

    @Override
    public void setSize(float size) {
        this.mobSize = size;
    }

    @Override
    public float getSize() {
        return this.mobSize;
    }
}


package me.kaloyankys.sizematters.mixin;

import me.kaloyankys.sizematters.SizeMod;
import me.kaloyankys.sizematters.SizeOfMob;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements SizeOfMob {

    @Shadow
    public abstract boolean isCreative();

    @Shadow public abstract void jump();

    private float mobSize = 1.0f;

    public PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("HEAD"), method = "eatFood", cancellable = true)
    private void interactWithSize(World world, ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
        if (stack.getItem() == SizeMod.ENLARGEMENT_PILL) {
            if (mobSize < 6.9f) {
                this.enlarge();
            } else {
                this.explode();
            }
        if (!this.isCreative()) {
            stack.decrement(1);
        }
    } else if (stack.getItem() == SizeMod.SHRINKING_PILL) {
            if (mobSize > 0.05f) {
                this.shrink();
            } else {
                this.explode();
            }
            if (!this.isCreative()) {
                stack.decrement(1);
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

    public void enlarge() {
        mobSize = mobSize + 0.1f;
        this.setSize(mobSize);
    }

    public void shrink() {
        mobSize = mobSize - 0.025f;
        this.setSize(mobSize);
    }

    public void explode() {
        mobSize = 1.0f;
        this.getEntityWorld().createExplosion(this, this.getX(), this.getY(), this.getZ(),
                3.5f, false, Explosion.DestructionType.BREAK);
        this.kill();
    }
}
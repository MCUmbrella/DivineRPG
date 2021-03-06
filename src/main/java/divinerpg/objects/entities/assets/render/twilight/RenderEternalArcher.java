package divinerpg.objects.entities.assets.render.twilight;

import divinerpg.api.Reference;
import divinerpg.objects.entities.assets.model.twilight.ModelEternalArcher;
import divinerpg.objects.entities.entity.twilight.EntityEternalArcher;
import divinerpg.registry.ModItems;
import divinerpg.registry.ModWeapons;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;

public class RenderEternalArcher extends RenderLiving<EntityEternalArcher> {

    ResourceLocation texture = new ResourceLocation(Reference.MODID, "textures/entity/eternal_archer.png");

    public RenderEternalArcher(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelEternalArcher(), 0);
        addLayer(new HandsLayer(this));
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityEternalArcher entity) {
        return texture;
    }

    @Override
    public void preRenderCallback(EntityEternalArcher entity, float f) {
        GL11.glScalef(2.5f, 2.5f, 2.5f);
    }

    private class HandsLayer implements LayerRenderer<EntityEternalArcher> {
        protected final RenderEternalArcher renderEternalArcher;

        public HandsLayer(RenderEternalArcher renderEternalArcherIn) {
            this.renderEternalArcher = renderEternalArcherIn;
        }

        @Override
        public void doRenderLayer(EntityEternalArcher entity, float limbSwing, float limbSwingAmount, float partialTicks,
                                  float ageInTicks, float netHeadYaw, float headPitch, float scale) {
            for (ModelRenderer renderRight : ((ModelEternalArcher) this.renderEternalArcher.getMainModel()).armsRight) {
                GlStateManager.enableRescaleNormal();
                GlStateManager.pushMatrix();
                renderRight.postRender(0.0625F);
                GlStateManager.translate(-0.0625F, 0.4375F, 0.0625F);
                GlStateManager.translate(0.03F, 0F, 0F);
                GlStateManager.rotate(90, 0, 1, 0);
                GlStateManager.rotate(45, 0, 0, -1);
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                Minecraft.getMinecraft().getItemRenderer().renderItem(entity, new ItemStack(ModWeapons.haliteBow),
                        ItemCameraTransforms.TransformType.NONE);
                GlStateManager.popMatrix();
                GlStateManager.disableRescaleNormal();
            }
            for (ModelRenderer renderLeft : ((ModelEternalArcher) this.renderEternalArcher.getMainModel()).armsLeft) {
                GlStateManager.enableRescaleNormal();
                GlStateManager.pushMatrix();
                renderLeft.postRender(0.0625F);
                GlStateManager.translate(-0.0625F, 0.4375F, 0.0625F);
                GlStateManager.translate(0.08F, 0F, 0F);
                GlStateManager.rotate(90, 0, 1, 0);
                GlStateManager.rotate(45, 0, 0, -1);
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                Minecraft.getMinecraft().getItemRenderer().renderItem(entity, new ItemStack(ModWeapons.haliteBow),
                        ItemCameraTransforms.TransformType.NONE);
                GlStateManager.popMatrix();
                GlStateManager.disableRescaleNormal();
            }
        }

        @Override
        public boolean shouldCombineTextures() {
            return false;
        }
    }
}
package net.valhelsia.valhelsia_core.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.valhelsia.valhelsia_core.client.Cosmetic;
import net.valhelsia.valhelsia_core.client.CosmeticsCategory;
import net.valhelsia.valhelsia_core.client.CosmeticsManager;
import net.valhelsia.valhelsia_core.client.CosmeticsModels;
import net.valhelsia.valhelsia_core.client.model.CauldronBackpackModel;
import net.valhelsia.valhelsia_core.client.model.CosmeticsModel;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;

/**
 * Cosmetics Back Layer <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.renderer.CosmeticsBackLayer
 *
 * @author Valhelsia Team
 * @version 16.0.13
 * @since 2021-10-27
 */
public class CosmeticsBackLayer<T extends AbstractClientPlayerEntity> extends LayerRenderer<T, PlayerModel<T>> {

    private final CosmeticsManager cosmeticsManager;
    private CosmeticsModel<T> model;

    public CosmeticsBackLayer(LivingRenderer<T, PlayerModel<T>> entityRenderer) {
        super(entityRenderer);
        this.model = new CauldronBackpackModel<>();
        this.cosmeticsManager = CosmeticsManager.getInstance();
    }

    @Override
    public void render(@Nonnull MatrixStack poseStack, @Nonnull IRenderTypeBuffer buffer, int packedLight, @Nonnull T player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        UUID uuid = player.getUniqueID();
        List<Cosmetic> cosmetics = cosmeticsManager.getCosmeticsForPlayer(uuid, CosmeticsCategory.BACK);
        Cosmetic activeCosmetic = cosmeticsManager.getActiveCosmeticForPlayer(uuid, CosmeticsCategory.BACK);

        if (activeCosmetic == null || !cosmetics.contains(activeCosmetic) || activeCosmetic.getName().contains("cape")) {
            return;
        }

        ResourceLocation texture = cosmeticsManager.getCosmeticTexture(activeCosmetic);

        if (texture == null) {
            return;
        }

        model = (CosmeticsModel<T>) CosmeticsModels.getFromCosmetic(activeCosmetic);

        poseStack.push();

        //this.getParentModel().get().translateAndRotate(poseStack);

        if (this.model != null) {
            IVertexBuilder vertexConsumer = buffer.getBuffer(RenderType.getEntityCutout(texture));

            this.model.setPosition(poseStack);
            this.model.getModel().render(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }

//        Random random = player.getRNG();
//        if (this.model == CosmeticsModels.CAULDRON_BACKPACK.getModel() && random.nextDouble() < 0.1D) {
//            double dx = player.getPosX() - (player.getLookVec().x * 3);
//            double dz = player.getPosZ() - (player.getLookVec().z * 3);
//
//            Particle particle = Minecraft.getInstance().particles.addParticle(ParticleTypes.EFFECT, dx, player.getPosY() + 1.5D, dz, 0.0D, random.nextDouble() / 5.0D, 0.0D);
//
//            int color = 3381504;
//            float f2 = 0.75F + random.nextFloat() * 0.25F;
//            particle.setColor((float)((color >> 16 & 255) / 255.0) * f2, (float)((color >> 8 & 255) / 255.0) *f2, (float)((color >> 0 & 255) / 255.0) * f2);
//            //player.worldClient.ren(ParticleTypes.EFFECT, player.getPosX(), player.getPosY() + 1.5D, player.getPosZ() + 0.5D, 0.0D, player.getRNG().nextDouble() / 5.0D, 0.0D);
//           // particle.mo
//        }

        poseStack.pop();
    }

}

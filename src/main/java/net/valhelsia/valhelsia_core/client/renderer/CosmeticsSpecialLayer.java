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
import net.valhelsia.valhelsia_core.client.model.CosmeticsModel;
import net.valhelsia.valhelsia_core.client.model.WitchHatModel;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;

/**
 * Cosmetics Hand Layer <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.renderer.CosmeticsHandLayer
 *
 * @author Valhelsia Team
 * @version 16.0.13
 * @since 2021-10-27
 */
public class CosmeticsSpecialLayer<T extends AbstractClientPlayerEntity> extends LayerRenderer<T, PlayerModel<T>> {

    private final CosmeticsManager cosmeticsManager;
    private CosmeticsModel<T> model;

    public CosmeticsSpecialLayer(LivingRenderer<T, PlayerModel<T>> entityRenderer) {
        super(entityRenderer);
        this.model = new WitchHatModel<>();
        this.cosmeticsManager = CosmeticsManager.getInstance();
    }

    @Override
    public void render(@Nonnull MatrixStack poseStack, @Nonnull IRenderTypeBuffer buffer, int packedLight, @Nonnull T player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        UUID uuid = player.getUniqueID();
        List<Cosmetic> cosmetics = cosmeticsManager.getCosmeticsForPlayer(uuid, CosmeticsCategory.SPECIAL);
        Cosmetic activeCosmetic = cosmeticsManager.getActiveCosmeticForPlayer(uuid, CosmeticsCategory.SPECIAL);

        if (activeCosmetic == null || !cosmetics.contains(activeCosmetic)) {
            return;
        }

        ResourceLocation texture = cosmeticsManager.getCosmeticTexture(activeCosmetic);

        if (texture == null) {
            return;
        }

        model = (CosmeticsModel<T>) CosmeticsModels.getFromCosmetic(activeCosmetic);

        poseStack.push();

        if (this.model.translateToParent()) {
            this.getEntityModel().translateHand(player.getPrimaryHand().opposite(), poseStack);
        }

        if (this.model != null) {
            IVertexBuilder vertexConsumer = buffer.getBuffer(RenderType.getEntityCutout(texture));

            this.model.setPosition(poseStack);
            this.model.getModel().render(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }

        poseStack.pop();
    }

}

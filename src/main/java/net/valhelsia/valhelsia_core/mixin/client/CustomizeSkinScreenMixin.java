package net.valhelsia.valhelsia_core.mixin.client;

import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.CustomizeSkinScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.SettingsScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.valhelsia.valhelsia_core.ValhelsiaCore;
import net.valhelsia.valhelsia_core.client.CosmeticsManager;
import net.valhelsia.valhelsia_core.client.screen.CosmeticsWardrobeScreen;
import net.valhelsia.valhelsia_core.client.screen.NoCosmeticsScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.UUID;

/**
 * Customize Skin Screen Mixin <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.mixin.client.CustomizeSkinScreenMixin
 *
 * @author Valhelsia Team
 * @version 16.0.11
 * @since 2021-08-08
 */
@Mixin(CustomizeSkinScreen.class)
public class CustomizeSkinScreenMixin extends SettingsScreen {

    public CustomizeSkinScreenMixin(Screen previousScreen, GameSettings gameSettingsObj, ITextComponent textComponent) {
        super(previousScreen, gameSettingsObj, textComponent);
    }

    @ModifyVariable(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/CustomizeSkinScreen;addButton(Lnet/minecraft/client/gui/widget/Widget;)Lnet/minecraft/client/gui/widget/Widget;", ordinal = 1, shift = At.Shift.AFTER), method = "init", ordinal = 0)
    public int init(int i) {
        Minecraft minecraft = this.getMinecraft();
        ++i;

        CosmeticsManager cosmeticsManager = CosmeticsManager.getInstance();
        UUID uuid = this.getMinecraft().getSession().getProfile().getId();

        this.addButton(new Button(this.width / 2 - 155 + i % 2 * 160, this.height / 6 + 24 * (i >> 1), 150, 20,
                new TranslationTextComponent("gui." + ValhelsiaCore.MOD_ID + ".cosmeticsSettings").appendString("..."),
                (button) -> {
                    if (!cosmeticsManager.getLoadedPlayers().contains(uuid) || cosmeticsManager.getCosmeticsForPlayer(uuid).isEmpty()) {
                        minecraft.displayGuiScreen(new NoCosmeticsScreen(this));
                    } else {
                        minecraft.displayGuiScreen(new CosmeticsWardrobeScreen(this));
                    }
        }));

        return i;
    }
}

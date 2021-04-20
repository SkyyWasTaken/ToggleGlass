package us.skyywastaken.hideglass.misc;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import us.skyywastaken.hideglass.HideGlass;

public class GlassKeybindingManager {
    public static KeyBinding hideGlass;

    public static void initKeybind() {
        hideGlass = new KeyBinding("key.hideglass", Keyboard.KEY_G, HideGlass.MODID);
        ClientRegistry.registerKeyBinding(hideGlass);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent event) {
        if(hideGlass.isPressed()) {
            GlassBehaviorManager.glassIsHidden = !GlassBehaviorManager.glassIsHidden;
            Minecraft.getMinecraft().renderGlobal.loadRenderers();
        }
    }
}

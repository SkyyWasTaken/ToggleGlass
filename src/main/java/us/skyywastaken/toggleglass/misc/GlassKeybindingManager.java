package us.skyywastaken.toggleglass.misc;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

public class GlassKeybindingManager {
    public static KeyBinding hideGlass;

    public static void initKeybind() {
        hideGlass = new KeyBinding("key.toggleglass", Keyboard.KEY_G, "ToggleGlass");
        ClientRegistry.registerKeyBinding(hideGlass);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent event) {
        if (hideGlass.isPressed()) {
            GlassBehaviorManager.glassIsHidden = !GlassBehaviorManager.glassIsHidden;
            Minecraft.getMinecraft().renderGlobal.loadRenderers();
            EntityPlayerSP clientPlayer = Minecraft.getMinecraft().thePlayer;
            playSuccessNoise(clientPlayer);
            sendToggleSuccessMessage(clientPlayer);
        }
    }

    private void playSuccessNoise(EntityPlayerSP clientPlayer) {
        BlockPos playerPos = clientPlayer.getPosition();
        Minecraft.getMinecraft().theWorld.playSoundAtPos(playerPos, "note.bass", Float.MAX_VALUE,
                2, false);
    }

    private void sendToggleSuccessMessage(EntityPlayerSP clientPlayer) {
        String toggleMessage = EnumChatFormatting.GREEN + "Glass is now " + EnumChatFormatting.AQUA
                + EnumChatFormatting.BOLD + (GlassBehaviorManager.glassIsHidden ? "INVISIBLE" : "VISIBLE")
                + EnumChatFormatting.RESET + EnumChatFormatting.GREEN + "!";
        clientPlayer.addChatMessage(new ChatComponentText(toggleMessage));
    }
}

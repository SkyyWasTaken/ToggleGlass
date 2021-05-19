package us.skyywastaken.toggleglass;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import us.skyywastaken.toggleglass.misc.GlassKeybindingManager;

@Mod(modid = ToggleGlass.MODID, name = ToggleGlass.NAME, version = ToggleGlass.VERSION)
public class ToggleGlass {
    public static final String MODID = "toggleglass";
    public static final String VERSION = "0.0.1";
    public static final String NAME = "Toggle Glass";

    @EventHandler
    public void init(FMLInitializationEvent event) {
        GlassKeybindingManager.initKeybind();
        MinecraftForge.EVENT_BUS.register(new GlassKeybindingManager());
    }
}

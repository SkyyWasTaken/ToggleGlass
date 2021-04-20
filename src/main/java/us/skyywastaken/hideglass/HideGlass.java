package us.skyywastaken.hideglass;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import us.skyywastaken.hideglass.misc.GlassKeybindingManager;

@Mod(modid = HideGlass.MODID, name = HideGlass.NAME, version = HideGlass.VERSION)
public class HideGlass
{
    public static final String MODID = "hideglass";
    public static final String VERSION = "0.0.1";
    public static final String NAME = "Hide Glass";
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        GlassKeybindingManager.initKeybind();
        MinecraftForge.EVENT_BUS.register(new GlassKeybindingManager());
        System.out.println("TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST");
    }
}

package us.skyywastaken.hideglass.asm;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;


@IFMLLoadingPlugin.TransformerExclusions({"us.skyywastaken.hideglass.asm", "us.skyywastaken.hideglass.patch.GlassPatch"})
@IFMLLoadingPlugin.MCVersion("1.8.9")
public class HideGlassCorePlugin implements IFMLLoadingPlugin {
    @Override
    public String[] getASMTransformerClass() {
        return new String[]{"us.skyywastaken.hideglass.asm.BlockGlassTransformer"};
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}

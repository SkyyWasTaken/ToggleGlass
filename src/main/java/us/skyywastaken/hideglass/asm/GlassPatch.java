package us.skyywastaken.hideglass.asm;

import net.minecraft.block.BlockBreakable;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import us.skyywastaken.hideglass.misc.GlassBehaviorManager;

public class GlassPatch extends BlockBreakable {
    public GlassPatch(Material materialIn, boolean ignoreSimilarity)
    {
        super(materialIn, ignoreSimilarity);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side){
        if(GlassBehaviorManager.glassIsHidden) {
            return false;
        } else {
            return super.shouldSideBeRendered(worldIn, pos, side);
        }
    }
}

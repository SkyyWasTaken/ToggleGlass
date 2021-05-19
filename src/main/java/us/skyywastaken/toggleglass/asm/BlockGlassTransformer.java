package us.skyywastaken.toggleglass.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

public class BlockGlassTransformer implements IClassTransformer {
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        MethodNode newMethodNode;
        switch (name) {
            case "net.minecraft.block.BlockGlass":
                newMethodNode = GlassMethodHelper.getGlassMethod(false);
                break;
            case "net.minecraft.block.BlockPane":
                newMethodNode = GlassMethodHelper.getGlassPaneMethod(false);
                break;
            case "ahc":
                newMethodNode = GlassMethodHelper.getGlassMethod(true);
                break;
            case "ajs":
                newMethodNode = GlassMethodHelper.getStainedGlassMethod(true);
                break;
            case "ajt":
                newMethodNode = GlassMethodHelper.getStainedGlassPaneMethod(true);
                break;
            case "akd":
                newMethodNode = GlassMethodHelper.getGlassPaneMethod(true);
                break;
            case "net.minecraft.block.BlockStainedGlassPane":
                newMethodNode = GlassMethodHelper.getStainedGlassPaneMethod(false);
                break;
            case "net.minecraft.block.BlockStainedGlass":
                newMethodNode = GlassMethodHelper.getStainedGlassMethod(false);
                break;
            default:
                return basicClass;
        }

        return this.transformGlassClass(basicClass, newMethodNode);
    }

    private byte[] transformGlassClass(byte[] classToTransform, MethodNode newMethod) {
        try {
            ClassNode classNode = ASMUtils.getClassNode(classToTransform);
            this.addNewRenderMethod(classNode, newMethod);
            return ASMUtils.getByteArrayFromClassNode(classNode);
        } catch (Exception var4) {
            var4.printStackTrace();
            return classToTransform;
        }
    }

    private void addNewRenderMethod(ClassNode classToTransform, MethodNode newRenderMethod) {
        classToTransform.methods.add(newRenderMethod);
    }
}

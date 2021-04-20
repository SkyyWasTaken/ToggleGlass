package us.skyywastaken.hideglass.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.io.FileOutputStream;
import java.io.IOException;

public class BlockGlassTransformer implements IClassTransformer {
    private static String deObfuscatedGlassMapping = "net.minecraft.block.BlockGlass";
    private static String deObfuscatedStainedGlassMapping = "net.minecraft.block.BlockStainedGlass";
    private static String obfuscatedGlassMapping = "ahc";
    private static String obfuscatedStainedGlassMapping = "ajs";

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if(name.equals(deObfuscatedGlassMapping)) {
            return transformGlassClass(basicClass, GlassMethodHelper.getGlassMethod(false));
        } else if (name.equals(obfuscatedGlassMapping)) {
            return transformGlassClass(basicClass, GlassMethodHelper.getGlassMethod(true));
        } else if (name.equals(deObfuscatedStainedGlassMapping)) {
            return transformGlassClass(basicClass, GlassMethodHelper.getStainedGlassMethod(false));
        } else if (name.equals(obfuscatedStainedGlassMapping)) {
            return transformGlassClass(basicClass, GlassMethodHelper.getStainedGlassMethod(true));
        }
        return basicClass;
    }

    private byte[] transformGlassClass(byte[] classToTransform, MethodNode newMethod) {
        try {
            ClassNode classNode = ASMUtils.getClassNode(classToTransform);
            addNewRenderMethod(classNode, newMethod);
            return ASMUtils.getByteArrayFromClassNode(classNode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classToTransform;
    }

    private void addNewRenderMethod(ClassNode classToTransform, MethodNode newRenderMethod) {
        classToTransform.methods.add(newRenderMethod);
    }

}

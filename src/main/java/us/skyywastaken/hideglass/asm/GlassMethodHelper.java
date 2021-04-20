package us.skyywastaken.hideglass.asm;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FrameNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class GlassMethodHelper {

    public static MethodNode getGlassMethod(boolean isObfuscated) {
        HashMap<String, String> mappings;
        if(isObfuscated) {
            mappings = getObfuscatedGlassMappings(false);
        } else {
            mappings = getDeObfuscatedGlassMappings(false);
        }
        return getNewPatchMethod(mappings);
    }

    public static MethodNode getStainedGlassMethod(boolean isObfuscated) {
        HashMap<String, String> mappings;
        if(isObfuscated) {
            mappings = getObfuscatedGlassMappings(true);
        } else {
            mappings = getDeObfuscatedGlassMappings(true);
        }
        return getNewPatchMethod(mappings);
    }


    public static MethodNode getNewPatchMethod(HashMap<String, String> mappings) {
        /* Method contents:
           if(glassIsHidden) {
            return false;
           } else {
            return super.patchedMethod();
           }
         */

        MethodNode methodNode = new MethodNode();
        methodNode.name = mappings.get("sideRenderMethodName");
        methodNode.desc = mappings.get("sideRenderMethodDesc");
        methodNode.maxStack = 4;
        methodNode.maxLocals = 4;
        methodNode.exceptions = new ArrayList<>();
        methodNode.access = 1;

        AnnotationNode clientOnlyAnnotation = new AnnotationNode("net/minecraftforge/fml/relauncher/SideOnly");
        clientOnlyAnnotation.values = new ArrayList<>();
        clientOnlyAnnotation.values.add("value");
        clientOnlyAnnotation.values.add(new String[]{"net/minecraftforge/fml/relauncher/Side", "CLIENT"});
        methodNode.visibleAnnotations = new ArrayList<>(Collections.singleton(clientOnlyAnnotation));
        LabelNode labelZero = new LabelNode();
        LabelNode labelOne = new LabelNode();
        LabelNode labelTwo = new LabelNode();
        LabelNode labelThree = new LabelNode();

        // LABEL 0
        LineNumberNode lineNumberZero = new LineNumberNode(3621, labelOne);
        FieldInsnNode glassIsHiddenVariable = new FieldInsnNode(Opcodes.GETSTATIC,
                "us/skyywastaken/hideglass/misc/GlassBehaviorManager", "glassIsHidden", "Z");
        JumpInsnNode ifGlassIsHidden = new JumpInsnNode(Opcodes.IFEQ, labelOne);

        // LABEL 2
        LineNumberNode lineNumberTwo = new LineNumberNode(3622, labelTwo);
        InsnNode falseValue = new InsnNode(Opcodes.ICONST_0);
        InsnNode returnInsn = new InsnNode(Opcodes.IRETURN);

        // LABEL 1
        LineNumberNode lineNumberOne = new LineNumberNode(3623, labelOne);
        FrameNode frameNode = new FrameNode(Opcodes.F_SAME, 4, null, 4, null);
        VarInsnNode varInsnNodeZero = new VarInsnNode(Opcodes.ALOAD, 0);
        VarInsnNode varInsnNodeOne = new VarInsnNode(Opcodes.ALOAD, 1);
        VarInsnNode varInsnNodeTwo = new VarInsnNode(Opcodes.ALOAD, 2);
        VarInsnNode varInsnNodeThree = new VarInsnNode(Opcodes.ALOAD, 3);
        MethodInsnNode invokeDefaultMethod = new MethodInsnNode(Opcodes.INVOKESPECIAL,
                mappings.get("asmParentClassLocation"), mappings.get("sideRenderMethodName"),
                mappings.get("sideRenderMethodDesc"), false);
        InsnNode returnInsnTwo = new InsnNode(Opcodes.IRETURN);

        // LABEL 3
        LocalVariableNode thisLocalVariableNode = new LocalVariableNode("this", "L"
                + mappings.get("asmGlassClassLocation") + ";", null, labelZero, labelThree, 0);
        LocalVariableNode worldInLocalVariableNode = new LocalVariableNode("worldIn", "L"
                + mappings.get("asmBlockAccessLocation") + ";", null, labelZero, labelThree, 1);
        LocalVariableNode posLocalVariableNode = new LocalVariableNode("pos", "L"
                + mappings.get("asmBlockPosLocation") + ";", null, labelZero, labelThree, 2);
        LocalVariableNode sideLocalVariableNode = new LocalVariableNode("side", "L"
                + mappings.get("asmEnumFacingLocation") + ";", null, labelZero, labelThree, 3);
        List<LocalVariableNode> localVariableNodes = new ArrayList<>();
        localVariableNodes.add(thisLocalVariableNode);
        localVariableNodes.add(worldInLocalVariableNode);
        localVariableNodes.add(posLocalVariableNode);
        localVariableNodes.add(sideLocalVariableNode);
        methodNode.localVariables = localVariableNodes;

        InsnList hideGlassInstructions = methodNode.instructions;
        hideGlassInstructions.add(labelZero);
        hideGlassInstructions.add(lineNumberZero);
        hideGlassInstructions.add(glassIsHiddenVariable);
        hideGlassInstructions.add(ifGlassIsHidden);

        hideGlassInstructions.add(labelTwo);
        hideGlassInstructions.add(lineNumberTwo);
        hideGlassInstructions.add(falseValue);
        hideGlassInstructions.add(returnInsn);

        hideGlassInstructions.add(labelOne);
        hideGlassInstructions.add(lineNumberOne);
        hideGlassInstructions.add(frameNode);
        hideGlassInstructions.add(varInsnNodeZero);
        hideGlassInstructions.add(varInsnNodeOne);
        hideGlassInstructions.add(varInsnNodeTwo);
        hideGlassInstructions.add(varInsnNodeThree);
        hideGlassInstructions.add(invokeDefaultMethod);
        hideGlassInstructions.add(returnInsnTwo);

        hideGlassInstructions.add(labelThree);

        return methodNode;
    }

    private static HashMap<String, String> getDeObfuscatedGlassMappings(boolean isStained) {
        HashMap<String, String> returnMappings = new HashMap<>();
        if(!isStained) {
            returnMappings.put("glassClassName", "net.minecraft.block.BlockGlass");
            returnMappings.put("asmGlassClassLocation", "net/minecraft/block/BlockGlass");
        } else {
            returnMappings.put("glassClassName", "net.minecraft.block.BlockStainedGlass");
            returnMappings.put("asmGlassClassLocation", "net/minecraft/block/BlockStainedGlass");
        }
        returnMappings.put("parentClassName", "net.minecraft.block.BlockBreakable");
        returnMappings.put("asmParentClassLocation", "net/minecraft/block/BlockBreakable");
        returnMappings.put("sideRenderMethodName", "shouldSideBeRendered");
        returnMappings.put("sideRenderMethodDesc", "(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;)Z");
        returnMappings.put("asmBlockAccessLocation", "net/minecraft/world/IBlockAccess");
        returnMappings.put("asmBlockPosLocation", "net/minecraft/util/BlockPos");
        returnMappings.put("asmEnumFacingLocation", "net/minecraft/util/EnumFacing");
        return returnMappings;
    }

    private static HashMap<String, String> getObfuscatedGlassMappings(boolean isStained) {
        HashMap<String, String> returnMappings = new HashMap<>();
        if(!isStained) {
            returnMappings.put("glassClassName", "ahc");
            returnMappings.put("asmGlassClassLocation", "ahc");
        } else {
            returnMappings.put("glassClassName", "ajs");
            returnMappings.put("asmGlassClassLocation", "ajs");
        }
        returnMappings.put("parentClassName", "ahj");
        returnMappings.put("asmParentClassLocation", "ahj");
        returnMappings.put("sideRenderMethodName", "func_176225_a");
        returnMappings.put("sideRenderMethodDesc", "(Ladq;Lcj;Lcq;)Z");
        returnMappings.put("asmBlockAccessLocation", "adq");
        returnMappings.put("asmBlockPosLocation", "cj");
        returnMappings.put("asmEnumFacingLocation", "cq");
        return returnMappings;
    }
}

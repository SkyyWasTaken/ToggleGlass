package us.skyywastaken.toggleglass.asm;

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

public class GlassMethodHelper {
    public GlassMethodHelper() {
    }

    public static MethodNode getGlassMethod(boolean isObfuscated) {
        HashMap<String, String> mappings;
        if (isObfuscated) {
            mappings = getObfuscatedGlassMappings(false);
        } else {
            mappings = getDeObfuscatedGlassMappings(false);
        }

        return getNewPatchMethod(mappings);
    }

    public static MethodNode getStainedGlassMethod(boolean isObfuscated) {
        HashMap<String, String> mappings;
        if (isObfuscated) {
            mappings = getObfuscatedGlassMappings(true);
        } else {
            mappings = getDeObfuscatedGlassMappings(true);
        }

        return getNewPatchMethod(mappings);
    }

    public static MethodNode getGlassPaneMethod(boolean isObfuscated) {
        HashMap<String, String> mappings;
        if (isObfuscated) {
            mappings = getObfuscatedGlassPaneMappings(false);
        } else {
            mappings = getDeObfuscatedGlassPaneMappings(false);
        }

        return getNewPatchMethod(mappings);
    }

    public static MethodNode getStainedGlassPaneMethod(boolean isObfuscated) {
        HashMap<String, String> mappings;
        if (isObfuscated) {
            mappings = getObfuscatedGlassPaneMappings(true);
        } else {
            mappings = getDeObfuscatedGlassPaneMappings(true);
        }

        return getNewPatchMethod(mappings);
    }

    public static MethodNode getNewPatchMethod(HashMap<String, String> mappings) {

        MethodNode returnNode = new MethodNode();
        returnNode.name = mappings.get("renderTypeMethodName");
        returnNode.desc = "()I";
        returnNode.maxStack = 4;
        returnNode.maxLocals = 4;
        returnNode.exceptions = new ArrayList<>();
        returnNode.access = 1;

        AnnotationNode clientOnlyAnnotation = new AnnotationNode("net/minecraftforge/fml/relauncher/SideOnly");
        clientOnlyAnnotation.values = new ArrayList<>();
        clientOnlyAnnotation.values.add("value");
        clientOnlyAnnotation.values.add(new String[]{"net/minecraftforge/fml/relauncher/Side", "CLIENT"});
        returnNode.visibleAnnotations = new ArrayList<>(Collections.singleton(clientOnlyAnnotation));

        InsnList instructions = returnNode.instructions;
        LabelNode labelZero = new LabelNode();
        LabelNode labelTwo = new LabelNode();
        LabelNode labelOne = new LabelNode();
        LabelNode labelThree = new LabelNode();

        //label 0
        instructions.add(labelZero);
        instructions.add(new LineNumberNode(420, labelZero));
        instructions.add(new FieldInsnNode(Opcodes.GETSTATIC,
                "us/skyywastaken/toggleglass/misc/GlassBehaviorManager", "glassIsHidden", "Z"));
        instructions.add(new JumpInsnNode(Opcodes.IFEQ, labelOne));

        if(mappings.containsKey("getMaterialMethodName")) {
            instructions.add(new VarInsnNode(Opcodes.ALOAD, 0));
            instructions.add(new MethodInsnNode(Opcodes.INVOKESPECIAL,
                    mappings.get("asmBlockClassLocation"), mappings.get("getMaterialMethodName"),
                    "()L" + mappings.get("asmMaterialLocation") + ";", false));
            instructions.add(new FieldInsnNode(Opcodes.GETSTATIC, mappings.get("asmMaterialLocation"),
                    mappings.get("glassMaterialName"), "L" + mappings.get("asmMaterialLocation") + ";"));
            instructions.add(new JumpInsnNode(Opcodes.IF_ACMPNE, labelOne));
        }

        //label 2
        instructions.add(labelTwo);
        instructions.add(new LineNumberNode(421, labelTwo));
        instructions.add(new InsnNode(Opcodes.ICONST_M1));
        instructions.add(new InsnNode(Opcodes.IRETURN));

        //label 1
        instructions.add(labelOne);
        instructions.add(new LineNumberNode(422, labelOne));
        instructions.add(new FrameNode(3, 4, null, 4, null));
        instructions.add(new VarInsnNode(Opcodes.ALOAD, 0));
        instructions.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, mappings.get("asmBlockClassLocation"),
                mappings.get("renderTypeMethodName"), "()I", false));
        instructions.add(new InsnNode(Opcodes.IRETURN));

        //label 3
        LocalVariableNode thisLocalVariableNode = new LocalVariableNode("this", "L" + mappings.get("asmCurrentClassLocation") + ";", null, labelZero, labelThree, 0);
        returnNode.localVariables = new ArrayList<>(Collections.singleton(thisLocalVariableNode));

        return returnNode;
    }

    private static HashMap<String, String> getDeObfuscatedGlassMappings(boolean isStained) {
        HashMap<String, String> returnMappings = new HashMap<>();
        if (!isStained) {
            returnMappings.put("asmCurrentClassLocation", "net/minecraft/block/BlockGlass");
        } else {
            returnMappings.put("asmCurrentClassLocation", "net/minecraft/block/BlockStainedGlass");
        }

        returnMappings.put("asmBlockClassLocation", "net/minecraft/block/Block");
        returnMappings.put("renderTypeMethodName", "getRenderType");
        return returnMappings;
    }

    private static HashMap<String, String> getObfuscatedGlassMappings(boolean isStained) {
        HashMap<String, String> returnMappings = new HashMap<>();
        if (!isStained) {
            returnMappings.put("asmCurrentClassLocation", "ahc");
        } else {
            returnMappings.put("asmCurrentClassLocation", "ajs");
        }

        returnMappings.put("asmBlockClassLocation", "afh");
        returnMappings.put("renderTypeMethodName", "func_149645_b");
        return returnMappings;
    }

    private static HashMap<String, String> getDeObfuscatedGlassPaneMappings(boolean isStained) {
        HashMap<String, String> returnMappings = new HashMap<>();
        if (!isStained) {
            returnMappings.put("asmCurrentClassLocation", "net/minecraft/block/BlockPane");
        } else {
            returnMappings.put("asmCurrentClassLocation", "net/minecraft/block/BlockStainedGlassPane");
        }

        returnMappings.put("asmBlockClassLocation", "net/minecraft/block/Block");
        returnMappings.put("renderTypeMethodName", "getRenderType");
        returnMappings.put("asmMaterialLocation", "net/minecraft/block/material/Material");
        returnMappings.put("glassMaterialName", "glass");
        returnMappings.put("getMaterialMethodName", "getMaterial");
        return returnMappings;
    }

    private static HashMap<String, String> getObfuscatedGlassPaneMappings(boolean isStained) {
        HashMap<String, String> returnMappings = new HashMap<>();
        if (!isStained) {
            returnMappings.put("asmCurrentClassLocation", "akd");
        } else {
            returnMappings.put("asmCurrentClassLocation", "ajt");
        }

        returnMappings.put("asmBlockClassLocation", "afh");
        returnMappings.put("renderTypeMethodName", "func_149645_b");
        returnMappings.put("asmMaterialLocation", "arm");
        returnMappings.put("glassMaterialName", "s");
        returnMappings.put("getMaterialMethodName", "func_149688_o");
        return returnMappings;
    }
}

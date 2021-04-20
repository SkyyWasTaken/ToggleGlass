package us.skyywastaken.hideglass.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.io.IOException;

public class ASMUtils {
    public static ClassNode getClassNode(byte[] passedClassBytes) {
        ClassReader classReader = new ClassReader(passedClassBytes);
        ClassNode classNode = new ClassNode();
        classReader.accept(classNode, 0);
        return classNode;
    }

    public static ClassNode getClassNode(String classLocation) {
        try {
            Class.forName("us.skyywastaken.hideglass.asm.GlassPatch");
            System.out.println(classLocation);
            ClassReader classReader = new ClassReader(classLocation);
            ClassNode returnNode = new ClassNode();
            classReader.accept(returnNode, 0);
            return returnNode;
        } catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] getByteArrayFromClassNode(ClassNode passedClassNode) {
        ClassWriter classWriter = new ClassWriter(0);
        passedClassNode.accept(classWriter);
        return classWriter.toByteArray();
    }

    public static void getMethodNodeFromClass(ClassNode classNode, String methodName) {

    }

    public static void AddMethodToClassNode(ClassNode classNode, MethodNode passedMethodNode) {
        classNode.methods.add(passedMethodNode);
    }
}

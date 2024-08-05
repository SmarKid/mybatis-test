package zzk;

import org.apache.ibatis.executor.resultset.DefaultResultSetHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class Analysis {
    public static void main(String[] args) {
        Class<MappedStatement> clazz = MappedStatement.class;
        for (Method method : clazz.getDeclaredMethods()) {
            int modifiers = method.getModifiers();
            if (Modifier.isPublic(modifiers) && !method.getName().startsWith("get")
            && !method.getName().startsWith("set")) {
                System.out.println(method.getName());
            }
        }
        System.out.println("-----------------------------");
        for (Field field : clazz.getDeclaredFields()) {
            System.out.println(field.getName());
        }


    }
}

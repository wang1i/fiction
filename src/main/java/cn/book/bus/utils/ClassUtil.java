package cn.book.bus.utils;

import cn.book.bus.em.FictionEnum;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 通过反射返回map参数
 * */
public class ClassUtil {


    public static Map<String, String> setConditionMap(Object obj,Object obj2) {

        Map<String, String> map = new HashMap<>();
        String[] fieldNames = getFiledName(obj);
        String[] fieldNames2 = getFiledName(obj2);
        //获取属性的名字
        for (String name : fieldNames) {     //遍历所有属性
            for (String name2:fieldNames2){
                //判断需要判断的对象某些属性（字段名称）
                if (name2.equals(name)) {
                    //根据字段获取值
                    long value = (long) getFieldValueByName(name, obj);
                    if (value != 0) {
                        //如果对象的这些属性不为空
                        map.put(name2, FictionEnum.getName((Long) value));
                    }
                }
            }
        }
        return map;
    }

    /* 根据属性名获取属性值
     *
     */
    private static Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[]{});
            Object value = method.invoke(o, new Object[]{});
            return value;
        } catch (Exception e) {

            return null;
        }
    }

    /**
     * 获取属性名数组
     */
    private static String[] getFiledName(Object o) {
        //获取对象的属性数组
        Field[] fields = o.getClass().getDeclaredFields();
        //获取属性名称
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            //获取属性的类型
            // System.out.println(fields[i].getType());
            fieldNames[i] = fields[i].getName();
        }
        return fieldNames;
    }
    public static void main(String[] args) {

    }
}


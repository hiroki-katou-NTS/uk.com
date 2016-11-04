package nts.arc.layer.ws.json;

import java.lang.reflect.Field;

final class JacksonUtil {

    /**
     * Checks if is in nts package.
     *
     * @param type the type
     * @return true, if is in nts package
     */
    public static boolean isInNtsPackage(Class<?> type) {
        return type.getCanonicalName().startsWith("nts") 
                || type.getCanonicalName().startsWith("nittsu");
    }
    
    /**
     * Gets the enum value field.
     *
     * @param type the type
     * @return the enum value field
     */
    public static Field getEnumValueField(Class<?> type) {
        return getEnumField(type, "value");
    }
    
    /**
     * Gets the enum value field.
     *
     * @param type the type
     * @return the enum value field
     */
    public static Field getEnumNameIdValueField(Class<?> type) {
        return getEnumField(type, "nameId");
    }
    
    private static Field getEnumField(Class<?> type, String fieldName) {
        Field valueField;
        try {
            valueField = type.getDeclaredField(fieldName);
        } catch (Exception ex) {
            throw new RuntimeException("'" + fieldName + "' field not found.");
        }
        
        if (!valueField.getType().equals(String.class)) {
            throw new RuntimeException("Do not support value field which is not int.");
        }
        
        return valueField;
    }
}

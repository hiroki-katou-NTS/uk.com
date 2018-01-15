package nts.uk.ctx.pereg.dom.common;

import java.lang.reflect.Field;

public class ConstantEnum {

	public static <E> boolean IsInvalid(int constantValue, Class<E> enumClass) {
		Field valueField = getValueField(enumClass);

		// find a constant matches specified constant value
		for (E constant : enumClass.getEnumConstants()) {
			int current = getValue(valueField, constant);
			if (constantValue == current) {
				return true;
			}
		}

		return false;
	}

	private static <E> Field getValueField(Class<E> enumClass) {
		try {
			return enumClass.getField("value");
		} catch (NoSuchFieldException | SecurityException ex) {
			throw new IllegalArgumentException(String.format("value field is not defined: %s", enumClass.getName()),
					ex);
		}
	}

	private static <E> int getValue(Field valueField, E constant) {
		int current;
		try {
			// current = valueField.getInt(constant);
			current = (int) valueField.get(constant);
		} catch (IllegalArgumentException | IllegalAccessException ex) {
			throw new RuntimeException(ex);
		}
		return current;
	}

	public static <E> int getEnumValue(int constantValue, Class<E> enumClass) {

		if (IsInvalid(constantValue, enumClass)) {

			return constantValue;
		}
		Field valueField = getValueField(enumClass);

		return getValue(valueField, enumClass.getEnumConstants()[0]);

	}

	public static <E> int getEnumValue(int constantValue, Class<E> enumClass, int defaultValue) {

		if (IsInvalid(constantValue, enumClass)) {

			return constantValue;
		}
		return defaultValue;

	}

}

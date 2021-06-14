package nts.uk.ctx.exio.dom.input.workspace;

import static nts.uk.ctx.exio.dom.input.workspace.DataType.*;

import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.function.Function;

import lombok.SneakyThrows;
import lombok.Value;
import lombok.val;
import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.LongPrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.TimeAsMinutesPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMantissaMaxLength;
import nts.arc.primitive.constraint.DecimalMaxValue;
import nts.arc.primitive.constraint.DecimalRange;
import nts.arc.primitive.constraint.HalfIntegerMaxValue;
import nts.arc.primitive.constraint.HalfIntegerRange;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerRange;
import nts.arc.primitive.constraint.LongMaxValue;
import nts.arc.primitive.constraint.LongRange;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.constraint.TimeMaxValue;
import nts.arc.primitive.constraint.TimeRange;
import nts.gul.reflection.ClassReflection;
import nts.gul.reflection.ReflectionUtil;
import nts.gul.time.minutesbased.MinutesBasedTimeParser;
import nts.uk.ctx.exio.dom.input.importableitem.CheckMethod;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItem;

/**
 * データ型構成
 */
@Value
public class DataTypeConfiguration {

	DataType type;
	
	int length;
	
	int scale;
	
	public static DataTypeConfiguration guid() {
		return new DataTypeConfiguration(STRING, 36, 0);
	}
	
	/**
	 * 受入可能項目から構築する
	 * @param importableItem
	 * @return
	 */
	public static DataTypeConfiguration of(ImportableItem importableItem) {
		
		val dataType = DataType.of(importableItem.getItemType());
		
		if (dataType == DATE) {
			return new DataTypeConfiguration(DATE, 0, 0);
		}
		
		val domainConstraint = importableItem.getDomainConstraint().get();
		val constraintClass = domainConstraint.getConstraintClass();
		
		if (domainConstraint.getCheckMethod() == CheckMethod.PRIMITIVE_VALUE) {
			return typePrimitiveValue(constraintClass);
		} else {
			return typeEnum(constraintClass);
		}
	}
	
	private static DataTypeConfiguration typePrimitiveValue(Class<?> pvClass) {
		
		DataType type = null;
		Integer length = null;
		Integer scale = null;
		
		if (ClassReflection.isSubclass(pvClass, StringPrimitiveValue.class)) {
			type = STRING;
			length = getAnnotationValue(pvClass, StringMaxLength.class, a -> a.value());
			scale = 0;
		}
		
		else if (ClassReflection.isSubclass(pvClass, IntegerPrimitiveValue.class)) {
			type = INT;
			length = getLengthOfInteger(pvClass);
			scale = 0;
		}
		
		else if (ClassReflection.isSubclass(pvClass, LongPrimitiveValue.class)) {
			type = INT;
			length = getLengthOfLong(pvClass);
			scale = 0;
		}
		
		else if (ClassReflection.isSubclass(pvClass, TimeAsMinutesPrimitiveValue.class)) {
			type = INT;
			length = getLengthOfTime(pvClass);
			scale = 0;
		}
		
		else if (ClassReflection.isSubclass(pvClass, HalfIntegerPrimitiveValue.class)) {
			type = REAL;
			length = getLengthOfHalfInteger(pvClass);
			scale = 1;
		}
		
		else if (ClassReflection.isSubclass(pvClass, DecimalPrimitiveValue.class)) {
			type = REAL;
			length = getLengthOfDecimal(pvClass);
			scale = getAnnotationValue(pvClass, DecimalMantissaMaxLength.class, a -> a.value());
		}
		
		if (type == null || length == null || scale == null) {
			throw new RuntimeException("制約の解析に失敗: " + pvClass.getName());
		}
		
		return new DataTypeConfiguration(type, length, scale);
	}

	private static Integer getLengthOfInteger(Class<?> pvClass) {
		
		Integer maxValue = getAnnotationValue(pvClass, IntegerMaxValue.class, a -> a.value());
		if (maxValue != null) {
			return lengthOf(maxValue);
		}

		Integer rangeMax = getAnnotationValue(pvClass, IntegerRange.class, a -> a.max());
		if (rangeMax != null) {
			return lengthOf(rangeMax);
		}
		
		return null;
	}

	private static int lengthOf(int value) {
		return String.valueOf(value).length();
	}

	private static Integer getLengthOfLong(Class<?> pvClass) {
		
		Long maxValue = getAnnotationValue(pvClass, LongMaxValue.class, a -> a.value());
		if (maxValue != null) {
			return lengthOf(maxValue);
		}

		Long rangeMax = getAnnotationValue(pvClass, LongRange.class, a -> a.max());
		if (rangeMax != null) {
			return lengthOf(rangeMax);
		}
		
		return null;
	}

	private static int lengthOf(long value) {
		return String.valueOf(value).length();
	}

	private static Integer getLengthOfTime(Class<?> pvClass) {
		
		String max = getAnnotationValue(pvClass, TimeMaxValue.class, a -> a.value());
		
		if (max == null) {
			max = getAnnotationValue(pvClass, TimeRange.class, a -> a.max());
		}
		
		if (max == null) {
			return null;
		}
		
		return MinutesBasedTimeParser.parse(max).asDuration();
	}
	
	private static Integer getLengthOfHalfInteger(Class<?> pvClass) {
		
		Double maxValue = getAnnotationValue(pvClass, HalfIntegerMaxValue.class, a -> a.value());
		if (maxValue != null) {
			return lengthOf(maxValue);
		}

		Double rangeMax = getAnnotationValue(pvClass, HalfIntegerRange.class, a -> a.max());
		if (rangeMax != null) {
			return lengthOf(rangeMax);
		}
		
		return null;
	}

	private static int lengthOf(double value) {
		return String.valueOf((int) value).length();
	}
	
	private static Integer getLengthOfDecimal(Class<?> pvClass) {
		
		String maxValue = getAnnotationValue(pvClass, DecimalMaxValue.class, a -> a.value());
		if (maxValue != null) {
			return lengthOfDecimal(maxValue);
		}

		String rangeMax = getAnnotationValue(pvClass, DecimalRange.class, a -> a.max());
		if (rangeMax != null) {
			return lengthOfDecimal(rangeMax);
		}
		
		return null;
	}
	
	private static int lengthOfDecimal(String value) {
		return new BigDecimal(value).toBigInteger().toString().length();
	}
	
	private static <A extends Annotation, V> V getAnnotationValue(Class<?> pvClass, Class<A> annoClass, Function<A, V> reader) {
		A anno = pvClass.getAnnotation(annoClass);
		if (anno == null) {
			return null;
		}
		
		return reader.apply(anno);
	}
	
	@SneakyThrows
	private static DataTypeConfiguration typeEnum(Class<?> enumClass) {
		
		val valueField = enumClass.getField("value");
		
		int maxValue = Arrays.asList(enumClass.getEnumConstants()).stream()
				.map(e -> ReflectionUtil.getFieldValue(valueField, e))
				.mapToInt(v -> (int) v)
				.max()
				.getAsInt();
		
		return new DataTypeConfiguration(INT, lengthOf(maxValue), 0);
	}
}

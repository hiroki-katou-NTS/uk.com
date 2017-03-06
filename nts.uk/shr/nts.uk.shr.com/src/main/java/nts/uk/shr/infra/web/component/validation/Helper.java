package nts.uk.shr.infra.web.component.validation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import lombok.val;
import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.LongPrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.DecimalRange;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;
import nts.arc.primitive.constraint.IntegerRange;
import nts.arc.primitive.constraint.LongMaxValue;
import nts.arc.primitive.constraint.LongMinValue;
import nts.arc.primitive.constraint.LongRange;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

class Helper {
	
	/**
	 * these constraints have single parameter
	 */
	static HashMap<String, String> CONSTRAINTS_SIGNLE_PARAM = new HashMap<>();
	static {
		CONSTRAINTS_SIGNLE_PARAM.put(StringCharType.class.getSimpleName(), "charType");
		CONSTRAINTS_SIGNLE_PARAM.put(StringMaxLength.class.getSimpleName(), "maxLength");
		CONSTRAINTS_SIGNLE_PARAM.put(IntegerMaxValue.class.getSimpleName(), "max");
		CONSTRAINTS_SIGNLE_PARAM.put(IntegerMinValue.class.getSimpleName(), "min");
		CONSTRAINTS_SIGNLE_PARAM.put(LongMaxValue.class.getSimpleName(), "max");
		CONSTRAINTS_SIGNLE_PARAM.put(LongMinValue.class.getSimpleName(), "min");
	}
	
	/**
	 * these constraints have multiple parameters: max, min
	 */
	static HashSet<String> CONSTRAINTS_MAX_MIN_PARAM = new HashSet<>();
	static {
		CONSTRAINTS_MAX_MIN_PARAM.add(IntegerRange.class.getSimpleName());
		CONSTRAINTS_MAX_MIN_PARAM.add(LongRange.class.getSimpleName());
		CONSTRAINTS_MAX_MIN_PARAM.add(DecimalRange.class.getSimpleName());
	}
	
	static HashMap<String, String> CHARTYPE_NAMES_MAP = new HashMap<>();
	static {
		CHARTYPE_NAMES_MAP.put(CharType.ANY_HALF_WIDTH.name(), "AnyHalfWidth");
		CHARTYPE_NAMES_MAP.put(CharType.ALPHA_NUMERIC.name(), "AlphaNumeric");
		CHARTYPE_NAMES_MAP.put(CharType.ALPHABET.name(), "Alphabet");
		CHARTYPE_NAMES_MAP.put(CharType.NUMERIC.name(), "Numeric");
	}
	
	static String getValueType(Class<?> inputClass) {
		if (StringPrimitiveValue.class.isAssignableFrom(inputClass)) {
			return "String";
		} else if (IntegerPrimitiveValue.class.isAssignableFrom(inputClass)) {
			return "Integer";
		} else if (LongPrimitiveValue.class.isAssignableFrom(inputClass)) {
			return "Integer";
		} else if (DecimalPrimitiveValue.class.isAssignableFrom(inputClass)) {
			return "Decimal";
		} else {
			throw new RuntimeException("not supported: " + inputClass.getName());
		}
	}
	
	static String getAnnotationName(String representationOfAnnotation) {
    	int end = representationOfAnnotation.indexOf("(");
    	String noEnd = representationOfAnnotation.substring(0, end);
    	int start = noEnd.lastIndexOf(".") + 1;

    	return noEnd.substring(start, end);
	}
	
	static String getAnnotationParametersString(String representationOfAnnotation) {
    	int start = representationOfAnnotation.indexOf("(") + 1;
    	int end = representationOfAnnotation.indexOf(")");
		return representationOfAnnotation.substring(start, end);
	}
	
	static String parseSingleParameterValue(String constraintName, String parametersString) {
		String jsValue = parametersString.replaceAll("value=", "");
		
		if (constraintName.equals("StringCharType")) {
			jsValue = "'" + Helper.CHARTYPE_NAMES_MAP.get(jsValue) + "'";
		}
		
		return jsValue;
	}
	
	static Map<String, String> parseMultipleParametersString(String parametersString) {
		val results = new HashMap<String, String>();
		
		for (String param : parametersString.split(",")) {
			String[] paramParts = param.split("=");
			results.put(paramParts[0].trim(), paramParts[1].trim());
		}
		
		return results;
	}
}

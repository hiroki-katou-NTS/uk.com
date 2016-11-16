package nts.uk.shr.web.component.validation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import lombok.val;
import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;

class Helper {
	
	static HashMap<String, String> CONSTRAINTS_SIGNLE_PARAM = new HashMap<>();
	{
		CONSTRAINTS_SIGNLE_PARAM.put("StringCharType", "charType");
		CONSTRAINTS_SIGNLE_PARAM.put("StringMaxLength", "maxLength");
		CONSTRAINTS_SIGNLE_PARAM.put("IntegerMaxValue", "max");
		CONSTRAINTS_SIGNLE_PARAM.put("IntegerMinValue", "min");
	}
	
	static HashMap<String, String> CHARTYPE_NAMES_MAP = new HashMap<>();
	{
		CHARTYPE_NAMES_MAP.put(CharType.ANY_HALF_WIDTH.name(), "AnyHalfWidth");
		CHARTYPE_NAMES_MAP.put(CharType.ALPHA_NUMERIC.name(), "AlphaNumeric");
		CHARTYPE_NAMES_MAP.put(CharType.ALPHABET.name(), "Alphabet");
		CHARTYPE_NAMES_MAP.put(CharType.NUMERIC.name(), "Numeric");
	}
	
	static HashSet<String> CONSTRAINTS_MAX_MIN_PARAM = new HashSet<>();
	{
		CONSTRAINTS_MAX_MIN_PARAM.add("IntegerRange");
	}
	
	static Class<?> findClass(String className) {
		Class<?> inputClass = null;
		try {
			inputClass = Class.forName(className);
		} catch (ClassNotFoundException ex) {
			//throw RuntimeException if needed
		}
		return inputClass;
	}
	
	static String getValueType(Class<?> inputClass) {
		if(StringPrimitiveValue.class.isAssignableFrom(inputClass)) {
			return "String";
		} else if(IntegerPrimitiveValue.class.isAssignableFrom(inputClass)) {
			return "Integer";
		} else if(DecimalPrimitiveValue.class.isAssignableFrom(inputClass)) {
			return "Decimal";
		} else {
			throw new RuntimeException("not supported: " + inputClass.getName());
		}
	}
	
	static String getAnnotationName(String representationOfAnnotation) {
    	int start = representationOfAnnotation.lastIndexOf(".") + 1;
    	int end = representationOfAnnotation.indexOf("(");
    	return representationOfAnnotation.substring(start, end);
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

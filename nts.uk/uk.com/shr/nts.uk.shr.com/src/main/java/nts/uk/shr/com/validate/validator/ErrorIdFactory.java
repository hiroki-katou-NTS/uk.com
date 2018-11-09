package nts.uk.shr.com.validate.validator;

public class ErrorIdFactory {
	
	/************************** STRING ****************************************/
	
	public static String getCharTypeErrorId() {
		return "FND_E_STRING_TYPE";
	}
	
	public static String getMaxLengthErrorId() {
		return "FND_E_STRING_MAX_LENGTH";
	}
	
	public static String getRegExpErrorId() {
		return "FND_E_STRING_PATTERN";
	}
	
	/************************** NUMERIC ****************************************/
	
	public static String getMinusErrorId() {
		return "FND_E_NUMBERIC_MINUS";
	}
	
	public static String getNumericMinErrorId() {
		return "FND_E_NUMBERIC_MIN";
	}
	
	public static String getNumericMaxErrorId() {
		return "FND_E_NUMERIC_MAX";
	}
	
	public static String getIntegerPartErrorId() {
		return "FND_E_NUMERIC_INT_PART";
	}
	
	public static String getDecimalPartErrorId() {
		return "FND_E_NUMERIC_DEC_PART";
	}
	
	/************************** DATE ****************************************/
	
	public static String getDateErrorId() {
		return "FND_E_DATE_STYLE";
	}
	
	/************************** TIME ****************************************/
	
	public static String getTimeStyleErrorId() {
		return "FND_E_TIME_STYLE";
	}
	
	public static String getTimeMinErrorId() {
		return "FND_E_TIME_MIN";
	}
	
	public static String getTimeMaxErrorId() {
		return "FND_E_TIME_MAX";
	}
	
}

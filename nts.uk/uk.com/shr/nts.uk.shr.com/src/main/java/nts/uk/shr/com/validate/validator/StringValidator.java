package nts.uk.shr.com.validate.validator;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nts.uk.shr.com.validate.constraint.implement.StringCharType;
import nts.uk.shr.com.validate.constraint.implement.StringConstraint;

public class StringValidator {

	private static final Pattern alphabetPattern = Pattern.compile("[a-zA-Z]+$");
	private static final Pattern numericPattern = Pattern.compile("[0-9]+$");
	private static final Pattern alphabetNumericPattern = Pattern.compile("[a-zA-Z0-9]+$");
	private static final Pattern hiraganaPattern = Pattern.compile("^[\\u3040-\\u309Fー　]+$");
	private static final Pattern katakanaPattern = Pattern.compile("^[\\u30A0-\\u30FFー　]+$");
	private static final Pattern kanaPattern = Pattern.compile("^[\\u3040-\\u309F\\u30A0-\\u30FFー　\\uFF61-\\uFF9F]+$");
	private static final Pattern anyPattern = Pattern.compile("");
	private static final Pattern anyHalfWidthPattern = Pattern.compile("[\\u0020-\\u007F\\uff61-\\uFF9F]+$");

	public static Optional<String> validate(StringConstraint constraint, String value) {
		
		if (!validateCharType(constraint.getCharType(), value)) {
			return Optional.of(ErrorIdFactory.getCharTypeErrorId());
		}
		
		if(!validateMaxLength(constraint.getMaxLenght(), value)) {
			return Optional.of(ErrorIdFactory.getMaxLengthErrorId());
		}
		
		if (constraint.getRegExpression() != null && !validateRegEx(constraint.getRegExpression(), value)) {
			return Optional.of(ErrorIdFactory.getRegExpErrorId());
		}
		
		return Optional.empty();
	};

	private static boolean validateCharType(StringCharType type, String value) {

		if (value.isEmpty()) {
			return true;
		}

		Pattern pattern;

		switch (type) {
		case ALPHABET:
			pattern = alphabetPattern;
			break;
		case NUMERIC:
			pattern = numericPattern;
			break;
		case ALPHA_NUMERIC:
			pattern = alphabetNumericPattern;
			break;
		case HIRAGANA:
			pattern = hiraganaPattern;
			break;
		case KATAKANA:
			pattern = katakanaPattern;
			break;
		case KANA:
			pattern = kanaPattern;
			break;
		case ANY_HALF_WIDTH:
			pattern = anyHalfWidthPattern;
			break;
		default:
			pattern = anyPattern;
			break;
		}

		return pattern.matcher(value).matches();
	}

	private static boolean validateMaxLength(int maxLength, String value) {
		return value.length() <= maxLength;
	}

	private static boolean validateRegEx(String validatedStr, String value) {
		if(validatedStr == null || validatedStr.isEmpty()) {
			return true;
		}
		Pattern pattern = Pattern.compile(validatedStr);
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}
	
}

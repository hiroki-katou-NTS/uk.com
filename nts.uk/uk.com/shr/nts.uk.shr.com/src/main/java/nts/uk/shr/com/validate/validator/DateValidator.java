package nts.uk.shr.com.validate.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.regex.Pattern;

import nts.uk.shr.com.validate.constraint.implement.DateConstraint;
import nts.uk.shr.com.validate.constraint.implement.DateType;

public class DateValidator {

	private static final Pattern yearPattern = Pattern.compile("^\\d{4}$");

	private static final Pattern yearMonthPattern = Pattern.compile("^\\d{4}\\/(0?[1-9]|1[012])$");

	private static final Pattern datePattern = Pattern
			.compile("^\\d{4}\\/(0?[1-9]|1[012])\\/(0?[1-9]|[12][0-9]|3[01])$");

	public static Optional<String> validate(DateConstraint constraint, String stringValue) {

		if (stringValue.isEmpty()) {
			return Optional.empty();
		}

		Pattern pattern;

		switch (constraint.getDateType()) {
		case YEAR:
			pattern = yearPattern;
			break;

		case YEARMONTH:
			pattern = yearMonthPattern;
			break;
		case DATE:
		default:
			pattern = datePattern;
			break;
		}

		if (!pattern.matcher(stringValue).matches()) {
			return Optional.of(ErrorIdFactory.DateErrorId);
		}
		
		if (constraint.getDateType() == DateType.DATE) {
			if (!validateDate(stringValue)) {
				return Optional.of(ErrorIdFactory.DateErrorId);
			}
		}

		return Optional.empty();

	}
	
	private static boolean validateDate(String value) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(value.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
	}

}

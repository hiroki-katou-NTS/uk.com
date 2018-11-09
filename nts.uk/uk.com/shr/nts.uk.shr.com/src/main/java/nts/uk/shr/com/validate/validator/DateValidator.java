package nts.uk.shr.com.validate.validator;

import java.util.Optional;
import java.util.regex.Pattern;

import nts.uk.shr.com.validate.constraint.implement.DateConstraint;

public class DateValidator {

	private static final Pattern yearPattern = Pattern.compile("^\\d{4}$");

	private static final Pattern yearMonthPattern = Pattern.compile("^\\d{4}\\/(0?[1-9]|1[012])$");

	private static final Pattern datePattern = Pattern
			.compile("^\\d{4}\\/(0?[1-9]|1[012])\\/(0?[1-9]|[12][0-9]|3[01])$");

	public static Optional<String> validate(DateConstraint constraint, Object value) {

		String stringValue = value.toString();
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
			return Optional.of(ErrorIdFactory.getDateErrorId());
		}

		return Optional.empty();

	}

}

package nts.uk.shr.com.validate.validator;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nts.uk.shr.com.validate.constraint.implement.TimeConstraint;

public class TimeValidator extends TimeMinMaxValidator{
	
	private static final String TIME_FORMAT = "^[0-9]+:[0-5][0-9]$";

	public static boolean validateTimeStyle(String value) {
		Pattern pattern = Pattern.compile(TIME_FORMAT);
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}

	public static Optional<String> validate(TimeConstraint constraint, String value) {

		// validate style
		if (!validateTimeStyle(value)) {
			return Optional.of(ErrorIdFactory.TimeStyleErrorId);
		}

		return validateMinMax(constraint.getMin(), constraint.getMax(), value);
	}

}

package nts.uk.shr.com.validate.validator;

import java.util.Optional;

import nts.uk.shr.com.validate.constraint.implement.TimeConstraint;

public class TimeValidator {

	public static Optional<String> validate(TimeConstraint constraint, String value) {

		// validate style
		if (!TimeStyleValidator.validateTimeStyle(value)) {
			return Optional.of(ErrorIdFactory.getTimeStyleErrorId());
		}

		return TimeStyleValidator.validateMinMax(constraint.getMin(), constraint.getMax(), value);
	}

}

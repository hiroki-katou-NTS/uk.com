package nts.uk.shr.com.validate.service;

import java.math.BigDecimal;
import java.util.Optional;

import nts.uk.shr.com.validate.constraint.implement.DateConstraint;
import nts.uk.shr.com.validate.constraint.implement.DateType;
import nts.uk.shr.com.validate.constraint.implement.NumericConstraint;
import nts.uk.shr.com.validate.constraint.implement.StringCharType;
import nts.uk.shr.com.validate.constraint.implement.StringConstraint;
import nts.uk.shr.com.validate.constraint.implement.TimeConstraint;

public class CellValidateTest {
	public static void main(String[] args) {
		StringConstraint stringConstraint = new StringConstraint(1, StringCharType.ALPHA_NUMERIC, 13, false);
		NumericConstraint numConstraint = new NumericConstraint(2, false, new BigDecimal(10), new BigDecimal(150),
				3, 3);
		DateConstraint dateConstraint = new DateConstraint(3, DateType.YEAR);
		TimeConstraint timeConstaint = new TimeConstraint(4, 100, 360);

		Optional<String> stringErr = CellValidateService.validateValue(stringConstraint, "a12345678");
		Optional<String> numbericError = CellValidateService.validateValue(numConstraint, "123");
		Optional<String> dateError = CellValidateService.validateValue(dateConstraint, "2000");
		Optional<String> timeError = CellValidateService.validateValue(timeConstaint, "1:50");
		
		printErrors(stringErr, numbericError, dateError, timeError);
	}

	private static void printErrors(Optional<String> stringErr, Optional<String> numbericError, Optional<String> dateError,
			Optional<String> timeError) {
		printError(stringErr);
		printError(numbericError);
		printError(dateError);
		printError(timeError);

	}

	private static void printError(Optional<String> error) {
		if (error.isPresent()) {
			System.out.println(error.get());
		}
	}
}

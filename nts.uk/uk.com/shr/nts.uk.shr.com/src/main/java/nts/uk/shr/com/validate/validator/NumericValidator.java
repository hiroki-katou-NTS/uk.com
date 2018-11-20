package nts.uk.shr.com.validate.validator;

import java.math.BigDecimal;
import java.util.Optional;

import nts.uk.shr.com.validate.constraint.implement.NumericConstraint;

public class NumericValidator {
	
	public static Optional<String> validate(NumericConstraint constraint, BigDecimal value) {
		
		if(!constraint.isMinusAvailable() && !validateMinus(value)) {
			return Optional.of(ErrorIdFactory.getMinusErrorId());
		}
		
		if (!validateMin(constraint.getMin(), value)) {
			return Optional.of(ErrorIdFactory.getNumericMinErrorId());
		}
		
		if (!validateMax(constraint.getMax(), value)) {
			return Optional.of(ErrorIdFactory.getNumericMaxErrorId());
		}
		
		if (!validateIntegerPart(constraint.getIntegerPart(), value)) {
			return Optional.of(ErrorIdFactory.getIntegerPartErrorId());
		}
		
		if (!validateDecimalPart(constraint.getDecimalPart(), value)) {
			return Optional.of(ErrorIdFactory.getDecimalPartErrorId());
		}
		
		return Optional.empty();
	}
	
	private static boolean validateMinus(BigDecimal value) {
		return value.compareTo(BigDecimal.ZERO) >= 0;
	}
	
	private static boolean validateMin(BigDecimal max, BigDecimal value) {
		return value.compareTo(max) >= 0;
	}
	
	private static boolean validateMax(BigDecimal max, BigDecimal value) {
		return value.compareTo(max) <= 0;
	}
	
	private static boolean validateIntegerPart(int integerPart, BigDecimal value) {
		value = value.stripTrailingZeros();
		int integerPartOfValue =  value.precision() - value.scale();
		return integerPartOfValue <= integerPart;
	}
	
	private static boolean validateDecimalPart(int decimalPart, BigDecimal value) {
		value = value.stripTrailingZeros();
		return value.scale() <= decimalPart;
	}
	
	

}

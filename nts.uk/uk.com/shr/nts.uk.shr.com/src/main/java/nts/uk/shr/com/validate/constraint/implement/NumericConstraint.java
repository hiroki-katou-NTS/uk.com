package nts.uk.shr.com.validate.constraint.implement;

import java.math.BigDecimal;

import lombok.Getter;
import nts.uk.shr.com.validate.constraint.DataConstraint;
import nts.uk.shr.com.validate.constraint.ValidatorType;

@Getter
public class NumericConstraint extends DataConstraint {

	private boolean minusAvailable;

	private boolean money;

	private BigDecimal min;

	private BigDecimal max;

	private int integerPart;

	private int decimalPart;

	public NumericConstraint(int column, boolean minusAvailable, boolean money, BigDecimal min, BigDecimal max, int integerPart,
			int decimalPart) {
		super(column, ValidatorType.NUMERIC);
		this.minusAvailable = minusAvailable;
		this.money = money;
		this.min = min;
		this.max = max;
		this.integerPart = integerPart;
		this.decimalPart = decimalPart;
	}

	public NumericConstraint(int column, boolean minusAvailable, BigDecimal min, BigDecimal max, int integerPart,
			int decimalPart) {
		super(column, ValidatorType.NUMERIC);
		this.minusAvailable = minusAvailable;
		this.min = min;
		this.max = max;
		this.integerPart = integerPart;
		this.decimalPart = decimalPart;
	}

}

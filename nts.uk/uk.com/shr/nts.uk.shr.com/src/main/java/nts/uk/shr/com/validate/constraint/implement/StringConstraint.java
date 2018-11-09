package nts.uk.shr.com.validate.constraint.implement;

import lombok.Getter;
import nts.uk.shr.com.validate.constraint.DataConstraint;
import nts.uk.shr.com.validate.constraint.ValidatorType;

@Getter
public class StringConstraint extends DataConstraint {

	private StringCharType charType;

	private int maxLenght;

	private boolean fixed;
	
	private String regExpression;
	
	public StringConstraint(int column, StringCharType charType, int maxLenght, boolean fixed, String regExpression) {
		super(column, ValidatorType.STRING);
		this.charType = charType;
		this.maxLenght = maxLenght;
		this.fixed = fixed;
		this.regExpression = regExpression;
	}

	public StringConstraint(int column, StringCharType charType, int maxLenght, boolean fixed) {
		super(column, ValidatorType.STRING);
		this.charType = charType;
		this.maxLenght = maxLenght;
		this.fixed = fixed;
	}

}

package nts.uk.shr.com.validate.constraint;

import lombok.Getter;

@Getter
public class DataConstraint {
	
	protected int column;
	
	protected ValidatorType constraintType;
	
	public DataConstraint(int column, ValidatorType constraintType) {
		super();
		this.column = column;
		this.constraintType = constraintType;
	}
	
}

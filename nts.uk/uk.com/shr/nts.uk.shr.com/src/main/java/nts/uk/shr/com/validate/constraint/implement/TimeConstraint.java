package nts.uk.shr.com.validate.constraint.implement;

import lombok.Getter;
import nts.uk.shr.com.validate.constraint.DataConstraint;
import nts.uk.shr.com.validate.constraint.ValidatorType;

@Getter
public class TimeConstraint extends DataConstraint{
	
	private int min;
	
	private int max;
	
	public TimeConstraint(int column, int min, int max) {
		super(column, ValidatorType.TIME);
		this.min = min;
		this.max = max;
	}
	
}

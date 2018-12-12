package nts.uk.shr.com.validate.constraint.implement;

import lombok.Getter;
import nts.uk.shr.com.validate.constraint.DataConstraint;
import nts.uk.shr.com.validate.constraint.ValidatorType;

@Getter
public class TimePointConstraint extends DataConstraint{
	
	private int min;
	
	private int max;
	
	public TimePointConstraint(int column, int min, int max) {
		super(column, ValidatorType.TIMEPOINT);
		this.min = min;
		this.max = max;
	}
	
}

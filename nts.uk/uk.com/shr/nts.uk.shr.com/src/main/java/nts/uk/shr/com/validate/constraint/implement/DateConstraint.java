package nts.uk.shr.com.validate.constraint.implement;

import lombok.Getter;
import nts.uk.shr.com.validate.constraint.DataConstraint;
import nts.uk.shr.com.validate.constraint.ValidatorType;

@Getter
public class DateConstraint extends DataConstraint{
	
	private DateType dateType;
	
	public DateConstraint(int column, DateType dateType) {
		super(column, ValidatorType.DATE);
		this.dateType = dateType;
	}
	
}

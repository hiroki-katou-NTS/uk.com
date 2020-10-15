package nts.uk.ctx.at.schedule.dom.shift.management.workavailability;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(100)
public class WorkAvailabilityMemo extends StringPrimitiveValue<WorkAvailabilityMemo>{
	
	private static final long serialVersionUID = -8249746777865553044L;

	public WorkAvailabilityMemo(String rawValue) {
		super(rawValue);
	}

}

package nts.uk.ctx.at.schedule.dom.shift.management.workexpect;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(100)
public class WorkExpectationMemo extends StringPrimitiveValue<WorkExpectationMemo>{
	
	private static final long serialVersionUID = -8249746777865553044L;

	public WorkExpectationMemo(String rawValue) {
		super(rawValue);
	}

}

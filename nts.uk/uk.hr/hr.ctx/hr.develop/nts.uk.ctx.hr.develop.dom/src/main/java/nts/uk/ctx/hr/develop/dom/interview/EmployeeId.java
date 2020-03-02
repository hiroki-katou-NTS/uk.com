package nts.uk.ctx.hr.develop.dom.interview;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(36)
public class EmployeeId extends StringPrimitiveValue<EmployeeId> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmployeeId(String rawValue) {
		super(rawValue);
	}

}

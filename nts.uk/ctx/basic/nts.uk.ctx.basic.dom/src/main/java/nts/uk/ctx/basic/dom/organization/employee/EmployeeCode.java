package nts.uk.ctx.basic.dom.organization.employee;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 
 * 社員CD
 */
@StringMaxLength(12)
public class EmployeeCode extends StringPrimitiveValue<EmployeeCode> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmployeeCode(String rawValue) {
		super(rawValue);
	}

}

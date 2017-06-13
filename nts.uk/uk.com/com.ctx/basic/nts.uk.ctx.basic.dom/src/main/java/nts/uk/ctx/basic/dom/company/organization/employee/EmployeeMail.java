package nts.uk.ctx.basic.dom.company.organization.employee;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 
 * 社員 MAIL
 */
@StringMaxLength(80)
public class EmployeeMail extends StringPrimitiveValue<EmployeeMail> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmployeeMail(String rawValue) {
		super(rawValue);
	}

}

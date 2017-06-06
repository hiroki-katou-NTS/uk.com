package nts.uk.ctx.basic.dom.organization.employee;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

/**
 * 社員ID
 *
 */
@StringCharType(CharType.ALPHABET)
@StringMaxLength(36)
public class EmployeeId extends CodePrimitiveValue<EmployeeId> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmployeeId(String rawValue) {
		super(rawValue);
	}

}

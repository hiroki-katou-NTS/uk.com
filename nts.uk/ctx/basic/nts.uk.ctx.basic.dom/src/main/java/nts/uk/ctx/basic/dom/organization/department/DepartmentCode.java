package nts.uk.ctx.basic.dom.organization.department;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

@StringCharType(CharType.ALPHABET)
@StringMaxLength(10)
public class DepartmentCode extends CodePrimitiveValue<DepartmentCode>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DepartmentCode(String rawValue) {
		super(rawValue);
	}

}

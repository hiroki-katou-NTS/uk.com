package nts.uk.ctx.basic.dom.organization.employment;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

@StringMaxLength(10)
public class EmploymentCode extends CodePrimitiveValue<EmploymentCode> {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public EmploymentCode(String rawValue) {
		super(rawValue);
	}
}

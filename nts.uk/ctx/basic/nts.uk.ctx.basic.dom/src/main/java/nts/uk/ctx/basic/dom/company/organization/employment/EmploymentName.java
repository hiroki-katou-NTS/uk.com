package nts.uk.ctx.basic.dom.company.organization.employment;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(20)
public class EmploymentName extends StringPrimitiveValue<EmploymentName>{

	private static final long serialVersionUID = -1325727366520482013L;
	
	
	public EmploymentName(String rawValue) {
		super(rawValue);
	}


}

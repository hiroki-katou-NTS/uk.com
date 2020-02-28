package nts.uk.ctx.pr.core.dom.wageprovision.processdatecls;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(24)
public class EmploymentCode extends StringPrimitiveValue<EmploymentCode>{

	private static final long serialVersionUID = 1L;

	public EmploymentCode(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}
}

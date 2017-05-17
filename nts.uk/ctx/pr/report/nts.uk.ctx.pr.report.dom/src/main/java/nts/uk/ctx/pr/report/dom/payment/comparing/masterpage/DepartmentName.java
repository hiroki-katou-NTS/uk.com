package nts.uk.ctx.pr.report.dom.payment.comparing.masterpage;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(20)
public class DepartmentName extends StringPrimitiveValue<DepartmentName> {

	private static final long serialVersionUID = 1L;

	public DepartmentName(String rawValue) {
		super(rawValue);
	}

}

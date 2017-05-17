package nts.uk.ctx.pr.report.dom.payment.comparing.confirm;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(50)
public class EmployeeName extends StringPrimitiveValue<EmployeeName> {

	private static final long serialVersionUID = 1L;

	public EmployeeName(String rawValue) {
		super(rawValue);
	}

}

package nts.uk.ctx.pr.report.dom.payment.comparing.confirm;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(30)
public class ReasonDifference extends StringPrimitiveValue<ReasonDifference> {

	private static final long serialVersionUID = 1L;

	public ReasonDifference(String rawValue) {
		super(rawValue);
	}

}

package nts.uk.ctx.pr.report.dom.payment.comparing;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(40)
public class FormName extends StringPrimitiveValue<FormName> {

	private static final long serialVersionUID = 1L;

	public FormName(String rawValue) {
		super(rawValue);
	}

}

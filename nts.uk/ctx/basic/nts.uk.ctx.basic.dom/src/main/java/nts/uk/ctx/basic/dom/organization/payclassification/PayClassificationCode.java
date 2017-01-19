package nts.uk.ctx.basic.dom.organization.payclassification;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(10)
public class PayClassificationCode extends StringPrimitiveValue<PayClassificationCode> {

	public PayClassificationCode(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}

package nts.uk.ctx.bs.employee.dom.employment.stampcard;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(20)
public class CardNumber extends StringPrimitiveValue<CardNumber>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CardNumber(String rawValue) {
		super(rawValue);
	}

}

package nts.uk.ctx.bs.person.dom.person.contact;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 *  The class PhoneNumber 電話番号
 * @author lanlt
 *
 */
@StringMaxLength(41)
public class PhoneNumber extends StringPrimitiveValue<PhoneNumber>{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	public PhoneNumber(String rawValue) {
		super(rawValue);
	}

}


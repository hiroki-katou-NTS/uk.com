package nts.uk.ctx.bs.person.dom.person.info.currentaddress;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(value = 20)
@StringCharType(value = CharType.NUMERIC)
public class PhoneNumber extends StringPrimitiveValue<PhoneNumber>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PhoneNumber(String phoneNumber){
		super(phoneNumber);
	}
}

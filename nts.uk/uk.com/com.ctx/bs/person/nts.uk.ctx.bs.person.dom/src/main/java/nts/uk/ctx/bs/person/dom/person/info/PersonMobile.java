package nts.uk.ctx.bs.person.dom.person.info;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(20)
public class PersonMobile extends StringPrimitiveValue<PersonMobile> {

	public PersonMobile(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}

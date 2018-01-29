package nts.uk.ctx.bs.person.dom.person.contact;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

@StringCharType(CharType.ANY_HALF_WIDTH)
@StringMaxLength(256)
public class MailAddress extends StringPrimitiveValue<MailAddress>{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	public MailAddress(String rawValue) {
		super(rawValue);
	}

}


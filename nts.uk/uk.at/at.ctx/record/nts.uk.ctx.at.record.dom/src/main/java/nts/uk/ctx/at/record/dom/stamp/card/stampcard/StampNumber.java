package nts.uk.ctx.at.record.dom.stamp.card.stampcard;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.constraint.StringRegEx;


@StringMaxLength(20)
@StringCharType(CharType.ANY_HALF_WIDTH)
@StringRegEx("^[a-zA-Z0-9\\s#$%&()~:|{}*+?@'<>_/;\"\\\\\\[\\]\\`-]{1,20}$")
public class StampNumber extends StringPrimitiveValue<StampNumber> {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public StampNumber(String rawValue) {		
		super(rawValue);
	}
	
	
}

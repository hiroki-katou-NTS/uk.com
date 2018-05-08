package nts.uk.ctx.at.record.dom.stamp.card.stampcard;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringRegEx;

@StringRegEx("a-z A-Z 0-9 \" # $ % & ( ) ~ | { } [ ] @ : ` * + ? ; / _ \\ - ><")
public class StampNumber extends StringPrimitiveValue<StampNumber> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StampNumber(String rawValue) {
		super(rawValue);
	}

}
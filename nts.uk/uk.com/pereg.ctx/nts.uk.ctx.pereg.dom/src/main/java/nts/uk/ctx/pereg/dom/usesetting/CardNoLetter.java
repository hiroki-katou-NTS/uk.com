package nts.uk.ctx.pereg.dom.usesetting;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(20)
public class CardNoLetter extends StringPrimitiveValue<CardNoLetter> {

	private static final long serialVersionUID = 1L;

	public CardNoLetter(String rawValue) {
		super(rawValue);
	}
}

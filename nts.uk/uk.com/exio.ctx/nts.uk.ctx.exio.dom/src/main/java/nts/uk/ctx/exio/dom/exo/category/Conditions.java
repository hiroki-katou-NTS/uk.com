package nts.uk.ctx.exio.dom.exo.category;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(2000)
public class Conditions extends StringPrimitiveValue<Conditions> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Conditions(String rawValue) {
		super(rawValue);
	}

}

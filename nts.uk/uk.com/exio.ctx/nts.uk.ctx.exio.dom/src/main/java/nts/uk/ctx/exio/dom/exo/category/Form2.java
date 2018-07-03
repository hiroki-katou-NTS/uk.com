package nts.uk.ctx.exio.dom.exo.category;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(4000)
public class Form2 extends StringPrimitiveValue<Form2> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Form2(String rawValue) {
		super(rawValue);
	}

}

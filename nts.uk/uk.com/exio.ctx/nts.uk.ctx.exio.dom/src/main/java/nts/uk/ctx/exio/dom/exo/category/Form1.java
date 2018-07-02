package nts.uk.ctx.exio.dom.exo.category;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(4000)
public class Form1 extends StringPrimitiveValue<Form1> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Form1(String rawValue) {
		super(rawValue);
	}

}

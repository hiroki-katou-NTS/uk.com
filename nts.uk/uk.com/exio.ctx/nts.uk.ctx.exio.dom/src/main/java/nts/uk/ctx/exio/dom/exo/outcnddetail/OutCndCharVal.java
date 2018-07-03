package nts.uk.ctx.exio.dom.exo.outcnddetail;

import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(20)
public class OutCndCharVal extends StringPrimitiveValue<PrimitiveValue<String>> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OutCndCharVal(String rawValue) {
		super(rawValue);
	}

}

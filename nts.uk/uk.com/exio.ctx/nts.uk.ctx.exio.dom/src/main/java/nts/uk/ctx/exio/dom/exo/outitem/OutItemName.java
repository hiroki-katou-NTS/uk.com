package nts.uk.ctx.exio.dom.exo.outitem;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(20)
public class OutItemName extends StringPrimitiveValue<OutItemName> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OutItemName(String rawValue) {
		super(rawValue);
	}
}

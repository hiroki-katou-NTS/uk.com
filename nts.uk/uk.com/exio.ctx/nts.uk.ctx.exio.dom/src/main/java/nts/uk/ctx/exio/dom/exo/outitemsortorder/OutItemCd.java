package nts.uk.ctx.exio.dom.exo.outitemsortorder;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(3)
public class OutItemCd extends StringPrimitiveValue<OutItemCd>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OutItemCd(String rawValue) {
		super(rawValue);
	}
}

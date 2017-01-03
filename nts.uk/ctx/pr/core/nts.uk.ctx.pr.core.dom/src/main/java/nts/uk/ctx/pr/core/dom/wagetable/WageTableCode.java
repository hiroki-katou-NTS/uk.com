package nts.uk.ctx.pr.core.dom.wagetable;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(3)
public class WageTableCode extends StringPrimitiveValue<WageTableCode>{

	public WageTableCode(String rawValue) {
		super(rawValue);
	}

	private static final long serialVersionUID = 1L;

}

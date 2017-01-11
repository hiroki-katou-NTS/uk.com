package nts.uk.ctx.pr.core.dom.wagetable;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(30)
public class WageTableName extends StringPrimitiveValue<WageTableName> {

	public WageTableName(String rawValue) {
		super(rawValue);
	}

	private static final long serialVersionUID = 1L;

}

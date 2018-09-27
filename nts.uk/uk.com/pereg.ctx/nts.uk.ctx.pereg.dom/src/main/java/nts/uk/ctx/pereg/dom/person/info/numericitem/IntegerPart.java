package nts.uk.ctx.pereg.dom.person.info.numericitem;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

@IntegerRange(max = 10, min = 0)
public class IntegerPart extends IntegerPrimitiveValue<IntegerPart>{

	private static final long serialVersionUID = 1L;

	public IntegerPart(Integer rawValue) {
		super(rawValue);
	}

}

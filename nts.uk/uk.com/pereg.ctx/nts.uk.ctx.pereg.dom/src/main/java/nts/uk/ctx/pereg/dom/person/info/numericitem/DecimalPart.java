package nts.uk.ctx.pereg.dom.person.info.numericitem;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

@IntegerRange(max = 5, min = 0)
public class DecimalPart extends IntegerPrimitiveValue<DecimalPart> {

	private static final long serialVersionUID = 1L;

	public DecimalPart(Integer rawValue) {
		super(rawValue);
	}

}

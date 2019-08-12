package nts.uk.ctx.hr.shared.dom.primitiveValue;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

@IntegerRange(min=1, max=6)
public class Integer_1_6 extends IntegerPrimitiveValue<Integer_1_6>{

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public Integer_1_6(Integer rawValue) {
		super(rawValue);
	}
}

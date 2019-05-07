package nts.uk.ctx.hr.develop.dom.careermgmt.careerpath;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

@IntegerRange(min=0, max=99)
public class Integer_0_99 extends IntegerPrimitiveValue<Integer_0_99>{

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public Integer_0_99(Integer rawValue) {
		super(rawValue);
	}
}

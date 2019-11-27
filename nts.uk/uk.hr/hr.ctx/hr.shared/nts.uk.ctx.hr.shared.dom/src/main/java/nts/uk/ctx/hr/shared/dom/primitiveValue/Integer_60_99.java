package nts.uk.ctx.hr.shared.dom.primitiveValue;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * @author thanhpv
 * 定年年齢
 */
@IntegerRange(min=60, max=99)
public class Integer_60_99 extends IntegerPrimitiveValue<Integer_60_99>{

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public Integer_60_99(Integer rawValue) {
		super(rawValue);
	}
}

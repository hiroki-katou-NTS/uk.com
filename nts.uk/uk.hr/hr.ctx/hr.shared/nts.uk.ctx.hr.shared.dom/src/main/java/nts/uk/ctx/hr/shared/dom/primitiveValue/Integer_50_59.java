package nts.uk.ctx.hr.shared.dom.primitiveValue;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * @author thanhpv
 * 希望コース申請年齢
 */
@IntegerRange(min=50, max=59)
public class Integer_50_59 extends IntegerPrimitiveValue<Integer_50_59>{

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public Integer_50_59(Integer rawValue) {
		super(rawValue);
	}
}

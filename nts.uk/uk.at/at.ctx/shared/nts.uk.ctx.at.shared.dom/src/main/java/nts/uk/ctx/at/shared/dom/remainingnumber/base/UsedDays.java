package nts.uk.ctx.at.shared.dom.remainingnumber.base;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;

/**
 * 使用日数
 * @author HopNT
 *
 */
@HalfIntegerRange(min=0d, max = 1d)
public class UsedDays extends HalfIntegerPrimitiveValue<UsedDays>{

	private static final long serialVersionUID = 1L;

	public UsedDays(Double rawValue) {
		super(rawValue);
	}

}

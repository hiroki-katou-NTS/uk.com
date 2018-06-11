package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.remainingnumber;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;

@HalfIntegerRange(min = -999.5 , max = 999.5)
public class DayNumberOfRemain extends HalfIntegerPrimitiveValue<DayNumberOfRemain>{

	/**
	 * 日数
	 */
	private static final long serialVersionUID = 1L;

	public DayNumberOfRemain(Double rawValue) {
		super(rawValue);
	}

}

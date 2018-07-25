package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;

@HalfIntegerRange(min = 0, max = 999.5)
public class DayNumberOfUse extends HalfIntegerPrimitiveValue<DayNumberOfUse> {

	/**
	 * 日数
	 */
	private static final long serialVersionUID = 1L;

	public DayNumberOfUse(Double rawValue) {
		super(rawValue);
	}

}

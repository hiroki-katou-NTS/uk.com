package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;

// tepy - 特別休暇残数用付与日数
@HalfIntegerRange(min = 0, max = 999.5)
public class DayNumberOver extends HalfIntegerPrimitiveValue<DayNumberOver>{

	/**
	 *  日数
	 */
	private static final long serialVersionUID = 1L;

	public DayNumberOver(Double rawValue) {
		super(rawValue);
	}

}

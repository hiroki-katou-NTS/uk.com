package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

@IntegerRange(min = 0 , max = 366)
public class DayNumberOfExeeded extends IntegerPrimitiveValue<DayNumberOfExeeded>{

	/**
	 *  日数
	 */
	private static final long serialVersionUID = 1L;

	public DayNumberOfExeeded(Integer rawValue) {
		super(rawValue);
	}

}

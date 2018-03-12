package nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;

@IntegerMaxValue(4200)
public class WorkTime extends IntegerPrimitiveValue<WorkTime> {

	/**
	 * 時刻(日区分付き)
	 * 
	 * @author sonnlb
	 */
	private static final long serialVersionUID = 1L;

	public WorkTime(Integer rawValue) {
		super(rawValue);
	}

}

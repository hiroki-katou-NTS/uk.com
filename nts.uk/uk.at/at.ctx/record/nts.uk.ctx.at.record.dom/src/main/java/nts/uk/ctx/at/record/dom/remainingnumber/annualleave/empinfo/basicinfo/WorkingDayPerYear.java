package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.basicinfo;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

@IntegerRange(min = 0, max = 366)
public class WorkingDayPerYear extends IntegerPrimitiveValue<WorkingDayPerYear>{

	private static final long serialVersionUID = -3451172594066522368L;

	public WorkingDayPerYear(Integer rawValue) {
		super(rawValue);
	}

}

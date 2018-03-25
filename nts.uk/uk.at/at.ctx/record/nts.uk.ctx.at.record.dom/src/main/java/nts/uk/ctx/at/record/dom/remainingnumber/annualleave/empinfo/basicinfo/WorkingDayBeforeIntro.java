package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.basicinfo;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

@IntegerRange(min = 0, max = 366)
public class WorkingDayBeforeIntro extends IntegerPrimitiveValue<WorkingDayBeforeIntro> {

	private static final long serialVersionUID = 4052002222054808385L;

	public WorkingDayBeforeIntro(Integer rawValue) {
		super(rawValue);
	}

}

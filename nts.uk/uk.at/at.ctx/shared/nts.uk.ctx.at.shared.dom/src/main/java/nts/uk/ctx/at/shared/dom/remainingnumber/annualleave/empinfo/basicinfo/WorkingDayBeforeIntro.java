package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo;

import java.io.Serializable;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

@IntegerRange(min = 0, max = 366)
public class WorkingDayBeforeIntro extends IntegerPrimitiveValue<WorkingDayBeforeIntro> implements Serializable {

	private static final long serialVersionUID = 4052002222054808385L;

	public WorkingDayBeforeIntro(Integer rawValue) {
		super(rawValue);
	}

}

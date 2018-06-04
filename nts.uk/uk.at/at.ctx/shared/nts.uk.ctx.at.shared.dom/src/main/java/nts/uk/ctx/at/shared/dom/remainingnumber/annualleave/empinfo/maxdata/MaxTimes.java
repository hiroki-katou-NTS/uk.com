package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

@IntegerRange(min = 0, max = 99)
public class MaxTimes extends IntegerPrimitiveValue<MaxTimes>{

	private static final long serialVersionUID = 4628893805208803243L;

	public MaxTimes(Integer rawValue) {
		super(rawValue);
	}

}

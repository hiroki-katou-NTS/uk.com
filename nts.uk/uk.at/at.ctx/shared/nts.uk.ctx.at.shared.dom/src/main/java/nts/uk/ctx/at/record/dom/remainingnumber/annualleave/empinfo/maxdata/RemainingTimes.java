package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.maxdata;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

@IntegerRange(min = -99, max = 99)
public class RemainingTimes extends IntegerPrimitiveValue<RemainingTimes>{

	private static final long serialVersionUID = -6059109049160476458L;

	public RemainingTimes(Integer rawValue) {
		super(rawValue);
	}

}


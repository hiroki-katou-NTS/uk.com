package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

@IntegerRange(min = 0, max = 99)
public class UsedTimes extends IntegerPrimitiveValue<UsedTimes>{

	private static final long serialVersionUID = -6059109049160476458L;

	public UsedTimes(Integer rawValue) {
		super(rawValue);
	}

}


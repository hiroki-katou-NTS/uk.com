package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

@TimeRange(max="23:59", min = "00:00")
public class BonusPayTime extends TimeDurationPrimitiveValue<BonusPayTime>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BonusPayTime(int rawValue) {
		super(rawValue);
	}

}

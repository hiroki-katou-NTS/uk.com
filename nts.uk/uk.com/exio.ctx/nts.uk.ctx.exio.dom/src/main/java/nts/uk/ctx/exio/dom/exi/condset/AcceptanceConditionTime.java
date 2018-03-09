package nts.uk.ctx.exio.dom.exi.condset;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

@TimeRange(min = "00:00", max = "999:59")
public class AcceptanceConditionTime extends TimeDurationPrimitiveValue<AcceptanceConditionTime>{


	public AcceptanceConditionTime(int timeAsMinutes) {
		super(timeAsMinutes);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}

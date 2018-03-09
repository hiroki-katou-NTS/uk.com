package nts.uk.ctx.exio.dom.exi.condset;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeMaxValue;
import nts.arc.primitive.constraint.TimeMinValue;

@TimeMinValue("-24:00")
@TimeMaxValue("99:59")
public class AcceptanceConditionTimeMoment extends TimeDurationPrimitiveValue<AcceptanceConditionTimeMoment>{
	

	public AcceptanceConditionTimeMoment(int timeAsMinutes) {
		super(timeAsMinutes);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}

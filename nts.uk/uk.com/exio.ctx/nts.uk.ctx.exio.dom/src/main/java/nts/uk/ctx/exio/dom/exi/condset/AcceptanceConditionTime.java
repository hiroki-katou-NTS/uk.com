package nts.uk.ctx.exio.dom.exi.condset;

import nts.arc.primitive.TimeClockPrimitiveValue;
import nts.arc.primitive.constraint.TimeMaxValue;
import nts.arc.primitive.constraint.TimeMinValue;

@TimeMinValue("0")
@TimeMaxValue("999:59")
public class AcceptanceConditionTime extends TimeClockPrimitiveValue<AcceptanceConditionTime>{


	public AcceptanceConditionTime(Integer timeAsMinutes) {
		super(timeAsMinutes);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}

package nts.uk.ctx.exio.dom.exi.condset.type.time;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeMaxValue;
import nts.arc.primitive.constraint.TimeMinValue;
/**
 * 受入条件時刻
 */
@TimeMinValue("-24:00")
@TimeMaxValue("99:59")
public class ImportingConditionTimeMoment extends TimeDurationPrimitiveValue<ImportingConditionTimeMoment>{
	

	public ImportingConditionTimeMoment(int timeAsMinutes) {
		super(timeAsMinutes);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}

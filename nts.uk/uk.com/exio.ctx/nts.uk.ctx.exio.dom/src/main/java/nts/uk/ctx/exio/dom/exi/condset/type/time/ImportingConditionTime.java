package nts.uk.ctx.exio.dom.exi.condset.type.time;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

/**
 *  受入条件時間
 */
@TimeRange(min = "00:00", max = "999:59")
public class ImportingConditionTime extends TimeDurationPrimitiveValue<ImportingConditionTime>{


	public ImportingConditionTime(int timeAsMinutes) {
		super(timeAsMinutes);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}

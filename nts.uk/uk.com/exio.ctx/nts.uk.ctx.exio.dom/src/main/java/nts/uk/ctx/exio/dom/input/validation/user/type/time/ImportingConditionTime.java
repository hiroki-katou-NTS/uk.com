package nts.uk.ctx.exio.dom.input.validation.user.type.time;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

/**
 *  受入条件時間
 */
@TimeRange(min = "00:00", max = "999:59")
public class ImportingConditionTime extends TimeDurationPrimitiveValue<ImportingConditionTime>{

	private static final long serialVersionUID = 1L;

	public ImportingConditionTime(int timeAsMinutes) {
		super(timeAsMinutes);
	}

}

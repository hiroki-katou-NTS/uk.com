package nts.uk.ctx.at.record.dom.remainingnumber.excessleave;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

@TimeRange(min="-999:59",max="999:59")
public class RemainTime extends TimeDurationPrimitiveValue<RemainTime>{

	/**
	 * 超過有休残時間
	 */
	private static final long serialVersionUID = 1L;

	public RemainTime(int timeAsMinutes) {
		super(timeAsMinutes);
	}

}

package nts.uk.ctx.at.shared.dom.remainingnumber.excessleave;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

@TimeRange(min="0:00",max="999:59")
public class ExcessiveOccurrenceTime extends TimeDurationPrimitiveValue<ExcessiveOccurrenceTime>{

	/**
	 * 超過有休発生時間
	 */
	private static final long serialVersionUID = 1L;

	public ExcessiveOccurrenceTime(int timeAsMinutes) {
		super(timeAsMinutes);
	}

}

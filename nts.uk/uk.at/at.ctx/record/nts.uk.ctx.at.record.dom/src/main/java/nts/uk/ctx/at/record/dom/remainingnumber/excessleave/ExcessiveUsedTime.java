package nts.uk.ctx.at.record.dom.remainingnumber.excessleave;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

@TimeRange(min="0:00",max="999:59")
public class ExcessiveUsedTime extends TimeDurationPrimitiveValue<ExcessiveUsedTime>{

	/**
	 * 超過有休使用時間
	 */
	private static final long serialVersionUID = 1L;

	public ExcessiveUsedTime(int timeAsMinutes) {
		super(timeAsMinutes);
	}

}

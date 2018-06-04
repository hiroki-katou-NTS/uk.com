package nts.uk.ctx.at.record.dom.remainingnumber.subhdmana;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

@TimeRange(min="0:00",max="48:00")
public class UsedHours extends TimeDurationPrimitiveValue<UsedHours>{

	private static final long serialVersionUID = 1L;

	public UsedHours(int timeAsMinutes) {
		super(timeAsMinutes);
	}

}

package nts.uk.ctx.at.record.dom.remainingnumber.base;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

@TimeRange(min="0:00", max = "48:00")
public class ManagementDataHours extends TimeDurationPrimitiveValue<ManagementDataHours>{

	private static final long serialVersionUID = 1L;

	public ManagementDataHours(int timeAsMinutes) {
		super(timeAsMinutes);
	}

}

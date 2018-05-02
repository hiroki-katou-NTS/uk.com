package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.maxdata;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

@TimeRange(min ="-999:59", max="999:59")
public class RemainingMinutes extends TimeDurationPrimitiveValue<RemainingMinutes>{

	private static final long serialVersionUID = -8135525580841453174L;

	public RemainingMinutes(int timeAsMinutes) {
		super(timeAsMinutes);
	}

}

package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

@TimeRange(min ="0:00", max="999:59")
public class UsedMinutes extends TimeDurationPrimitiveValue<UsedMinutes>{

	private static final long serialVersionUID = -8135525580841453174L;

	public UsedMinutes(int timeAsMinutes) {
		super(timeAsMinutes);
	}

	@Override
	protected Integer reviseRawValue(Integer rawValue) {
		if (rawValue == null) return super.reviseRawValue(0);
		if (rawValue > 999 * 60 + 59) rawValue = 999 * 60 + 59;
		if (rawValue < 0) rawValue = 0;
		return super.reviseRawValue(rawValue);
	}
}

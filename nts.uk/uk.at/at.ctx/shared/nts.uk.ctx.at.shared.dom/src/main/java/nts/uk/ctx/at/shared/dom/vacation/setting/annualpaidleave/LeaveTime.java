package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

import java.io.Serializable;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

/**
 * 時間
 * @author masaaki_jinno
 *
 */
@TimeRange(min ="0:00", max="999:59")
public class LeaveTime extends TimeDurationPrimitiveValue<LeaveTime> implements Serializable{

	private static final long serialVersionUID = -8135525580841453174L;

	public LeaveTime(int timeAsMinutes) {
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

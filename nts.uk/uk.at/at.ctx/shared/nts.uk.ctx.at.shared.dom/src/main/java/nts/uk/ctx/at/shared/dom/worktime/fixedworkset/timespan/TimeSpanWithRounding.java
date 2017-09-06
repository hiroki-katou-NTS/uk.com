package nts.uk.ctx.at.shared.dom.worktime.fixedworkset.timespan;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.TimeRoundingSetting;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class TimeSpanWithRounding extends TimeSpanForCalc {

	@Getter
	private final TimeRoundingSetting rounding;
	
	public TimeSpanWithRounding(TimeWithDayAttr start, TimeWithDayAttr end, TimeRoundingSetting rounding) {
		super(start, end);
		this.rounding = rounding;
	}
	
	public int roundedLengthAsMinutesWithDeductingBy(int deductingTimeAsMinutes) {
		return this.rounding.round(this.lengthAsMinutes() - deductingTimeAsMinutes);
	}
}

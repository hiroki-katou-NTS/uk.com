package nts.uk.ctx.at.shared.dom.worktime.fixedworkset.timespan;

import lombok.Getter;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 時間帯(丸め付き)
 * @author keisuke_hoshina
 *
 */
public class TimeSpanWithRounding extends TimeSpanForCalc {

	@Getter
	private final Finally<TimeRoundingSetting> rounding;
	
	public TimeSpanWithRounding(TimeWithDayAttr start, TimeWithDayAttr end, Finally<TimeRoundingSetting> rounding) {
		super(start, end);
		this.rounding = rounding;
	}
	
	public int roundedLengthAsMinutesWithDeductingBy(int deductingTimeAsMinutes) {
		return this.rounding.get().round(this.lengthAsMinutes() - deductingTimeAsMinutes);
	}
	
	public TimeSpanWithRounding newTimeSpan(TimeSpanForCalc newTimeSpan) {
		return new TimeSpanWithRounding(newTimeSpan.getStart(), newTimeSpan.getEnd(), rounding);
	}
}

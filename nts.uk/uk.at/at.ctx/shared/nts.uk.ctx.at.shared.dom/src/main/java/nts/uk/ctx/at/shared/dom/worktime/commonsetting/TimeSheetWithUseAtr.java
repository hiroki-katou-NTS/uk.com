package nts.uk.ctx.at.shared.dom.worktime.commonsetting;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.time.HasTimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.UseAtr;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 時間帯(使用区分付き)
 * @author keisuke_hoshina
 *
 */

@Value
@EqualsAndHashCode(callSuper = false)
public class TimeSheetWithUseAtr extends AggregateRoot implements HasTimeSpanForCalc<TimeSheetWithUseAtr> {
	
	private UseAtr useAtr;
	
	private TimeWithDayAttr startTime;
	
	private TimeWithDayAttr endTime;
	
	private int count;

	@Override
	public TimeSpanForCalc getTimeSpan() {
		return new TimeSpanForCalc(startTime, endTime);
	}

	@Override
	public TimeSheetWithUseAtr newSpanWith(TimeWithDayAttr start, TimeWithDayAttr end) {
		return new TimeSheetWithUseAtr(this.useAtr, start, end, this.count);
	}

}

package nts.uk.ctx.at.shared.dom.worktime.commonsetting;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime_old.SiftCode;
import nts.uk.ctx.at.shared.dom.worktimeset_old.Timezone;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 所定時間設定
 * 
 * @author keisuke_hoshina
 *
 */

@Value
@EqualsAndHashCode(callSuper = false)
public class PredetermineTimeSet extends AggregateRoot {

	private SiftCode siftcode;

	private TimeWithDayAttr dateStartTime;

	private AttendanceTime rangeTimeDay;

	private PredetermineTimeSheetSetting specifiedTimeSheet;

	private PredetermineTime additionSet;

	public int getPredetermineEndTime() {
		return this.dateStartTime.minute() + (int) this.rangeTimeDay.minute();
	}

	public Timezone getTimeSheetOf(int workNo) {
		return this.specifiedTimeSheet.getMatchWorkNoTimeSheet(workNo);
	}

	/**
	 * 1日の範囲を時間帯として返す
	 * 
	 * @return 1日の範囲(時間帯)
	 */
	public TimeSpanForCalc getOneDaySpan() {
		return new TimeSpanForCalc(dateStartTime,
				new TimeWithDayAttr(dateStartTime.valueAsMinutes() + rangeTimeDay.valueAsMinutes()));
	}

}

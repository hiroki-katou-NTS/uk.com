package nts.uk.ctx.at.schedule.dom.shift.management.shifttable;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.DayOfWeek;
import nts.arc.time.calendar.seek.DateSeek;

/**
 * 締切曜日
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.シフト管理.シフト勤務.シフト表.締切曜日
 * @author hiroko_miura
 *
 */
@Value
@RequiredArgsConstructor
public class DeadlineDayofWeek implements DomainValue {

	/** 週 */
	private final DeadlineWeekAtr weekAtr;
	
	/** 曜日 */
	private final DayOfWeek dayOfWeek;
	
	/**
	 * 直前
	 * @param targetDate
	 * @return
	 */
	public GeneralDate getLastdeadline(GeneralDate targetDate) {
		if (this.weekAtr == DeadlineWeekAtr.TWO_WEEK_AGO) {
			targetDate = targetDate.addDays(7);
		}
		
		return targetDate.previous(DateSeek.dayOfWeek(this.dayOfWeek));
	}
}

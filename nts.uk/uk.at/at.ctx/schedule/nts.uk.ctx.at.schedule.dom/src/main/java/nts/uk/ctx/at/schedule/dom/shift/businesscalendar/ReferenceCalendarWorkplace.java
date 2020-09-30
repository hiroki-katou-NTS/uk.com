package nts.uk.ctx.at.schedule.dom.shift.businesscalendar;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkdayDivision;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarWorkplace;
/**
 * 営業日カレンダーの参照先(職場)
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.勤務予定のアラームチェック.同日休日禁止.営業日カレンダーの参照先(職場)
 * @author lan_lt
 *
 */
@Getter
@AllArgsConstructor
public class ReferenceCalendarWorkplace implements ReferenceCalendar{
	/**　職場ID */
	private  final String workplaceID;
	
	@Override
	public BusinessDaysCalendarType getBusinessDaysCalendarType() {
		return BusinessDaysCalendarType.WORKPLACE;
	}

	@Override
	public Optional<WorkdayDivision> getWorkdayDivision(ReferenceCalendar.Require require, GeneralDate day) {
		return require.getCalendarWorkplaceByDay(this.workplaceID, day).map(c -> c.getWorkDayDivision());
	}

	public static interface Require {
		/**
		 * [R-1] 指定日の職場営業日カレンダーを取得する
		 * @param workplaceId
		 * @param date
		 * @return
		 */
		Optional<CalendarWorkplace> getCalendarWorkplaceByDay(String workplaceId, GeneralDate date);
	}
}

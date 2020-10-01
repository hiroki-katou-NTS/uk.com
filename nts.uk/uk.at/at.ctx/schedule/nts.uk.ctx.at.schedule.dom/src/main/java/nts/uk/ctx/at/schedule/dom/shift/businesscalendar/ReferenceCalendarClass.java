package nts.uk.ctx.at.schedule.dom.shift.businesscalendar;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.ClassificationCode;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkdayDivision;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarClass;
/**
 * 営業日カレンダーの参照先(分類)
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.勤務予定のアラームチェック.同日休日禁止.営業日カレンダーの参照先(分類) * @author lan_lt
 *
 */
@Getter
@AllArgsConstructor
public class ReferenceCalendarClass implements ReferenceCalendar{
	/**　分類コード */
	private final ClassificationCode classCode;

	@Override
	public BusinessDaysCalendarType getBusinessDaysCalendarType() {
		return BusinessDaysCalendarType.CLASSSICATION;
	}

	@Override
	public Optional<WorkdayDivision> getWorkdayDivision(ReferenceCalendar.Require require, GeneralDate date) {
		return require.getCalendarClassByDay(this.classCode, date).map(c -> c.getWorkDayDivision());
	}

	public static interface Require {
		/**
		 * [R-1] 指定日の分類営業日カレンダーを取得する
		 * @param classCode
		 * @param date
		 * @return
		 */
		Optional<CalendarClass> getCalendarClassByDay(ClassificationCode classCode, GeneralDate date);
	}
}

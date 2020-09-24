package nts.uk.ctx.at.schedule.dom.schedule.alarm.bansamedayholiday;

import java.util.Optional;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkdayDivision;

/**
 * 営業日カレンダーの参照先(会社)
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.勤務予定のアラームチェック.同日休日禁止
 * @author lan_lt
 *
 */
@AllArgsConstructor
public class ReferenceCalendarCompany implements ReferenceCalendar {

	@Override
	public BusinessDaysCalendarType getBusinessDaysCalendarType() {
		return BusinessDaysCalendarType.COMPANY;
	}
	
	@Override
	public Optional<WorkdayDivision> getWorkdayDivision(Require require, GeneralDate date) {
		return require.getCalendarCompanyByDay(date).map(c -> c.getWorkDayDivision());
	}
}

package nts.uk.ctx.at.schedule.dom.schedule.alarm.bansamedayholiday;

import java.util.Optional;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkdayDivision;

/**
 * 営業日カレンダーの参照先(会社)
 * 
 * @author lan_lt
 *
 */
@AllArgsConstructor
public class CalendarCompanyReference implements CalendarReference {

	@Override
	public BusinessDaysCalendarType getBusinessDaysCalendarType() {
		return BusinessDaysCalendarType.COMPANY;
	}
	
	@Override
	public Optional<WorkdayDivision> getWorkdayDivision(Require require,
			GeneralDate date) {
		return require.getCalendarCompanyByDay(date).map(c -> c.getWorkDayDivision());
	}
}

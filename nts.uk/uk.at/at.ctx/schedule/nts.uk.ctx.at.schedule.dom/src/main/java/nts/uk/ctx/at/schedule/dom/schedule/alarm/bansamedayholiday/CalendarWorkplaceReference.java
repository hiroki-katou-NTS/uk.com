package nts.uk.ctx.at.schedule.dom.schedule.alarm.bansamedayholiday;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkdayDivision;
/**
 * 営業日カレンダーの参照先(職場)
 * @author lan_lt
 *
 */
public class CalendarWorkplaceReference implements CalendarReference{
	
	private String workplaceID;
	
	@Override
	public BusinessDaysCalendarType getBusinessDaysCalendarType() {
		return BusinessDaysCalendarType.WORKPLACE;
	}

	@Override
	public Optional<WorkdayDivision> getWorkdayDivision(Require require, GeneralDate day) {
		return require.getCalendarWorkplaceByDay(this.workplaceID, day).map(c -> c.getWorkDayDivision());
	}

}

package nts.uk.ctx.at.schedule.dom.schedule.alarm.bansamedayholiday;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.ClassificationCode;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkdayDivision;
/**
 * 営業日カレンダーの参照先(分類)
 * @author lan_lt
 *
 */
public class CalendarClssReference implements CalendarReference{
	
	private ClassificationCode clsCode;

	
	@Override
	public BusinessDaysCalendarType getBusinessDaysCalendarType() {
		return BusinessDaysCalendarType.CLASSSICATION;
	}

	@Override
	public Optional<WorkdayDivision> getWorkdayDivision(Require require, GeneralDate date) {
		return require.getCalendarClassByDay(this.clsCode.v(), date).map(c -> c.getWorkDayDivision());
	}

}

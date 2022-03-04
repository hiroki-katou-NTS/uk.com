package nts.uk.ctx.at.schedule.pub.nrweb.monthwage;

import java.util.List;

import nts.arc.time.calendar.period.DatePeriod;

public interface NRWebGetMonthWageSchedulePub {

	//NRWeb照会月間賃金予定を取得する
	public List<NRWebMonthWageScheduleExport> getMonthWageSchedule(String employeeId, DatePeriod period);

}

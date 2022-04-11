package nts.uk.ctx.at.schedule.pub.nrweb.wage;

import nts.arc.time.calendar.period.DatePeriod;

/**
* @author sakuratani
* 
* 			NRWeb照会月間賃金予定を取得Pub
*
*/
public interface NRWebGetMonthWageSchedulePub {

	//NRWeb照会月間賃金予定を取得する
	public NRWebMonthWageScheduleExport get(String employeeId, DatePeriod period);

}

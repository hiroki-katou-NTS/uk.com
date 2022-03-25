package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage.month;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage.NRWebMonthWageScheduleImported;

/**
* @author sakuratani
*
*			NRWeb照会月間賃金予定を取得Adapter
*         
*/
public interface NRWebGetMonthWageScheduleAdapter {

	public NRWebMonthWageScheduleImported get(String employeeId, DatePeriod period);

}

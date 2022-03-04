package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage;

import java.util.List;

import nts.arc.time.calendar.period.DatePeriod;

/**
 * @author sakuratani
 *
 *         NRWeb照会月間賃金予定を取得Adapter
 */
public interface NRWebGetMonthWageScheduleAdapter {

	public List<NRWebMonthWageScheduleImported> GetDataMonthWageSchedule(String cid, String employeeId,
			DatePeriod period);

}

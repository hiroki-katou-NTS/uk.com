package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage.month;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage.NRWebMonthWageRecordImported;

/**
* @author sakuratani
*
*			NRWeb照会月間賃金実績を取得Adapter
*         
*/
public interface NRWebGetMonthWageRecordAdapter {

	public NRWebMonthWageRecordImported get(String employeeId, DatePeriod period);

}

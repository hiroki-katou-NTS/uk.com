package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage.annual;

import java.util.List;

import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage.NRWebMonthWageRecordImported;

/**
* @author sakuratani
*
*         	NRWeb照会年間賃金月別実績を取得Adapter
*         
*/
public interface NRWebGetAnnualWageRecordAdapter {

	public NRWebMonthWageRecordImported get(String employeeId, DatePeriod period, List<YearMonth> yearMonth);
}

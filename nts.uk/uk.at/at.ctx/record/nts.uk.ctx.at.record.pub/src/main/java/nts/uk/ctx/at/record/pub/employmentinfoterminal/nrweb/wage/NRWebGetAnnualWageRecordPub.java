package nts.uk.ctx.at.record.pub.employmentinfoterminal.nrweb.wage;

import java.util.List;

import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;

/**
* @author sakuratani
* 
* 			NRWeb照会年間賃金月別実績を取得Pub
*
*/
public interface NRWebGetAnnualWageRecordPub {

	//RequestList701 NRWeb照会月間賃金実績を取得
	public NRWebMonthWageRecordExport get(String employeeId, DatePeriod period, List<YearMonth> yearMonths);
}

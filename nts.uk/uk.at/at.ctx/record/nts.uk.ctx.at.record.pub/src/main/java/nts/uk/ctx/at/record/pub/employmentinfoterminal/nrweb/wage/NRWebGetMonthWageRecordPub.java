package nts.uk.ctx.at.record.pub.employmentinfoterminal.nrweb.wage;

import nts.arc.time.calendar.period.DatePeriod;

/**
* @author sakuratani
* 
* 			NRWeb照会月間賃金実績を取得Pub
*
*/
public interface NRWebGetMonthWageRecordPub {

	//RequestList701 NRWeb照会月間賃金実績を取得
	public NRWebMonthWageRecordExport get(String employeeId, DatePeriod period);

}

package nts.uk.ctx.at.record.pub.employmentinfoterminal.nrweb.monthwage;

import java.util.List;

import nts.arc.time.calendar.period.DatePeriod;

public interface NRWebGetMonthWageRecordPub {

	//RequestList701 NRWeb照会月間賃金実績を取得
	public List<NRWebMonthWageRecordExport> getMonthWageRecord(String employeeId, DatePeriod period);

}

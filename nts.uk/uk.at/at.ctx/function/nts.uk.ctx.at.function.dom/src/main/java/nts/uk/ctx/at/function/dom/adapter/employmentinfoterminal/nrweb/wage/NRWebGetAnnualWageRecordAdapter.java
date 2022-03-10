package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage;

import java.util.List;

import nts.arc.time.calendar.period.DatePeriod;

public interface NRWebGetAnnualWageRecordAdapter {
	public List<NRWebAnnualWageRecordImported> getAnnualWageRecord(String employeeId, DatePeriod period);

}

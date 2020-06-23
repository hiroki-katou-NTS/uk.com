package nts.uk.ctx.at.record.dom.adapter.company;

import java.util.List;

import nts.arc.time.calendar.period.DatePeriod;

public interface SyCompanyRecordAdapter {
	List<AffCompanyHistImport> getAffCompanyHistByEmployee(List<String> sid, DatePeriod datePeriod);
	
	List<StatusOfEmployeeExport> getListAffComHistByListSidAndPeriod(List<String> sid, DatePeriod datePeriod);
	
}

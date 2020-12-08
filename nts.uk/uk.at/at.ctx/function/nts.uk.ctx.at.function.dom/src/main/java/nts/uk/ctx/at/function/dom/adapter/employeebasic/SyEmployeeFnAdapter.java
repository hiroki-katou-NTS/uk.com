package nts.uk.ctx.at.function.dom.adapter.employeebasic;

import java.util.List;
import java.util.Optional;

import nts.arc.time.calendar.period.DatePeriod;

public interface SyEmployeeFnAdapter {
	List<EmployeeBasicInfoFnImport> findBySIds(List<String> sIds);
	
	List<String> getListEmployeeId(List<String> wkpIds, DatePeriod dateperiod);
	
	List<EmployeeInfoImport> getByListSid(List<String> sIds);
	
	List<String> filterSidLstByDatePeriodAndSids(List<String> sids, DatePeriod period);
	
	List<String> filterSidByCidAndPeriod(String cid, DatePeriod period);
	
	Optional<String> getCompanyId(String employeeId);
}

package nts.uk.ctx.bs.employee.pub.company;

import java.util.List;

import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface SyCompanyPub {
	
	/**
	 * RequestList No.211
	 * @param sid
	 * @param datePeriod
	 * @return
	 */
	List<AffCompanyHistExport> GetAffCompanyHistByEmployee(List<String> sid, DatePeriod datePeriod);
	

}

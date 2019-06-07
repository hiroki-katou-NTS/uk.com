package nts.uk.ctx.bs.employee.pub.employmentstatus;

import java.util.List;

import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface EmploymentStatusPub {
	/**
	 * @author Danpv
	 * RequestList433
	 * 社員ID（List）と期間から期間中１日ずつの在職状態を取得
	 */
	List<EmploymentStatus> findListOfEmployee(List<String> employeeIds, DatePeriod period);

}

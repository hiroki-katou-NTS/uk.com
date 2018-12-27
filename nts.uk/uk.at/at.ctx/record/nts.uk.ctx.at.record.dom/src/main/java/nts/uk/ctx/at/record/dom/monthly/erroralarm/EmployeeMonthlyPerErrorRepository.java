package nts.uk.ctx.at.record.dom.monthly.erroralarm;

import java.util.List;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

public interface EmployeeMonthlyPerErrorRepository {

	void insertAll(EmployeeMonthlyPerError domain);
	
	void removeAll( String employeeID, YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate);
	
	List<EmployeeMonthlyPerError> findError(List<String> employeeID, YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate);
	
}

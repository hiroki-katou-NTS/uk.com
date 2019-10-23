package nts.uk.ctx.at.record.dom.workrecord.closurestatus;

import java.util.List;
import java.util.Optional;

import nts.arc.time.YearMonth;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 
 * @author HungTT
 *
 */
public interface ClosureStatusManagementRepository {
	
	public Optional<ClosureStatusManagement> getById(String employeeId, YearMonth ym, int closureId, ClosureDate closureDate);

	public Optional<ClosureStatusManagement> getLatestByEmpId(String employeeId);
	
	public void add(ClosureStatusManagement domain);

	public List<ClosureStatusManagement> getByIdListAndDatePeriod(List<String> employeeIds, DatePeriod span);
	
}

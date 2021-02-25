package nts.uk.ctx.at.shared.dom.scherec.closurestatus;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import nts.arc.time.YearMonth;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * 
 * @author HungTT
 *
 */
public interface ClosureStatusManagementRepository {
	
	public Optional<ClosureStatusManagement> getById(String employeeId, YearMonth ym, int closureId, ClosureDate closureDate);

	public Optional<ClosureStatusManagement> getLatestByEmpId(String employeeId);
	
	public Map<String, ClosureStatusManagement> getLatestBySids(List<String> sids);
	
	public void add(ClosureStatusManagement domain);

	public List<ClosureStatusManagement> getByIdListAndDatePeriod(List<String> employeeIds, DatePeriod span);
	
}

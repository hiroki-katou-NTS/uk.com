package nts.uk.ctx.at.record.dom.workrecord.closurestatus;

import java.util.Optional;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;

/**
 * 
 * @author HungTT
 *
 */
public interface ClosureStatusManagementRepository {
	
	public Optional<ClosureStatusManagement> getById(String employeeId, YearMonth ym, int closureId, ClosureDate closureDate);

	public Optional<ClosureStatusManagement> getLatestByEmpId(String employeeId);
	
	public void add(ClosureStatusManagement domain);
	
}

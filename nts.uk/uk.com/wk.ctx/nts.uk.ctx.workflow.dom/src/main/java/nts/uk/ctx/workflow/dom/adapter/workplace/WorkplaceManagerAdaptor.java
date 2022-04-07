package nts.uk.ctx.workflow.dom.adapter.workplace;

import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface WorkplaceManagerAdaptor {
	
	Optional<WorkplaceManagerImport> findWkpMngByEmpWkpDate(String employeeID, String workplaceID, GeneralDate date);
	
}

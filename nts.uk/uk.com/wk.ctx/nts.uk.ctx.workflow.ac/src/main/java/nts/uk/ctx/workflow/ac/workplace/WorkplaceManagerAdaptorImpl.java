package nts.uk.ctx.workflow.ac.workplace;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.pub.workplace.WorkplaceListPub;
import nts.uk.ctx.workflow.dom.adapter.workplace.WorkplaceManagerAdaptor;
import nts.uk.ctx.workflow.dom.adapter.workplace.WorkplaceManagerImport;

@Stateless
public class WorkplaceManagerAdaptorImpl implements WorkplaceManagerAdaptor {

	@Inject
	private WorkplaceListPub workplaceListPub;
	
	@Override
	public Optional<WorkplaceManagerImport> findWkpMngByEmpWkpDate(String employeeID, String workplaceID,
			GeneralDate date) {
		return workplaceListPub.findWkpMngByEmpWkpDate(employeeID, workplaceID, date)
			.map(x -> new WorkplaceManagerImport(
					x.getWorkplaceManagerId(), 
					x.getEmployeeId(), 
					x.getWorkplaceId(), 
					x.getHistoryPeriod()));
	}

}

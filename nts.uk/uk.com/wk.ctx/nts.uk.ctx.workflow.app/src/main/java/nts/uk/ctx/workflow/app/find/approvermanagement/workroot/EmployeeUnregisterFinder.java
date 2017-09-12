package nts.uk.ctx.workflow.app.find.approvermanagement.workroot;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.EmployeeUnregisterOutput;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.unregisterapproval.EmployeeUnregisterApprovalRoot;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class EmployeeUnregisterFinder {
	@Inject
	private EmployeeUnregisterApprovalRoot approvalRoot;
	
	public List<EmployeeUnregisterOutput> lstEmployeeUnregister(GeneralDate baseDate){
		String companyId = AppContexts.user().companyId();
		return approvalRoot.lstEmployeeUnregister(companyId, baseDate);
	}
}

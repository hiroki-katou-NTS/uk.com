package nts.uk.ctx.workflow.app.find.approvermanagement.workroot;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.masterapproverroot.ApproverRootMaster;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.EmployeeUnregisterOutput;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.MasterApproverRootOutput;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.unregisterapproval.EmployeeUnregisterApprovalRoot;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class EmployeeUnregisterFinder {
	@Inject
	private EmployeeUnregisterApprovalRoot approvalRoot;
	@Inject
	private ApproverRootMaster masterRoot;
	
	public List<EmployeeUnregisterOutput> lstEmployeeUnregister(GeneralDate baseDate, int sysAtr){
		String companyId = AppContexts.user().companyId();
		return approvalRoot.lstEmployeeUnregister(companyId, baseDate, sysAtr);
	}
	
	public MasterApproverRootOutput masterInfors(MasterApproverRootDto dto) {
		String companyId = AppContexts.user().companyId();
		return masterRoot.masterInfors(companyId,dto.getSysAtr(), dto.getBaseDate(), dto.isChkCompany(), dto.isChkWorkplace(), dto.isChkPerson(), new ArrayList<>());
	}
	
}

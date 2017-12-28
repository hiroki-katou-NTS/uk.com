package nts.uk.ctx.workflow.dom.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApplicationType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.EmploymentRootAtr;
import nts.uk.ctx.workflow.dom.service.output.ApprovalRootContentOutput;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class GenerateApprovalRootStateImpl implements GenerateApprovalRootStateService {

	@Inject
	private CollectApprovalRootService collectApprovalRootService;
	
	@Override
	public ApprovalRootContentOutput getApprovalRootState(String companyID, String employeeID, ApplicationType appType, GeneralDate date) {
		return collectApprovalRootService.getApprovalRootOfSubjectRequest(
				companyID, 
				employeeID, 
				EmploymentRootAtr.CONFIRMATION, 
				appType, 
				date);
	}

}

package nts.uk.ctx.workflow.dom.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApplicationType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.EmploymentRootAtr;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootState;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootStateRepository;
import nts.uk.ctx.workflow.dom.approverstatemanagement.RootType;
import nts.uk.ctx.workflow.dom.service.output.ApprovalRootContentOutput;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class ApprovalRootStateImpl implements ApprovalRootStateService {
	
	@Inject
	private CollectApprovalRootService collectApprovalRootService;
	
	@Inject
	private ApprovalRootStateRepository approvalRootStateRepository;

	@Override
	public void insertAppRootType(String companyID, String employeeID, ApplicationType appType, GeneralDate date, String appID) {
		ApprovalRootContentOutput approvalRootContentOutput = collectApprovalRootService.getApprovalRootOfSubjectRequest(companyID, employeeID, EmploymentRootAtr.APPLICATION, appType, date);
		ApprovalRootState approvalRootState = approvalRootContentOutput.getApprovalRootState();
		approvalRootStateRepository.insert(ApprovalRootState.createFromFirst(
				appID,  
				RootType.EMPLOYMENT_APPLICATION, 
				approvalRootState.getHistoryID(), 
				date, 
				employeeID, 
				approvalRootState));
	}

	@Override
	public void delete(String rootStateID) {
		approvalRootStateRepository.delete(rootStateID);
	}

	@Override
	public List<ApprovalRootState> getByPeriod(String employeeID, GeneralDate startDate, GeneralDate endDate, Integer rootType) {
		return approvalRootStateRepository.findAppByEmployeeIDRecordDate(
				startDate, 
				endDate, 
				employeeID, 
				rootType);
	}
}

package nts.uk.ctx.at.record.dom.approvalmanagement.domainservice;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.approvalmanagement.repository.ApprovalStatusOfDailyPerforRepository;

@Stateless
public class DeleteApprovalStaOfDailyPerforServiceImpl implements DeleteApprovalStaOfDailyPerforService{

	@Inject
	private ApprovalStatusOfDailyPerforRepository approvalStatusOfDailyPerforRepository;
	
	@Override
	public void deleteApprovalStaOfDailyPerforService(String employeeID, GeneralDate processingDate) {
		this.approvalStatusOfDailyPerforRepository.delete(employeeID, processingDate);
	}

}

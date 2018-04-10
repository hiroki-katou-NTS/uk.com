package nts.uk.ctx.at.record.dom.approvalmanagement.domainservice;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.approvalmanagement.ApprovalStatusOfDailyPerfor;
import nts.uk.ctx.at.record.dom.approvalmanagement.repository.ApprovalStatusOfDailyPerforRepository;

@Stateless
public class CreateApprovalStaOfDailyPerforServiceImpl implements CreateApprovalStaOfDailyPerforService{
	
	@Inject
	private ApprovalStatusOfDailyPerforRepository approvalStatusOfDailyPerforRepository;

	@Override
	public void createApprovalStaOfDailyPerforService(String employeeID, GeneralDate processingDate) {
//		// ワークフロー．アルゴリズム「承認ルートインスタンスを生成する」を実行する
//		
//		if (1 == 2) {
//			String approvalRouteID = "";
//			ApprovalStatusOfDailyPerfor approvalStatusOfDailyPerfor = new ApprovalStatusOfDailyPerfor(employeeID, processingDate, approvalRouteID);
//			this.approvalStatusOfDailyPerforRepository.insert(approvalStatusOfDailyPerfor);
//		}		
	}

}

package nts.uk.ctx.at.record.dom.approvalmanagement.domainservice;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.approvalrootstate.AppRootStateConfirmAdapter;
import nts.uk.ctx.at.record.dom.adapter.approvalrootstate.AppRootStateConfirmImport;
import nts.uk.ctx.at.record.dom.approvalmanagement.ApprovalStatusOfDailyPerfor;
import nts.uk.ctx.at.record.dom.approvalmanagement.repository.ApprovalStatusOfDailyPerforRepository;

@Stateless
public class CreateApprovalStaOfDailyPerforServiceImpl implements CreateApprovalStaOfDailyPerforService{
	
	@Inject
	private ApprovalStatusOfDailyPerforRepository approvalStatusOfDailyPerforRepository;
	
	@Inject
	private AppRootStateConfirmAdapter appRootStateConfirmAdapter;

	@Override
	public void createApprovalStaOfDailyPerforService(String companyId, String employeeID, GeneralDate processingDate) {
		// ワークフロー．アルゴリズム「承認ルートインスタンスを生成する」を実行する
		AppRootStateConfirmImport appRootStateConfirmImport = this.appRootStateConfirmAdapter.appRootStateConfirmFinder(companyId, employeeID, 1, null, processingDate);
		if (appRootStateConfirmImport.getIsError() == false) {
			ApprovalStatusOfDailyPerfor approvalStatusOfDailyPerfor = new ApprovalStatusOfDailyPerfor(employeeID, processingDate, appRootStateConfirmImport.getRootStateID().get());
			this.approvalStatusOfDailyPerforRepository.insert(approvalStatusOfDailyPerfor);
		}		
	}

}

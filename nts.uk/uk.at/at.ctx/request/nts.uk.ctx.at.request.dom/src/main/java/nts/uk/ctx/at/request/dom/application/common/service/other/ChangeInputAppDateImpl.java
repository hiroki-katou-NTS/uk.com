package nts.uk.ctx.at.request.dom.application.common.service.other;

//import java.util.List;
//
//import javax.inject.Inject;

import nts.arc.time.GeneralDate;
//import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootAdapter;
//import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootImport;

public class ChangeInputAppDateImpl implements ChangeInputAppDate {

//	@Inject
//	private ApprovalRootAdapter approvalRootAdapter;
	@Override
	public GeneralDate changeInputAppDate(String cid,String sid,int employmentRootAtr,int appType,GeneralDate standardDate) {
		//1.社員の対象申請の承認ルートを取得する
		//List<ApprovalRootImport> approvalRootImports = this.approvalRootAdapter.getApprovalRootOfSubjectRequest(cid, sid, employmentRootAtr, appType, standardDate);
		return null;
	}

}

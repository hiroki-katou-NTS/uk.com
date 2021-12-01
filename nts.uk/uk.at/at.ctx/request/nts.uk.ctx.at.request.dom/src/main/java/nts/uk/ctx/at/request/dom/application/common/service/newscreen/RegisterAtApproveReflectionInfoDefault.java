package nts.uk.ctx.at.request.dom.application.common.service.newscreen;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ReflectedState;
import nts.uk.ctx.at.request.dom.application.ReflectionStatusOfDay;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApproveResultImport;

@Stateless
public class RegisterAtApproveReflectionInfoDefault implements RegisterAtApproveReflectionInfoService {
	
	@Inject
	private ApprovalRootStateAdapter approvalRootStateAdapter;
	
	@Inject
	private ApplicationRepository applicationRepository;
	
	@Override
	public String newScreenRegisterAtApproveInfoReflect(String empID, Application application) {
		// 2.承認する(ApproveService)
		ApproveResultImport approveResultImport = approvalRootStateAdapter.doApprove(application.getAppID(), application.getEnteredPersonID(), "");
		// アルゴリズム「承認全体が完了したか」を実行する(thực hiện thuật toán 「」)
		Boolean approvalCompletionFlag = approvalRootStateAdapter.isApproveAllComplete(approveResultImport.getApprovalRootState());
		if(!approvalCompletionFlag) {
			return "";
		}
		// 「反映情報」．実績反映状態を「反映待ち」にする
		for(ReflectionStatusOfDay reflectionStatusOfDay : application.getReflectionStatus().getListReflectionStatusOfDay()) {
			reflectionStatusOfDay.setActualReflectStatus(ReflectedState.WAITREFLECTION);
		}
		applicationRepository.update(application);
		// 社員の雇用履歴を全て取得する
		// 指定した期間の申請を反映する
		// xử lý trên UI
		return application.getAppID();
	}
}

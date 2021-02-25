package nts.uk.ctx.at.request.dom.application.common.service.detailscreen;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalFrameImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApproverStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.DetailScreenAppData;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.DetailScreenApprovalData;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DetailScreenBeforeImpl implements DetailScreenBefore {
	
	@Inject
	private ApplicationRepository applicationRepository;
	
	@Inject
	private ApprovalRootStateAdapter approvalRootStateAdapter;
	
	@Override
	public DetailScreenAppData getDetailScreenAppData(String appID) {
		String companyID = AppContexts.user().companyId();
		// アルゴリズム「申請IDを使用して申請一覧を取得する」を実行する
		Optional<Application> opApp = applicationRepository.findByID(companyID, appID);
		if(!opApp.isPresent()){
			throw new BusinessException("Msg_198");
		}
		Application application = opApp.get();
		// 15-1.詳細画面の承認コメントを取得する
		DetailScreenApprovalData detailScreenApprovalData = this.getApprovalDetail(application.getAppID());
		
		return new DetailScreenAppData(application, detailScreenApprovalData);
	}

	@Override
	public DetailScreenApprovalData getApprovalDetail(String appID) {
		String loginEmpID = AppContexts.user().employeeId();
		String authorComment = Strings.EMPTY;
		List<ApprovalPhaseStateImport_New> approvalPhaseStateLst = approvalRootStateAdapter.getApprovalDetail(appID);
		// 承認フェーズListを5～1の逆順でループする
		approvalPhaseStateLst.sort(Comparator.comparing(ApprovalPhaseStateImport_New::getPhaseOrder));
		for(ApprovalPhaseStateImport_New approvalPhase : approvalPhaseStateLst){
			boolean find = false;
			for(ApprovalFrameImport_New approvalFrame : approvalPhase.getListApprovalFrame()){
				for(ApproverStateImport_New approverState : approvalFrame.getListApprover()) {
					// ループ中の承認枠．承認者＝ログイン社員の場合
					if(approverState.getApproverID().equals(loginEmpID)) {
						authorComment = approverState.getApprovalReason();
						find = true;
						break;
					}
				}
			}
			if(find){
				break;
			}
		}
		return new DetailScreenApprovalData(approvalPhaseStateLst, authorComment);
	}

}

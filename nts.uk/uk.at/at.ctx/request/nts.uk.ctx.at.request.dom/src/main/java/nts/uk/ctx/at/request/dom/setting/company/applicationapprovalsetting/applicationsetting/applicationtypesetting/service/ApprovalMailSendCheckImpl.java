package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.service;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.AppTypeSetting;
import nts.uk.shr.com.context.AppContexts;

/**
 * 承認処理後にメールを自動送信するか判定
 *
 */
@Stateless
public class ApprovalMailSendCheckImpl implements ApprovalMailSendCheck {
	
	@Inject
	private ApprovalRootStateAdapter approvalRootStateAdapter;

	@Override
	public String sendMailApplicant(AppTypeSetting appTypeSetting, Application application, Boolean allApprovalFlg) {
		// ドメインモデル「申請種類別設定」．承認処理時に自動でメールを送信するをチェックする(check domain 「申請種類別設定」．承認処理時に自動でメールを送信する)
		if(!appTypeSetting.isSendMailWhenApproval()) {
			return "";
		}
		// INPUT.承認完了フラグをチェックする
		if(!allApprovalFlg) {
			return "";
		}
		// 申請者本人をメール送信先リストに追加して、返す
		return application.getEmployeeID();
	}

	@Override
	public List<String> sendMailApprover(AppTypeSetting appTypeSetting, Application application, Integer phaseNumber) {
		String companyID = AppContexts.user().companyId();
		// ドメインモデル「申請種類別設定」．承認処理時に自動でメールを送信するをチェックする
		if(!appTypeSetting.isSendMailWhenApproval()) {
			return Collections.emptyList();
		}
		// 指定する承認フェーズの承認が完了したか
		boolean phaseComplete = approvalRootStateAdapter.isApproveApprovalPhaseStateComplete(companyID, application.getAppID(), phaseNumber);
		if(!phaseComplete){
			return Collections.emptyList();
		}
		// アルゴリズム「次の承認の番の承認者を取得する(メール通知用)」を実行する
		List<String> destination = approvalRootStateAdapter.getNextApprovalPhaseStateMailList(
				application.getAppID(), 
				phaseNumber - 1);
		// メール送信先リストを返す
		return destination;
	}
}

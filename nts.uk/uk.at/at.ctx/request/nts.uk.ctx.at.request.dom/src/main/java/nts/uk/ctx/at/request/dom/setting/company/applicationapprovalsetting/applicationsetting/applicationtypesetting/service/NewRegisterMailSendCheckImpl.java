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
 * 新規登録時のメール送信判定
 *
 */
@Stateless
public class NewRegisterMailSendCheckImpl implements NewRegisterMailSendCheck {

	@Inject
	private ApprovalRootStateAdapter approvalRootStateAdapter;

	@Override
	public List<String> sendMail(AppTypeSetting appTypeSetting, Application application, Integer phaseNumber) {
		String companyID = AppContexts.user().companyId();
		// ドメインモデル「申請種類別設定」．新規登録時に自動でメールを送信するをチェックする(check domain 「申請種類別設定」．新規登録時に自動でメールを送信する)
		if(!appTypeSetting.isSendMailWhenRegister()) {
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
				phaseNumber + 1);
		// メール送信先リストを返す
		return destination;
	}

	
}

package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.service;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.MailResult;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
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
	
	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithm;

	@Override
	public ProcessResult sendMail(AppTypeSetting appTypeSetting, Application application, Integer phaseNumber) {
		String companyID = AppContexts.user().companyId();
		ProcessResult processResult = new ProcessResult();
		processResult.setProcessDone(true);
		// ドメインモデル「申請種類別設定」．新規登録時に自動でメールを送信するをチェックする(check domain 「申請種類別設定」．新規登録時に自動でメールを送信する)
		if(!appTypeSetting.isSendMailWhenRegister()) {
			return processResult;
		}
		// 指定する承認フェーズの承認が完了したか
		boolean phaseComplete = approvalRootStateAdapter.isApproveApprovalPhaseStateComplete(companyID, application.getAppID(), phaseNumber);
		if(!phaseComplete){
			return processResult;
		}
		// アルゴリズム「次の承認の番の承認者を取得する(メール通知用)」を実行する
		List<String> destination = approvalRootStateAdapter.getNextApprovalPhaseStateMailList(
				application.getAppID(), 
				phaseNumber + 1);
		// メール送信先リストに送信先があるかチェックする(check danh sách người nhậnmail có người nhận nào hay không)
		if(CollectionUtil.isEmpty(destination)) {
			return processResult;
		}
		// メール送信先リストにメール送信する(gửi mail cho danh sách người nhận mail)
		MailResult applicantResult = otherCommonAlgorithm.sendMailApproverApprove(destination, application, Collections.emptyList());
		processResult.setProcessDone(true);
		processResult.setAutoSuccessMail(applicantResult.getSuccessList());
		processResult.setAutoFailMail(applicantResult.getFailList());
		processResult.setAutoFailServer(applicantResult.getFailServerList());
		return processResult;
	}

	
}

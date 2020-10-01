package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.MailResult;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.AppTypeSetting;

/**
 * 承認処理後にメールを自動送信するか判定
 *
 */
@Stateless
public class ApprovalMailSendCheckImpl implements ApprovalMailSendCheck {
	
	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithm;

	@Override
	public ProcessResult sendMail(AppTypeSetting appTypeSetting, Application application, Boolean allApprovalFlg) {
		ProcessResult processResult = new ProcessResult();
		processResult.setProcessDone(true);
		// ドメインモデル「申請種類別設定」．承認処理時に自動でメールを送信するをチェックする(check domain 「申請種類別設定」．承認処理時に自動でメールを送信する)
		if(!appTypeSetting.isSendMailWhenApproval()) {
			return processResult;
		}
		// アルゴリズム「承認全体が完了したか」の実行結果をチェックする
		if(!allApprovalFlg) {
			return processResult;
		}
		// 申請者本人にメール送信する(gửi mail cho người viết đơn)
		MailResult applicantResult = otherCommonAlgorithm.sendMailApplicantApprove(application);
		processResult.setAutoSuccessMail(applicantResult.getSuccessList());
		processResult.setAutoFailMail(applicantResult.getFailList());
		processResult.setAutoFailServer(applicantResult.getFailServerList());
		return processResult;
	}
}

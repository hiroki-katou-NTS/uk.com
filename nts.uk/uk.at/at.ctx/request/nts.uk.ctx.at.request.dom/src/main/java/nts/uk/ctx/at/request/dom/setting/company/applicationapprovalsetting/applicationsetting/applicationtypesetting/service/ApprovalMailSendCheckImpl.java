package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.service;

import java.util.ArrayList;
import java.util.List;

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
	public ProcessResult sendMail(String appID, AppTypeSetting appTypeSetting, Application application, Boolean allApprovalFlg) {
		boolean isProcessDone = true;
		boolean isAutoSendMail = false;
		List<String> autoSuccessMail = new ArrayList<>();
		List<String> autoFailMail = new ArrayList<>();
		// ドメインモデル「申請種類別設定」．承認処理時に自動でメールを送信するをチェックする(check domain 「申請種類別設定」．承認処理時に自動でメールを送信する)
		if(!appTypeSetting.isSendMailWhenApproval()) {
			return new ProcessResult(isProcessDone, isAutoSendMail, autoSuccessMail, autoFailMail, appID, "");
		}
		// アルゴリズム「承認全体が完了したか」の実行結果をチェックする
		if(!allApprovalFlg) {
			return new ProcessResult(isProcessDone, isAutoSendMail, autoSuccessMail, autoFailMail, appID, "");
		}
		// 申請者本人にメール送信する(gửi mail cho người viết đơn)
		MailResult applicantResult = otherCommonAlgorithm.sendMailApplicantApprove(application);
		autoSuccessMail.addAll(applicantResult.getSuccessList());
		autoFailMail.addAll(applicantResult.getFailList());
		return new ProcessResult(isProcessDone, isAutoSendMail, autoSuccessMail, autoFailMail, appID, "");
	}
}

package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.MailResult;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.common.AppCanAtr;

/**
 * 承認処理後にメールを自動送信するか判定
 *
 */
@Stateless
public class ApprovalMailSendCheckImpl implements ApprovalMailSendCheck {
	
	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithm;

	@Override
	public ProcessResult sendMail(String appID, String reflectAppId, AppTypeDiscreteSetting discreteSetting, Application application, Boolean allApprovalFlg) {
		boolean isProcessDone = true;
		boolean isAutoSendMail = false;
		List<String> autoSuccessMail = new ArrayList<>();
		List<String> autoFailMail = new ArrayList<>();
		if (discreteSetting.getSendMailWhenApprovalFlg().equals(AppCanAtr.CAN)) {
			isAutoSendMail = true;
			if(allApprovalFlg.equals(Boolean.TRUE)){
				MailResult applicantResult = otherCommonAlgorithm.sendMailApplicantApprove(application);
				autoSuccessMail.addAll(applicantResult.getSuccessList());
				autoFailMail.addAll(applicantResult.getFailList());
			}
		}
		return new ProcessResult(isProcessDone, isAutoSendMail, autoSuccessMail, autoFailMail, appID, reflectAppId);
	}
}

package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.service;

import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSetting;

/**
 * 承認処理後にメールを自動送信するか判定
 *
 */
public interface ApprovalMailSendCheck {
	public ProcessResult sendMail(String appID, String reflectAppId, AppTypeDiscreteSetting discreteSetting, Application_New application, Boolean allApprovalFlg);
}
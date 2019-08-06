package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.service;

import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSetting;

/**
 * 新規登録時のメール送信判定
 *
 */
public interface NewRegisterMailSendCheck {
	public ProcessResult sendMail(AppTypeDiscreteSetting discreteSetting, Application_New application, String companyID, String appID, 
			String reflectAppId, String employeeID, Integer phaseNumber);
}
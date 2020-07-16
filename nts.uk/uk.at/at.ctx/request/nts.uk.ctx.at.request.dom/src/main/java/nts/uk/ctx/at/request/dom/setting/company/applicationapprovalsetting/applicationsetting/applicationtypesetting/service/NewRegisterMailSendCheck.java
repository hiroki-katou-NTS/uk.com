package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.service;

import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSetting;

/**
 * refactor 4
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.申請承認.設定.会社別.申請承認設定.申請設定.申請種類別設定.アルゴリズム.新規登録時のメール送信判定
 * @author Doan Duy Hung
 *
 */
public interface NewRegisterMailSendCheck {
	
	/**
	 * 新規登録時のメール送信判定
	 * @param discreteSetting
	 * @param application
	 * @param companyID
	 * @param appID
	 * @param reflectAppId
	 * @param employeeID
	 * @param phaseNumber
	 * @return
	 */
	public ProcessResult sendMail(AppTypeDiscreteSetting discreteSetting, Application application, String companyID, String appID, 
			String reflectAppId, String employeeID, Integer phaseNumber);
}
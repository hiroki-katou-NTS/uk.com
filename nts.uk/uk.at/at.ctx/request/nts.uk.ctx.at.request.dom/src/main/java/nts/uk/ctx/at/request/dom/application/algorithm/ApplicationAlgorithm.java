package nts.uk.ctx.at.request.dom.application.algorithm;

import nts.uk.ctx.at.request.dom.application.SendMailAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.AppTypeSetting;

/**
 * refactor 4
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.申請承認.申請.アルゴリズム
 * @author Doan Duy Hung
 *
 */
public interface ApplicationAlgorithm {
	
	/**
	 * 登録処理時のメール自動送信するかの判定
	 * @param appID
	 * @param appTypeSetting
	 * @param mailServerSet
	 * @return
	 */
	public SendMailAtr checkAutoSendMailRegister(String appID, AppTypeSetting appTypeSetting, boolean mailServerSet);
	
}

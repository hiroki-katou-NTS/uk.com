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
	 * @param appTypeSetting 申請種類別設定
	 * @param mailServerSet メールサーバ設定済区分
	 * @return
	 */
	public SendMailAtr checkAutoSendMailRegister(AppTypeSetting appTypeSetting, boolean mailServerSet);
	
}

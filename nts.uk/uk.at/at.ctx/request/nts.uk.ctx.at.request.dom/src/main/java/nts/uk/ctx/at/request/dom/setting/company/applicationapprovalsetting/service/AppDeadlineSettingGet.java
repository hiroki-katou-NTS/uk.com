package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.service;

import nts.uk.ctx.at.request.dom.application.common.service.smartphone.output.DeadlineLimitCurrentMonth;

/**
 * refactor 4
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.申請承認.設定.会社別.申請承認設定.アルゴリズム.[No.239]申請締切設定を取得する
 * @author Doan Duy Hung
 *
 */
public interface AppDeadlineSettingGet {
	
	/**
	 * 申請締切設定を取得する
	 * @param companyID
	 * @param closureID
	 * @return
	 */
	public DeadlineLimitCurrentMonth getApplicationDeadline(String companyID, String employeeID, int closureID);
	
}

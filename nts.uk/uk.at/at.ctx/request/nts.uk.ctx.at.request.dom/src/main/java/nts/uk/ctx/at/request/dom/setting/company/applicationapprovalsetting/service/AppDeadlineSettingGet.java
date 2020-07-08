package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.service;

import nts.uk.ctx.at.request.dom.application.common.service.smartphone.output.DeadlineLimitCurrentMonth;

/**
 * refactor 4
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

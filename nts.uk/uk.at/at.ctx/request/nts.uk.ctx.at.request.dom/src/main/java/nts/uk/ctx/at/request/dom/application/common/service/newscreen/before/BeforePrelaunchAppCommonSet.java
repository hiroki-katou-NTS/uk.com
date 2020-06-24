package nts.uk.ctx.at.request.dom.application.common.service.newscreen.before;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType_Old;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.AppCommonSettingOutput;

/**
 * 1-1.新規画面起動前申請共通設定を取得する
 * @author Doan Duy Hung
 *
 */
public interface BeforePrelaunchAppCommonSet {
	
	/**
	 * 1-1.新規画面起動前申請共通設定を取得する
	 * @param companyID
	 * @param employeeID
	 * @param rootAtr
	 * @param targetApp
	 * @param appDate
	 */
	public AppCommonSettingOutput prelaunchAppCommonSetService(String companyID, String employeeID, int rootAtr, ApplicationType_Old targetApp, GeneralDate appDate);
	
}

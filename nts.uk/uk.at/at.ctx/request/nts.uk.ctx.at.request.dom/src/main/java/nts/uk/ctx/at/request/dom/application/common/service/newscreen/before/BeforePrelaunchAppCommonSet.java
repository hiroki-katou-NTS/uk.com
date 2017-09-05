package nts.uk.ctx.at.request.dom.application.common.service.newscreen.before;

import nts.arc.time.GeneralDate;

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
	public void prelaunchAppCommonSetService(String companyID, String employeeID, int rootAtr, int targetApp, GeneralDate appDate);
	
}

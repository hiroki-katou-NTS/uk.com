package nts.uk.ctx.at.request.dom.application.common.service.newscreen.before;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.PrePostAtr;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.InitValueAtr;

/**
 * 
 * 2-1.新規画面登録前の処理
 *
 */
public interface NewBeforeRegister {
	/**
	 * 2-1.新規画面登録前の処理
	 * @param companyID
	 * @param employeeID
	 * @param startDate
	 * @param endDate
	 * @param postAtr
	 * @param routeAtr
	 * @param targetApp
	 */
	public void processBeforeRegister(String companyID, String employeeID, GeneralDate date, PrePostAtr prePostAtr, int employmentRootAtr, int appType);
	
	/**
	 * 1.入社前退職チェック
	 * @param companyID 会社ID
	 * @param employeeID 社員ID（申請本人の社員ID）
	 * @param date 申請する開始日
	 */
	public void retirementCheckBeforeJoinCompany(String companyID, String employeeID, GeneralDate date);
	
	/**
	 * 2.申請の締め切り期限のチェック
	 * @param companyID
	 * @param appID
	 * @param appStartDate
	 * @param appEndDate
	 * @param startDate
	 * @param endDate
	 */
	public void deadlineApplicationCheck(String companyID, String appID, GeneralDate appStartDate, GeneralDate appEndDate, GeneralDate startDate, GeneralDate endDate);
	
	/**
	 * 5.申請の受付制限をチェック
	 * @param postAtr 事前事後区分
	 * @param startDate 申請する開始日
	 * @param endDate 申請する終了日
	 */
	public void applicationAcceptanceRestrictionsCheck(PrePostAtr postAtr, GeneralDate startDate, GeneralDate endDate);
	
	/**
	 * 3.確定チェック
	 * @param companyID 会社ID
	 * @param employeeID 社員ID（申請本人の社員ID）
	 * @param appDate 申請対象日
	 */
	public void confirmationCheck(String companyID, String employeeID, GeneralDate appDate);
}

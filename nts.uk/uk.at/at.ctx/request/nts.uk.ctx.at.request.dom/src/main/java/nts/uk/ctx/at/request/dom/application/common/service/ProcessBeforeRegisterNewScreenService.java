package nts.uk.ctx.at.request.dom.application.common.service;
/**
 * 
 * 2-1.新規画面登録前の処理
 *
 */
public interface ProcessBeforeRegisterNewScreenService {
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
	public void processBeforeRegisterNewScreen(String companyID, String employeeID, String startDate, String endDate, int postAtr, int routeAtr, String targetApp);
	
	/**
	 * 1.入社前退職チェック
	 * @param companyID
	 * @param employeeID
	 * @param date
	 */
	public void retirementCheckBeforeJoinCompany(String companyID, String employeeID, String date);
	
	/**
	 * 2.申請の締め切り期限のチェック
	 * @param appID
	 * @param appStartDate
	 * @param appEndDate
	 * @param startDate
	 * @param endDate
	 */
	public void deadlineApplicationCheck(String appID, String appStartDate, String appEndDate, String startDate, String endDate);
	
	/**
	 * 5.申請の受付制限をチェック
	 * @param postAtr 事前事後区分
	 * @param startDate 申請する開始日
	 * @param endDate 申請する終了日
	 */
	public void applicationAcceptanceRestrictionsCheck(int postAtr, String startDate, String endDate);
	
	/**
	 * 3.確定チェック
	 * @param companyID 会社ID
	 * @param employeeID 社員ID（申請本人の社員ID）
	 * @param appDate 申請対象日
	 */
	public void confirmationCheck(String companyID, String employeeID, String appDate);
}

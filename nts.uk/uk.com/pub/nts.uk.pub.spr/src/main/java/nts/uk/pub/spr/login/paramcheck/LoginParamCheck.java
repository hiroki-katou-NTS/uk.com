package nts.uk.pub.spr.login.paramcheck;

import nts.arc.time.GeneralDate;

/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface LoginParamCheck {
	
	/**
	 * パラメータチェック（事前早出申請）
	 * @param employeeCD
	 * @param startTime
	 * @param date
	 * @param reason
	 * @return
	 */
	public String checkParamPreApp(String employeeCD, String startTime, String date, String reason);
	
	/**
	 * パラメータチェック（事前残業申請）
	 * @param employeeCD
	 * @param endTime
	 * @param date
	 * @param reason
	 * @return
	 */
	public String checkParamOvertime(String employeeCD, String endTime, String date, String reason);
	
	/**
	 * パラメータチェック（日別実績の修正）
	 * @param employeeCD
	 * @param startTime
	 * @param endTime
	 * @param date
	 * @param reason
	 * @return
	 */
	public String checkParamAdjustDaily(String employeeCD, String startTime, String endTime, String date, String reason, String stampFlg);
	
	/**
	 * パラメータチェック（承認一覧）
	 * @param date
	 * @param selectType
	 */
	public void checkParamApprovalList(String date, String selectType);
	
	/**
	 * パラメータチェック（日別実績の確認）
	 * @param date
	 */
	public void checkParamConfirmDaily(String date);
	
	/**
	 * パラメータチェック（残業申請確認）
	 * @param appID
	 */
	public void checkParamConfirmOvertime(String appID);
	
	public GeneralDate getDate(String date);
	
}

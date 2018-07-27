package nts.uk.pub.spr.login;

import java.util.List;

import nts.uk.pub.spr.login.output.LoginUserContextSpr;
import nts.uk.pub.spr.login.output.RoleInfoSpr;
/**
 * 01 勤怠システムへ
 * @author Doan Duy Hung
 *
 */
public interface SprLoginFormService {
	
	/**
	 * 01 勤怠システムへ
	 * @param menu
	 * @param loginEmployeeCode
	 * @param employeeCode
	 * @param startTime
	 * @param endTime
	 * @param date
	 * @param selectType
	 * @param appID
	 * @param reason
	 */
	public LoginUserContextSpr loginFromSpr(String menuCD, String loginEmployeeCD, String employeeCD, String startTime, 
			String endTime, String date, String selectType, String appID, String reason, String stampFlg);
	/**
	 * パラメータチェック
	 * @param menu
	 * @param loginEmployeeCD
	 * @param employeeCD
	 * @param startTime
	 * @param endTime
	 * @param date
	 * @param selectType
	 * @param appID
	 * @param reason
	 */
	public String paramCheck(String menuCD, String loginEmployeeCD, String employeeCD, String startTime, 
			String endTime, String date, String selectType, String appID, String reason, String stampFlg);
	
	/**
	 * パラメータチェック（共通）
	 * @param menu
	 * @param loginEmployeeCD
	 */
	public void paramCheckBasic(String menuCD, String loginEmployeeCD);
	
	/**
	 * セッション生成
	 * @param employeeCD ログイン社員コード
	 * @param employeeID ログイン社員ID
	 * @param personID 個人ID
	 * @return ログインユーザコンテキスト
	 */
	public LoginUserContextSpr generateSession(String loginEmployeeCD, String loginEmployeeID, String personID, String employeeID);
	
	/**
	 * 権限情報取得
	 * @param userID ユーザID
	 * @return role
	 */
	public List<RoleInfoSpr> getRoleInfo(String userID);
	
}

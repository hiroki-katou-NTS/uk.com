package nts.uk.pub.spr.login;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.pub.spr.login.output.LoginUserContextSpr;
/**
 * 01 勤怠システムへ
 * @author Doan Duy Hung
 *
 */
public interface SprLoginFormService {
	
	/**
	 * 01 勤怠システムへ
	 * @param loginCD ログイン社員コード
	 * @param loginID ログイン社員ID
	 * @param personID 個人ID
	 * @param desScreen 遷移先画面（0～6）
	 * @param otherParam ほか各画面必須パラメータ
	 */
	public void loginFromSpr(String companyID, String loginCD, String loginID, String targetID, 
			String personID, Integer destScreen, Integer otherParam, GeneralDate date, String appID,
			GeneralDateTime startTime, GeneralDateTime endTime, Integer extractionTarget);
	
	/**
	 * セッション生成
	 * @param employeeCD ログイン社員コード
	 * @param employeeID ログイン社員ID
	 * @param personID 個人ID
	 * @return ログインユーザコンテキスト
	 */
	public LoginUserContextSpr generateSession(String employeeCD, String employeeID, String personID, GeneralDate date);
	
	/**
	 * 権限情報取得
	 * @param userID ユーザID
	 * @return role
	 */
	public String getRoleInfo(String userID, GeneralDate date);
	
	/**
	 * 画面遷移
	 * @param companyID 会社ID
	 * @param loginID ログイン社員ID
	 * @param targetID （対象社員ID）
	 * @param personID 個人ID
	 * @param destScreen 遷移先画面（0～6）
	 * @param otherParam ほか各画面必須パラメータ
	 * @param date 対象日
	 * @param appID 申請ID
	 * @param startTime 開始時刻
	 * @param endTime 終了時刻
	 * @param extractionTarget 抽出対象[0:全て、1:早出・普通残業のみ]
	 */
	public void screenTransition(String companyID, String loginID, String targetID, String personID, 
			Integer destScreen, Integer otherParam, GeneralDate date, String appID,
			GeneralDateTime startTime, GeneralDateTime endTime, Integer extractionTarget);
	
}

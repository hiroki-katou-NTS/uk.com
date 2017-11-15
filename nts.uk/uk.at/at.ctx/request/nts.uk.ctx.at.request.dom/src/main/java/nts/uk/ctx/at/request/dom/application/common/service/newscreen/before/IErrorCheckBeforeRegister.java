package nts.uk.ctx.at.request.dom.application.common.service.newscreen.before;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeInput;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeCheckResult;

/**
 * 登録前エラーチェック
 * @author ThangNQ
 *
 */
public interface IErrorCheckBeforeRegister {
	
	/**
	 * 計算ボタン未クリックチェック
	 * @param 計算フラグ:CalculateFlg(0,1)
	 * @param companyID
	 * @param employeeID
	 * @param rootAtr
	 * @param targetApp
	 * @param appDate
	 */
	void calculateButtonCheck(int CalculateFlg, String companyID, String employeeID, int rootAtr, ApplicationType targetApp, GeneralDate appDate);
	
	/**
	 * 事前申請超過チェック
	 * @param companyId: 会社ID
	 * @param refPlan: 申請.実績反映状態
	 * @param overtimeInputs: 申請時間(input time in a ATTENDANCE)
	 * @return 0: Normal. 1: 背景色を設定する
	 * 
	 */
	OvertimeCheckResult preApplicationExceededCheck(String companyId, GeneralDate appDate, GeneralDate inputDate, PrePostAtr prePostAtr, int attendanceId, List<OverTimeInput> overtimeInputs) ;
	
	/**
	 * 実績超過チェック
	 * @param companyId: 会社ID
	 * @param appDate: 画面上の申請日付
	 * @param usAtr: 残業休出申請共通設定.実績超過区分
	 */
	void OvercountCheck(String companyId, GeneralDate appDate, UseAtr usAtr);
	
	//３６協定時間上限チェック（月間）
	void TimeUpperLimitMonthCheck();
	
	//３６協定時間上限チェック（年間）
	void TimeUpperLimitYearCheck();
	
	//事前否認チェック
	void preliminaryDenialCheck();
}

package nts.uk.ctx.at.request.dom.application.common.service.newscreen.before;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.ReflectPlanPerState;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.overtime.primitivevalue.OvertimeAppPrimitiveTime;

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
	 * @param refPlan: 実績反映状態
	 * @param preAppTimeInputs: 事前申請の申請時間(List Time in minute)
	 * @param afterAppTimeInputs: 事後申請の申請時間(List Time in minute)
	 * @return 0: Normal. 1: 背景色を設定する
	 * 
	 */
	int preApplicationExceededCheck(String companyId, ReflectPlanPerState refPlan, List<OvertimeAppPrimitiveTime> preAppTimeInputs, List<OvertimeAppPrimitiveTime> afterAppTimeInputs) ;
	
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

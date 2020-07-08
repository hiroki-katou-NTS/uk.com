package nts.uk.ctx.at.request.dom.application.common.service.newscreen.before;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.PrePostAtr_Old;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeCheckResult;

/**
 * 登録前エラーチェック
 * @author ThangNQ
 *
 */
public interface IErrorCheckBeforeRegister {
	
	/**
	 * 実績超過チェック
	 * @param companyId: 会社ID
	 * @param appDate: 画面上の申請日付
	 * @param usAtr: 残業休出申請共通設定.実績超過区分
	 */
	void OvercountCheck(String companyId, GeneralDate appDate, PrePostAtr_Old prePostAtr);
	
	//３６協定時間上限チェック（年間）
	void TimeUpperLimitYearCheck();
	
	/**
	 * 03-05_事前否認チェック
	 * @param companyId: 会社ID
	 * @param appDate: 申請日
	 * @param inputDate: 入力日
	 * @param prePostAtr: 事前事後区分
	 * @return true: show confirm dialog,
	 */
	OvertimeCheckResult preliminaryDenialCheck(String companyId, String employeeID, GeneralDate appDate, GeneralDateTime inputDate, PrePostAtr_Old prePostAtr,int appType);
	
}

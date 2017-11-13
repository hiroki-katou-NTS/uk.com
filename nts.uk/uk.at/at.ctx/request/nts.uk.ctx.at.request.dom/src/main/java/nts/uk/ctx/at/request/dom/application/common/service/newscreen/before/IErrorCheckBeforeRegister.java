package nts.uk.ctx.at.request.dom.application.common.service.newscreen.before;

/**
 * 登録前エラーチェック
 * @author ThangNQ
 *
 */
public interface IErrorCheckBeforeRegister {
	
	//計算ボタン未クリックチェック
	void calculateButtonCheck();
	
	//事前申請超過チェック
	void preApplicationExceededCheck();
	
	//実績超過チェック
	void OvercountCheck();
	
	//３６協定時間上限チェック（月間）
	void TimeUpperLimitMonthCheck();
	
	//３６協定時間上限チェック（年間）
	void TimeUpperLimitYearCheck();
	
	//事前否認チェック
	void preliminaryDenialCheck();
}

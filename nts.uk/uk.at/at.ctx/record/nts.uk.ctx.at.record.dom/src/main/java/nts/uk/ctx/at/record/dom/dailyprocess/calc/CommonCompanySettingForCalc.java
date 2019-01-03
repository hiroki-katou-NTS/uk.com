package nts.uk.ctx.at.record.dom.dailyprocess.calc;

/**
 * 計算に必要なパラメータを取得する
 * @author keisuke_hoshina
 *
 */
public interface CommonCompanySettingForCalc {
	
	default ManagePerCompanySet getCompanySetting() {
		return getCompanySetting(CalculateOption.asDefault());
	}
	
	ManagePerCompanySet getCompanySetting(CalculateOption calcOption); 
}

package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal;

import nts.uk.ctx.at.shared.dom.dailyprocess.calc.CalculateOption;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManagePerCompanySet;

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

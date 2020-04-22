package nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime;

import nts.uk.ctx.at.shared.dom.workrule.addsettingofworktime.NotUseAtr;

/**
 * 加算設定 
 * @author ken_takasu
 *
 */
public interface AddSetting {
	NotUseAtr getNotUseAtr(StatutoryDivision statutoryDivision);
	CalculationByActualTimeAtr getCalculationByActualTimeAtr(StatutoryDivision statutoryDivision);
	HolidayCalcMethodSet getHolidayCalcMethodSet();
}

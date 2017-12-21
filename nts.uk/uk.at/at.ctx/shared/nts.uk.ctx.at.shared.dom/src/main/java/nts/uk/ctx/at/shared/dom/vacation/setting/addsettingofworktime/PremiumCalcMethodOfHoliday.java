package nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime;

import lombok.Value;

/**
 * 休暇の割増計算方法
 * @author keisuke_hoshina
 *
 */
@Value
public class PremiumCalcMethodOfHoliday {
	private CalculationByActualTimeAtr calculationByActualTime;
}

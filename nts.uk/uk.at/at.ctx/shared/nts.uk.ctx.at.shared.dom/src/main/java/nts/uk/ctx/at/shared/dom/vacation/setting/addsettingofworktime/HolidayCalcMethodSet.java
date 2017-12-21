package nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime;

import lombok.Value;

/**
 * 休暇の計算方法の設定
 * @author ken_takasu
 *
 */
@Value
public class HolidayCalcMethodSet {
	private WorkTimeCalcMethodOfHoliday workTimeCalcMethodOfHoliday;
	private PremiumCalcMethodOfHoliday  premiumCalcMethodOfHoliday;
}

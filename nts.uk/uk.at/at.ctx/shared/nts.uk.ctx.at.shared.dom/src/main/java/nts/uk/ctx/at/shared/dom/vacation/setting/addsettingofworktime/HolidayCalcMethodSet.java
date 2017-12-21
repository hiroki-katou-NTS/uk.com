package nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime;

import lombok.Value;

/**
 * 休暇の計算方法の設定
 * @author ken_takasu
 *
 */
@Value
public class HolidayCalcMethodSet {
	private WorkTimeCalcMethodOfHoliday workTimeCalcMethodOfHoliday;//休暇の就業時間計算方法
	private PremiumCalcMethodOfHoliday  premiumCalcMethodOfHoliday;//休暇の割増計算方法
}

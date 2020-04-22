package nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime;

import java.util.Optional;

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
	
	
	public HolidayCalcMethodSet(WorkTimeCalcMethodOfHoliday workTimeCalcMethod, PremiumCalcMethodOfHoliday premiumCalcMethod) {
		this.workTimeCalcMethodOfHoliday = workTimeCalcMethod;
		this.premiumCalcMethodOfHoliday = premiumCalcMethod;
	}

	/**
	 * 別packageの休暇の計算方法の設定を変換する。リファクタリング時に削除予定。
	 * @param other nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.HolidayCalcMethodSet
	 * @return 休暇の計算方法の設定
	 */
	public static HolidayCalcMethodSet convertHolidayCalcMethodSet(
			nts.uk.ctx.at.shared.dom.calculation.holiday.kmk013_splitdomain.HolidayCalcMethodSet other) {
		return new HolidayCalcMethodSet(
				new WorkTimeCalcMethodOfHoliday(
						other.getWorkTimeCalcMethodOfHoliday().getCalculateActualOperation().isCalclationByActualTime()?
								CalculationByActualTimeAtr.CalculationByActualTime:CalculationByActualTimeAtr.CalculationOtherThanActualTime,
						Optional.empty()),
				new PremiumCalcMethodOfHoliday(
						Optional.empty(),
						other.getPremiumCalcMethodOfHoliday().getCalculateActualOperation().isCalclationByActualTime()?
								CalculationByActualTimeAtr.CalculationByActualTime:CalculationByActualTimeAtr.CalculationOtherThanActualTime)
				);
	}
}


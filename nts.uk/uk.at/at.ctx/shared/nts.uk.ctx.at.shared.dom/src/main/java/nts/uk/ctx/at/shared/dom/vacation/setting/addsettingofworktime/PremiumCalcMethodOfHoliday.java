package nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime;

import java.util.Optional;

import lombok.Value;

/**
 * 休暇の割増計算方法
 * @author keisuke_hoshina
 *
 */
@Value
public class PremiumCalcMethodOfHoliday {
	private Optional<PremiumCalcMethodDetailOfHoliday> detailSet;//詳細設定
	private CalculationByActualTimeAtr calculationByActualTime;//実働時間のみで計算する
	

	public PremiumCalcMethodOfHoliday(Optional<PremiumCalcMethodDetailOfHoliday> pre, CalculationByActualTimeAtr calcAtr) {
		this.detailSet = pre;
		this.calculationByActualTime = calcAtr;
	}
}

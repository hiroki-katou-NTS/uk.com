package nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime;

import lombok.Value;

/**
 * 休暇の就業時間計算方法
 * @author ken_takasu
 *
 */
@Value
public class WorkTimeCalcMethodOfHoliday {
	private CalculationByActualTimeAtr calculationByActualTime;//実働のみで計算する
	private WorkTimeCalcMethodDetailOfHoliday detailSet;//詳細設定
}

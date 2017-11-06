package nts.uk.ctx.at.record.dom.daily;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalcSet;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalcSetOfHolidayWorkTime;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalculationCategoryOutsideHours;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalculationOfOverTimeWork;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.RaisingSalaryCalcAtr;

/**
 * 日別実績の計算区分
 * @author keisuke_hoshina
 *
 */
@Value
public class CalcAtrOfDaily {
	private RaisingSalaryCalcAtr bonusPay;
	private AutoCalculationOfOverTimeWork overTimeWork;
	private AutoCalcSetOfHolidayWorkTime holidayHolidayWorkTime;
	
	/**
	 * 計算区分を全て「する」に変更する
	 */
	public static CalcAtrOfDaily turnAllAtrTrue() {
		return new CalcAtrOfDaily(new RaisingSalaryCalcAtr(true, true)
								 ,new AutoCalculationOfOverTimeWork(legalOvertimeHours, legalOtTime, normalOvertimeHours, normalOtTime, earlyOvertimeHours, earlyOtTime)
								 , new AutoCalcSetOfHolidayWorkTime(new AutoCalcSet(AutoCalculationCategoryOutsideHours.CalculateEmbossing)));
	}
}

package nts.uk.ctx.at.record.dom.daily;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalcSetOfHolidayWorkTime;
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
}

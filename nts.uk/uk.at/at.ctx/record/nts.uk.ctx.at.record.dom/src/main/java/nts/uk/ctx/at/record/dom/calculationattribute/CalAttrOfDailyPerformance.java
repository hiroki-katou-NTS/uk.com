package nts.uk.ctx.at.record.dom.calculationattribute;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 
 * @author nampt
 * 日別実績の計算区分 - root
 *
 */
@Getter
public class CalAttrOfDailyPerformance extends AggregateRoot {
	
	//フレックス超過時間
	private AutoCalculationSetting flexExcessTime;
	
	//加給
	private AutoCalRaisingSalarySetting rasingSalarySetting;
	
	//休出時間
	private AutoCalHolidaySetting holidayTimeSetting;
	
	//残業時間
	private AutoCalOfOverTime overtimeSetting;
	
	//遅刻早退
	private AutoCalOfLeaveEarlySetting leaveEarlySetting;
	
//	/**
//	 * 計算区分を全て「する」に変更する
//	 */
//	public static CalAttrOfDailyPerformance turnAllAtrTrue() {
//		return new CalAttrOfDailyPerformance(new RaisingSalaryCalcAtr(true, true)
//								 ,new AutoCalculationOfOverTimeWork(legalOvertimeHours, legalOtTime, normalOvertimeHours, normalOtTime, earlyOvertimeHours, earlyOtTime)
//								 , new AutoCalcSetOfHolidayWorkTime(new AutoCalcSet(AutoCalculationCategoryOutsideHours.CalculateEmbossing)));
//	}
}

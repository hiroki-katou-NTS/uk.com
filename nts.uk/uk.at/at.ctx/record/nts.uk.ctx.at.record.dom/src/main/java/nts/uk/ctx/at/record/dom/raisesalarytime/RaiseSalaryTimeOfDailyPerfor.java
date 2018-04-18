package nts.uk.ctx.at.record.dom.raisesalarytime;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.calculationattribute.BonusPayAutoCalcSet;
import nts.uk.ctx.at.record.dom.calculationattribute.CalAttrOfDailyPerformance;
import nts.uk.ctx.at.record.dom.daily.bonuspaytime.BonusPayTime;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.ActualWorkTimeSheetAtr;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.BonusPayAtr;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculationRangeOfOneDay;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalRaisingSalarySetting;

/**
 * 
 * @author nampt
 * 日別実績の加給時間
 *
 */
@Getter
@AllArgsConstructor
public class RaiseSalaryTimeOfDailyPerfor {
	
	//加給時間
	private List<BonusPayTime> raisingSalaryTimes;
	
	//特定日加給時間
	private List<BonusPayTime> autoCalRaisingSalarySettings;
	
	public static RaiseSalaryTimeOfDailyPerfor calcBonusPayTime(CalculationRangeOfOneDay oneDayRange,AutoCalRaisingSalarySetting raisingAutoCalcSet,BonusPayAutoCalcSet bonusPayAutoCalcSet,
			   											CalAttrOfDailyPerformance calcAtrOfDaily) {
		val bonusPay = oneDayRange.calcBonusPayTime(raisingAutoCalcSet, bonusPayAutoCalcSet, calcAtrOfDaily,BonusPayAtr.BonusPay);
		val specBonusPay = oneDayRange.calcSpecBonusPayTime(raisingAutoCalcSet, bonusPayAutoCalcSet, calcAtrOfDaily, BonusPayAtr.SpecifiedBonusPay);
		
		return new RaiseSalaryTimeOfDailyPerfor(bonusPay, specBonusPay);
	}
	
	

}

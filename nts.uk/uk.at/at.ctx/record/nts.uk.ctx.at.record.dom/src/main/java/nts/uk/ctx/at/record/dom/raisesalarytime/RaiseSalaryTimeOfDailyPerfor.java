package nts.uk.ctx.at.record.dom.raisesalarytime;

import java.util.Collections;
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
		if(oneDayRange == null) return new RaiseSalaryTimeOfDailyPerfor(Collections.emptyList(), Collections.emptyList());
		val bonusPay = oneDayRange.calcBonusPayTime(raisingAutoCalcSet, bonusPayAutoCalcSet, calcAtrOfDaily,BonusPayAtr.BonusPay);
		val specBonusPay = oneDayRange.calcSpecBonusPayTime(raisingAutoCalcSet, bonusPayAutoCalcSet, calcAtrOfDaily, BonusPayAtr.SpecifiedBonusPay);
		
		return new RaiseSalaryTimeOfDailyPerfor(bonusPay, specBonusPay);
	}
	
	/**
	 * 加給・特定日加給の上限値制御指示
	 * @param upperTime 上限値
	 */
	public void controlUpperTimeForSalaryTime(AttendanceTime upperTime) {
		this.controlUpperTime(this.raisingSalaryTimes,upperTime);
		this.controlUpperTime(this.autoCalRaisingSalarySettings,upperTime);
	}

	/**
	 * 上限制御処理
	 * @param bonusPayTimeList 上限制御をする対象
	 * @param upperTime 上限時間
	 */
	private void controlUpperTime(List<BonusPayTime> bonusPayTimeList, AttendanceTime upperTime) {
		//上限制御
		bonusPayTimeList.forEach(tc -> {
			tc.controlUpperTime(upperTime);
		});
	}

}

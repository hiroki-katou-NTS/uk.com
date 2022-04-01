package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workingstyle.flex.SettingOfFlexWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.FlexWithinWorkTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManageReGetClass;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.CalculationRangeOfOneDay;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours.WithinWorkTimeSheet;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 所定内深夜時間
 * @author keisuke_hoshina
 */
@Getter
public class WithinStatutoryMidNightTime {
	/** 時間 */
	private TimeDivergenceWithCalculation time; 
	
	public WithinStatutoryMidNightTime(TimeDivergenceWithCalculation time) {
		super();
		this.time = time;
	}
	
	/**
	 * 所定内深夜時間の計算指示を出す
	 * @param recordReget 実績
	 * @param flexCalcMethod フレックス勤務の設定
	 * @return　所定内深夜時間
	 */
	public static WithinStatutoryMidNightTime calcPredetermineMidNightTime(
			ManageReGetClass recordReGet,
			Optional<SettingOfFlexWork> flexCalcMethod) {

		// 労働条件項目
		WorkingConditionItem conditionItem = recordReGet.getPersonDailySetting().getPersonInfo();
		// 勤務種類
		if (!recordReGet.getWorkType().isPresent()){
			return new WithinStatutoryMidNightTime(TimeDivergenceWithCalculation.defaultValue());
		}
		WorkType workType = recordReGet.getWorkType().get();
		// フレックス勤務日かどうか
		boolean isFlex = conditionItem.getLaborSystem().isFlexTimeWork();
		if (recordReGet.getWorkTimeSetting().isPresent()){
			isFlex = recordReGet.getWorkTimeSetting().get().getWorkTimeDivision().isFlexWorkDay(conditionItem);
		}
		
		CalculationRangeOfOneDay oneDay = recordReGet.getCalculationRangeOfOneDay();
		AttendanceTime calcTime = new AttendanceTime(0);
		if(oneDay.getWithinWorkingTimeSheet().isPresent()) {
			WithinWorkTimeSheet withinWorkTimeSheet = oneDay.getWithinWorkingTimeSheet().get();
			if (isFlex){
				calcTime = ((FlexWithinWorkTimeSheet)withinWorkTimeSheet).calcWithinMidnightTime(
						recordReGet.getPersonDailySetting(),
						recordReGet.getIntegrationOfDaily(),
						recordReGet.getIntegrationOfWorkTime(),
						recordReGet.getIntegrationOfDaily().getCalAttr().getFlexExcessTime().getFlexOtTime().getCalAtr(),
						workType,
						flexCalcMethod.get(),
						recordReGet.getCalculationRangeOfOneDay().getPredetermineTimeSetForCalc(),
						recordReGet.getIntegrationOfDaily().getCalAttr().getLeaveEarlySetting(),
						recordReGet.getAddSetting(),
						recordReGet.getHolidayAddtionSet().get(),
						recordReGet.getDailyUnit(),
						recordReGet.getWorkTimezoneCommonSet(),
						recordReGet.getIntegrationOfDaily().getCalAttr().getFlexExcessTime().getFlexOtTime().getUpLimitORtSet(),
						NotUseAtr.NOT_USE);
			}
			else{
				calcTime = withinWorkTimeSheet.calcMidNightTime();
			}
		}
		return new WithinStatutoryMidNightTime(TimeDivergenceWithCalculation.sameTime(calcTime));
	}
	 
	/**
	 * 実績超過乖離時間の計算
	 * @return
	 */
	public int calcOverLimitDivergenceTime() {
		return this.getTime().getDivergenceTime().valueAsMinutes();
	}
	
	/**
	 * 実績超過乖離時間が発生しているか判定する
	 * @return 乖離時間が発生している
	 */
	public boolean isOverLimitDivergenceTime() {
		return this.calcOverLimitDivergenceTime() > 0 ? true:false;
	}
	
	/**
	 * 乖離時間のみ再計算
	 * @return
	 */
	public WithinStatutoryMidNightTime calcDiverGenceTime() {
		return new WithinStatutoryMidNightTime(this.time!=null?this.time.calcDiverGenceTime():TimeDivergenceWithCalculation.emptyTime());
	}
	
	/**
	 * 深夜時間の上限時間調整処理
	 * @param upperTime 上限時間
	 */
	public void controlUpperTime(AttendanceTime upperTime) {
		this.time = TimeDivergenceWithCalculation.createTimeWithCalculation(this.time.getTime().greaterThan(upperTime)?upperTime:this.time.getTime(), 
																			this.time.getCalcTime().greaterThan(upperTime)?upperTime:this.time.getCalcTime()); 
	}
		
}

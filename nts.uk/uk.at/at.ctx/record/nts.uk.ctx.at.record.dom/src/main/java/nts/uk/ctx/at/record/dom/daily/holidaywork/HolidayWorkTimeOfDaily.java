package nts.uk.ctx.at.record.dom.daily.holidaywork;

import java.util.ArrayList;
import java.util.List;

import lombok.Value;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.record.dom.bonuspay.BonusPayAutoCalcSet;
import nts.uk.ctx.at.record.dom.calculationattribute.CalAttrOfDailyPerformance;
import nts.uk.ctx.at.record.dom.daily.BonusPayTime;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.ActualWorkTimeSheetAtr;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.BonusPayAtr;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.ControlHolidayWorkTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalcSetOfHolidayWorkTime;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.StaturoryAtrOfHolidayWork;

/**
 * 日別実績の休出時間
 * @author keisuke_hoshina
 *
 */
@Value
public class HolidayWorkTimeOfDaily {
	private List<HolidayWorkFrameTimeSheet> holidayWorkFrameTimeSheet;
	private List<HolidayWorkFrameTime> holidayWorkFrameTime;
	private Finally<HolidayMidnightWork> holidayMidNightWork;
	private AttendanceTime holidayTimeSpentAtWork = new AttendanceTime(0);
	
	public HolidayWorkTimeOfDaily(List<HolidayWorkFrameTimeSheet> holidayWorkFrameTimeSheet,List<HolidayWorkFrameTime> holidayWorkFrameTime,
								   Finally<HolidayMidnightWork> holidayMidNightWork) {
		this.holidayWorkFrameTimeSheet = holidayWorkFrameTimeSheet;
		this.holidayWorkFrameTime = holidayWorkFrameTime;
		this.holidayMidNightWork = holidayMidNightWork;
	}
	
	/**
	 * 休出時間枠時間帯をループさせ時間を計算する
	 */
	public List<HolidayWorkFrameTime> collectHolidayWorkTime(AutoCalcSetOfHolidayWorkTime autoCalcSet) {
		List<HolidayWorkFrameTime> calcHolidayWorkTimeList = new ArrayList<>();
		for(HolidayWorkFrameTimeSheet holidyWorkFrameTimeSheet : holidayWorkFrameTimeSheet) {
			calcHolidayWorkTimeList.add(holidyWorkFrameTimeSheet.calcOverTimeWorkTime(autoCalcSet));
		}
		return calcHolidayWorkTimeList;
	}
	
	/**
	 * 休出枠時間へ休出時間の集計結果を追加する
	 * @param hasAddListClass 休出時間帯の集計を行った後の休出枠時間クラス
	 */
	public void addToList(ControlHolidayWorkTime hasAddListClass) {
		this.holidayWorkFrameTime.addAll(hasAddListClass.getHolidayWorkFrame());
	}
	
	/**
	 * 休出時間に含まれている加給時間帯を計算する
	 * @return　加給時間クラス
	 */
	public List<BonusPayTime> calcBonusPay(BonusPayAutoCalcSet bonusPayAutoCalcSet,BonusPayAtr bonusPayAtr,CalAttrOfDailyPerformance calcAtrOfDaily){
		List<BonusPayTime> bonusPayList = new ArrayList<>();
		for(HolidayWorkFrameTimeSheet frameTimeSheet: holidayWorkFrameTimeSheet) {
			bonusPayList.addAll(frameTimeSheet.calcBonusPay(ActualWorkTimeSheetAtr.HolidayWork,bonusPayAutoCalcSet,calcAtrOfDaily));
		}
		return bonusPayList;
	}
	
	/**
	 * 休出時間に含まれている特定日加給時間帯を計算する
	 * @return　加給時間クラス
	 */
	public List<BonusPayTime> calcSpecifiedBonusPay(BonusPayAutoCalcSet bonusPayAutoCalcSet,BonusPayAtr bonusPayAtr,CalAttrOfDailyPerformance calcAtrOfDaily){
		List<BonusPayTime> bonusPayList = new ArrayList<>();
		for(HolidayWorkFrameTimeSheet frameTimeSheet: holidayWorkFrameTimeSheet) {
			bonusPayList.addAll(frameTimeSheet.calcSpacifiedBonusPay(ActualWorkTimeSheetAtr.HolidayWork,bonusPayAutoCalcSet,calcAtrOfDaily));
		}
		return bonusPayList;
	}
	/**
	 * 休出時間が含んでいる深夜時間の算出
	 * @return
	 */
	public HolidayMidnightWork calcMidNightTimeIncludeHolidayWorkTime(AutoCalcSetOfHolidayWorkTime autoCalcSet) {
		EachStatutoryHolidayWorkTime eachTime = new EachStatutoryHolidayWorkTime();
		for(HolidayWorkFrameTimeSheet  frameTime : holidayWorkFrameTimeSheet) {
			eachTime.addTime(frameTime.getStatutoryAtr(), frameTime.calcMidNight(autoCalcSet.getLateNightTime().getCalculationClassification()));
		}
		List<HolidayWorkMidNightTime> holidayWorkList = new ArrayList<>();
		holidayWorkList.add(new HolidayWorkMidNightTime(TimeWithCalculation.sameTime(new AttendanceTime(eachTime.getStatutory())),StaturoryAtrOfHolidayWork.WithinPrescribedHolidayWork));
		holidayWorkList.add(new HolidayWorkMidNightTime(TimeWithCalculation.sameTime(new AttendanceTime(eachTime.getExcess())),StaturoryAtrOfHolidayWork.ExcessOfStatutoryHolidayWork));
		holidayWorkList.add(new HolidayWorkMidNightTime(TimeWithCalculation.sameTime(new AttendanceTime(eachTime.getPublicholiday())),StaturoryAtrOfHolidayWork.PublicHolidayWork));
		return new HolidayMidnightWork(holidayWorkList);
	}
	
	/**
	 * 全枠の休出時間の合計の算出
	 * @return　休出時間
	 */
	public int calcTotalFrameTime() {
		int totalTime = 0;
		for(HolidayWorkFrameTime holidayWorkFrameTime : holidayWorkFrameTime) {
			totalTime += holidayWorkFrameTime.getHolidayWorkTime().get().getTime().valueAsMinutes();
		}
		return totalTime;
	}
}

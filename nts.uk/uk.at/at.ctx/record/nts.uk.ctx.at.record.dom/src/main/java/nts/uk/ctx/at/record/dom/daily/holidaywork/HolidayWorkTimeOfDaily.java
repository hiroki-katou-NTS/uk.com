package nts.uk.ctx.at.record.dom.daily.holidaywork;

import java.util.ArrayList;
import java.util.List;

import lombok.Value;
<<<<<<< HEAD
import nts.uk.ctx.at.record.dom.bonuspay.autocalc.BonusPayAutoCalcSet;
import nts.uk.ctx.at.record.dom.daily.AttendanceLeavingWork;
import nts.uk.ctx.at.record.dom.daily.BonusPayTime;
import nts.uk.ctx.at.record.dom.daily.CalcAtrOfDaily;
import nts.uk.ctx.at.record.dom.daily.ExcessOverTimeWorkMidNightTime;
=======
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.record.dom.bonuspay.BonusPayAutoCalcSet;
import nts.uk.ctx.at.record.dom.calculationattribute.CalAttrOfDailyPerformance;
import nts.uk.ctx.at.record.dom.daily.BonusPayTime;
>>>>>>> 73f7cfb32c... 月別実績で使用する日別計算クラスのコミット
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.ActualWorkTimeSheetAtr;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.BonusPayAtr;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.ControlHolidayWorkTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalcSetOfHolidayWorkTime;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.StaturoryAtrOfHolidayWork;
<<<<<<< HEAD
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.FixRestSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.WorkTimeCommonSet;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
=======
>>>>>>> 73f7cfb32c... 月別実績で使用する日別計算クラスのコミット

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
<<<<<<< HEAD
	}
	
	/**
	 * 休出時間帯の作成と時間計算
	 * @param attendanceTime
	 * @param oneDayRange　１日の計算範囲.１日の範囲
	 * @return
	 */
	public static OverDayEnd.SplitHolidayWorkTime getHolidayWorkTimeOfDaily(List<FixRestSetting> fixTimeSet ,AttendanceLeavingWork attendanceLeave,
			OverDayEndCalcSet dayEndSet,WorkTimeCommonSet overDayEndSet ,List<HolidayWorkFrameTimeSheet> holidayTimeWorkItem,
			WorkType beforeDay,WorkType toDay,WorkType afterDay) {
		
		List<HolidayWorkFrameTimeSheet> holidayWorkTimeSheetList = new ArrayList<HolidayWorkFrameTimeSheet>();
		List<HolidayWorkFrameTime> holidayWorkFrameTimeList = new ArrayList<HolidayWorkFrameTime>();
		Optional<TimeSpanForCalc> timeSpan;
		TimeSpanForCalc attendanceLeaveSheet = new TimeSpanForCalc(attendanceLeave.getAttendance().getActualEngrave().getTimesOfDay(),attendanceLeave.getLeaveWork().getActualEngrave().getTimesOfDay());
		for(FixRestSetting holidayTimeSheet : fixTimeSet) {
			/*計算範囲の判断*/
			timeSpan = attendanceLeaveSheet.getDuplicatedWith(holidayTimeSheet.getHours().getSpan());
			/*控除時間帯を分割*/
			/*控除時間帯を保持(控除時間帯を分割で取得したListに対してmapを使う)*/
			
			/*遅刻早退処理*/
		//	if(/*遅刻早退どっちの設定を見てもOKなので、どちらかの休出の場合でも計算するを見る*/) {
		//	}
			
			/*休出時間帯の作成*/
			holidayWorkTimeSheetList.add(new HolidayWorkFrameTimeSheet());
		}
		/*0時跨ぎ*/
		OverDayEnd overEnd = new OverDayEnd();
		OverDayEnd.SplitHolidayWorkTime splitOverTimeWork = overEnd.new SplitHolidayWorkTime(dayEndSet,overDayEndSet ,holidayTimeWorkItem,beforeDay,toDay,afterDay);
		
		return splitOverTimeWork;
=======
		this.holidayMidNightWork = holidayMidNightWork;
>>>>>>> 73f7cfb32c... 月別実績で使用する日別計算クラスのコミット
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
	public List<BonusPayTime> calcBonusPay(BonusPayAutoCalcSet bonusPayAutoCalcSet,BonusPayAtr bonusPayAtr,CalcAtrOfDaily calcAtrOfDaily){
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
	public List<BonusPayTime> calcSpecifiedBonusPay(BonusPayAutoCalcSet bonusPayAutoCalcSet,BonusPayAtr bonusPayAtr,CalcAtrOfDaily calcAtrOfDaily){
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
<<<<<<< HEAD
	public void calcMidNightTimeIncludeHolidayWorkTime(AutoCalcSetOfHolidayWorkTime autoCalcSet) {
		EachStatutoryHolidayWorkTime eachTime;
		for(HolidayWorkFrameTimeSheet  frameTime : holidayWorkFrameTimeSheet) {
			eachTime.addTime(frameTime.getStatutoryAtr(), frameTime.calcMidNight(autoCalcSet.getLateNightTime().getCalculationClassification()));
		}
		return new HolidayWorkMidNightTime(TimeWithCalculation.sameTime(new AttendanceTime(eachTime.getTimeBaseOnAtr())));
=======
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
>>>>>>> 73f7cfb32c... 月別実績で使用する日別計算クラスのコミット
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

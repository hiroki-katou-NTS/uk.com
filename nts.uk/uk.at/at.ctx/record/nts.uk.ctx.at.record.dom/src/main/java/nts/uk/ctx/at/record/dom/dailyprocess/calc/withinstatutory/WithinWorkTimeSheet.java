package nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.val;
import nts.uk.ctx.at.record.dom.MidNightTimeSheet;
import nts.uk.ctx.at.record.dom.bonuspay.autocalc.BonusPayAutoCalcSet;
import nts.uk.ctx.at.record.dom.daily.BonusPayTime;
import nts.uk.ctx.at.record.dom.daily.CalcAtrOfDaily;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.record.dom.daily.midnight.WithinStatutoryMidNightTime;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.ActualWorkTimeSheetAtr;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.BonusPayAtr;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.DeductionTimeSheet;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.BonusPaySetting;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.BonusPayTimesheet;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.SpecifiedbonusPayTimeSheet;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalculationCategoryOutsideHours;
import nts.uk.ctx.at.shared.dom.worktime.AmPmClassification;
import nts.uk.ctx.at.shared.dom.worktime.CommomSetting.PredetermineTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.WorkTimeCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.WorkTimeOfTimeSheetSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.WorkTimeOfTimeSheetSetList;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceHolidayAttr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 就業時間内時間帯
 * @author keisuke_hoshina
 *
 */
@RequiredArgsConstructor
public class WithinWorkTimeSheet {

	//必要になったら追加
	//private WorkingHours
	//private RaisingSalaryTime
	
	private final List<WithinWorkTimeFrame> withinWorkTimeFrame;
	private final List<LeaveEarlyDecisionClock> leaveEarlyDecisionClock;
	private final List<LateDecisionClock> lateDecisionClock;
	
	/**
	 * 就業時間内時間帯の作成
	 * @param workType　勤務種類クラス
	 * @param predetermineTimeSet 所定時間設定クラス
	 * @param fixedWorkSetting  固定勤務設定クラス
	 * @return 就業時間内時間帯クラス
	 */
	public static WithinWorkTimeSheet createAsFixedWork(
			WorkType workType,
			PredetermineTimeSet predetermineTimeSet,
			FixedWorkSetting fixedWorkSetting,
			WorkTimeCommonSet workTimeCommonSet,
			DeductionTimeSheet deductionTimeSheet,
			BonusPaySetting bonusPaySetting
			) {
		

		predetermineTimeSet.getSpecifiedTimeSheet().correctPredetermineTimeSheet(workType.getDailyWork());

		//遅刻猶予時間の取得
		val lateGraceTime = workTimeCommonSet.getLateSetting().getGraceTimeSetting();//引数でworkTimeCommonSet毎渡すように修正予定
		//早退猶予時間の取得
		val leaveEarlyGraceTime = workTimeCommonSet.getLeaveEarlySetting().getGraceTimeSetting();
						
		val timeFrames = new ArrayList<WithinWorkTimeFrame>();
		WithinWorkTimeFrame timeFrame;
		val workingHourSet = createWorkingHourSet(workType, predetermineTimeSet, fixedWorkSetting);
		
		for (int frameNo = 0; frameNo < workingHourSet.toArray().length; frameNo++) {
			List<BonusPayTimesheet> bonusPayTimeSheet = new ArrayList<>();
			List<SpecifiedbonusPayTimeSheet> specifiedBonusPayTimeSheet = new ArrayList<>();
			Optional<MidNightTimeSheet> midNightTimeSheet;
			for(WorkTimeOfTimeSheetSet duplicateTimeSheet :workingHourSet) {
				timeFrame = new WithinWorkTimeFrame(frameNo, duplicateTimeSheet.getTimeSpan(),duplicateTimeSheet.getTimeSpan(),Collections.emptyList(),Collections.emptyList(),Optional.empty(),Collections.emptyList());
				/*控除時間を分割する
				 * */
				/*加給*/
				bonusPayTimeSheet = bonusPaySetting.createDuplicationBonusPayTimeSheet(duplicateTimeSheet.getTimeSpan());
				specifiedBonusPayTimeSheet = bonusPaySetting.createDuplicationSpecifyBonusPay(duplicateTimeSheet.getTimeSpan());
				/*深夜*/
				midNightTimeSheet = timeFrame.createMidNightTimeSheet();
				
				timeFrames.add(new WithinWorkTimeFrame(timeFrame.getWorkingHoursTimeNo(),timeFrame.getTimeSheet(),timeFrame.getCalcrange(),timeFrame.getDeductionTimeSheets(),bonusPayTimeSheet,midNightTimeSheet,specifiedBonusPayTimeSheet));
			}
		}
		
		/*所定内割増時間の時間帯作成*/
		
		return new WithinWorkTimeSheet(
				timeFrames,
				LeaveEarlyDecisionClock.createListOfAllWorks(predetermineTimeSet, deductionTimeSheet, leaveEarlyGraceTime),
				LateDecisionClock.createListOfAllWorks(predetermineTimeSet, deductionTimeSheet, lateGraceTime));
	}
	
	/**
	 * 遅刻・早退時間を控除する
	 */
	public void deductLateAndLeaveEarly() {
		
	}
	
	/**
	 * 指定した枠番の就業時間内時間枠を返す
	 * @param frameNo
	 * @return
	 */
	public WithinWorkTimeFrame getFrameAt(int frameNo) {
		return this.withinWorkTimeFrame.get(frameNo);
	}

	/**
	 *  所定時間と重複している時間帯の判定
	 * @param workType　勤務種類クラス
	 * @param predetermineTimeSet 所定時間設定クラス
	 * @param fixedWorkSetting 固定勤務設定クラス
	 * @return 所定時間と重複している時間帯
	 */
	private static List<WorkTimeOfTimeSheetSet> createWorkingHourSet(WorkType workType, PredetermineTimeSet predetermineTimeSet,
			FixedWorkSetting fixedWorkSetting) {
		
		val attendanceHolidayAttr = workType.getAttendanceHolidayAttr();
		return getWorkingHourSetByAmPmClass(fixedWorkSetting, attendanceHolidayAttr).extractBetween(
				predetermineTimeSet.getDateStartTime(),
				new TimeWithDayAttr(predetermineTimeSet.getPredetermineEndTime()));
	}

	/**
	 * 平日出勤の出勤時間帯を取得
	 * @param fixedWorkSetting 固定勤務設定クラス
	 * @param attendanceHolidayAttr 出勤休日区分
	 * @return 出勤時間帯
	 */
	private static WorkTimeOfTimeSheetSetList getWorkingHourSetByAmPmClass(
			FixedWorkSetting fixedWorkSetting,
			AttendanceHolidayAttr attendanceHolidayAttr) {
		
		switch (attendanceHolidayAttr) {
		case FULL_TIME:
		case HOLIDAY:
			return fixedWorkSetting.getWorkingHourSet(AmPmClassification.ONE_DAY);
		case MORNING:
			return fixedWorkSetting.getWorkingHourSet(AmPmClassification.AM);
		case AFTERNOON:
			return fixedWorkSetting.getWorkingHourSet(AmPmClassification.PM);
		default:
			throw new RuntimeException("unknown attendanceHolidayAttr" + attendanceHolidayAttr);
		}
	}
	
	/**
	 * 引数のNoと一致する遅刻判断時刻を取得する
	 * @param workNo
	 * @return　遅刻判断時刻
	 */
	public LateDecisionClock getlateDecisionClock(int workNo) {
		List<LateDecisionClock> clockList = this.lateDecisionClock.stream().filter(tc -> tc.getWorkNo()==workNo).collect(Collectors.toList());
		if(clockList.size()>1) {
			throw new RuntimeException("Exist duplicate workNo : " + workNo);
		}
		return clockList.get(0);
	}
	
	/**
	 * 引数のNoと一致する早退判断時刻を取得する
	 * @param workNo
	 * @return　早退判断時刻
	 */
	public LeaveEarlyDecisionClock getleaveEarlyDecisionClock(int workNo) {
		List<LeaveEarlyDecisionClock> clockList = this.leaveEarlyDecisionClock.stream().filter(tc -> tc.getWorkNo()==workNo).collect(Collectors.toList());
		if(clockList.size()>1) {
			throw new RuntimeException("Exist duplicate workNo : " + workNo);
		}
		return clockList.get(0);
	}
	
	
	/**
	 * 日別計算の遅刻早退時間の計算
	 * @return
	 */
	public int calcLateLeaveEarlyinWithinWorkTime() {
		
	}
	
	/**
	 * 就業時間内時間帯に入っている加給時間の計算
	 */
	public List<BonusPayTime> calcBonusPayTimeInWithinWorkTime(BonusPayAutoCalcSet bonusPayAutoCalcSet,BonusPayAtr bonusPayAtr,CalcAtrOfDaily calcAtrOfDaily) {
		List<BonusPayTime> bonusPayList = new ArrayList<>();
		for(WithinWorkTimeFrame timeFrame : withinWorkTimeFrame) {
			bonusPayList.addAll(timeFrame.calcBonusPay(ActualWorkTimeSheetAtr.WithinWorkTime,bonusPayAutoCalcSet, calcAtrOfDaily));
		}
		return bonusPayList;
	}
	/**
	 * 就業時間内時間帯に入っている特定加給時間の計算
	 */
	public List<BonusPayTime> calcSpecifiedBonusPayTimeInWithinWorkTime(BonusPayAutoCalcSet bonusPayAutoCalcSet,BonusPayAtr bonusPayAtr,CalcAtrOfDaily calcAtrOfDaily) {
		List<BonusPayTime> bonusPayList = new ArrayList<>();
		for(WithinWorkTimeFrame timeFrame : withinWorkTimeFrame) {
			bonusPayList.addAll(timeFrame.calcBonusPay(ActualWorkTimeSheetAtr.WithinWorkTime,bonusPayAutoCalcSet, calcAtrOfDaily));
		}
		return bonusPayList;
	}
	/**
	 * 法定内深夜時間の計算
	 * @return　法定内深夜時間
	 */
	public WithinStatutoryMidNightTime calcMidNightTime(AutoCalculationCategoryOutsideHours autoCalcSet) {
		int totalMidNightTime = 0;
		totalMidNightTime = withinWorkTimeFrame.stream()
											   .filter(tg -> tg.getMidNightTimeSheet().isPresent())
											   .map(ts -> ts.getMidNightTimeSheet().get().calcMidNight(autoCalcSet))
											   .collect(Collectors.summingInt(tc -> tc));
		return new WithinStatutoryMidNightTime(TimeWithCalculation.sameTime(new AttendanceTime(totalMidNightTime)));
	}
}

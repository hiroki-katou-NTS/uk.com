package nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.val;

import nts.uk.ctx.at.record.dom.daily.breaktimegoout.BreakTimeSheet;
import nts.uk.ctx.at.record.dom.daily.breaktimegoout.BreakTimeSheetOfDaily;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.DeductionTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.record.mekestimesheet.LeaveEarlyTimeSheet;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.BonusPaySetting;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.CalculationByActualTimeAtr;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.HolidayAdditionAtr;
import nts.uk.ctx.at.shared.dom.worktime.AmPmClassification;
import nts.uk.ctx.at.shared.dom.worktime.CommomSetting.PredetermineTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.CommomSetting.TimeSheetWithUseAtr;
import nts.uk.ctx.at.shared.dom.worktime.CommonSetting.lateleaveearly.LateLeaveEarlyClassification;
import nts.uk.ctx.at.shared.dom.worktime.CommonSetting.lateleaveearly.LateLeaveEarlyGraceTime;
import nts.uk.ctx.at.shared.dom.worktime.CommonSetting.lateleaveearly.LateLeaveEarlySettingOfWorkTime;
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
		
		val timeFrames = new ArrayList<WithinWorkTimeFrame>();
		predetermineTimeSet.getSpecifiedTimeSheet().correctPredetermineTimeSheet(workType.getDailyWork());
		
		val workingHourSet = createWorkingHourSet(workType, predetermineTimeSet, fixedWorkSetting);
		
		WithinWorkTimeFrame newTimeFrame;
		for (int frameNo = 0; frameNo < workingHourSet.toArray().length; frameNo++) {
			for(WorkTimeOfTimeSheetSet duplicateTimeSheet :workingHourSet) {
				newTimeFrame = new WithinWorkTimeFrame(frameNo, duplicateTimeSheet.getTimeSpan(), duplicateTimeSheet.getTimeSpan());
				newTimeFrame.bonusPaySetting.createBonusPayTimeSheetList();
				
				timeFrames.add();
			}
		}
		
		/*所定内割増時間の時間帯作成*/
		
		return new WithinWorkTimeSheet(timeFrames,leaveEarlyDecisionClock,lateDecisionClock);
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
	
	
	public LeaveEarlyDecisionClock getleaveEarlyDecisionClock(int workNo) {
		List<LeaveEarlyDecisionClock> clockList = this.leaveEarlyDecisionClock.stream().filter(tc -> tc.getWorkNo()==workNo).collect(Collectors.toList());
		if(clockList.size()>1) {
			throw new RuntimeException("Exist duplicate workNo : " + workNo);
		}
		return clockList.get(0);
	}
	
	
	
	/**
	 * 遅刻早退時間帯の作成
	 * @return
	 */
	public List<LateDecisionClock> lateLeaveEarlyTimeSheetCleate() {
		List<LateDecisionClock> lateDecisionClock = new ArrayList<LateDecisionClock>();
		for(int workNo=1;workNo<3;workNo++) {//勤務回数分ループ	
			
		}
	}
	
	
	/**
	 * 遅刻判断時刻の取得
	 * @return
	 */
	public TimeWithDayAttr getLateDecisionClock(WorkTimeCommonSet workTimeCommonSet,PredetermineTimeSet predetermineTimeSet,int workNo,DeductionTimeSheet deductionTimeSheet) {
		LateLeaveEarlyGraceTime lateGraceTime = workTimeCommonSet.getGraceTimeSet(LateLeaveEarlyClassification.LEAVEEARLY).getGraceTime();//遅刻猶予時間の取得
		TimeWithDayAttr lateDecisionClock = ;
	
	}
	
	/**
	 * 遅刻判断時刻の作成
	 * @param predetermineTimeSet
	 * @param workNo
	 * @param deductionTimeSheet
	 * @param lateGraceTime
	 * @return
	 */
	public TimeWithDayAttr lateDecisionClockCreate(PredetermineTimeSet predetermineTimeSet,int workNo,DeductionTimeSheet deductionTimeSheet,LateLeaveEarlyGraceTime lateGraceTime) {
		if(lateGraceTime.v().equals(0)){
			//猶予時間が0：00の場合、所定時間の開始時刻を判断時刻にする
			TimeWithDayAttr lateDecisionClock = new TimeWithDayAttr(predetermineTimeSet.getSpecifiedTimeSheet().getMatchWorkNoTimeSheet(workNo).getStartTime().valueAsMinutes());			
		}else {
			//猶予時間帯の終了時刻の作成
			val correctedEndTime = predetermineTimeSet.getSpecifiedTimeSheet().getMatchWorkNoTimeSheet(workNo).getTimeSpan().getStart().forwardByMinutes(lateGraceTime.minute());
			//猶予時間帯の作成
			TimeSpanForCalc correctedTimeSheet = new TimeSpanForCalc(predetermineTimeSet.getSpecifiedTimeSheet().getMatchWorkNoTimeSheet(workNo).getTimeSpan().getStart(),correctedEndTime);
			//休憩種類ごとに休憩時間帯をループ
			for(BreakTimeSheetOfDaily breakTimeSheet : deductionTimeSheet.getBreakTimeSheet().getBreakTimeSheetOfDaily()) {
			
				}
			}//補正後の猶予時間帯の開始時刻を判断時刻とする		
			TimeWithDayAttr lateDecisionClock = new TimeWithDayAttr(correctedTimeSheet.getStart().valueAsMinutes());	
		}
	
	/**
	 * 遅刻猶予時間帯と控除時間帯との重複チェック
	 * @return
	 */
	public TimeSpanForCalc correctedTimeSheet(BreakTimeSheetOfDaily breakTimeSheet,TimeSpanForCalc correctedTimeSheet) {	
		//休憩時間帯分ループ
		for(BreakTimeSheet timeSheet : breakTimeSheet.getBreakTimeSheet()) {
			//重複している時間帯を取得
			Optional<TimeSpanForCalc> duplicatedTimeSheet = correctedTimeSheet.getDuplicatedWith(timeSheet.getTimeSheet());
			if(duplicatedTimeSheet.isPresent()) {//重複している時間帯が存在する場合
				//猶予時間帯の開始時刻を重複している時間分手前にズラす
				correctedTimeSheet.getEnd().forwardByMinutes(duplicatedTimeSheet.get().lengthAsMinutes());
			}		
		}
		return correctedTimeSheet;
	}
	
	
	
	
	/**
	 * 就業時間の計算(控除時間差し引いた後)
	 * @return
	 */
	public int calcWorkTime(CalculationByActualTimeAtr  calcActualTime,DeductionTimeSheet dedTimeSheet) {
		
		HolidayAdditionAtr holidayAddition = HolidayAdditionAtr.HolidayAddition.convertFromCalcByActualTimeToHolidayAdditionAtr(calcActualTime);
		
		int workTime = calcWorkTimeBeforeDeductPremium(holidayAddition ,dedTimeSheet);
		if(holidayAddition.isHolidayAddition()) {
			/*休暇加算時間を計算*/
			/*休暇加算時間を加算*/
		}
		return workTime;
	}
	
	/**
	 * 就業時間内時間枠の全枠分の就業時間を算出する
	 * (所定内割増時間を差し引く前)
	 * @return 就業時間
	 */
	public int calcWorkTimeBeforeDeductPremium(HolidayAdditionAtr holidayAdditionAtr,DeductionTimeSheet dedTimeSheet) {
		int workTime = 0;
		for(WithinWorkTimeFrame copyItem: withinWorkTimeFrame) {
			workTime += copyItem.calcActualWorkTimeAndWorkTime(holidayAdditionAtr,dedTimeSheet);
		}
		return workTime;
	}
	
	/**
	 * 日別計算の遅刻早退時間の計算
	 * @return
	 */
	public int calcLateLeaveEarlyinWithinWorkTime() {
		
	}
}

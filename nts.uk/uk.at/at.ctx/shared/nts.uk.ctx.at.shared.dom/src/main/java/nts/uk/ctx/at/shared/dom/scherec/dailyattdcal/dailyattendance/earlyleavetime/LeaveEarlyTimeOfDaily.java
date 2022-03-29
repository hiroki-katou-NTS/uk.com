package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.earlyleavetime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.PremiumAtr;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.AddSettingOfWorkingTime;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.AdditionAtr;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayAddtionSet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.AttendanceItemDictionaryForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.IntervalExemptionTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManageReGetClass;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSpanForDailyCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours.LateLeaveEarlyTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours.LeaveEarlyTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours.WithinWorkTimeFrame;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.holidaypriorityorder.CompanyHolidayPriorityOrder;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneLateEarlySet;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkNo;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeForm;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 日別勤怠の早退時間
 * @author ken_takasu
 */
@Getter
public class LeaveEarlyTimeOfDaily {
	//早退時間
	private TimeWithCalculation leaveEarlyTime;
	//早退控除時間
	private TimeWithCalculation leaveEarlyDeductionTime;
	//勤務No
	private WorkNo workNo;
	//休暇使用時間
	private TimevacationUseTimeOfDaily timePaidUseTime;
	//インターバル時間
	private IntervalExemptionTime intervalTime;
	// 早退報告したのでアラームにしない
	@Setter
	private boolean doNotSetAlarm;
	
	/** 休暇相殺時間 */
	private TimevacationUseTimeOfDaily timeOffsetUseTime;
	/** 加算時間 */
	private AttendanceTime addTime;

	public LeaveEarlyTimeOfDaily(TimeWithCalculation leaveEarlyTime, TimeWithCalculation lateDeductionTime, WorkNo workNo,
			TimevacationUseTimeOfDaily timePaidUseTime, IntervalExemptionTime exemptionTime) {	
		this.leaveEarlyTime = leaveEarlyTime;
		this.leaveEarlyDeductionTime = lateDeductionTime;
		this.workNo = workNo;
		this.timePaidUseTime = timePaidUseTime;
		this.intervalTime = exemptionTime;
		this.doNotSetAlarm = false;
		this.timeOffsetUseTime = TimevacationUseTimeOfDaily.defaultValue();
		this.addTime = new AttendanceTime(0);
	}
	
	public LeaveEarlyTimeOfDaily(TimeWithCalculation leaveEarlyTime, TimeWithCalculation lateDeductionTime, WorkNo workNo,
			TimevacationUseTimeOfDaily timePaidUseTime, IntervalExemptionTime exemptionTime,
			TimevacationUseTimeOfDaily timeOffsetTime, AttendanceTime addTime) {	
		this.leaveEarlyTime = leaveEarlyTime;
		this.leaveEarlyDeductionTime = lateDeductionTime;
		this.workNo = workNo;
		this.timePaidUseTime = timePaidUseTime;
		this.intervalTime = exemptionTime;
		this.doNotSetAlarm = false;
		this.timeOffsetUseTime = timeOffsetTime;
		this.addTime = addTime;
	}
	
	/** 相殺代休時間を求める */
	public AttendanceTime getOffsetCompensatoryTime() {
		
		/** IF ＠早退控除時間。計算時間　＜　＠休暇使用時間。時間代休使用時間 */
		if (this.leaveEarlyDeductionTime.getCalcTime().lessThan(this.timePaidUseTime.getTimeCompensatoryLeaveUseTime())) {
			/** Return　＠早退控除時間。計算時間	*/
			return this.leaveEarlyDeductionTime.getCalcTime();
		}
		
		/** Return　＠休暇使用時間。時間代休使用時間 */
		return this.timePaidUseTime.getTimeCompensatoryLeaveUseTime();
	}
	
	/**
	 * 早退時間のみ更新
	 * @param leaveEarlyTime
	 */
	public void rePlaceLeaveEarlyTime(TimeWithCalculation leaveEarlyTime) {
		this.leaveEarlyTime = leaveEarlyTime;
	}
	
	public static LeaveEarlyTimeOfDaily noLeaveEarlyTimeOfDaily() {
		return new LeaveEarlyTimeOfDaily(TimeWithCalculation.sameTime(new AttendanceTime(0)),
										 TimeWithCalculation.sameTime(new AttendanceTime(0)),
										 new WorkNo(1),
										 TimevacationUseTimeOfDaily.defaultValue(),
										 IntervalExemptionTime.defaultValue());
	}
	
	/**
	 * 日別実績の早退時間
	 * @param recordClass 時間帯作成、時間計算で再取得が必要になっているクラスたちの管理クラス
	 * @return 日別実績の早退時間(List)
	 */
	public static List<LeaveEarlyTimeOfDaily> calcList(ManageReGetClass recordClass) {
		
		//日別実績の早退時間
		List<LeaveEarlyTimeOfDaily> leaveEarlyTime = new ArrayList<>();
		if(recordClass.getCoreTimeSetting().isPresent() && recordClass.getCoreTimeSetting().get().getTimesheet().isNOT_USE()) {
			//こちらのケースは早退は常に0：00
			leaveEarlyTime.add(LeaveEarlyTimeOfDaily.noLeaveEarlyTimeOfDaily());
		} else {
			//早退（時間帯から計算）
			for(TimeLeavingWork work : recordClass.getCalculationRangeOfOneDay().getAttendanceLeavingWork().getTimeLeavingWorks()) {
				leaveEarlyTime.add(LeaveEarlyTimeOfDaily.calcLeaveEarlyTime(recordClass, work.getWorkNo()));
			}
		}
		return leaveEarlyTime;
	}
	
	/**
	 * 早退時間の計算
	 * @param oneDay 1日の計算範囲
	 * @param workNo 勤務No
	 * @param leaveEarly 日別実績の計算区分.遅刻早退の自動計算設定.早退
	 * @param holidayCalcMethodSet 休暇の計算方法の設定
	 * @param commonSetting 就業時間帯の共通設定
	 * @return 日別実績の早退時間
	 */
	public static LeaveEarlyTimeOfDaily calcLeaveEarlyTime(
			ManageReGetClass recordClass, WorkNo workNo) {
		
		//勤務Noに一致する早退時間をListで取得する
		List<LeaveEarlyTimeSheet> leaveEarlyTimeSheetList = recordClass.getCalculationRangeOfOneDay().getWithinWorkingTimeSheet().isPresent()
				? recordClass.getCalculationRangeOfOneDay().getWithinWorkingTimeSheet().get().getWithinWorkTimeFrame().stream()
						.map(t -> t.getLeaveEarlyTimeSheet().orElse(null))
						.filter(t -> t != null && workNo.compareTo(t.getWorkNo()) == 0 && t.getForDeducationTimeSheet().isPresent())
						.sorted((leaveEarlyTimeSheet1,leaveEarlyTimeSheet2) -> leaveEarlyTimeSheet1.getForDeducationTimeSheet().get().getTimeSheet().getStart().compareTo(
								leaveEarlyTimeSheet2.getForDeducationTimeSheet().get().getTimeSheet().getStart()))
						.collect(Collectors.toList())
				: new ArrayList<>();
		
		// 早退時間帯を１つの時間帯にする
		LateLeaveEarlyTimeSheet forRecordTimeSheet = null;
		LateLeaveEarlyTimeSheet forDeductTimeSheet = null;
		for (LeaveEarlyTimeSheet srcSheet : leaveEarlyTimeSheetList){
			if (srcSheet.getForRecordTimeSheet().isPresent()){
				LateLeaveEarlyTimeSheet srcRecord = srcSheet.getForRecordTimeSheet().get();
				if (forRecordTimeSheet == null){
					forRecordTimeSheet = new LateLeaveEarlyTimeSheet(
							new TimeSpanForDailyCalc(
									srcRecord.getTimeSheet().getStart(),
									srcRecord.getTimeSheet().getEnd()),
							srcRecord.getRounding());
				}
				forRecordTimeSheet.addOtherSheet(srcRecord);
			}
			if (srcSheet.getForDeducationTimeSheet().isPresent()){
				LateLeaveEarlyTimeSheet srcDeduct = srcSheet.getForDeducationTimeSheet().get();
				if (forDeductTimeSheet == null){
					forDeductTimeSheet = new LateLeaveEarlyTimeSheet(
							new TimeSpanForDailyCalc(
									srcDeduct.getTimeSheet().getStart(),
									srcDeduct.getTimeSheet().getEnd()),
							srcDeduct.getRounding());
				}
				forDeductTimeSheet.addOtherSheet(srcDeduct);
			}
		}
		
		LeaveEarlyTimeSheet leaveEarlyTimeSheet = new LeaveEarlyTimeSheet(
				Optional.ofNullable(forRecordTimeSheet), Optional.ofNullable(forDeductTimeSheet), workNo.v());
		
		// 遅刻早退を控除するかどうか判断　→　控除区分
		NotUseAtr notDeductLateLeaveEarly = NotUseAtr.NOT_USE;
		if (WithinWorkTimeFrame.isDeductLateLeaveEarly(recordClass.getIntegrationOfWorkTime(), PremiumAtr.RegularWork,
				recordClass.getHolidayCalcMethodSet(), recordClass.getWorkTimezoneCommonSet(), NotUseAtr.NOT_USE)) {
			notDeductLateLeaveEarly = NotUseAtr.USE;
		}
		boolean leaveEarly = recordClass.getIntegrationOfDaily().getCalAttr().getLeaveEarlySetting().isLeaveEarly();
		//早退計上時間の計算
		TimeWithCalculation leaveEarlyTime = leaveEarlyTimeSheet.calcForRecordTime(leaveEarly, true, NotUseAtr.USE);
		//早退控除時間の計算
		TimeWithCalculation leaveEarlyDeductionTime = leaveEarlyTimeSheet.calcDedctionTime(leaveEarly,notDeductLateLeaveEarly);
		//休暇使用時間
		Optional<TimevacationUseTimeOfDaily> useTime = recordClass.getIntegrationOfDaily().getAttendanceTimeOfDailyPerformance()
				.map(c -> c.getLeaveEarlyTimeOfDaily()).orElseGet(() -> new ArrayList<>())
				.stream().filter(l -> l.getWorkNo().equals(workNo))
				.map(l -> l.getTimePaidUseTime()).findFirst();
		
		/** 〇早退時間を作る */
		LeaveEarlyTimeOfDaily leaveEarlyTimeOfDaily = new LeaveEarlyTimeOfDaily(
				leaveEarlyTime,
				leaveEarlyDeductionTime,
				workNo,
				useTime.orElse(TimevacationUseTimeOfDaily.defaultValue()),
				IntervalExemptionTime.defaultValue());
		
		/** 相殺休暇使用時間の計算 */
		leaveEarlyTimeOfDaily.calcOffsetVacationUseTimeCore(recordClass, leaveEarlyTimeSheet, forDeductTimeSheet);
		
		/** 〇早退時間を返す */
		return leaveEarlyTimeOfDaily;
	}
	
	/** 相殺休暇使用時間の計算 */
	private void calcOffsetVacationUseTimeCore(ManageReGetClass recordClass, 
			LeaveEarlyTimeSheet leaveEarlyTimeSheet,
			LateLeaveEarlyTimeSheet forDeductTimeSheet) {
		
		//遅刻控除時間の計算
		TimeWithCalculation lateDeductionTime = leaveEarlyTimeSheet.calcDedctionTime(
				recordClass.getIntegrationOfDaily().getCalAttr().getLeaveEarlySetting().isLate(), NotUseAtr.USE);

		/** 加算時間と相殺休暇使用時間を補正する */
		correctAddTimeAndOffsetVacationUseTime(recordClass.getHolidayCalcMethodSet(), 
				recordClass.getWorkTimezoneCommonSet(), 
				recordClass.getCompanyCommonSetting().getCompanyHolidayPriorityOrder(), 
				forDeductTimeSheet, recordClass.getHolidayAddtionSet(), lateDeductionTime.getCalcTime());
	}
	
	/** 加算時間と相殺休暇使用時間を補正する */
	private void correctAddTimeAndOffsetVacationUseTime(AddSettingOfWorkingTime addSet, 
			Optional<WorkTimezoneCommonSet> commonSetting, CompanyHolidayPriorityOrder compHolPrioOrder, 
			LateLeaveEarlyTimeSheet earlySheet, Optional<HolidayAddtionSet> holidayAddtionSet, AttendanceTime leaveEarly) {
		
		/** [1] 遅刻早退を就業時間に含めるか判断する */
		if (addSet.isIncludeLateEarlyInWorkTime(PremiumAtr.RegularWork, commonSetting.map(c -> c.getLateEarlySet()))
				&& holidayAddtionSet.isPresent()) {
			
			if (earlySheet != null && this.leaveEarlyDeductionTime.getCalcTime().greaterThan(0)) {
				
				/** 相殺時間休暇使用時間の算算 */
				val offset = earlySheet.calcTotalOffsetTimeVacationUseTime(compHolPrioOrder, this.timePaidUseTime);
				
				/** 就業時間に加算する値だけを取得する*/
				val addTime = offset.getValueForAddWorkTime(holidayAddtionSet.get());
				
				/** 相殺時間を補正する */
				addTime.setSpecialHolidayFrameNo(this.timePaidUseTime.getSpecialHolidayFrameNo());
				this.timeOffsetUseTime = addTime;
				
				/** 加算時間を補正する */
				this.addTime = new AttendanceTime(this.leaveEarlyDeductionTime.getCalcTime().v() - addTime.totalVacationAddTime());
				return;
			}else {
				
				/** 加算時間を補正する */
				this.addTime = new AttendanceTime(leaveEarly.v());
			}
		} 
			
	}
	
	/**
	 * 早退時間のエラーチェック 
	 * @return エラーである。
	 */
	public List<EmployeeDailyPerError>  checkError(String employeeId,
												  GeneralDate targetDate,
												  String searchWord,
												  AttendanceItemDictionaryForCalc attendanceItemDictionary,
												  ErrorAlarmWorkRecordCode errorCode) {
		List<EmployeeDailyPerError> returnErrorList = new ArrayList<>();
		if(this.getLeaveEarlyTime().getTime().greaterThan(0)) {
			val itemId = attendanceItemDictionary.findId(searchWord+this.workNo.v());
			if(itemId.isPresent())
				returnErrorList.add(new EmployeeDailyPerError(AppContexts.user().companyCode(), employeeId, targetDate, errorCode, itemId.get()));
		}
		return returnErrorList;
	}
	
	/**
	 * 休暇加算時間の計算
	 * @param calcMethodSet 休暇の計算方法の設定
	 * @param holidayAddtionSet 休暇加算時間設定
	 * @param workTimeForm 就業時間帯の勤務形態
	 * @return 時間休暇加算時間
	 */
	public AttendanceTime calcVacationAddTime(AddSettingOfWorkingTime calcMethodSet, Optional<HolidayAddtionSet> holidayAddtionSet, WorkTimeForm workTimeForm) {
		if(calcMethodSet.isAddVacation(PremiumAtr.RegularWork).isNotUse()) {
			return AttendanceTime.ZERO;
		}
		return holidayAddtionSet.get().getAddTime(this.timePaidUseTime, this.leaveEarlyTime.getCalcTime(), workTimeForm);
	}
	
	/**
	 * 時間休暇加算時間を取得する
	 * @param calcMethodSet 労働時間の加算設定
	 * @param holidayAddtionSet 休暇加算時間設定
	 * @param frames 就業時間内時間枠(List)
	 * @param workTimeForm 就業時間帯の勤務形態
	 * @return 時間休暇加算時間
	 */
	public AttendanceTime calcVacationAddTime(AddSettingOfWorkingTime calcMethodSet, HolidayAddtionSet holidayAddtionSet,
			List<WithinWorkTimeFrame> frames, WorkTimeForm workTimeForm) {
		if(calcMethodSet.isAddVacation(PremiumAtr.RegularWork).isNotUse()) {
			return AttendanceTime.ZERO;
		}
		Optional<LeaveEarlyTimeSheet> leaveEarly = frames.stream()
				.filter(f -> f.getWorkingHoursTimeNo().equals(new EmTimeFrameNo(this.workNo.v())))
				.map(f -> f.getLeaveEarlyTimeSheet())
				.findFirst().flatMap(l -> l);
		//計算早退計上時間（丸め前）
		AttendanceTime leaveEarlyCalcTime = leaveEarly.isPresent() ?
				leaveEarly.get().calcForRecordTime(true, false, NotUseAtr.NOT_USE).getCalcTime() : AttendanceTime.ZERO;
		//計算早退計上時間（丸め後）
		AttendanceTime roundAfter = leaveEarly.isPresent() ?
				leaveEarly.get().calcForRecordTime(true, false, NotUseAtr.USE).getCalcTime() : AttendanceTime.ZERO;
		//休暇使用時間
		AttendanceTime useTime = new AttendanceTime(this.timePaidUseTime.calcTotalVacationAddTime(Optional.of(holidayAddtionSet), AdditionAtr.WorkingHoursOnly));
		if(leaveEarlyCalcTime.lessThanOrEqualTo(useTime) && roundAfter.greaterThan(useTime)) {
			//丸め前だったら相殺しきれるが、丸め後だと相殺しきれない場合、休暇加算時間は丸め後の時間帯から計算した値を使いたい
			return roundAfter;
		}
		return holidayAddtionSet.getAddTime(this.timePaidUseTime, roundAfter, workTimeForm);
	}
	
	//クリア 早退時間の時間
	public void  resetData() {
		this.leaveEarlyTime.setTime(new AttendanceTime(0));
	}
	
	public static LeaveEarlyTimeOfDaily createDefaultWithNo(int no) {
		return new LeaveEarlyTimeOfDaily(TimeWithCalculation.sameTime(new AttendanceTime(0)),
				TimeWithCalculation.sameTime(new AttendanceTime(0)), new WorkNo(no),
				TimevacationUseTimeOfDaily.defaultValue(), IntervalExemptionTime.defaultValue());
	}
}

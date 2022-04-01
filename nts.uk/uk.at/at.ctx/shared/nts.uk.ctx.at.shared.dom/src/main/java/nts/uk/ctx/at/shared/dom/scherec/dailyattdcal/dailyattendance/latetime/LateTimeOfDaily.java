package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.latetime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.PremiumAtr;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.AdditionAtr;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayAddtionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.AddSettingOfWorkingTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workingstyle.flex.SettingOfFlexWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.AttendanceItemDictionaryForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.FlexWithinWorkTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.IntervalExemptionTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManageReGetClass;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSpanForDailyCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.CalculationRangeOfOneDay;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.DeductionAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours.LateLeaveEarlyTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours.LateTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours.WithinWorkTimeFrame;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.holidaypriorityorder.CompanyHolidayPriorityOrder;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneLateEarlySet;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkNo;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeForm;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 日別勤怠の遅刻時間
 * @author ken_takasu
 */
@Getter
public class LateTimeOfDaily {
	//遅刻時間
	private TimeWithCalculation lateTime;
	//遅刻控除時間
	private TimeWithCalculation lateDeductionTime;
	//勤務No
	private WorkNo workNo;
	//休暇使用時間
	private TimevacationUseTimeOfDaily timePaidUseTime;
	//インターバル時間
	private IntervalExemptionTime exemptionTime;
	//遅刻報告したのでアラームにしない
	@Setter
	private boolean doNotSetAlarm;
	/** 休暇相殺時間 */
	private TimevacationUseTimeOfDaily timeOffsetUseTime;
	/** 加算時間 */
	private AttendanceTime addTime;
	
	public LateTimeOfDaily(TimeWithCalculation lateTime, TimeWithCalculation lateDeductionTime, WorkNo workNo,
			TimevacationUseTimeOfDaily timePaidUseTime, IntervalExemptionTime exemptionTime) {		
		this.lateTime = lateTime;
		this.lateDeductionTime = lateDeductionTime;
		this.workNo = workNo;
		this.timePaidUseTime = timePaidUseTime;
		this.exemptionTime = exemptionTime;
		this.doNotSetAlarm = false;
		this.timeOffsetUseTime = TimevacationUseTimeOfDaily.defaultValue();
		this.addTime = new AttendanceTime(0);
	}
	
	public LateTimeOfDaily(TimeWithCalculation lateTime, TimeWithCalculation lateDeductionTime, WorkNo workNo,
			TimevacationUseTimeOfDaily timePaidUseTime, IntervalExemptionTime exemptionTime,
			TimevacationUseTimeOfDaily timeOffsetTime, AttendanceTime addTime) {		
		this.lateTime = lateTime;
		this.lateDeductionTime = lateDeductionTime;
		this.workNo = workNo;
		this.timePaidUseTime = timePaidUseTime;
		this.exemptionTime = exemptionTime;
		this.doNotSetAlarm = false;
		this.timeOffsetUseTime = timeOffsetTime;
		this.addTime = addTime;
	}
	
	/** 相殺代休時間を求める */
	public AttendanceTime getOffsetCompensatoryTime() {
		
		/** IF ＠遅刻控除時間。計算時間　＜　＠休暇使用時間。時間代休使用時間 */
		if (this.lateDeductionTime.getCalcTime().lessThan(this.timePaidUseTime.getTimeCompensatoryLeaveUseTime())) {
			/** Return　＠遅刻控除時間。計算時間	*/
			return this.lateDeductionTime.getCalcTime();
		}
		
		/** Return　＠休暇使用時間。時間代休使用時間 */
		return this.timePaidUseTime.getTimeCompensatoryLeaveUseTime();
	}
	

	/**
	 * 遅刻時間のみ更新
	 * @param lateTime
	 */
	public void rePlaceLateTime(TimeWithCalculation lateTime) {
		this.lateTime = lateTime;
	}

	/**
	 * 日別実績の遅刻時間
	 * @param recordClass 再取得クラス
	 * @param settingOfFlex フレックス勤務の設定
	 * @return 日別実績の遅刻時間(List)
	 */
	public static List<LateTimeOfDaily> calcList(
			ManageReGetClass recordClass,
			Optional<SettingOfFlexWork> settingOfFlex) {
		
		//日別実績の遅刻時間
		List<LateTimeOfDaily> lateTime = new ArrayList<>();
		if(recordClass.getCoreTimeSetting().isPresent() && recordClass.getCoreTimeSetting().get().getTimesheet().isNOT_USE()) {
			//コア無しフレックス遅刻時間の計算
			lateTime.add(LateTimeOfDaily.calcNoCoreLateTime(recordClass, settingOfFlex));
		}else {
			//遅刻時間の計算（時間帯から計算）
			for(TimeLeavingWork work : recordClass.getCalculationRangeOfOneDay().getAttendanceLeavingWork().getTimeLeavingWorks()) {
				lateTime.add(LateTimeOfDaily.calcLateTime(recordClass, work.getWorkNo()));
			}
		}
		return lateTime;
	}
	
	/**
	 * 遅刻時間の計算
	 * @param oneDay 1日の計算範囲
	 * @param workNo 勤務No
	 * @param late 日別実績の計算区分.遅刻早退の自動計算設定.遅刻
	 * @param holidayCalcMethodSet 休暇の計算方法の設定
	 * @param commonSetting 就業時間帯の共通設定
	 * @return 日別実績の遅刻時間
	 */
	private static LateTimeOfDaily calcLateTime(
			ManageReGetClass recordClass, WorkNo workNo) {
		
		//勤務Noに一致する遅刻時間をListで取得する
		List<LateTimeSheet> lateTimeSheetList = getLateTimeSheet(recordClass.getCalculationRangeOfOneDay(), workNo);
		
		// 遅刻時間帯を１つの時間帯にする
		LateLeaveEarlyTimeSheet forRecordTimeSheet = null;
		LateLeaveEarlyTimeSheet forDeductTimeSheet = null;
		for (LateTimeSheet srcSheet : lateTimeSheetList){
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
		
		LateTimeSheet lateTimeSheet = new LateTimeSheet(
				Optional.ofNullable(forRecordTimeSheet), Optional.ofNullable(forDeductTimeSheet), workNo.v());
		
		// 遅刻早退を控除するかどうか判断　→　控除区分
		NotUseAtr notDeductLateLeaveEarly = NotUseAtr.NOT_USE;
		if (WithinWorkTimeFrame.isDeductLateLeaveEarly(recordClass.getIntegrationOfWorkTime(), PremiumAtr.RegularWork,
				recordClass.getHolidayCalcMethodSet(), recordClass.getWorkTimezoneCommonSet(), NotUseAtr.NOT_USE)) {
			notDeductLateLeaveEarly = NotUseAtr.USE;
		}
		boolean late = recordClass.getIntegrationOfDaily().getCalAttr().getLeaveEarlySetting().isLate();
		//遅刻計上時間の計算
		TimeWithCalculation lateTime = lateTimeSheet.calcForRecordTime(late, true, NotUseAtr.USE);
		//遅刻控除時間の計算
		TimeWithCalculation lateDeductionTime = lateTimeSheet.calcDedctionTime(late,notDeductLateLeaveEarly);
		//休暇使用時間
		Optional<TimevacationUseTimeOfDaily> useTime = recordClass.getIntegrationOfDaily().getAttendanceTimeOfDailyPerformance()
				.map(c -> c.getLateTimeOfDaily()).orElseGet(() -> new ArrayList<>()).stream()
				.filter(l -> l.getWorkNo().equals(workNo))
				.map(l -> l.getTimePaidUseTime()).findFirst();
		
		/** 〇遅刻時間を作る */
		LateTimeOfDaily lateTimeOfDaily = new LateTimeOfDaily(
				lateTime,
				lateDeductionTime,
				workNo,
				useTime.orElse(TimevacationUseTimeOfDaily.defaultValue()),
				IntervalExemptionTime.defaultValue());

		/** 相殺休暇使用時間の計算 */
		lateTimeOfDaily.calcOffsetVacationUseTimeCore(recordClass, lateTimeSheet, forDeductTimeSheet);
		
		/** 〇遅刻時間を返す */
		return lateTimeOfDaily;
	}
	
	/** 加算時間と相殺休暇使用時間を補正する */
	private void correctAddTimeAndOffsetVacationUseTime(AddSettingOfWorkingTime addSet, 
			Optional<WorkTimezoneCommonSet> commonSetting, CompanyHolidayPriorityOrder compHolPrioOrder, 
			LateLeaveEarlyTimeSheet lateSheet, Optional<HolidayAddtionSet> holidayAddtionSet, AttendanceTime lateTime) {
		
		/** [1] 遅刻早退を就業時間に含めるか判断する */
		if (addSet.isIncludeLateEarlyInWorkTime(PremiumAtr.RegularWork, commonSetting.map(c -> c.getLateEarlySet()))
				&& holidayAddtionSet.isPresent()) {
			
			if (lateSheet != null && this.lateDeductionTime.getCalcTime().greaterThan(0)) {
				
				/** 相殺時間休暇使用時間の算算 */
				val offset = lateSheet.calcTotalOffsetTimeVacationUseTime(compHolPrioOrder, this.timePaidUseTime);
				
				/** 就業時間に加算する値だけを取得する*/
				val addTime = offset.getValueForAddWorkTime(holidayAddtionSet.get());
				
				/** 相殺時間を補正する */
				addTime.setSpecialHolidayFrameNo(this.timePaidUseTime.getSpecialHolidayFrameNo());
				this.timeOffsetUseTime = addTime;
				
				/** 加算時間を補正する */
				this.addTime = new AttendanceTime(this.lateDeductionTime.getCalcTime().v() - addTime.totalVacationAddTime());
				return;
			} else {
				
				/** 加算時間を補正する */
				this.addTime = new AttendanceTime(lateTime.v());
			}
		}
			
		/** 加算時間を加算する */
//		this.addTime = this.lateDeductionTime.getCalcTime();
	}
	
	/**
	 * コア無しフレックス遅刻時間の計算
	 * @param recordClass 再取得クラス
	 * @param settingOfFlex フレックス勤務の設定
	 * @return 日別実績の遅刻時間
	 */
	private static LateTimeOfDaily calcNoCoreLateTime(
			ManageReGetClass recordClass,
			Optional<SettingOfFlexWork> settingOfFlex) {

		// 勤務種類
		if (!recordClass.getWorkType().isPresent()) return LateTimeOfDaily.createDefaultWithNo(1);
		WorkType workType = recordClass.getWorkType().get();
		// フレックス就業時間内時間帯
		FlexWithinWorkTimeSheet flexTimeSheet = (FlexWithinWorkTimeSheet)recordClass.getCalculationRangeOfOneDay().getWithinWorkingTimeSheet().get();
		// 計上用のコアタイム無しの遅刻時間計算
		TimeWithCalculation actualLateTime = flexTimeSheet.calcLateTimeOfNoCoreTime(
				recordClass.getPersonDailySetting(),
				recordClass.getIntegrationOfDaily(),
				recordClass.getIntegrationOfWorkTime(),
				DeductionAtr.Appropriate,
				PremiumAtr.RegularWork,
				workType,
				recordClass.getCalculationRangeOfOneDay().getPredetermineTimeSetForCalc(),
				recordClass.getIntegrationOfDaily().getCalAttr().getLeaveEarlySetting(),
				recordClass.getAddSetting(),
				recordClass.getHolidayAddtionSet().get(),
				recordClass.getDailyUnit(),
				NotUseAtr.NOT_USE,
				settingOfFlex);
		// 控除用コアタイム無しの遅刻時間計算
		TimeWithCalculation deductLateTime = flexTimeSheet.calcLateTimeOfNoCoreTime(
				recordClass.getPersonDailySetting(),
				recordClass.getIntegrationOfDaily(),
				recordClass.getIntegrationOfWorkTime(),
				DeductionAtr.Deduction,
				PremiumAtr.RegularWork,
				workType,
				recordClass.getCalculationRangeOfOneDay().getPredetermineTimeSetForCalc(),
				recordClass.getIntegrationOfDaily().getCalAttr().getLeaveEarlySetting(),
				recordClass.getAddSetting(),
				recordClass.getHolidayAddtionSet().get(),
				recordClass.getDailyUnit(),
				NotUseAtr.NOT_USE,
				settingOfFlex);
		// 日別実績の遅刻時間の確認　（休暇使用時間の計算のため）
		List<LateTimeOfDaily> lateDailies = recordClass.getIntegrationOfDaily().getAttendanceTimeOfDailyPerformance().isPresent()
				? recordClass.getIntegrationOfDaily().getAttendanceTimeOfDailyPerformance().get().getLateTimeOfDaily()
				: Collections.emptyList();
		// 休暇使用時間の計算
		Optional<TimevacationUseTimeOfDaily> useTime = lateDailies.stream()
				.filter(l -> l.getWorkNo().equals(new WorkNo(1)))
				.map(l -> l.getTimePaidUseTime()).findFirst();
		
		/** 遅刻時間を作る */
		val lateTime = new LateTimeOfDaily(
				actualLateTime,
				deductLateTime,
				new WorkNo(1),
				useTime.orElse(TimevacationUseTimeOfDaily.defaultValue()),
				IntervalExemptionTime.defaultValue());
		
		/** 相殺休暇使用時間の計算 */
		lateTime.calcOffsetVacationUseTimeNoCore(recordClass, settingOfFlex, flexTimeSheet);
		
		/** 遅刻時間を返す */
		return lateTime;
	}
	
	/** 相殺休暇使用時間の計算 （コアタイムなし）*/
	private void calcOffsetVacationUseTimeNoCore(ManageReGetClass recordClass, 
			Optional<SettingOfFlexWork> settingOfFlex,
			FlexWithinWorkTimeSheet flexTimeSheet) {
		
		/** 遅刻控除時間の計算 */
		val lateTime = flexTimeSheet.calcLateTimeForOffset(
				recordClass.getPersonDailySetting(),
				recordClass.getIntegrationOfDaily(),
				recordClass.getIntegrationOfWorkTime(),
				PremiumAtr.RegularWork,
				recordClass.getWorkType().get(),
				recordClass.getCalculationRangeOfOneDay().getPredetermineTimeSetForCalc(),
				recordClass.getIntegrationOfDaily().getCalAttr().getLeaveEarlySetting(),
				recordClass.getAddSetting(),
				recordClass.getHolidayAddtionSet().get(),
				recordClass.getDailyUnit(),
				NotUseAtr.NOT_USE,
				settingOfFlex);
		
		// 遅刻早退時間帯を作る
		LateLeaveEarlyTimeSheet forDeductTimeSheet = new LateLeaveEarlyTimeSheet(
				new TimeSpanForDailyCalc(
						new TimeWithDayAttr(0),
						new TimeWithDayAttr(lateTime.valueAsMinutes())),
				TimeRoundingSetting.oneMinDown());
		forDeductTimeSheet.setDeductionOffSetTime(Optional.of(this.timePaidUseTime));

		/** 加算時間と相殺休暇使用時間を補正する */
		correctAddTimeAndOffsetVacationUseTime(recordClass.getHolidayCalcMethodSet(), 
				recordClass.getWorkTimezoneCommonSet(), 
				recordClass.getCompanyCommonSetting().getCompanyHolidayPriorityOrder(), 
				forDeductTimeSheet, recordClass.getHolidayAddtionSet(), lateTime);
	}
	
	/** 相殺休暇使用時間の計算 （コアタイムあり）*/
	private void calcOffsetVacationUseTimeCore(ManageReGetClass recordClass, 
			LateTimeSheet lateTimeSheet, LateLeaveEarlyTimeSheet forDeductTimeSheet) {
		
		//遅刻控除時間の計算
		TimeWithCalculation lateDeductionTime = lateTimeSheet.calcDedctionTime(
				recordClass.getIntegrationOfDaily().getCalAttr().getLeaveEarlySetting().isLate(), NotUseAtr.USE);

		/** 加算時間と相殺休暇使用時間を補正する */
		correctAddTimeAndOffsetVacationUseTime(recordClass.getHolidayCalcMethodSet(), 
				recordClass.getWorkTimezoneCommonSet(), 
				recordClass.getCompanyCommonSetting().getCompanyHolidayPriorityOrder(), 
				forDeductTimeSheet, recordClass.getHolidayAddtionSet(), lateDeductionTime.getCalcTime());
	}

	/** 勤務Noに一致する遅刻時間をListで取得する */
	private static List<LateTimeSheet> getLateTimeSheet(CalculationRangeOfOneDay oneDay, WorkNo workNo) {
		//勤務Noに一致する遅刻時間をListで取得する
		List<LateTimeSheet> lateTimeSheetList = oneDay.getWithinWorkingTimeSheet().isPresent()
				? oneDay.getWithinWorkingTimeSheet().get().getWithinWorkTimeFrame().stream()
						.map(t -> t.getLateTimeSheet().orElse(null))
						.filter(t -> t != null && workNo.compareTo(t.getWorkNo()) == 0 && t.getForDeducationTimeSheet().isPresent())
						.sorted((lateTimeSheet1,lateTimeSheet2) -> lateTimeSheet1.getForDeducationTimeSheet().get().getTimeSheet().getStart().compareTo(
								lateTimeSheet2.getForDeducationTimeSheet().get().getTimeSheet().getStart()))
						.collect(Collectors.toList())
				: new ArrayList<>();
		return lateTimeSheetList;
	}
	
	/**
	 * 遅刻時間のエラーチェック 
	 * @return エラーである。
	 */
	public List<EmployeeDailyPerError>  checkError(String employeeId,
												  GeneralDate targetDate,
												  String searchWord,
												  AttendanceItemDictionaryForCalc attendanceItemDictionary,
												  ErrorAlarmWorkRecordCode errorCode) {
		List<EmployeeDailyPerError> returnErrorList = new ArrayList<>();
		if(this.getLateTime().getTime().greaterThan(0)) {
			val itemId = attendanceItemDictionary.findId(searchWord+this.workNo.v());
			if(itemId.isPresent())
				returnErrorList.add(new EmployeeDailyPerError(AppContexts.user().companyCode(), employeeId, targetDate, errorCode, itemId.get()));
		}
		return returnErrorList;
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
		Optional<LateTimeSheet> late = frames.stream()
				.filter(f -> f.getWorkingHoursTimeNo().equals(new EmTimeFrameNo(this.workNo.v())))
				.map(f -> f.getLateTimeSheet())
				.findFirst().flatMap(l -> l);
		//計算遅刻計上時間（丸め前）
		AttendanceTime lateCalcTime = late.isPresent() ?
				late.get().calcForRecordTime(true, false, NotUseAtr.NOT_USE).getCalcTime() : AttendanceTime.ZERO;
		//計算遅刻計上時間（丸め後）
		AttendanceTime roundAfter = late.isPresent() ?
				late.get().calcForRecordTime(true, false, NotUseAtr.USE).getCalcTime() : AttendanceTime.ZERO;
		//休暇使用時間
		AttendanceTime useTime = new AttendanceTime(this.timePaidUseTime.calcTotalVacationAddTime(Optional.of(holidayAddtionSet), AdditionAtr.WorkingHoursOnly));
		if(lateCalcTime.lessThanOrEqualTo(useTime) && roundAfter.greaterThan(useTime)) {
			//丸め前だったら相殺しきれるが、丸め後だと相殺しきれない場合、休暇加算時間は丸め後の時間帯から計算した値を使いたい
			return roundAfter;
		}
		return holidayAddtionSet.getAddTime(this.timePaidUseTime, roundAfter, workTimeForm);
	}
	
	public void resetData() {
		this.lateTime.setTime(new AttendanceTime(0));
	}
	
	public static LateTimeOfDaily createDefaultWithNo(int no) {
		return new LateTimeOfDaily(TimeWithCalculation.sameTime(new AttendanceTime(0)),
				TimeWithCalculation.sameTime(new AttendanceTime(0)), new WorkNo(no),
				TimevacationUseTimeOfDaily.defaultValue(), IntervalExemptionTime.defaultValue());
	}
}
package nts.uk.ctx.at.record.dom.actualworkinghours;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.actualworkinghours.daily.temporarytime.TemporaryTimeOfDaily;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeOfDaily;
import nts.uk.ctx.at.record.dom.calculationattribute.BonusPayAutoCalcSet;
import nts.uk.ctx.at.record.dom.calculationattribute.CalAttrOfDailyPerformance;
import nts.uk.ctx.at.record.dom.calculationattribute.enums.AutoCalOverTimeAttr;
import nts.uk.ctx.at.record.dom.daily.DeductionTotalTime;
import nts.uk.ctx.at.record.dom.daily.ExcessOfStatutoryTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.LateTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.LeaveEarlyTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.record.dom.daily.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.breaktimegoout.BreakTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.calcset.CalcMethodOfNoWorkingDay;
import nts.uk.ctx.at.record.dom.daily.latetime.IntervalExemptionTime;
import nts.uk.ctx.at.record.dom.daily.vacationusetime.HolidayOfDaily;
import nts.uk.ctx.at.record.dom.daily.withinworktime.WithinStatutoryTimeOfDaily;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.AttendanceItemDictionaryForCalc;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculationRangeOfOneDay;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CheckExcessAtr;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.DeductionAtr;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.IntegrationOfDaily;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.VacationClass;
import nts.uk.ctx.at.record.dom.raborstandardact.flex.SettingOfFlexWork;
import nts.uk.ctx.at.record.dom.raisesalarytime.RaiseSalaryTimeOfDailyPerfor;
import nts.uk.ctx.at.record.dom.shorttimework.ShortWorkTimeOfDaily;
import nts.uk.ctx.at.record.dom.shorttimework.enums.ChildCareAttribute;
import nts.uk.ctx.at.record.dom.stamp.GoOutReason;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.record.dom.workrecord.errorsetting.SystemFixedErrorAlarm;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;
import nts.uk.ctx.at.record.dom.worktime.primitivevalue.WorkTimes;
import nts.uk.ctx.at.shared.dom.calculation.holiday.HolidayAddtionSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.WorkDeformedLaborAdditionSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.WorkFlexAdditionSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.WorkRegularAdditionSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.kmk013_splitdomain.HolidayCalcMethodSet;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalFlexOvertimeSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalOvertimeSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.DailyUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.StatutoryDivision;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryOccurrenceSetting;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalRaisingSalarySetting;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;
import nts.uk.ctx.at.shared.dom.workrule.waytowork.PersonalLaborCondition;
import nts.uk.ctx.at.shared.dom.worktime.common.HolidayCalculation;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkNo;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneOtherSubHolTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.flexset.CoreTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDailyAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * 
 * @author nampt
 * 総労働時間
 *
 */
@Getter
public class TotalWorkingTime {
	
	//総労働時間
	private AttendanceTime totalTime;
	
	//総計算時間
	private AttendanceTime totalCalcTime;
	
	//実働時間
	private AttendanceTime actualTime;
	
	//日別実績の所定内時間
	private WithinStatutoryTimeOfDaily withinStatutoryTimeOfDaily;
	
	//日別実績の所定外時間
	private ExcessOfStatutoryTimeOfDaily excessOfStatutoryTimeOfDaily;
	
	//日別実績の遅刻時間
	private List<LateTimeOfDaily> lateTimeOfDaily = Collections.emptyList();
	
	//日別実績の早退時間
	private List<LeaveEarlyTimeOfDaily> leaveEarlyTimeOfDaily = Collections.emptyList(); 
	
	//日別実績の休憩時間
	private BreakTimeOfDaily breakTimeOfDaily;
	
	//日別実績の外出時間	
	private List<OutingTimeOfDaily> outingTimeOfDailyPerformance;
		
	//加給時間
	private RaiseSalaryTimeOfDailyPerfor raiseSalaryTimeOfDailyPerfor;
	
	//勤務回数
	private WorkTimes workTimes;
	
	/*日別実績の臨時時間*/
	private TemporaryTimeOfDaily temporaryTime;
	
	/*短時間勤務時間*/
	private ShortWorkTimeOfDaily shotrTimeOfDaily;
	
	/*日別実績の休暇時間*/
	private HolidayOfDaily holidayOfDaily;
	
	/*休暇加算時間*/
	@Setter
	private AttendanceTime vacationAddTime = new AttendanceTime(0);
	
	/**
	 * Construtor
	 * @param totalTime
	 * @param totalCalcTime
	 * @param actualTime
	 * @param withinStatutoryTimeOfDaily
	 * @param excessOfStatutoryTimeOfDaily
	 * @param lateTimeOfDaily
	 * @param leaveEarlyTimeOfDaily
	 * @param breakTimeOfDaily
	 * @param outingTimeOfDailyPerformance
	 * @param raiseSalaryTimeOfDailyPerfor
	 * @param workTimes
	 * @param temporaryTime
	 */
	public TotalWorkingTime(AttendanceTime totalTime, AttendanceTime totalCalcTime, AttendanceTime actualTime,
			WithinStatutoryTimeOfDaily withinStatutoryTimeOfDaily,
			ExcessOfStatutoryTimeOfDaily excessOfStatutoryTimeOfDaily, List<LateTimeOfDaily> lateTimeOfDaily,
			List<LeaveEarlyTimeOfDaily> leaveEarlyTimeOfDaily, BreakTimeOfDaily breakTimeOfDaily,
			List<OutingTimeOfDaily> outingTimeOfDailyPerformance,
			RaiseSalaryTimeOfDailyPerfor raiseSalaryTimeOfDailyPerfor, WorkTimes workTimes,
			TemporaryTimeOfDaily temporaryTime, ShortWorkTimeOfDaily shotrTime,HolidayOfDaily holidayOfDaily) {
		super();
		this.totalTime = totalTime;
		this.totalCalcTime = totalCalcTime;
		this.actualTime = actualTime;
		this.withinStatutoryTimeOfDaily = withinStatutoryTimeOfDaily;
		this.excessOfStatutoryTimeOfDaily = excessOfStatutoryTimeOfDaily;
		this.lateTimeOfDaily = lateTimeOfDaily;
		this.leaveEarlyTimeOfDaily = leaveEarlyTimeOfDaily;
		this.breakTimeOfDaily = breakTimeOfDaily;
		this.outingTimeOfDailyPerformance = outingTimeOfDailyPerformance;
		this.raiseSalaryTimeOfDailyPerfor = raiseSalaryTimeOfDailyPerfor;
		this.workTimes = workTimes;
		this.temporaryTime = temporaryTime;
		this.shotrTimeOfDaily = shotrTime;
		this.holidayOfDaily = holidayOfDaily;
	}
	
	/**
	 * 日別実績の総労働時間の計算
	 * @param breakTimeCount 
	 * @param integrationOfDaily 
	 * @param flexSetting 
	 * @return 
	 */
	public static TotalWorkingTime calcAllDailyRecord(CalculationRangeOfOneDay oneDay,
			   Optional<PersonalLaborCondition> personalCondition,
			   VacationClass vacationClass,
			   WorkType workType,
			   WorkingSystem workingSystem,
			   WorkDeformedLaborAdditionSet illegularAddSetting,
			   WorkFlexAdditionSet flexAddSetting,
			   WorkRegularAdditionSet regularAddSetting,
			   HolidayAddtionSet holidayAddtionSet,
			   AutoCalOverTimeAttr overTimeAutoCalcAtr,
			   Optional<WorkTimeDailyAtr> workTimeDailyAtr,
			   Optional<SettingOfFlexWork> flexCalcMethod,
			   HolidayCalcMethodSet holidayCalcMethodSet,
			   BonusPayAutoCalcSet bonusPayAutoCalcSet,
			   CalAttrOfDailyPerformance calcAtrOfDaily,
			   List<WorkTimezoneOtherSubHolTimeSet> eachWorkTimeSet,
			   List<CompensatoryOccurrenceSetting> eachCompanyTimeSet, 
			   int breakTimeCount, IntegrationOfDaily integrationOfDaily,
			   AutoCalFlexOvertimeSetting flexAutoCalSet,
			   Optional<CoreTimeSetting> coreTimeSetting,
			   DailyUnit dailyUnit,
			   List<OverTimeFrameNo> statutoryFrameNoList
			   ) {
		

		Optional<WorkTimeCode> workTimeCode = Optional.empty();
		//日別実績の所定外時間
		if(oneDay.getWorkInformationOfDaily().getRecordInfo().getWorkTimeCode() != null) {
			workTimeCode = oneDay.getWorkInformationOfDaily().getRecordInfo().getWorkTimeCode().v() == null
																		?Optional.empty()
																		:Optional.of(new WorkTimeCode(oneDay.getWorkInformationOfDaily().getRecordInfo().getWorkTimeCode().v().toString()));
		}
		
		//staticを外すまでのフレ事前申請取り出し処理
		AttendanceTime flexPreAppTime = new AttendanceTime(0);
		if(integrationOfDaily.getAttendanceTimeOfDailyPerformance().isPresent()
				&& integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily() != null
				&& integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime() != null
				&& integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getActualTime() != null
				&& integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily() != null
				&& integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getOverTimeWork().isPresent()
				&& integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().getFlexTime() != null
				) {
			flexPreAppTime = integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().getFlexTime().getBeforeApplicationTime();
		}
		
		/*日別実績の所定内時間(就業時間)*/
		val withinStatutoryTimeOfDaily = WithinStatutoryTimeOfDaily.calcStatutoryTime(oneDay,
				   																      personalCondition,
				   																      vacationClass,
				   																      workType,
				   																      calcAtrOfDaily.getLeaveEarlySetting().getLeaveLate().isUse(),  //日別実績の計算区分.遅刻早退の自動計算設定.遅刻
				   																      calcAtrOfDaily.getLeaveEarlySetting().getLeaveEarly().isUse(),  //日別実績の計算区分.遅刻早退の自動計算設定.早退
				   																      workingSystem,
				   																      illegularAddSetting,
				   																      flexAddSetting,
				   																      regularAddSetting,
				   																      holidayAddtionSet,
				   																      flexAutoCalSet.getFlexOtTime().getCalAtr(),
				   																      holidayCalcMethodSet, 
				   																      CalcMethodOfNoWorkingDay.isCalculateFlexTime, 
				   																      overTimeAutoCalcAtr, 
				   																      flexCalcMethod, 
				   																      workTimeDailyAtr, 
				   																      workTimeCode,
				   																      flexPreAppTime, 
				   																      coreTimeSetting,
				   																      dailyUnit);

		
		ExcessOfStatutoryTimeOfDaily excesstime =ExcessOfStatutoryTimeOfDaily.calculationExcessTime(oneDay, 
																									calcAtrOfDaily.getOvertimeSetting(), 
																									calcAtrOfDaily.getHolidayTimeSetting().getRestTime(), 
																									CalcMethodOfNoWorkingDay.isCalculateFlexTime,
																									holidayCalcMethodSet,
																									overTimeAutoCalcAtr,
																									workType,flexCalcMethod,oneDay.getPredetermineTimeSetForCalc()
																									,vacationClass,oneDay.getTimeVacationAdditionRemainingTime().get(),
																									StatutoryDivision.Nomal,
																									workTimeCode,
																									personalCondition,
																									calcAtrOfDaily.getLeaveEarlySetting().getLeaveLate().isUse(),  //日別実績の計算区分.遅刻早退の自動計算設定.遅刻
																									calcAtrOfDaily.getLeaveEarlySetting().getLeaveEarly().isUse(),  //日別実績の計算区分.遅刻早退の自動計算設定.早退
																									workingSystem,illegularAddSetting,flexAddSetting,regularAddSetting,
																									holidayAddtionSet,
																									workTimeDailyAtr,
																									eachWorkTimeSet,
																									eachCompanyTimeSet,
																									integrationOfDaily,
																									flexPreAppTime,
																									flexAutoCalSet,
																									dailyUnit, statutoryFrameNoList);
		int overWorkTime = excesstime.getOverTimeWork().isPresent()?excesstime.getOverTimeWork().get().calcTotalFrameTime():0;
		overWorkTime += excesstime.getOverTimeWork().isPresent()?excesstime.getOverTimeWork().get().calcTransTotalFrameTime():0;
		int holidayWorkTime = excesstime.getWorkHolidayTime().isPresent()?excesstime.getWorkHolidayTime().get().calcTotalFrameTime():0;
		holidayWorkTime += excesstime.getWorkHolidayTime().isPresent()?excesstime.getWorkHolidayTime().get().calcTransTotalFrameTime():0;
		
		//日別実績の遅刻時間
		List<LateTimeOfDaily> lateTime = new ArrayList<>();
		//日別実績の早退時間
		List<LeaveEarlyTimeOfDaily> leaveEarlyTime = new ArrayList<>();
		if(coreTimeSetting.isPresent() && coreTimeSetting.get().getTimesheet().isNOT_USE()) {
			//コアタイム無し（時間帯を使わずに計算）
			AttendanceTime time =coreTimeSetting.get().getMinWorkTime().minusMinutes(withinStatutoryTimeOfDaily.getWorkTime().valueAsMinutes());
			if(time.lessThanOrEqualTo(0))time = new AttendanceTime(0);
			TimeWithCalculation latetime = TimeWithCalculation.sameTime(time);
			lateTime.add(new LateTimeOfDaily(calcAtrOfDaily.getLeaveEarlySetting().getLeaveLate().isUse() ? latetime : TimeWithCalculation.sameTime(new AttendanceTime(0)),
					latetime,
				  new WorkNo(1),
				  new TimevacationUseTimeOfDaily(new AttendanceTime(0),new AttendanceTime(0),new AttendanceTime(0),new AttendanceTime(0)),
				  new IntervalExemptionTime(new AttendanceTime(0),new AttendanceTime(0),new AttendanceTime(0))));
			
			//こちらのケースは早退は常に0：00
			leaveEarlyTime.add(LeaveEarlyTimeOfDaily.noLeaveEarlyTimeOfDaily());
		}else {
			//遅刻（時間帯から計算）
			for(TimeLeavingWork work : oneDay.getAttendanceLeavingWork().getTimeLeavingWorks())
				lateTime.add(LateTimeOfDaily.calcLateTime(oneDay, work.getWorkNo(),calcAtrOfDaily.getLeaveEarlySetting().getLeaveLate().isUse(),holidayCalcMethodSet));
			//早退（時間帯から計算）
			for(TimeLeavingWork work : oneDay.getAttendanceLeavingWork().getTimeLeavingWorks())
				leaveEarlyTime.add(LeaveEarlyTimeOfDaily.calcLeaveEarlyTime(oneDay, work.getWorkNo(),calcAtrOfDaily.getLeaveEarlySetting().getLeaveEarly().isUse(),holidayCalcMethodSet));
		}
		//日別実績の休憩時間
		val breakTime = BreakTimeOfDaily.calcTotalBreakTime(oneDay,breakTimeCount);

		//日別実績の外出時間
		val outingList = new ArrayList<OutingTimeOfDaily>();
		val outingTime = OutingTimeOfDaily.calcOutingTime(oneDay);
		outingList.add(outingTime);
		
		val shotrTime = new ShortWorkTimeOfDaily(new WorkTimes(1),
											 DeductionTotalTime.of(TimeWithCalculation.sameTime(new AttendanceTime(0)),
													 			   TimeWithCalculation.sameTime(new AttendanceTime(0)),
													 			   TimeWithCalculation.sameTime(new AttendanceTime(0))),
											 DeductionTotalTime.of(TimeWithCalculation.sameTime(new AttendanceTime(0)),
										 			   			   TimeWithCalculation.sameTime(new AttendanceTime(0)),
										 			   			   TimeWithCalculation.sameTime(new AttendanceTime(0))),
											 ChildCareAttribute.CARE
											);
		//加給時間
		val raiseTime = RaiseSalaryTimeOfDailyPerfor.calcBonusPayTime(oneDay, calcAtrOfDaily.getRasingSalarySetting(), bonusPayAutoCalcSet, calcAtrOfDaily);
		//勤務回数
		val workCount = new WorkTimes(workCounter(oneDay));
		/*日別実績の臨時時間*/
		val tempTime = new TemporaryTimeOfDaily(Collections.emptyList());

		//日別実績の休暇
		val vacationOfDaily = VacationClass.calcUseRestTime(workType,
															oneDay.getPredetermineTimeSetForCalc(),
															workTimeCode,
															personalCondition,
															holidayAddtionSet,
															outingList,
															lateTime,
															leaveEarlyTime);
		//休暇加算時間の計算
		val vacationAddTime =  vacationClass.calcVacationAddTime(nts.uk.ctx.at.shared.dom.PremiumAtr.RegularWork,
																 workingSystem,
																 holidayAddtionSet,
																 workType,
																 oneDay.getPredetermineTimeSetForCalc(),
																 workTimeCode,
																 personalCondition,
																 holidayCalcMethodSet);
				
				
		
		//総労働時間
		
		int flexTime = workTimeDailyAtr.isPresent() && workTimeDailyAtr.get().isFlex() 
						? excesstime.getOverTimeWork().get().getFlexTime().getFlexTime().getCalcTime().valueAsMinutes()
						:0;
		flexTime = (flexTime<0)?0:flexTime;
		val totalWorkTime = new AttendanceTime(withinStatutoryTimeOfDaily.getWorkTime().valueAsMinutes()
						  					   + withinStatutoryTimeOfDaily.getWithinPrescribedPremiumTime().valueAsMinutes() 
						  					   + overWorkTime
						  					   + holidayWorkTime
						  					   + tempTime.totalTemporaryFrameTime()
						  					   + flexTime);
		
		//総計算時間
		val totalCalcTime = new AttendanceTime(withinStatutoryTimeOfDaily.getActualWorkTime().valueAsMinutes()
	   											+ withinStatutoryTimeOfDaily.getWithinPrescribedPremiumTime().valueAsMinutes() 
	   											+ overWorkTime
	   											+ holidayWorkTime
	   											+ tempTime.totalTemporaryFrameTime()
	   											+ flexTime);
		
		//実働時間
		val actualTime = new AttendanceTime(withinStatutoryTimeOfDaily.getActualWorkTime().valueAsMinutes()
				   			+ withinStatutoryTimeOfDaily.getWithinPrescribedPremiumTime().valueAsMinutes() 
				   			+ overWorkTime
				   			+ holidayWorkTime
				   			+ tempTime.totalTemporaryFrameTime()
				   			+ flexTime
				   			/*変形基準内残業の時間もここにタス*/);
		
		TotalWorkingTime returnTotalWorkingTimereturn = new TotalWorkingTime(totalWorkTime,
																				totalCalcTime,
																				actualTime,
																				withinStatutoryTimeOfDaily,
																				excesstime,
																				lateTime,
																				leaveEarlyTime,
																				breakTime,
																				outingList,
																				raiseTime,
																				workCount,
																				tempTime,
																				shotrTime,
																				vacationOfDaily);
		returnTotalWorkingTimereturn.setVacationAddTime(new AttendanceTime(vacationAddTime.calcTotaladdVacationAddTime()));
		
		
		return returnTotalWorkingTimereturn;
	}
	
	private static int workCounter(CalculationRangeOfOneDay oneDay) {
		int workCount = 0;
		if(oneDay.getAttendanceLeavingWork() != null) {
			workCount = oneDay.getAttendanceLeavingWork().getTimeLeavingWorks().stream()
																   .filter(tc -> 
																   		tc.getAttendanceStamp() != null
																   	&&  tc.getAttendanceStamp().isPresent()
																   	&&  tc.getAttendanceStamp().get().getStamp() != null
																   	&&  tc.getAttendanceStamp().get().getStamp().isPresent()
																   	&&  tc.getAttendanceStamp().get().getStamp().get().getTimeWithDay() != null
																   	&&  tc.getLeaveStamp() != null
																   	&&  tc.getLeaveStamp().isPresent()
																   	&&  tc.getLeaveStamp().get().getStamp() != null
																   	&&  tc.getLeaveStamp().get().getStamp().isPresent()
																   	&&  tc.getLeaveStamp().get().getStamp().get().getTimeWithDay() != null)
																   .collect(Collectors.toList()).size();
		}
		//↓に臨時を入れる
		//リンジ
		
		return workCount;
	}

	public Optional<LeaveEarlyTimeOfDaily> getLeaveEarlyTimeNo(int no){
		return leaveEarlyTimeOfDaily.stream().filter(c -> c.getWorkNo().v() == no).findFirst();
	}

	/**
	 * エラーチェック(乖離以外)への分岐 
	 * @param attendanceItemConverter 
	 * @return 社員のエラーチェック一覧
	 */
	public List<EmployeeDailyPerError> checkOverTimeExcess(String employeeId,
														   GeneralDate targetDate,
														   SystemFixedErrorAlarm fixedErrorAlarmCode,
														   CheckExcessAtr checkAtr) {
		List<EmployeeDailyPerError> returnErrorItem = new ArrayList<>();
		AttendanceItemDictionaryForCalc attendanceItemDictionary = AttendanceItemDictionaryForCalc.setDictionaryValue(); 
		switch(checkAtr) {
			//残業超過
			case OVER_TIME_EXCESS:
				if(this.getExcessOfStatutoryTimeOfDaily() != null) 
					returnErrorItem.addAll(this.getExcessOfStatutoryTimeOfDaily().checkOverTimeExcess(employeeId, targetDate,"残業時間",attendanceItemDictionary,new ErrorAlarmWorkRecordCode(fixedErrorAlarmCode.value)));
				break;
			//休出超過
			case REST_TIME_EXCESS:
				if(this.getExcessOfStatutoryTimeOfDaily() != null)
					returnErrorItem.addAll(this.getExcessOfStatutoryTimeOfDaily().checkHolidayWorkTimeExcess(employeeId, targetDate,"休出時間", attendanceItemDictionary,new ErrorAlarmWorkRecordCode(fixedErrorAlarmCode.value)));
			//フレ超過
			case FLEX_OVER_TIME:
				if(this.getExcessOfStatutoryTimeOfDaily() != null) 
					returnErrorItem.addAll(this.getExcessOfStatutoryTimeOfDaily().checkFlexTimeExcess(employeeId, targetDate,"フレックス時間", attendanceItemDictionary,new ErrorAlarmWorkRecordCode(fixedErrorAlarmCode.value)));
				break;
			//深夜超過
			case MIDNIGHT_EXCESS:
				if(this.getWithinStatutoryTimeOfDaily() != null)
					returnErrorItem.addAll(this.getWithinStatutoryTimeOfDaily().checkWithinMidNightExcess(employeeId, targetDate,"内深夜時間",attendanceItemDictionary,new ErrorAlarmWorkRecordCode(fixedErrorAlarmCode.value)));
				if(this.getExcessOfStatutoryTimeOfDaily() != null)
					returnErrorItem.addAll(this.getExcessOfStatutoryTimeOfDaily().checkMidNightExcess(employeeId, targetDate,"外深夜時間", attendanceItemDictionary,new ErrorAlarmWorkRecordCode(fixedErrorAlarmCode.value)));
			//事前残業申請超過
			case PRE_OVERTIME_APP_EXCESS:
				if(this.getExcessOfStatutoryTimeOfDaily() != null) 
					returnErrorItem.addAll(this.getExcessOfStatutoryTimeOfDaily().checkPreOverTimeExcess(employeeId, targetDate,"残業時間", attendanceItemDictionary,new ErrorAlarmWorkRecordCode(fixedErrorAlarmCode.value)));
				break;
			//事前休出申請超過
			case PRE_HOLIDAYWORK_APP_EXCESS:
				if(this.getExcessOfStatutoryTimeOfDaily() != null)
					this.getExcessOfStatutoryTimeOfDaily().checkHolidayWorkTimeExcess(employeeId, targetDate,"休出時間",attendanceItemDictionary, new ErrorAlarmWorkRecordCode(fixedErrorAlarmCode.value));
			//事前フレ申請超過
			case PRE_FLEX_APP_EXCESS:
				if(this.getExcessOfStatutoryTimeOfDaily() != null) 
					returnErrorItem.addAll(this.getExcessOfStatutoryTimeOfDaily().checkPreFlexTimeExcess(employeeId, targetDate,"フレックス時間",attendanceItemDictionary, new ErrorAlarmWorkRecordCode(fixedErrorAlarmCode.value)));
				break;
			//事前深夜
			case PRE_MIDNIGHT_EXCESS:
				if(this.getExcessOfStatutoryTimeOfDaily() != null) 
					returnErrorItem.addAll(this.getExcessOfStatutoryTimeOfDaily().checkPreMidNightExcess(employeeId, targetDate,"外深夜時間",attendanceItemDictionary, new ErrorAlarmWorkRecordCode(fixedErrorAlarmCode.value)));
				break;
			//遅刻
			case LATE:
				if(!this.getLateTimeOfDaily().isEmpty())
					returnErrorItem.addAll(this.getLateTimeOfDaily().stream().map(tc -> tc.checkError(employeeId, targetDate,"早退時間", attendanceItemDictionary, new ErrorAlarmWorkRecordCode(fixedErrorAlarmCode.value))).flatMap(tc -> tc.stream()).collect(Collectors.toList()));					
				break;
			//早退
			case LEAVE_EARLY:
				if(!this.getLeaveEarlyTimeOfDaily().isEmpty())
					returnErrorItem.addAll(this.getLeaveEarlyTimeOfDaily().stream().map(tc -> tc.checkError(employeeId, targetDate,"早退時間", attendanceItemDictionary, new ErrorAlarmWorkRecordCode(fixedErrorAlarmCode.value))).flatMap(tc -> tc.stream()).collect(Collectors.toList()));;
				break;		   
			default :
				throw new RuntimeException("unknown error item Atr in DailyCalc:"+checkAtr);
		}
		return returnErrorItem;
	}
	
	public Optional<LateTimeOfDaily> getLateTimeNo(int no){
		return this.lateTimeOfDaily.stream().filter(l -> l.getWorkNo().v() == no).findFirst();
	}

	public Optional<OutingTimeOfDaily> getOutingTimeByReason(GoOutReason reason){
		return getOutingTimeByReason(reason.value);
	}

	public Optional<OutingTimeOfDaily> getOutingTimeByReason(int reason){
		return this.outingTimeOfDailyPerformance.stream().filter(o -> o.getReason().value == reason).findFirst();
	}

	public TotalWorkingTime calcDiverGenceTime() {
		return new TotalWorkingTime(this.totalTime,
									this.totalCalcTime,
									this.actualTime,
									this.withinStatutoryTimeOfDaily!=null?this.withinStatutoryTimeOfDaily.calcDiverGenceTime():this.withinStatutoryTimeOfDaily,
									this.excessOfStatutoryTimeOfDaily!=null?this.excessOfStatutoryTimeOfDaily.calcDiverGenceTime():this.excessOfStatutoryTimeOfDaily,
									this.lateTimeOfDaily,
									this.leaveEarlyTimeOfDaily,
									this.breakTimeOfDaily,
									this.outingTimeOfDailyPerformance,
									this.raiseSalaryTimeOfDailyPerfor, this.workTimes, this.temporaryTime, this.shotrTimeOfDaily, this.holidayOfDaily); 
	}


	/**
	 * 控除合計時間の計算(手修正不就労用)
	 * @return
	 */
	public AttendanceTime calcTotalDedTime(CalculationRangeOfOneDay oneDay) {
		//休憩時間
		AttendanceTime totalTime = BreakTimeOfDaily.calculationDedBreakTime(DeductionAtr.Deduction, oneDay).getTotalTime().getCalcTime();
		//外出
		//短時間
		
		return totalTime;
	}
	
	
	public void calcTotalWorkingTimeForReCalc() {
		this.totalTime = recalcTotalWorkingTime();
	}
	/**
	 * 手修正の再計算時に使用する総労働時間の計算
	 * @return
	 */
	public AttendanceTime recalcTotalWorkingTime() {
		int withinTime = calcWithinTime();
		int overTime = calcOverTime();
		int holidayTime = calcHolidayTime();
		int rinzi = 0;
		return new AttendanceTime(withinTime + overTime + holidayTime + rinzi);
	}

	public int calcHolidayTime() {
		int totalHolidayTimeTime = 0;
		int totaltransTime = 0;
		if(this.getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().isPresent()) {
			totalHolidayTimeTime = this.getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().get().getHolidayWorkFrameTime()
																									.stream()
																									.filter(tc -> tc.getHolidayWorkTime().isPresent())
																									.map(tc -> tc.getHolidayWorkTime().get().getTime().valueAsMinutes())
																									.collect(Collectors.summingInt(tc -> tc));
			totaltransTime = this.getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().get().getHolidayWorkFrameTime()
																							  .stream()
																							  .filter(tc -> tc.getTransferTime().isPresent())
																							  .map(tc -> tc.getTransferTime().get().getTime().valueAsMinutes())
																							  .collect(Collectors.summingInt(tc -> tc));
		}
		return totalHolidayTimeTime + totaltransTime;
	}
	
	/**
	 * 手修正再計算用
	 * 残業時間＋フレ＋振替時間を求める
	 * @return
	 */
	public int calcOverTime() {
		int removeFlexTime = 0;
		int flexTime = 0;
		if(this.excessOfStatutoryTimeOfDaily.getOverTimeWork().isPresent()) {
			removeFlexTime = calcOverTimeRemoveFlex();
			flexTime = this.getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().getFlexTime().getFlexTime().getTime().valueAsMinutes();
			flexTime = flexTime > 0 ? flexTime : 0;
		}
		return removeFlexTime + flexTime;
	}
	
	public int calcOverTimeRemoveFlex() {
		int totalOverTime = this.getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().getOverTimeWorkFrameTime()
				  				.stream()
				  				.filter(tc -> tc != null 
				  						&& tc.getOverTimeWork() != null
				  						&& tc.getOverTimeWork().getTime() != null)
				  				.map(tc -> tc.getOverTimeWork().getTime().valueAsMinutes())
				  				.collect(Collectors.summingInt(tc -> tc));
		int totaltransTime = this.getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().getOverTimeWorkFrameTime()
				   				 .stream()
				   				 .filter(tc -> tc != null 
				   				 	&& tc.getTransferTime() != null
				   				 	&& tc.getTransferTime().getTime() != null)
				   				 .map(tc -> tc.getTransferTime().getTime().valueAsMinutes())
				   				 .collect(Collectors.summingInt(tc -> tc));
		return totalOverTime + totaltransTime;
	}

	/**
	 * 手修正後の再計算(実働時間)
	 * @return
	 */
	public AttendanceTime recalcActualTime() {
		//実働時間
		return recalcTotalWorkingTime(); 
						 //+変形基準内残業を足して返す;
	}
	/**
	 * 手修正、再計算用
	 * 所定内時間+所定内割増時間を求める
	 * @return
	 */
	private int calcWithinTime() {
		return this.withinStatutoryTimeOfDaily.getWorkTime().valueAsMinutes()
				+ this.withinStatutoryTimeOfDaily.getWithinPrescribedPremiumTime().valueAsMinutes();
	}
	
	/**
	 * 深夜時間の上限時間調整処理(加給と深夜へ指示を出す)
	 * @param upperTime 上限時間
	 */
	public void controlUpperTime(AttendanceTime upperTime) {
		//深夜の上限制御
		this.controlUpperTimeForMidTime(upperTime);
		//加給の上限制御
		this.raiseSalaryTimeOfDailyPerfor.controlUpperTimeForSalaryTime(upperTime);
	}
	
	/**
	 * 深夜時間の上限時間制御指示
	 * @param upperTime 上限時間
	 */
	private void controlUpperTimeForMidTime(AttendanceTime upperTime) {
		if(this.withinStatutoryTimeOfDaily != null)
			this.withinStatutoryTimeOfDaily.controlMidTimeUpper(upperTime);
		if(this.excessOfStatutoryTimeOfDaily != null)
			this.excessOfStatutoryTimeOfDaily.controlMidTimeUpper(upperTime);
	}

	public void setWithinWorkTime(AttendanceTime predetermineTime) {
		if(this.withinStatutoryTimeOfDaily != null)
			this.withinStatutoryTimeOfDaily.setWorkTime(predetermineTime);
	}
		
	
	/**
	 * 大塚モードの計算（遅刻早退）
	 * @return
	 */
	public TotalWorkingTime reCalcLateLeave(HolidayCalculation holidayCalculation) {
		TotalWorkingTime result = this;
		//休暇時に計算する設定かどうか判断
		if(holidayCalculation.getIsCalculate().isNotUse()) {
			return result;
		}
		//遅刻早退の合計時間を取得
		TimeWithCalculation lateLeaveTotalTime = this.calcLateLeaveTotalTime();
		//欠勤控除時間の計算
		TimeWithCalculation absenteeismDeductionTime = this.calcAbsenteeismDeductionTime(lateLeaveTotalTime);
		
		if(!this.lateTimeOfDaily.isEmpty()) {
			//勤務NOの昇順でソート
			this.lateTimeOfDaily.sort((c1, c2) -> c1.getWorkNo().compareTo(c2.getWorkNo()));
			//欠勤控除時間を遅刻時間とする
			this.lateTimeOfDaily.get(0).rePlaceLateTime(absenteeismDeductionTime);
		}
		
		if(!this.leaveEarlyTimeOfDaily.isEmpty()) {
			//勤務NOの昇順でソート
			this.leaveEarlyTimeOfDaily.sort((c1, c2) -> c1.getWorkNo().compareTo(c2.getWorkNo()));
			//早退時間をクリア
			this.leaveEarlyTimeOfDaily.get(0).rePlaceLeaveEarlyTime(TimeWithCalculation.sameTime(new AttendanceTime(0)));
		}
		
		return result;
	}
	
	/**
	 * 遅刻早退の合計時間を取得
	 * 大塚専用処理なので遅刻早退クラスに実装しない
	 * @return
	 */
	public TimeWithCalculation calcLateLeaveTotalTime() {
		
		AttendanceTime time = new AttendanceTime(0);
		AttendanceTime calcTime = new AttendanceTime(0);
		//日別実績の遅刻時間の取得（遅刻早退合計時間への追加）
		if(!this.lateTimeOfDaily.isEmpty()) {
			List<LateTimeOfDaily> list = this.lateTimeOfDaily.stream().filter(l -> l.getWorkNo().equals(new WorkNo(1))).collect(Collectors.toList());
			if(!list.isEmpty()) {
				LateTimeOfDaily lateTimeOfDaily = list.get(0);
				time = time.addMinutes(lateTimeOfDaily.getLateTime().getTime().valueAsMinutes());
				calcTime = calcTime.addMinutes(lateTimeOfDaily.getLateTime().getCalcTime().valueAsMinutes());
			}
		}
		//日別実績の早退時間の取得（遅刻早退合計時間への追加）
		if(!this.leaveEarlyTimeOfDaily.isEmpty()) {
			List<LeaveEarlyTimeOfDaily> list = this.leaveEarlyTimeOfDaily.stream().filter(l -> l.getWorkNo().equals(new WorkNo(1))).collect(Collectors.toList());
			if(!list.isEmpty()) {
				LeaveEarlyTimeOfDaily leaveEarlyTimeOfDaily = list.get(0);
				time = time.addMinutes(leaveEarlyTimeOfDaily.getLeaveEarlyTime().getTime().valueAsMinutes());
				calcTime = calcTime.addMinutes(leaveEarlyTimeOfDaily.getLeaveEarlyTime().getCalcTime().valueAsMinutes());
			}
		}		
		return TimeWithCalculation.createTimeWithCalculation(time, calcTime);
	}
	
	/**
	 * 欠勤控除時間の計算
	 * @return
	 */
	public TimeWithCalculation calcAbsenteeismDeductionTime(TimeWithCalculation lateLeaveTotalTime) {

		//時間、計算時間から休暇加算時間を減算
		AttendanceTime time = lateLeaveTotalTime.getTime().minusMinutes(this.vacationAddTime.valueAsMinutes());;
		AttendanceTime calcTime = lateLeaveTotalTime.getCalcTime().minusMinutes(this.vacationAddTime.valueAsMinutes());
		
		//0:00以下なら0：00に補正
		if(time.valueAsMinutes()<0) {
			time = new AttendanceTime(0);
		}
		if(calcTime.valueAsMinutes()<0) {
			calcTime = new AttendanceTime(0);
		}
		
		return TimeWithCalculation.createTimeWithCalculation(time, calcTime);
	}
	
	
	
	
}

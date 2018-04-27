package nts.uk.ctx.at.record.dom.actualworkinghours;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
import nts.uk.ctx.at.record.dom.daily.breaktimegoout.BreakTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.calcset.CalcMethodOfNoWorkingDay;
import nts.uk.ctx.at.record.dom.daily.vacationusetime.HolidayOfDaily;
import nts.uk.ctx.at.record.dom.daily.withinworktime.WithinStatutoryTimeOfDaily;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.AttendanceItemDictionaryForCalc;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculationRangeOfOneDay;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CheckExcessAtr;
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
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.StatutoryDivision;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryOccurrenceSetting;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalRaisingSalarySetting;
import nts.uk.ctx.at.shared.dom.workrule.waytowork.PersonalLaborCondition;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneOtherSubHolTimeSet;
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
	public static TotalWorkingTime calcAllDailyRecord(CalculationRangeOfOneDay oneDay,AutoCalOvertimeSetting overTimeAutoCalcSet,AutoCalSetting holidayAutoCalcSetting,
			   Optional<PersonalLaborCondition> personalCondition,
			   VacationClass vacationClass,
			   WorkType workType,
			   boolean late,  //日別実績の計算区分.遅刻早退の自動計算設定.遅刻
			   boolean leaveEarly,  //日別実績の計算区分.遅刻早退の自動計算設定.早退
			   WorkingSystem workingSystem,
			   WorkDeformedLaborAdditionSet illegularAddSetting,
			   WorkFlexAdditionSet flexAddSetting,
			   WorkRegularAdditionSet regularAddSetting,
			   HolidayAddtionSet holidayAddtionSet,
			   AutoCalOverTimeAttr overTimeAutoCalcAtr,
			   Optional<WorkTimeDailyAtr> workTimeDailyAtr,
			   Optional<SettingOfFlexWork> flexCalcMethod,
			   HolidayCalcMethodSet holidayCalcMethodSet,
			   AutoCalRaisingSalarySetting raisingAutoCalcSet,
			   BonusPayAutoCalcSet bonusPayAutoCalcSet,
			   CalAttrOfDailyPerformance calcAtrOfDaily,
			   List<WorkTimezoneOtherSubHolTimeSet> eachWorkTimeSet,
			   List<CompensatoryOccurrenceSetting> eachCompanyTimeSet, 
			   int breakTimeCount, IntegrationOfDaily integrationOfDaily,
			   AutoCalFlexOvertimeSetting flexAutoCalSet
			   ) {
		

		Optional<WorkTimeCode> workTimeCode = Optional.empty();
		//日別実績の所定外時間
		if(oneDay.getWorkInformationOfDaily().getRecordInfo().getWorkTimeCode() != null) {
			workTimeCode = oneDay.getWorkInformationOfDaily().getRecordInfo().getWorkTimeCode().v() == null
																		?Optional.empty()
																		:Optional.of(new WorkTimeCode(oneDay.getWorkInformationOfDaily().getRecordInfo().getWorkTimeCode().v().toString()));
		}
		
		/*日別実績の法定内時間(就業時間)*/
		val withinStatutoryTimeOfDaily = WithinStatutoryTimeOfDaily.calcStatutoryTime(oneDay,
				   																      personalCondition,
				   																      vacationClass,
				   																      workType,
				   																      late,  //日別実績の計算区分.遅刻早退の自動計算設定.遅刻
				   																      leaveEarly,  //日別実績の計算区分.遅刻早退の自動計算設定.早退
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
				   																      flexAutoCalSet.getFlexOtTime().getUpLimitORtSet(),
				   																      workTimeDailyAtr.get(), 
				   																      workTimeCode);

		
		ExcessOfStatutoryTimeOfDaily excesstime =ExcessOfStatutoryTimeOfDaily.calculationExcessTime(oneDay, 
																									overTimeAutoCalcSet, 
																									holidayAutoCalcSetting, 
																									CalcMethodOfNoWorkingDay.isCalculateFlexTime,
																									holidayCalcMethodSet,
																									overTimeAutoCalcAtr,
																									workType,flexCalcMethod,oneDay.getPredetermineTimeSetForCalc()
																									,vacationClass,oneDay.getTimeVacationAdditionRemainingTime().get(),
																									StatutoryDivision.Nomal,
																									workTimeCode,
																									personalCondition,
																									late,  //日別実績の計算区分.遅刻早退の自動計算設定.遅刻
																									leaveEarly,  //日別実績の計算区分.遅刻早退の自動計算設定.早退
																									workingSystem,illegularAddSetting,flexAddSetting,regularAddSetting,
																									holidayAddtionSet,
																									workTimeDailyAtr.get(),
																									eachWorkTimeSet,
																									eachCompanyTimeSet,
																									integrationOfDaily);
		int overWorkTime = excesstime.getOverTimeWork().isPresent()?excesstime.getOverTimeWork().get().calcTotalFrameTime():0;
		overWorkTime += excesstime.getOverTimeWork().isPresent()?excesstime.getOverTimeWork().get().calcTransTotalFrameTime():0;
		int holidayWorkTime = excesstime.getWorkHolidayTime().isPresent()?excesstime.getWorkHolidayTime().get().calcTotalFrameTime():0;
		holidayWorkTime += excesstime.getWorkHolidayTime().isPresent()?excesstime.getWorkHolidayTime().get().calcTransTotalFrameTime():0;
		//日別実績の遅刻時間
		List<LateTimeOfDaily> lateTime = new ArrayList<>();
		for(TimeLeavingWork work : oneDay.getAttendanceLeavingWork().getTimeLeavingWorks())
			lateTime.add(LateTimeOfDaily.calcLateTime(oneDay, work.getWorkNo(),late,holidayCalcMethodSet));
		//日別実績の早退時間
		List<LeaveEarlyTimeOfDaily> leaveEarlyTime = new ArrayList<>();
		for(TimeLeavingWork work : oneDay.getAttendanceLeavingWork().getTimeLeavingWorks())
			leaveEarlyTime.add(LeaveEarlyTimeOfDaily.calcLeaveEarlyTime(oneDay, work.getWorkNo(),leaveEarly,holidayCalcMethodSet));
		
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
		val raiseTime = RaiseSalaryTimeOfDailyPerfor.calcBonusPayTime(oneDay, raisingAutoCalcSet, bonusPayAutoCalcSet, calcAtrOfDaily);
		//勤務回数
		val workTimes = new WorkTimes(1);
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
		val totalWorkTime = new AttendanceTime(withinStatutoryTimeOfDaily.getWorkTime().valueAsMinutes()
						  					   + withinStatutoryTimeOfDaily.getWithinPrescribedPremiumTime().valueAsMinutes() 
						  					   + overWorkTime
						  					   + holidayWorkTime
						  					   + tempTime.totalTemporaryFrameTime());
		
		//総計算時間
		val totalCalcTime = new AttendanceTime(0);
		
		//実働時間
		val actualTime = withinStatutoryTimeOfDaily.getActualWorkTime();
		
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
																				workTimes,
																				tempTime,
																				shotrTime,
																				vacationOfDaily);
		returnTotalWorkingTimereturn.setVacationAddTime(new AttendanceTime(vacationAddTime.calcTotaladdVacationAddTime()));
		
		
		return returnTotalWorkingTimereturn;
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
					this.getExcessOfStatutoryTimeOfDaily().checkOverTimeExcess(employeeId, targetDate,"残業時間",attendanceItemDictionary,new ErrorAlarmWorkRecordCode(fixedErrorAlarmCode.value));
				break;
			//休出超過
			case REST_TIME_EXCESS:
				if(this.getExcessOfStatutoryTimeOfDaily() != null)
					this.getExcessOfStatutoryTimeOfDaily().checkHolidayWorkTimeExcess(employeeId, targetDate,"休出時間", attendanceItemDictionary,new ErrorAlarmWorkRecordCode(fixedErrorAlarmCode.value));
			//フレ超過
			case FLEX_OVER_TIME:
				if(this.getExcessOfStatutoryTimeOfDaily() != null) 
					this.getExcessOfStatutoryTimeOfDaily().checkFlexTimeExcess(employeeId, targetDate,"フレックス時間", attendanceItemDictionary,new ErrorAlarmWorkRecordCode(fixedErrorAlarmCode.value));
				break;
			//深夜超過
			case MIDNIGHT_EXCESS:
				if(this.getWithinStatutoryTimeOfDaily() != null)
					this.getWithinStatutoryTimeOfDaily().checkWithinMidNightExcess(employeeId, targetDate,"内深夜時間",attendanceItemDictionary,new ErrorAlarmWorkRecordCode(fixedErrorAlarmCode.value));
				if(this.getExcessOfStatutoryTimeOfDaily() != null)
					this.getExcessOfStatutoryTimeOfDaily().checkMidNightExcess(employeeId, targetDate,"外深夜時間", attendanceItemDictionary,new ErrorAlarmWorkRecordCode(fixedErrorAlarmCode.value));
			//事前残業申請超過
			case PRE_OVERTIME_APP_EXCESS:
				if(this.getExcessOfStatutoryTimeOfDaily() != null) 
					this.getExcessOfStatutoryTimeOfDaily().checkPreOverTimeExcess(employeeId, targetDate,"残業時間", attendanceItemDictionary,new ErrorAlarmWorkRecordCode(fixedErrorAlarmCode.value));
				break;
			//事前休出申請超過
			case PRE_HOLIDAYWORK_APP_EXCESS:
				if(this.getExcessOfStatutoryTimeOfDaily() != null)
					this.getExcessOfStatutoryTimeOfDaily().checkHolidayWorkTimeExcess(employeeId, targetDate,"休出時間",attendanceItemDictionary, new ErrorAlarmWorkRecordCode(fixedErrorAlarmCode.value));
			//事前フレ申請超過
			case PRE_FLEX_APP_EXCESS:
				if(this.getExcessOfStatutoryTimeOfDaily() != null) 
					this.getExcessOfStatutoryTimeOfDaily().checkPreFlexTimeExcess(employeeId, targetDate,"フレックス時間",attendanceItemDictionary, new ErrorAlarmWorkRecordCode(fixedErrorAlarmCode.value));
				break;
			//事前深夜
			case PRE_MIDNIGHT_EXCESS:
				if(this.getExcessOfStatutoryTimeOfDaily() != null) 
					this.getExcessOfStatutoryTimeOfDaily().checkPreMidNightExcess(employeeId, targetDate,"外深夜時間",attendanceItemDictionary, new ErrorAlarmWorkRecordCode(fixedErrorAlarmCode.value));
				break;
			//遅刻
			case LATE:
				if(!this.getLateTimeOfDaily().isEmpty())
					this.getLateTimeOfDaily().stream()
					   								.map(tc -> tc.checkError(employeeId, targetDate,"早退時間", attendanceItemDictionary, new ErrorAlarmWorkRecordCode(fixedErrorAlarmCode.value)));					
				break;
			//早退
			case LEAVE_EARLY:
				if(!this.getLeaveEarlyTimeOfDaily().isEmpty())
					this.getLeaveEarlyTimeOfDaily().stream()
												   .map(tc -> tc.checkError(employeeId, targetDate,"早退時間", attendanceItemDictionary, new ErrorAlarmWorkRecordCode(fixedErrorAlarmCode.value)));
												   
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



	public void setWithinWorkTime(AttendanceTime predetermineTime) {
		if(this.withinStatutoryTimeOfDaily != null)
			this.withinStatutoryTimeOfDaily.setWorkTime(predetermineTime);
	}
		

}

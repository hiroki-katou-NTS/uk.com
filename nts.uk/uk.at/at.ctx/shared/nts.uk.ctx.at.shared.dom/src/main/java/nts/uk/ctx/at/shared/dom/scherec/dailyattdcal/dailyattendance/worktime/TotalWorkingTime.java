package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.shared.dom.PremiumAtr;
import nts.uk.ctx.at.shared.dom.common.amount.AttendanceAmountDaily;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayAddtionSet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.ExcessOfStatutoryMidNightTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.ExcessOfStatutoryTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.WithinStatutoryMidNightTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.WithinStatutoryTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkTimes;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.BreakTimeGoOutTimes;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.BreakTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.OutingTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.OutingTotalTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.DeductionTotalTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeDivergenceWithCalculationMinusExist;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.earlyleavetime.LeaveEarlyTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.SystemFixedErrorAlarm;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.holidayworktime.HolidayMidnightWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.holidayworktime.HolidayWorkFrameTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.holidayworktime.HolidayWorkTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.interval.IntervalTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.latetime.LateTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.overtimehours.ExcessOverTimeWorkMidNightTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.overtimehours.clearovertime.FlexTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.overtimehours.clearovertime.OverTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime.RaiseSalaryTimeOfDailyPerfor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortWorkTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.TemporaryTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.TemporaryTimes;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime.AbsenceOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime.AnnualOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime.HolidayOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime.OverSalaryOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime.SpecialHolidayOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime.SubstituteHolidayOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime.TimeDigestOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime.TransferHolidayOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime.VacationClass;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime.YearlyReservedOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workingstyle.flex.SettingOfFlexWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.AttendanceItemDictionaryForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.IntervalExemptionTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManagePerPersonDailySet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManageReGetClass;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.OutsideWorkTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSpanForDailyCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.declare.DeclareTimezoneResult;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.CalculationRangeOfOneDay;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.DeductionAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours.WithinWorkTimeFrame;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours.WithinWorkTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.CheckExcessAtr;
import nts.uk.ctx.at.shared.dom.shortworktime.ChildCareAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.IntegrationOfWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkNo;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDailyAtr;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceHolidayAttr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * ??????????????????????????????
 * @author nampt
 */
@Getter
public class TotalWorkingTime {
	
	/** ??????????????? */
	private AttendanceTime totalTime;
	
	/** ??????????????? */
	private AttendanceTime totalCalcTime;
	
	/** ???????????? */
	private AttendanceTime actualTime;
	
	/** ?????????????????? */
	private AttendanceTime calcDiffTime = new AttendanceTime(0);
	
	/** ?????????????????????????????? - ??????????????? (new) */
	private WithinStatutoryTimeOfDaily withinStatutoryTimeOfDaily;
	 
	/** ?????????????????????????????? - ??????????????? (new) */
	private ExcessOfStatutoryTimeOfDaily excessOfStatutoryTimeOfDaily;
	
	/** ??????????????????????????? */
	private List<LateTimeOfDaily> lateTimeOfDaily = new ArrayList<>();
	
	/** ??????????????????????????? */
	private List<LeaveEarlyTimeOfDaily> leaveEarlyTimeOfDaily = new ArrayList<>(); 
	
	/** ??????????????????????????? */
	private BreakTimeOfDaily breakTimeOfDaily;
	
	/** ??????????????????????????? */
	private List<OutingTimeOfDaily> outingTimeOfDailyPerformance;
		
	/** ???????????? */
	private RaiseSalaryTimeOfDailyPerfor raiseSalaryTimeOfDailyPerfor;
	
	/** ???????????? */
	private WorkTimes workTimes;
	
	/** ????????????????????? */
	private ShortWorkTimeOfDaily shotrTimeOfDaily;
	
	/** ??????????????????????????? */
	private HolidayOfDaily holidayOfDaily;
	
	/** ?????????????????? */
	private AttendanceTime vacationAddTime = new AttendanceTime(0);
	
	/** ????????????????????????: ??????????????????????????????????????? */
	private IntervalTimeOfDaily intervalTime;
	
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
	 * @param intervalTime
	 */
	public TotalWorkingTime(AttendanceTime totalTime, AttendanceTime totalCalcTime, AttendanceTime actualTime,
			WithinStatutoryTimeOfDaily withinStatutoryTimeOfDaily,
			ExcessOfStatutoryTimeOfDaily excessOfStatutoryTimeOfDaily, List<LateTimeOfDaily> lateTimeOfDaily,
			List<LeaveEarlyTimeOfDaily> leaveEarlyTimeOfDaily, BreakTimeOfDaily breakTimeOfDaily,
			List<OutingTimeOfDaily> outingTimeOfDailyPerformance,
			RaiseSalaryTimeOfDailyPerfor raiseSalaryTimeOfDailyPerfor, WorkTimes workTimes,
			ShortWorkTimeOfDaily shotrTime,HolidayOfDaily holidayOfDaily,
			IntervalTimeOfDaily intervalTime) {
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
		this.shotrTimeOfDaily = shotrTime;
		this.holidayOfDaily = holidayOfDaily;
		this.intervalTime = intervalTime;
	}
	
	public TotalWorkingTime(
			AttendanceTime totalTime,
			AttendanceTime totalCalcTime,
			AttendanceTime actualTime,
			WithinStatutoryTimeOfDaily withinStatutoryTimeOfDaily,
			ExcessOfStatutoryTimeOfDaily excessOfStatutoryTimeOfDaily,
			List<LateTimeOfDaily> lateTimeOfDaily,
			List<LeaveEarlyTimeOfDaily> leaveEarlyTimeOfDaily,
			BreakTimeOfDaily breakTimeOfDaily,
			List<OutingTimeOfDaily> outingTimeOfDailyPerformance,
			RaiseSalaryTimeOfDailyPerfor raiseSalaryTimeOfDailyPerfor,
			WorkTimes workTimes,
			ShortWorkTimeOfDaily shotrTime,
			HolidayOfDaily holidayOfDaily,
			IntervalTimeOfDaily intervalTime,
			AttendanceTime calcDiffTime,
			AttendanceTime vacationAddTime) {
		
		this(totalTime, totalCalcTime, actualTime, withinStatutoryTimeOfDaily, excessOfStatutoryTimeOfDaily,
				lateTimeOfDaily, leaveEarlyTimeOfDaily, breakTimeOfDaily, outingTimeOfDailyPerformance,
				raiseSalaryTimeOfDailyPerfor, workTimes, shotrTime, holidayOfDaily, intervalTime);
		this.calcDiffTime = calcDiffTime;
		this.vacationAddTime = vacationAddTime;
	}
	
	@SuppressWarnings("serial")
	public static TotalWorkingTime createAllZEROInstance() {
		List<HolidayWorkFrameTime> lstHolidayWorkFrameTime = new ArrayList<>();
		for(int i = 1; i<=10; i++) {
			TimeDivergenceWithCalculation holidayWorkTime = TimeDivergenceWithCalculation.createTimeWithCalculation(new AttendanceTime(0), new AttendanceTime(0));			
			HolidayWorkFrameTime frameTime = new HolidayWorkFrameTime(new HolidayWorkFrameNo(i),
					Finally.of(holidayWorkTime), Finally.of(holidayWorkTime), Finally.of(new AttendanceTime(0)));
			lstHolidayWorkFrameTime.add(frameTime);
		}
		return new TotalWorkingTime(new AttendanceTime(0),
									new AttendanceTime(0),
									new AttendanceTime(0),
									WithinStatutoryTimeOfDaily.createWithinStatutoryTimeOfDaily(new AttendanceTime(0), 
																								new AttendanceTime(0), 
																								new AttendanceTime(0), 
																								new WithinStatutoryMidNightTime(TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0))),
																								AttendanceAmountDaily.ZERO),
									new ExcessOfStatutoryTimeOfDaily(
											new ExcessOfStatutoryMidNightTime(
													TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0)),
													new AttendanceTime(0)),
											Optional.of(new OverTimeOfDaily(
													new ArrayList<>(),
													new ArrayList<>(),
													Finally.of(new ExcessOverTimeWorkMidNightTime(
															TimeDivergenceWithCalculation.createTimeWithCalculation(new AttendanceTime(0), new AttendanceTime(0)))),
													new AttendanceTime(0),
													new FlexTime(
															TimeDivergenceWithCalculationMinusExist.createTimeWithCalculation(new AttendanceTimeOfExistMinus(0), new AttendanceTimeOfExistMinus(0)),
															new AttendanceTime(0)),
													new AttendanceTime(0))),
											Optional.of(new HolidayWorkTimeOfDaily(
													new ArrayList<>(),
													lstHolidayWorkFrameTime,
													Finally.of(new HolidayMidnightWork(new ArrayList<>())),
													new AttendanceTime(0))),
											new TemporaryTimeOfDaily(
													new ArrayList<>(),
													new TemporaryTimes(0))),
									new ArrayList<LateTimeOfDaily>(){
										   {
										      add(new LateTimeOfDaily(TimeWithCalculation.sameTime(new AttendanceTime(0)), 
																	  TimeWithCalculation.sameTime(new AttendanceTime(0)), 
																	  new WorkNo(0), 
																	  TimevacationUseTimeOfDaily.defaultValue(), 
																	  IntervalExemptionTime.defaultValue()));
										  }},
									new ArrayList<LeaveEarlyTimeOfDaily>(){
											{
											  add(new LeaveEarlyTimeOfDaily(TimeWithCalculation.sameTime(new AttendanceTime(0)), 
											  								TimeWithCalculation.sameTime(new AttendanceTime(0)), 
											  								new WorkNo(0), 
											  								TimevacationUseTimeOfDaily.defaultValue(), 
											  								IntervalExemptionTime.defaultValue()));
											}},
									new BreakTimeOfDaily(DeductionTotalTime.of(TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0))),
											DeductionTotalTime.of(TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0))),
																  new BreakTimeGoOutTimes(0),
																  new AttendanceTime(0),
																  new ArrayList<>()),
									new ArrayList<>(),
									new RaiseSalaryTimeOfDailyPerfor(new ArrayList<>(),new ArrayList<>()),
									new WorkTimes(0),
									new ShortWorkTimeOfDaily(new WorkTimes(0),
															 DeductionTotalTime.of(TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0))),
															 DeductionTotalTime.of(TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0))),
															 ChildCareAtr.CARE),
									new HolidayOfDaily(new AbsenceOfDaily(new AttendanceTime(0)),
													   new TimeDigestOfDaily(new AttendanceTime(0),new AttendanceTime(0)),
													   new YearlyReservedOfDaily(new AttendanceTime(0)),
													   new SubstituteHolidayOfDaily(new AttendanceTime(0), new AttendanceTime(0)),
													   new OverSalaryOfDaily(new AttendanceTime(0), new AttendanceTime(0)),
													   new SpecialHolidayOfDaily(new AttendanceTime(0), new AttendanceTime(0)),
													   new AnnualOfDaily(new AttendanceTime(0), new AttendanceTime(0)),
													   new TransferHolidayOfDaily(new AttendanceTime(0))),
									IntervalTimeOfDaily.empty());
	}
	
	/**
	 * ??????????????????????????????
	 * @param reGetClass ??????????????????
	 * @param settingOfFlex ??????????????????????????????
	 * @param declareResult ???????????????????????????
	 * @return ???????????????
	 */
	public static TotalWorkingTime calcAllDailyRecord(
			ManageReGetClass reGetClass,
			Optional<SettingOfFlexWork> settingOfFlex,
			DeclareTimezoneResult declareResult) {

		// ????????????(Work)
		IntegrationOfDaily integrationOfDaily = reGetClass.getIntegrationOfDaily();
		// ??????????????????
		WorkingConditionItem conditionItem = reGetClass.getPersonDailySetting().getPersonInfo();
		// ??????ID
		String employeeId = conditionItem.getEmployeeId();
		// ?????????
		GeneralDate ymd = integrationOfDaily.getYmd();
		// ????????????
		WorkType workType = reGetClass.getWorkType().get();
		// ??????????????????
		Optional<WorkTimeDailyAtr> workTimeDailyAtr = reGetClass.getWorkTimeDailyAtr();
		// ?????????????????????????????????
		Optional<WorkTimeCode> workTimeCode = reGetClass.getWorkTimeCode();
		// ???????????????????????????
		Optional<PredetermineTimeSetForCalc> predetermineTimeSet =
				Optional.ofNullable(reGetClass.getCalculationRangeOfOneDay().getPredetermineTimeSetForCalc());
		// ????????????????????????
		Optional<HolidayAddtionSet> holidayAddtionSet = reGetClass.getHolidayAddtionSet();
		
		// 1??????????????????
		CalculationRangeOfOneDay calcRangeOfOneDay = reGetClass.getCalculationRangeOfOneDay();
		
		/*??????????????????????????????(????????????)*/
		val withinStatutoryTimeOfDaily = WithinStatutoryTimeOfDaily.calcStatutoryTime(reGetClass, settingOfFlex);
		
		//??????????????????????????????
		ExcessOfStatutoryTimeOfDaily excesstime = ExcessOfStatutoryTimeOfDaily.calculationExcessTime(
				reGetClass,
				settingOfFlex,
				declareResult);
		
		//???????????????????????????
		val breakTime = BreakTimeOfDaily.calcTotalBreakTime(
				calcRangeOfOneDay, reGetClass.getCalculatable());
		
		//???????????????????????????
		val outingList = OutingTimeOfDaily.calcList(reGetClass);
		
		//??????????????????????????????
		val shotrTime = ShortWorkTimeOfDaily.calcShortWorkTime(reGetClass, PremiumAtr.RegularWork);
		
		//????????????
		val raiseTime = RaiseSalaryTimeOfDailyPerfor.calcBonusPayTime(calcRangeOfOneDay,
				reGetClass.getCompanyCommonSetting().getBpTimeItemSetting(),
				reGetClass.getIntegrationOfDaily().getCalAttr());
		//????????????
		val workCount = new WorkTimes(workCounter(calcRangeOfOneDay));
		
		//???????????????????????????
		List<LateTimeOfDaily> lateTime = LateTimeOfDaily.calcList(reGetClass, settingOfFlex);
		
		//???????????????????????????
		List<LeaveEarlyTimeOfDaily> leaveEarlyTime = LeaveEarlyTimeOfDaily.calcList(reGetClass);
		
		//?????????????????????
		val vacationOfDaily = VacationClass.calcUseRestTime(
				reGetClass.getPersonDailySetting().getRequire(),
				employeeId,
				ymd,
				workType,
				predetermineTimeSet,
				holidayAddtionSet,
				integrationOfDaily);

		// ?????????????????????
		int temporaryMinutes = 0;
		if (calcRangeOfOneDay.getOutsideWorkTimeSheet().isPresent()){
			OutsideWorkTimeSheet outsideWorkTimeSheet = calcRangeOfOneDay.getOutsideWorkTimeSheet().get();
			if (outsideWorkTimeSheet.getTemporaryTimeSheet().isPresent()){
				temporaryMinutes = outsideWorkTimeSheet.getTemporaryTimeSheet().get().calcTemporaryTime().valueAsMinutes();
			}
		}
		
		//???????????????
		int flexTime = workTimeDailyAtr.isPresent()&&workTimeDailyAtr.get().isFlex() ? excesstime.getOverTimeWork().get().getFlexTime().getFlexTime().getTime().valueAsMinutes():0;
		flexTime = (flexTime<0)?0:flexTime;
		val totalWorkTime = new AttendanceTime(withinStatutoryTimeOfDaily.getWorkTime().valueAsMinutes()
											+ withinStatutoryTimeOfDaily.getWithinPrescribedPremiumTime().valueAsMinutes() 
											+ excesstime.calcOverTime().valueAsMinutes()
											+ excesstime.calcWorkHolidayTime().valueAsMinutes()
											+ temporaryMinutes
											+ flexTime
											+ excesstime.getOverTimeWork().map(o -> o.getIrregularWithinPrescribedOverTimeWork().valueAsMinutes()).orElse(0));
		
		//?????????????????????
		boolean isOOtsukaIWMode = decisionIWOOtsukaMode(workType,workTimeCode,reGetClass);
		//????????????IW????????????
		if(isOOtsukaIWMode) {
			if(reGetClass.getPredSetForOOtsuka().isPresent()) {
				withinStatutoryTimeOfDaily.setActualWorkTime(reGetClass.getPredSetForOOtsuka().get().getAdditionSet().getPredTime().getOneDay());
			}
		}
		
		//???????????????
		val totalCalcTime = new AttendanceTime(withinStatutoryTimeOfDaily.getActualWorkTime().valueAsMinutes()
											+ withinStatutoryTimeOfDaily.getWithinPrescribedPremiumTime().valueAsMinutes() 
											+ excesstime.calcOverTime().valueAsMinutes()
											+ excesstime.calcWorkHolidayTime().valueAsMinutes()
											+ temporaryMinutes
											+ flexTime);
		
		//????????????
		val actualTime = new AttendanceTime(withinStatutoryTimeOfDaily.getActualWorkTime().valueAsMinutes()
											+ withinStatutoryTimeOfDaily.getActualWithinPremiumTime().valueAsMinutes() 
											+ excesstime.calcOverTime().valueAsMinutes()
											+ excesstime.calcWorkHolidayTime().valueAsMinutes()
											+ temporaryMinutes
											+ flexTime);
		
		TotalWorkingTime returnTotalWorkingTimereturn = new TotalWorkingTime(
				totalWorkTime,
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
				shotrTime,
				vacationOfDaily,
				IntervalTimeOfDaily.empty());
		
		//???????????????????????????
		returnTotalWorkingTimereturn.vacationAddTime = calcHolidayAddTime(reGetClass, settingOfFlex);
		
		return returnTotalWorkingTimereturn;
	}
	
	/**
	 * ?????????????????????
	 * @param oneDay 1??????????????????
	 * @return ????????????
	 */
	private static int workCounter(CalculationRangeOfOneDay oneDay) {
		int workCount = 0;
		if(oneDay != null && oneDay.getAttendanceLeavingWork() != null) {
			//????????????????????????
			workCount = oneDay.getAttendanceLeavingWork().getTimeLeavingWorks().stream()
															   .filter(tc -> 
															   		tc.getAttendanceStamp() != null
															   	&&  tc.getAttendanceStamp().isPresent()
															   	&&  tc.getAttendanceStamp().get().getStamp() != null
															   	&&  tc.getAttendanceStamp().get().getStamp().isPresent()
															   	&&  tc.getAttendanceStamp().get().getStamp().get().getTimeDay().getTimeWithDay() != null
															   	&&  tc.getLeaveStamp() != null
															   	&&  tc.getLeaveStamp().isPresent()
															   	&&  tc.getLeaveStamp().get().getStamp() != null
															   	&&  tc.getLeaveStamp().get().getStamp().isPresent()
															   	&&  tc.getLeaveStamp().get().getStamp().get().getTimeDay().getTimeWithDay() != null
															   	&&  tc.getTimespan() != null
															   	&&  tc.getTimespan().lengthAsMinutes() > 0)
															   .collect(Collectors.toList()).size();
		}
		// ?????????????????????
		if (oneDay.getOutsideWorkTimeSheet().isPresent()){
			OutsideWorkTimeSheet outsideWorkTimeSheet = oneDay.getOutsideWorkTimeSheet().get();
			if (outsideWorkTimeSheet.getTemporaryTimeSheet().isPresent()){
				workCount += outsideWorkTimeSheet.getTemporaryTimeSheet().get().calcTemporaryTimes().v();
			}
		}
		return workCount;
	}

	public Optional<LeaveEarlyTimeOfDaily> getLeaveEarlyTimeNo(int no){
		return leaveEarlyTimeOfDaily.stream().filter(c -> c.getWorkNo().v() == no).findFirst();
	}

	/**
	 * ?????????????????????(????????????)???????????? 
	 * @param attendanceItemConverter 
	 * @return ????????????????????????????????????
	 */
	public List<EmployeeDailyPerError> checkOverTimeExcess(String employeeId,
														   GeneralDate targetDate,
														   SystemFixedErrorAlarm fixedErrorAlarmCode,
														   CheckExcessAtr checkAtr) {
		List<EmployeeDailyPerError> returnErrorItem = new ArrayList<>();
		AttendanceItemDictionaryForCalc attendanceItemDictionary = AttendanceItemDictionaryForCalc.setDictionaryValue(); 
		switch(checkAtr) {
			//????????????
			case OVER_TIME_EXCESS:
				if(this.getExcessOfStatutoryTimeOfDaily() != null) 
					returnErrorItem.addAll(this.getExcessOfStatutoryTimeOfDaily().checkOverTimeExcess(employeeId, targetDate,attendanceItemDictionary,new ErrorAlarmWorkRecordCode(fixedErrorAlarmCode.value)));
				break;
			//????????????
			case REST_TIME_EXCESS:
				if(this.getExcessOfStatutoryTimeOfDaily() != null)
					returnErrorItem.addAll(this.getExcessOfStatutoryTimeOfDaily().checkHolidayWorkTimeExcess(employeeId, targetDate, attendanceItemDictionary,new ErrorAlarmWorkRecordCode(fixedErrorAlarmCode.value)));
				break;
			//????????????
			case FLEX_OVER_TIME:
				if(this.getExcessOfStatutoryTimeOfDaily() != null) 
					returnErrorItem.addAll(this.getExcessOfStatutoryTimeOfDaily().checkFlexTimeExcess(employeeId, targetDate,"?????????????????????", attendanceItemDictionary,new ErrorAlarmWorkRecordCode(fixedErrorAlarmCode.value)));
				break;
			//????????????
			case MIDNIGHT_EXCESS:
				if(this.getWithinStatutoryTimeOfDaily() != null)
					returnErrorItem.addAll(this.getWithinStatutoryTimeOfDaily().checkWithinMidNightExcess(employeeId, targetDate,"???????????????",attendanceItemDictionary,new ErrorAlarmWorkRecordCode(fixedErrorAlarmCode.value)));
				if(this.getExcessOfStatutoryTimeOfDaily() != null)
					returnErrorItem.addAll(this.getExcessOfStatutoryTimeOfDaily().checkMidNightExcess(employeeId, targetDate,"???????????????", attendanceItemDictionary,new ErrorAlarmWorkRecordCode(fixedErrorAlarmCode.value)));
				break;
			//????????????????????????
			case PRE_OVERTIME_APP_EXCESS:
				if(this.getExcessOfStatutoryTimeOfDaily() != null) 
					returnErrorItem.addAll(this.getExcessOfStatutoryTimeOfDaily().checkPreOverTimeExcess(employeeId, targetDate, attendanceItemDictionary,new ErrorAlarmWorkRecordCode(fixedErrorAlarmCode.value)));
				break;
			//????????????????????????
			case PRE_HOLIDAYWORK_APP_EXCESS:
				if(this.getExcessOfStatutoryTimeOfDaily() != null)
					returnErrorItem.addAll(this.getExcessOfStatutoryTimeOfDaily().checkPreHolidayWorkTimeExcess(employeeId, targetDate,attendanceItemDictionary, new ErrorAlarmWorkRecordCode(fixedErrorAlarmCode.value)));
				break;
			//????????????????????????
			case PRE_FLEX_APP_EXCESS:
				if(this.getExcessOfStatutoryTimeOfDaily() != null) 
					returnErrorItem.addAll(this.getExcessOfStatutoryTimeOfDaily().checkPreFlexTimeExcess(employeeId, targetDate,"?????????????????????",attendanceItemDictionary, new ErrorAlarmWorkRecordCode(fixedErrorAlarmCode.value)));
				break;
			//????????????
			case PRE_MIDNIGHT_EXCESS:
				if(this.getExcessOfStatutoryTimeOfDaily() != null) 
					returnErrorItem.addAll(this.getExcessOfStatutoryTimeOfDaily().checkPreMidNightExcess(employeeId, targetDate,"???????????????",attendanceItemDictionary, new ErrorAlarmWorkRecordCode(fixedErrorAlarmCode.value)));
				break;
			//??????
			case LATE:
				if(!this.getLateTimeOfDaily().isEmpty())
					returnErrorItem.addAll(this.getLateTimeOfDaily().stream().map(tc -> tc.checkError(employeeId, targetDate,"????????????", attendanceItemDictionary, new ErrorAlarmWorkRecordCode(fixedErrorAlarmCode.value))).flatMap(tc -> tc.stream()).collect(Collectors.toList()));					
				break;
			//??????
			case LEAVE_EARLY:
				if(!this.getLeaveEarlyTimeOfDaily().isEmpty())
					returnErrorItem.addAll(this.getLeaveEarlyTimeOfDaily().stream().map(tc -> tc.checkError(employeeId, targetDate,"????????????", attendanceItemDictionary, new ErrorAlarmWorkRecordCode(fixedErrorAlarmCode.value))).flatMap(tc -> tc.stream()).collect(Collectors.toList()));;
				break;		   
			default :
				throw new RuntimeException("unknown error item Atr in DailyCalc:"+checkAtr);
		}
		return returnErrorItem;
	}
	
	public Optional<LateTimeOfDaily> getLateTimeNo(int no){
		return this.lateTimeOfDaily.stream().filter(l -> l.getWorkNo().v() == no).findFirst();
	}

	public Optional<OutingTimeOfDaily> getOutingTimeByReason(GoingOutReason reason){
		return getOutingTimeByReason(reason.value);
	}

	public Optional<OutingTimeOfDaily> getOutingTimeByReason(int reason){
		return this.outingTimeOfDailyPerformance.stream().filter(o -> o.getReason().value == reason).findFirst();
	}

	public TotalWorkingTime calcDiverGenceTime() {
		TotalWorkingTime result = new TotalWorkingTime(this.totalTime,
									this.totalCalcTime,
									this.actualTime,
									this.withinStatutoryTimeOfDaily!=null?this.withinStatutoryTimeOfDaily.calcDiverGenceTime():this.withinStatutoryTimeOfDaily,
									this.excessOfStatutoryTimeOfDaily!=null?this.excessOfStatutoryTimeOfDaily.calcDiverGenceTime():this.excessOfStatutoryTimeOfDaily,
									this.lateTimeOfDaily,
									this.leaveEarlyTimeOfDaily,
									this.breakTimeOfDaily,
									this.outingTimeOfDailyPerformance,
									this.raiseSalaryTimeOfDailyPerfor, this.workTimes, 
									this.shotrTimeOfDaily, this.holidayOfDaily,
									this.intervalTime);  
		result.vacationAddTime = this.vacationAddTime;
		return result;
	}


	/**
	 * ???????????????????????????(?????????????????????)
	 * @param recordReGetClass
	 * @param premiumAtr ????????????
	 * @return ??????????????????
	 */
	public AttendanceTime calcTotalDedTime(ManageReGetClass recordReGetClass, PremiumAtr premiumAtr) {
		//????????????
		AttendanceTime breakTime = BreakTimeOfDaily.calcTotalDeductBreakTime(
				recordReGetClass.getCalculationRangeOfOneDay(),
				DeductionAtr.Deduction).getTotalTime().getCalcTime();
		//??????
		AttendanceTime privateOutTime = OutingTotalTime.calcOutingTime(
				recordReGetClass.getCalculationRangeOfOneDay(),
				DeductionAtr.Deduction,
				GoingOutReason.PRIVATE,
				recordReGetClass.getGoOutCalc(),
				recordReGetClass.getIntegrationOfWorkTime().map(i -> i.getCommonSetting().getGoOutSet()), NotUseAtr.USE).getTotalTime().getCalcTime();
		AttendanceTime unionOutTime = OutingTotalTime.calcOutingTime(
				recordReGetClass.getCalculationRangeOfOneDay(),
				DeductionAtr.Deduction,
				GoingOutReason.UNION,
				recordReGetClass.getGoOutCalc(),
				recordReGetClass.getIntegrationOfWorkTime().map(i -> i.getCommonSetting().getGoOutSet()), NotUseAtr.USE).getTotalTime().getCalcTime();
		//?????????
		AttendanceTime shortWorkTime = ShortWorkTimeOfDaily.calcTotalShortWorkTime(
				recordReGetClass,
				DeductionAtr.Deduction,
				ShortWorkTimeOfDaily.getChildCareAttributeToDaily(recordReGetClass.getIntegrationOfDaily()),
				premiumAtr).getTotalTime().getCalcTime();
		
		AttendanceTime totalTime = AttendanceTime.ZERO;
		return totalTime
				.addMinutes(breakTime.valueAsMinutes())
				.addMinutes(privateOutTime.valueAsMinutes())
				.addMinutes(unionOutTime.valueAsMinutes())
				.addMinutes(shortWorkTime.valueAsMinutes());
	}
	
	
	public void calcTotalWorkingTimeForReCalc() {
		this.totalTime = recalcTotalWorkingTime();
	}
	/**
	 * ???????????????????????????????????????????????????????????????
	 * @return
	 */
	public AttendanceTime recalcTotalWorkingTime() {
		int withinTime = calcWithinTime();
		int overTime = calcOverTime();
		int holidayTime = calcHolidayTime();
		int rinzi = 0;
		if (this.getExcessOfStatutoryTimeOfDaily().getTemporaryTime().getTemporaryTime().size() > 0){
			rinzi = this.getExcessOfStatutoryTimeOfDaily().getTemporaryTime().getTemporaryTime().stream()
					.mapToInt(c -> c.getTemporaryTime().valueAsMinutes()).sum();
		}
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
	 * ?????????????????????
	 * ????????????????????????????????????????????????????????????????????????
	 * @return
	 */
	public int calcOverTime() {
		int removeFlexTime = 0;
		int flexTime = 0;
		int irregular = 0;
		if(this.excessOfStatutoryTimeOfDaily.getOverTimeWork().isPresent()) {
			removeFlexTime = calcOverTimeRemoveFlex();
			flexTime = this.getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().getFlexTime().getFlexTime().getTime().valueAsMinutes();
			flexTime = flexTime > 0 ? flexTime : 0;
			irregular = this.getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().getIrregularWithinPrescribedOverTimeWork().valueAsMinutes();
		}
		return removeFlexTime + flexTime + irregular;
	}
	
	public int calcOverTimeRemoveFlex() {
		int totalOverTime = 0;
		int totalTransTime = 0;
		if(this.getExcessOfStatutoryTimeOfDaily().getOverTimeWork().isPresent()) {
			totalOverTime = this.getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().getOverTimeWorkFrameTime()
				  				.stream()
				  				.filter(tc -> tc != null 
				  						&& tc.getOverTimeWork() != null
				  						&& tc.getOverTimeWork().getTime() != null)
				  				.map(tc -> tc.getOverTimeWork().getTime().valueAsMinutes())
				  				.collect(Collectors.summingInt(tc -> tc));
		
			totalTransTime = this.getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().getOverTimeWorkFrameTime()
				   				 .stream()
				   				 .filter(tc -> tc != null 
				   				 	&& tc.getTransferTime() != null
				   				 	&& tc.getTransferTime().getTime() != null)
				   				 .map(tc -> tc.getTransferTime().getTime().valueAsMinutes())
				   				 .collect(Collectors.summingInt(tc -> tc));
		}
		return totalOverTime + totalTransTime;
	}

	public void calcActualTimeForReCalc() {
		this.actualTime = recalcActualTime();
	}
	
	/**
	 * ????????????????????????(????????????)
	 * @return
	 */
	public AttendanceTime recalcActualTime() {
		//????????????
		int actualWorkTime = this.getWithinStatutoryTimeOfDaily().getActualWorkTime().v();
		int overTime = this.getExcessOfStatutoryTimeOfDaily().calcOverTime().v();
		int workHolidayTime = this.getExcessOfStatutoryTimeOfDaily().calcWorkHolidayTime().v();
		int flexOverTime = this.getExcessOfStatutoryTimeOfDaily().getOverTimeWork()
				.map(x -> x.getFlexTime().getFlexOverTime().v()).orElse(0);
		int irregularWithinPrescribedOverTime = this.getExcessOfStatutoryTimeOfDaily().getOverTimeWork()
				.map(x -> x.getIrregularWithinPrescribedOverTimeWork().v()).orElse(0);
		int withinPrescribedPremiumTime = this.getWithinStatutoryTimeOfDaily().getWithinPrescribedPremiumTime().v();
		// ????????????
		int temporaryMinutes = 0;
		if (this.excessOfStatutoryTimeOfDaily.getTemporaryTime().getTemporaryTime().size() > 0){
			temporaryMinutes = this.excessOfStatutoryTimeOfDaily.getTemporaryTime().getTemporaryTime().stream()
					.mapToInt(c -> c.getTemporaryTime().valueAsMinutes()).sum();
		}
		return new AttendanceTime(actualWorkTime + overTime + workHolidayTime + flexOverTime
				+ irregularWithinPrescribedOverTime + withinPrescribedPremiumTime + temporaryMinutes);
		//return this.getActualTime();
						 //+???????????????????????????????????????;
	}
	/**
	 * ????????????????????????
	 * ???????????????+?????????????????????????????????
	 * @return
	 */
	private int calcWithinTime() {
		if(this.getWithinStatutoryTimeOfDaily() != null) {
			int workTime = this.getWithinStatutoryTimeOfDaily().getWorkTime() != null
							?this.getWithinStatutoryTimeOfDaily().getWorkTime().valueAsMinutes()
							:0;
			int premiumTime = this.getWithinStatutoryTimeOfDaily().getWithinPrescribedPremiumTime() != null
							? this.getWithinStatutoryTimeOfDaily().getWithinPrescribedPremiumTime().valueAsMinutes()
							:0;
			return workTime + premiumTime;
		}
		else {
			return 0;
		}
	}
	
	/**
	 * ???????????????????????????????????????(?????????????????????????????????)
	 * @param upperTime ????????????
	 */
	public void controlUpperTime(AttendanceTime upperTime) {
		//?????????????????????
		this.controlUpperTimeForMidTime(upperTime);
		//?????????????????????
		this.raiseSalaryTimeOfDailyPerfor.controlUpperTimeForSalaryTime(upperTime);
	}
	
	/**
	 * ???????????????????????????????????????
	 * @param upperTime ????????????
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
	 * ??????????????????????????????????????????
	 * 
	 * @param fixRestTimeZoneSet
	 * @param fixWoSetting
	 * @param attendanceLeave
	 * @param workType
	 * @param attendanceTime
	 * @return
	 */
	public TotalWorkingTime reCalcLateLeave(Optional<WorkTimezoneCommonSet> workTimeZone,
			Optional<TimezoneOfFixedRestTimeSet> fixRestTimeZoneSet, List<EmTimeZoneSet> fixWoSetting,
			Optional<TimeLeavingOfDailyAttd> attendanceLeave, AttendanceTime actualPredTime, WorkType workType) {
		if (workType.getDailyWork().decisionNeedPredTime() != AttendanceHolidayAttr.FULL_TIME) {
			offSetRestTime(workTimeZone,fixRestTimeZoneSet,fixWoSetting,attendanceLeave);	
		}
		else {
			offSetUnUseBreakTime(workTimeZone, fixRestTimeZoneSet, fixWoSetting, attendanceLeave, actualPredTime);	
		}
		return this;
	}
	/**
	 * ???????????????(????????????)??????????????????????????????????????????
	 */
	private void offSetRestTime(Optional<WorkTimezoneCommonSet> workTimeZone,
			Optional<TimezoneOfFixedRestTimeSet> fixRestTimeZoneSet, List<EmTimeZoneSet> fixWoSetting,
			Optional<TimeLeavingOfDailyAttd> attendanceLeave) {		
		//????????????????????????????????????????????????
		if(!workTimeZone.isPresent()
		|| workTimeZone.get().getHolidayCalculation().getIsCalculate().isNotUse()) {
			return ;
		}

		AttendanceTime unBreakTime = new AttendanceTime(0);
		//???????????????????????????????????????????????????
		if(fixRestTimeZoneSet.isPresent()
		&& attendanceLeave != null
		&& attendanceLeave.isPresent()) {
			//??????????????????????????????
			unBreakTime = this.getBreakTimeOfDaily().calcUnUseBrekeTime(fixRestTimeZoneSet.get(), fixWoSetting, attendanceLeave.get());
		}

		
		
		
		//????????????????????????????????????
		TimeWithCalculation lateLeaveTotalTime = this.calcLateLeaveTotalTime();
		//???????????????????????????
		TimeWithCalculation absenteeismDeductionTime = this.calcAbsenteeismDeductionTime(lateLeaveTotalTime,unBreakTime);
		if(!this.lateTimeOfDaily.isEmpty()) {
			//??????NO?????????????????????
			this.lateTimeOfDaily.sort((c1, c2) -> c1.getWorkNo().compareTo(c2.getWorkNo()));
			//??????????????????????????????????????????
			this.lateTimeOfDaily.get(0).rePlaceLateTime(absenteeismDeductionTime);
		}
		
		if(!this.leaveEarlyTimeOfDaily.isEmpty()) {
			//??????NO?????????????????????
			this.leaveEarlyTimeOfDaily.sort((c1, c2) -> c1.getWorkNo().compareTo(c2.getWorkNo()));
			//????????????????????????
			this.leaveEarlyTimeOfDaily.get(0).rePlaceLeaveEarlyTime(TimeWithCalculation.sameTime(new AttendanceTime(0)));
		}
	}
	
	/**
	 * ???????????????(????????????)?????????????????????????????????????????????
	 */
	private void offSetUnUseBreakTime(Optional<WorkTimezoneCommonSet> workTimeZone, Optional<TimezoneOfFixedRestTimeSet> fixRestTimeZoneSet, List<EmTimeZoneSet> fixWoSetting, Optional<TimeLeavingOfDailyAttd> attendanceLeave,
									AttendanceTime actualPredTime) {
		AttendanceTime unBreakTime = new AttendanceTime(0);
		//???????????????????????????????????????????????????
		if(fixRestTimeZoneSet.isPresent()
		&& attendanceLeave != null
		&& attendanceLeave.isPresent()) {
			//??????????????????????????????
			unBreakTime = this.getBreakTimeOfDaily().calcUnUseBrekeTime(fixRestTimeZoneSet.get(), fixWoSetting, attendanceLeave.get());
		}
		
		int withinBreakTime = 0;
		//?????????????????????????????????????????????????????????
		if(fixRestTimeZoneSet.isPresent()) {
			for(DeductionTime breakTImeSheet : fixRestTimeZoneSet.get().getTimezones()) {
				//?????????????????????????????????????????????????????????stream
				withinBreakTime += fixWoSetting.stream().filter(tc -> tc.getTimezone().isOverlap(breakTImeSheet))
													  .map(tt -> tt.getTimezone().getDuplicatedWith(breakTImeSheet.timeSpan()).get().lengthAsMinutes())
													  .collect(Collectors.summingInt(ts -> ts));	
			}			
		}
		//??????????????????????????????
		final val withinUnUseBreakTime = this.getWithinStatutoryTimeOfDaily().calcUnUseWithinBreakTime(unBreakTime, actualPredTime, new AttendanceTime(withinBreakTime));
		if(withinUnUseBreakTime.greaterThan(0)) {
			final AttendanceTime restWithinUnUseBreakTime = minusLateTime(withinUnUseBreakTime);
			if(restWithinUnUseBreakTime.greaterThan(0)) {
				minusEarlyLeaveTime(restWithinUnUseBreakTime);
			}
		}
		
	}
	

	/**
	 * ???????????????(????????????)?????????????????????????????????????????????(????????????Ver)
	 * @return ???????????????????????????
	 */
	private AttendanceTime minusLateTime(AttendanceTime withinUnUseBreakTime) {
		AttendanceTime restOffSetTime = withinUnUseBreakTime;
		if(!this.lateTimeOfDaily.isEmpty()) {
			//??????NO?????????????????????
			this.lateTimeOfDaily.sort((c1, c2) -> c1.getWorkNo().compareTo(c2.getWorkNo()));
			for(LateTimeOfDaily ltOfDaily:this.lateTimeOfDaily) {
				val lateTime = ltOfDaily.getLateTime();
				//????????????????????????????????????????????????
				if(lateTime.getTime().greaterThan(restOffSetTime.valueAsMinutes())) {
					ltOfDaily.rePlaceLateTime(TimeWithCalculation.createTimeWithCalculation(lateTime.getTime().minusMinutes(restOffSetTime.valueAsMinutes()),
																							lateTime.getCalcTime()));
					restOffSetTime = new AttendanceTime(0);
					break;
				}
				//??????????????????????????????????????????????????????
				else {
					restOffSetTime = restOffSetTime.minusMinutes(lateTime.getTime().valueAsMinutes());
					ltOfDaily.rePlaceLateTime(TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(0),
																							lateTime.getCalcTime()));
				}
			}
		}
		return restOffSetTime;
	}

	/**
	 * ???????????????(????????????)?????????????????????????????????????????????(????????????Ver)
	 */
	private void minusEarlyLeaveTime(AttendanceTime restWithinUnUseBreakTime) {
		AttendanceTime restOffSetTime = restWithinUnUseBreakTime;
		if(!this.leaveEarlyTimeOfDaily.isEmpty()) {
			//??????NO?????????????????????
			this.leaveEarlyTimeOfDaily.sort((c1, c2) -> c1.getWorkNo().compareTo(c2.getWorkNo()));
			for(LeaveEarlyTimeOfDaily leOfDaily:this.leaveEarlyTimeOfDaily) {
				val leaveEarlyTime = leOfDaily.getLeaveEarlyTime();
				//????????????????????????????????????????????????
				if(leaveEarlyTime.getTime().greaterThan(restOffSetTime.valueAsMinutes())) {
					leOfDaily.rePlaceLeaveEarlyTime(TimeWithCalculation.createTimeWithCalculation(leaveEarlyTime.getTime().minusMinutes(restOffSetTime.valueAsMinutes()),
																								  leaveEarlyTime.getCalcTime()));
					restOffSetTime = new AttendanceTime(0);
					break;
				}
				//??????????????????????????????????????????????????????
				else {
					restOffSetTime = restOffSetTime.minusMinutes(leaveEarlyTime.getTime().valueAsMinutes());
					leOfDaily.rePlaceLeaveEarlyTime(TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(0),
																								  leaveEarlyTime.getCalcTime()));
				}
			}
		}
	}

	/**
	 * ????????????????????????????????????
	 * ??????????????????????????????????????????????????????????????????
	 * @return
	 */
	public TimeWithCalculation calcLateLeaveTotalTime() {
		
		AttendanceTime time = new AttendanceTime(0);
		AttendanceTime calcTime = new AttendanceTime(0);
		//??????????????????????????????????????????????????????????????????????????????
		if(!this.lateTimeOfDaily.isEmpty()) {
			List<LateTimeOfDaily> list = this.lateTimeOfDaily.stream().filter(l -> l.getWorkNo().equals(new WorkNo(1))).collect(Collectors.toList());
			if(!list.isEmpty()) {
				LateTimeOfDaily lateTimeOfDaily = list.get(0);
				time = time.addMinutes(lateTimeOfDaily.getLateTime().getTime().valueAsMinutes());
				calcTime = calcTime.addMinutes(lateTimeOfDaily.getLateTime().getCalcTime().valueAsMinutes());
			}
		}
		//??????????????????????????????????????????????????????????????????????????????
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
	 * ???????????????????????????
	 * @return
	 */
	public TimeWithCalculation calcAbsenteeismDeductionTime(TimeWithCalculation lateLeaveTotalTime,AttendanceTime unBreakTime) {

		//??????????????????????????????????????????????????????
		AttendanceTime time = lateLeaveTotalTime.getTime().minusMinutes(this.vacationAddTime.valueAsMinutes()).minusMinutes(unBreakTime.valueAsMinutes());
		AttendanceTime calcTime = lateLeaveTotalTime.getCalcTime().minusMinutes(this.vacationAddTime.valueAsMinutes()).minusMinutes(unBreakTime.valueAsMinutes());
		
		//0:00????????????0???00?????????
		if(time.valueAsMinutes()<0) {
			time = new AttendanceTime(0);
		}
		if(calcTime.valueAsMinutes()<0) {
			calcTime = new AttendanceTime(0);
		}
		
		return TimeWithCalculation.createTimeWithCalculation(time, calcTime);
	}
	
	/**
	 * ???????????????????????????
	 * @param recordClass ??????
	 * @param workType ????????????
	 * @param conditionItem ??????????????????
	 * @param flexCalcMethod ??????????????????????????????
	 * @param predetermineTimeSetByPersonInfo ???????????????????????????????????????
	 * @return ??????????????????
	 */
	private static AttendanceTime calcHolidayAddTime(
			ManageReGetClass recordClass,
			Optional<SettingOfFlexWork> flexCalcMethod) {
		
		int vacationAddTime = 0;		// ??????????????????
		
		// ????????????????????????????????????
		if (recordClass.getAddSetting().isAddVacation(PremiumAtr.RegularWork) == NotUseAtr.NOT_USE){
			return AttendanceTime.ZERO;
		}
		// ??????????????????
		ManagePerPersonDailySet personDailySet = recordClass.getPersonDailySetting();
		// ????????????
		if (!recordClass.getWorkType().isPresent()) return AttendanceTime.ZERO;
		WorkType workType = recordClass.getWorkType().get();
		// ??????????????????????????????
		Optional<WorkTimezoneCommonSet> commonSetting = Optional.empty();
		if (recordClass.getIntegrationOfWorkTime().isPresent()){
			IntegrationOfWorkTime integrationOfWorkTime = recordClass.getIntegrationOfWorkTime().get();
			commonSetting = Optional.of(integrationOfWorkTime.getCommonSetting());
		}
		// ?????????????????????????????????
		WithinWorkTimeSheet withinWorkTimeSheet =
				recordClass.getCalculationRangeOfOneDay().getWithinWorkingTimeSheet().get();
		// ????????????WORK?????????
		IntegrationOfDaily integrationOfDaily = recordClass.getIntegrationOfDaily();
		// ??????????????????????????????
		Optional<IntegrationOfWorkTime> integrationOfWorkTime = recordClass.getIntegrationOfWorkTime();
		// ??????????????????
		vacationAddTime += withinWorkTimeSheet.vacationAddProcess(
				personDailySet,
				integrationOfDaily,
				integrationOfWorkTime,
				PremiumAtr.RegularWork,
				workType,
				recordClass.getAddSetting(),
				recordClass.getHolidayAddtionSet().get(),
				flexCalcMethod,
				recordClass.getCalculationRangeOfOneDay().getPredetermineTimeSetForCalc(),
				recordClass.getDailyUnit(),
				NotUseAtr.NOT_USE).valueAsMinutes();
		// ????????????????????????????????????????????????????????????
		vacationAddTime += withinWorkTimeSheet.getTotalAddTimeByOffset(
				personDailySet,
				integrationOfDaily,
				integrationOfWorkTime,
				PremiumAtr.RegularWork,
				workType,
				recordClass.getAddSetting(),
				recordClass.getHolidayAddtionSet().get(),
				flexCalcMethod,
				recordClass.getCalculationRangeOfOneDay().getPredetermineTimeSetForCalc(),
				recordClass.getDailyUnit(),
				commonSetting,
				NotUseAtr.NOT_USE).valueAsMinutes();
		// ???????????????????????????
		return new AttendanceTime(vacationAddTime);
	}

	public TotalWorkingTime SpecialHolidayCalculationForOotsuka(ManageReGetClass recordClass) {
		// ??????????????????
		WorkingConditionItem conditionItem = recordClass.getPersonDailySetting().getPersonInfo();
		switch(conditionItem.getLaborSystem()) {
			case FLEX_TIME_WORK:
				AttendanceTimeOfExistMinus flexTime = this.excessOfStatutoryTimeOfDaily.getOverTimeWork().get().getFlexTime().getFlexTime().getTime();
				if(flexTime.lessThan(0))
					flexTime = AttendanceTimeOfExistMinus.ZERO;
				AttendanceTimeOfExistMinus calcFlexTime = this.excessOfStatutoryTimeOfDaily.getOverTimeWork().get().getFlexTime().getFlexTime().getCalcTime();
				if(calcFlexTime.lessThan(0))
					calcFlexTime = AttendanceTimeOfExistMinus.ZERO;
				AttendanceTime actualWorkTime = new AttendanceTime(0);
				if(!recordClass.getPredSetForOOtsuka().isPresent())
					break;
				for(TimezoneUse timeZone : recordClass.getPredSetForOOtsuka().get().getTimeSheets()) {
					if(timeZone.isUsed())
					{
						for(WithinWorkTimeFrame copyItem: recordClass.getCalculationRangeOfOneDay().getWithinWorkingTimeSheet().get().getWithinWorkTimeFrame()) {
							TimeWithDayAttr start = copyItem.getTimeSheet().getStart();
							TimeWithDayAttr end = copyItem.getTimeSheet().getEnd();
							if(timeZone.getDuplicatedWith(copyItem.getTimeSheet().getTimeSpan()).isPresent()) {
								if(copyItem.getTimeSheet().getStart().lessThan(timeZone.getStart())) {
									start = timeZone.getStart();
								}
								if(copyItem.getTimeSheet().getEnd().greaterThan(timeZone.getEnd())) {
									end = timeZone.getEnd();
								}
							}
							else {
								end = start;
							}
							copyItem.shiftTimeSheet(new TimeSpanForDailyCalc(start, end));
							actualWorkTime = new AttendanceTime(actualWorkTime.v() + copyItem.calcTotalTime().valueAsMinutes());
						}
					}
					
				}
		
				
				this.withinStatutoryTimeOfDaily.setActualWorkTime(actualWorkTime);
				if(this.excessOfStatutoryTimeOfDaily.getOverTimeWork().isPresent()) {
					int ootsukaFlex = (flexTime.valueAsMinutes() - actualWorkTime.valueAsMinutes()) < 0 ? 0 : flexTime.valueAsMinutes() - actualWorkTime.valueAsMinutes();
					this.excessOfStatutoryTimeOfDaily.getOverTimeWork().get().getFlexTime().setOnlyFlexTime(new AttendanceTimeOfExistMinus(ootsukaFlex));
				}
				
				break;
			case REGULAR_WORK:
				//?????????????????????????????????????????????
				AttendanceTime workActualTime = this.withinStatutoryTimeOfDaily.getActualWorkTime();
				AttendanceTime workTime = this.withinStatutoryTimeOfDaily.getWorkTime();
				/*workTime - workActualTime < 0*/
				AttendanceTime difference = workTime.minusMinutes(workActualTime.valueAsMinutes());
				if(workTime.lessThan(workActualTime))
					workTime = new AttendanceTime(0);
				this.withinStatutoryTimeOfDaily.setWorkTime(difference);
			
				
				AttendanceTime excessRow = this.totalCalcTime.minusMinutes(recordClass.getDailyUnit().getDailyTime().valueAsMinutes());
				//?????????????????????
				if(this.excessOfStatutoryTimeOfDaily.getOverTimeWork().isPresent()) {
					final AttendanceTime withinRow = new AttendanceTime(this.excessOfStatutoryTimeOfDaily.getOverTimeWork().get().calcTotalFrameTime().v()
											   					  + this.excessOfStatutoryTimeOfDaily.getOverTimeWork().get().calcTransTotalFrameTime().v()
											   					  - excessRow.valueAsMinutes());
					//?????????????????????????????????????????????
					this.excessOfStatutoryTimeOfDaily.getOverTimeWork().ifPresent(tc ->{
						tc.transWithinOverTimeForOOtsukaSpecialHoliday(recordClass.getOverTimeSheetSetting(),withinRow);
					});
				}
				break;
			default:
				break;
		}
		return this;
	}
	
	//????????????IW????????????
	private static boolean decisionIWOOtsukaMode(WorkType workType, Optional<WorkTimeCode> workTimeCode,
			ManageReGetClass recordReget) {
		if (!AppContexts.optionLicense().customize().ootsuka()) return false;
		if(!workTimeCode.isPresent()) return false;
		if(!(workTimeCode.get().v().equals("100") || workTimeCode.get().v().equals("101")))
			return false;
		if(workType.getWorkTypeCode().v().equals("100")) 
			return false;
		
		switch(workType.getDailyWork().decisionNeedPredTime()) {
		case AFTERNOON:
			if(workType.getDailyWork().getAfternoon().isHolidayWork()) {
				return false;
			}
			//??????or?????????????????????????????????
			else {
				return true;
			}
		case FULL_TIME:
			if(workType.getDailyWork().getOneDay().isHolidayWork()) {
				return false;
			}
			//??????or?????????????????????????????????
			else {
				return true;
			}
		case MORNING:
			if(workType.getDailyWork().getMorning().isHolidayWork()) {
				return false;
			}
			//??????or?????????????????????????????????
			else {
				return true;
			}
		case HOLIDAY:
			return false;
		default:
			throw new RuntimeException("unkwon pred need workType in IW Decision");
		}
	}

	/** ????????????????????????????????? */
	public AttendanceTime getTotalTimeSpecialVacation(int spcNo) {
		
		/** @???????????? */
		val late = this.lateTimeOfDaily.stream().filter(c -> c.getTimePaidUseTime().getSpecialHolidayFrameNo().map(n -> n.v()).orElse(0) == spcNo)
				.mapToInt(c -> c.getTimePaidUseTime().getTimeSpecialHolidayUseTime().valueAsMinutes()).sum();
		
		/** @???????????? */
		val leaveEarly = this.leaveEarlyTimeOfDaily.stream().filter(c -> c.getTimePaidUseTime().getSpecialHolidayFrameNo().map(n -> n.v()).orElse(0) == spcNo)
				.mapToInt(c -> c.getTimePaidUseTime().getTimeSpecialHolidayUseTime().valueAsMinutes()).sum();
		
		/** @???????????? */
		val outing = this.outingTimeOfDailyPerformance.stream().filter(c -> c.getTimeVacationUseOfDaily().getSpecialHolidayFrameNo().map(n -> n.v()).orElse(0) == spcNo)
				.mapToInt(c -> c.getTimeVacationUseOfDaily().getTimeSpecialHolidayUseTime().valueAsMinutes()).sum();
		
		/** return $???????????? */
		return new AttendanceTime(late + leaveEarly + outing);
	}

	public TotalWorkingTime(AttendanceTime totalTime, AttendanceTime totalCalcTime, AttendanceTime actualTime,
			WithinStatutoryTimeOfDaily withinStatutoryTimeOfDaily,
			ExcessOfStatutoryTimeOfDaily excessOfStatutoryTimeOfDaily, List<LateTimeOfDaily> lateTimeOfDaily,
			List<LeaveEarlyTimeOfDaily> leaveEarlyTimeOfDaily, BreakTimeOfDaily breakTimeOfDaily,
			List<OutingTimeOfDaily> outingTimeOfDailyPerformance,
			RaiseSalaryTimeOfDailyPerfor raiseSalaryTimeOfDailyPerfor, WorkTimes workTimes,
			ShortWorkTimeOfDaily shotrTimeOfDaily, HolidayOfDaily holidayOfDaily,
			AttendanceTime vacationAddTime, IntervalTimeOfDaily intervalTime) {
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
		this.shotrTimeOfDaily = shotrTimeOfDaily;
		this.holidayOfDaily = holidayOfDaily;
		this.vacationAddTime = vacationAddTime;
		this.intervalTime = intervalTime;
	}
	
	public AttendanceTime getWorkHolidayTime() {
		return this.getExcessOfStatutoryTimeOfDaily().calcWorkHolidayTime();
	}
	public AttendanceTime getOverTime() {
		return this.getExcessOfStatutoryTimeOfDaily().calcOverTime();
	}
	
}
package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.record.dom.actualworkinghours.ActualWorkingTimeOfDaily;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.adapter.personnelcostsetting.PersonnelCostSettingAdapter;
import nts.uk.ctx.at.record.dom.attendanceitem.util.AttendanceItemConvertFactory;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeSheet;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeSheet;
import nts.uk.ctx.at.record.dom.breakorgoout.enums.GoingOutReason;
import nts.uk.ctx.at.record.dom.breakorgoout.primitivevalue.OutingFrameNo;
import nts.uk.ctx.at.record.dom.calculationattribute.AutoCalcSetOfDivergenceTime;
import nts.uk.ctx.at.record.dom.calculationattribute.BonusPayAutoCalcSet;
import nts.uk.ctx.at.record.dom.calculationattribute.CalAttrOfDailyPerformance;
import nts.uk.ctx.at.record.dom.calculationattribute.HolidayTimesheetCalculationSetting;
import nts.uk.ctx.at.record.dom.calculationattribute.OvertimeTimesheetCalculationSetting;
import nts.uk.ctx.at.record.dom.calculationattribute.WorkingTimesheetCalculationSetting;
import nts.uk.ctx.at.record.dom.calculationattribute.enums.DivergenceTimeAttr;
import nts.uk.ctx.at.record.dom.calculationattribute.enums.LeaveAttr;
import nts.uk.ctx.at.record.dom.daily.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.record.dom.daily.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.AttendanceLeavingGateOfDaily;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.PCLogOnInfoOfDaily;
import nts.uk.ctx.at.record.dom.daily.midnight.MidNightTimeSheet;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemValueOfDaily;
import nts.uk.ctx.at.record.dom.daily.vacationusetime.AbsenceOfDaily;
import nts.uk.ctx.at.record.dom.daily.vacationusetime.AnnualOfDaily;
import nts.uk.ctx.at.record.dom.daily.vacationusetime.HolidayOfDaily;
import nts.uk.ctx.at.record.dom.daily.vacationusetime.OverSalaryOfDaily;
import nts.uk.ctx.at.record.dom.daily.vacationusetime.SpecialHolidayOfDaily;
import nts.uk.ctx.at.record.dom.daily.vacationusetime.SubstituteHolidayOfDaily;
import nts.uk.ctx.at.record.dom.daily.vacationusetime.TimeDigestOfDaily;
import nts.uk.ctx.at.record.dom.daily.vacationusetime.YearlyReservedOfDaily;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.ReflectBreakTimeOfDailyDomainService;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.converter.CalcDefaultValue;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.errorcheck.CalculationErrorCheckService;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.ootsuka.OotsukaProcessService;
//import nts.uk.ctx.at.record.dom.dailyprocess.calc.ootsuka.OotsukaProcessService;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceTime;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeRepository;
import nts.uk.ctx.at.record.dom.divergencetime.service.MasterShareBus;
import nts.uk.ctx.at.record.dom.divergencetime.service.MasterShareBus.MasterShareContainer;
import nts.uk.ctx.at.record.dom.optitem.OptionalItem;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemRepository;
import nts.uk.ctx.at.record.dom.optitem.applicable.EmpCondition;
import nts.uk.ctx.at.record.dom.optitem.applicable.EmpConditionRepository;
import nts.uk.ctx.at.record.dom.optitem.calculation.Formula;
import nts.uk.ctx.at.record.dom.optitem.calculation.FormulaRepository;
import nts.uk.ctx.at.record.dom.premiumtime.PremiumTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.raborstandardact.FlexCalcMethod;
import nts.uk.ctx.at.record.dom.raborstandardact.FlexCalcMethodOfEachPremiumHalfWork;
import nts.uk.ctx.at.record.dom.raborstandardact.FlexCalcMethodOfHalfWork;
import nts.uk.ctx.at.record.dom.raborstandardact.flex.SettingOfFlexWork;
import nts.uk.ctx.at.record.dom.statutoryworkinghours.DailyStatutoryWorkingHours;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationCD;
import nts.uk.ctx.at.record.dom.workrule.specific.CalculateOfTotalConstraintTime;
import nts.uk.ctx.at.record.dom.workrule.specific.LimitControlOfTotalWorkingSet;
import nts.uk.ctx.at.record.dom.workrule.specific.SpecificWorkRuleRepository;
import nts.uk.ctx.at.record.dom.workrule.specific.UpperLimitTotalWorkingHour;
import nts.uk.ctx.at.record.dom.worktime.TimeActualStamp;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
import nts.uk.ctx.at.record.dom.worktime.enums.StampSourceInfo;
import nts.uk.ctx.at.record.dom.worktime.primitivevalue.WorkTimes;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.BonusPaySetting;
import nts.uk.ctx.at.shared.dom.calculation.holiday.HolidayAddtionRepository;
import nts.uk.ctx.at.shared.dom.calculation.holiday.HolidayAddtionSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.HourlyPaymentAdditionSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.WorkDeformedLaborAdditionSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.WorkFlexAdditionSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.WorkRegularAdditionSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.kmk013_splitdomain.HolidayCalcMethodSet;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalAtrOvertime;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalFlexOvertimeSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalOvertimeSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalRestTimeSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalcOfLeaveEarlySetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.TimeLimitUpperLimitSetting;
import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveComSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryOccurrenceSetting;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalRaisingSalarySetting;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.OverDayEndAggregateFrameSet;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.OverDayEndCalcSet;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.OverDayEndCalcSetOfExcessHoliday;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.OverDayEndCalcSetOfExcessSpecialHoliday;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.OverDayEndCalcSetOfStatutoryHoliday;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.OverDayEndCalcSetOfWeekDay;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;
import nts.uk.ctx.at.shared.dom.workrule.overtime.StatutoryPrioritySet;
import nts.uk.ctx.at.shared.dom.workrule.statutoryworktime.DailyCalculationPersonalInformation;
import nts.uk.ctx.at.shared.dom.workrule.statutoryworktime.GetOfStatutoryWorkTime;
import nts.uk.ctx.at.shared.dom.workrule.waytowork.PersonalLaborCondition;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.CommonRestSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.JustCorrectionAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.LateEarlyAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.LegalOTSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.OverTimeOfTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.RestClockManageAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.RestTimeOfficeWorkCalcMethod;
import nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneOtherSubHolTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixRestTimezoneSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkCalcSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flexset.CoreTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowFixedRestCalcMethod;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowFixedRestSet;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.predset.BreakDownTimeDay;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetermineTime;
import nts.uk.ctx.at.shared.dom.worktime.predset.PrescribedTimezoneSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDailyAtr;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Stateless
public class CalculateDailyRecordServiceImpl implements CalculateDailyRecordService{

	@Inject
	private WorkTypeRepository workTypeRepository;
	@Inject
	private PredetemineTimeSettingRepository predetemineTimeSetRepository;
	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;
	@Inject
	private GetOfStatutoryWorkTime getOfStatutoryWorkTime;
	@Inject
	private FixedWorkSettingRepository fixedWorkSettingRepository;
	@Inject
	private FlowWorkSettingRepository flowWorkSettingRepository;
	@Inject
	private DiffTimeWorkSettingRepository diffTimeWorkSettingRepository;
	@Inject
	private FlexWorkSettingRepository flexWorkSettingRepository;
	@Inject
	private SpecificWorkRuleRepository specificWorkRuleRepository;

	
	@Inject
	private AttendanceItemConvertFactory attendanceItemConvertFactory;

	@Inject
	private OotsukaProcessService ootsukaProcessService;
	
	@Inject
	private CalculationErrorCheckService calculationErrorCheckService;

	@Inject
	private ReflectBreakTimeOfDailyDomainService reflectBreakTimeOfDailyDomainService;
	

	//任意項目の計算の為に追加
	@Inject
	private ShareEmploymentAdapter shareEmploymentAdapter;
	
	//割増計算用に追加
	@Inject
	private PersonnelCostSettingAdapter personnelCostSettingAdapter;
	
	/**
	 * 勤務情報を取得して計算
	 * @return 日別実績(Work)
	 */
	@Override
	public IntegrationOfDaily calculate(IntegrationOfDaily integrationOfDaily, ManagePerCompanySet companyCommonSetting
										,Optional<WorkInfoOfDailyPerformance> yesterDayInfo
										,Optional<WorkInfoOfDailyPerformance> tomorrowDayInfo) {
		
		DailyRecordToAttendanceItemConverter converter = attendanceItemConvertFactory.createDailyConverter();
		
		if (integrationOfDaily.getAffiliationInfor() == null || companyCommonSetting == null)
			return integrationOfDaily;
		boolean isShareContainerNotInit = companyCommonSetting.getShareContainer() == null;
		if(isShareContainerNotInit) {
			companyCommonSetting.setShareContainer(MasterShareBus.open());
		}
		// 実績データの計算
		val afterCalcResult = this.calcDailyAttendancePerformance(integrationOfDaily,companyCommonSetting, converter,yesterDayInfo,tomorrowDayInfo);
		//任意項目の計算
		val aftercalcOptionalItemResult = this.calcOptionalItem(afterCalcResult,converter,companyCommonSetting);
		//エラーチェック
//		return calculationErrorCheckService.errorCheck(afterCalcResult,companyCommonSetting);
		IntegrationOfDaily result = calculationErrorCheckService.errorCheck(aftercalcOptionalItemResult,companyCommonSetting);
		
		if(isShareContainerNotInit) {
			companyCommonSetting.getShareContainer().clearAll();
		}
		
		return result;
	}

	private IntegrationOfDaily calcDailyAttendancePerformance(IntegrationOfDaily integrationOfDaily,
															  ManagePerCompanySet companyCommonSetting, 
															  DailyRecordToAttendanceItemConverter converter, Optional<WorkInfoOfDailyPerformance> yesterDayInfo, Optional<WorkInfoOfDailyPerformance> tomorrowDayInfo) {
		
		val copyCalcAtr = integrationOfDaily.getCalAttr();
		//予定の時間帯
		val schedule = createSchedule(integrationOfDaily,companyCommonSetting,converter,yesterDayInfo,tomorrowDayInfo);
		//実績の時間帯
		val record = createRecord(integrationOfDaily,TimeSheetAtr.RECORD,companyCommonSetting,yesterDayInfo,tomorrowDayInfo);
		schedule.setCompanyCommonSetting(companyCommonSetting);
		record.setCompanyCommonSetting(companyCommonSetting);
		//実績が入力されていなくてもor実績側が休日でも、予定時間は計算する必要があるため
		if (!record.getCalculatable() && (!record.getWorkType().isPresent())) {
			integrationOfDaily.setCalAttr(copyCalcAtr);
			return integrationOfDaily;
		}
		val test = calcRecord(record,schedule, companyCommonSetting, converter);
		test.setCalAttr(copyCalcAtr);
		return test;
	}

	/**
	 * 実績データから時間帯の作成
	 * 
	 * @param companyId
	 *            会社ID
	 * @param employeeId
	 *            社員コード
	 * @param targetDate
	 *            対象日
	 * @param integrationOfDaily
	 * @param companyCommonSetting 
	 * @param tomorrowDayInfo 
	 * @param yesterDayInfo 
	 */
	private ManageReGetClass createRecord(IntegrationOfDaily integrationOfDaily,TimeSheetAtr timeSheetAtr, ManagePerCompanySet companyCommonSetting, Optional<WorkInfoOfDailyPerformance> yesterDayInfo, Optional<WorkInfoOfDailyPerformance> tomorrowDayInfo) {

		
		
		MasterShareContainer shareContainer = companyCommonSetting.getShareContainer();
		
		
		
		String companyId = AppContexts.user().companyId();
		String employeeId = integrationOfDaily.getAffiliationInfor().getEmployeeId();
		GeneralDate targetDate = integrationOfDaily.getAffiliationInfor().getYmd(); 
		
		/* 勤務種類の取得 */
		val workInfo = integrationOfDaily.getWorkInformation();
		Optional<WorkType> workType = this.workTypeRepository.findByPK(companyId,workInfo.getRecordInfo().getWorkTypeCode().v()); // 要確認：勤務種類マスタが削除されている場合は考慮しない？
	
		///ここのnullは治す　→　直さないと絶対落ちる
		if(!workType.isPresent()) return ManageReGetClass.cantCalc(Optional.empty(),integrationOfDaily,null);
		val beforeWorkType = workType;
		
		/* 労働制 */
		DailyCalculationPersonalInformation personalInfo = getPersonInfomation(integrationOfDaily, companyCommonSetting);
		if(personalInfo==null) return ManageReGetClass.cantCalc(workType,integrationOfDaily,personalInfo); 
		
		/*各加算設定取得用*/
		Map<String, AggregateRoot> map = companyCommonSetting.getHolidayAddition();
		
		/*各加算設定取得用*/
		AggregateRoot workRegularAdditionSet = map.get("regularWork");
		AggregateRoot workFlexAdditionSet = map.get("flexWork");
		AggregateRoot hourlyPaymentAdditionSet =  map.get("hourlyPaymentAdditionSet");
		AggregateRoot workDeformedLaborAdditionSet =  map.get("irregularWork");
		
		//通常勤務の加算設定
		WorkRegularAdditionSet regularAddSetting = workRegularAdditionSet!=null?(WorkRegularAdditionSet)workRegularAdditionSet:null;
		//フレックス勤務の加算設定
		WorkFlexAdditionSet flexAddSetting = workFlexAdditionSet!=null?(WorkFlexAdditionSet)workFlexAdditionSet:null;
		//変形労働勤務の加算設定
		WorkDeformedLaborAdditionSet illegularAddSetting = workDeformedLaborAdditionSet!=null?(WorkDeformedLaborAdditionSet)workDeformedLaborAdditionSet:null;
		//時給者の加算設定
		HourlyPaymentAdditionSet hourlyPaymentAddSetting = hourlyPaymentAdditionSet!=null?(HourlyPaymentAdditionSet)hourlyPaymentAdditionSet:null;
		
		HolidayCalcMethodSet holidayCalcMethodSet = HolidayCalcMethodSet.emptyHolidayCalcMethodSet();
		
		if(personalInfo.getWorkingSystem().isFlexTimeWork()) {
			//フレックス勤務の加算設定.休暇の計算方法の設定
			holidayCalcMethodSet = flexAddSetting!=null?flexAddSetting.getVacationCalcMethodSet():holidayCalcMethodSet;
		}
		else if(personalInfo.getWorkingSystem().isRegularWork()) {
			//通常勤務の加算設定.休暇の計算方法の設定
			holidayCalcMethodSet = regularAddSetting!=null?regularAddSetting.getVacationCalcMethodSet():holidayCalcMethodSet;
		}
			
		/*就業時間帯勤務区分*/
		//1日休日の場合、就業時間帯コードはnullであるので、
		//all0を計算させるため(実績が計算できなくても、予定時間を計算する必要がある
		if(workInfo == null || workInfo.getRecordInfo() == null || workInfo.getRecordInfo().getWorkTimeCode() == null)
			return ManageReGetClass.cantCalc2(workType,integrationOfDaily,personalInfo,holidayCalcMethodSet,regularAddSetting,flexAddSetting,hourlyPaymentAddSetting,illegularAddSetting);

		
		Optional<WorkTimeSetting> workTime = workTimeSettingRepository.findByCode(companyId,workInfo.getRecordInfo().getWorkTimeCode().toString());
		if(!workTime.isPresent()) return ManageReGetClass.cantCalc2(workType,integrationOfDaily,personalInfo,holidayCalcMethodSet,regularAddSetting,flexAddSetting,hourlyPaymentAddSetting,illegularAddSetting);
		
		//就業時間帯の共通設定
		Optional<WorkTimezoneCommonSet> commonSet = getWorkTimezoneCommonSet(integrationOfDaily,companyId,workTime,shareContainer);
		
		/* 1日の計算範囲クラスを作成 */
		val oneRange = createOneDayRange(integrationOfDaily,commonSet);

		/**
		 * 勤務種類が休日系なら、所定時間の時間を変更する
		 */
		if (workType.get().getDecisionAttendanceHolidayAttr()) {
			oneRange.getPredetermineTimeSetForCalc().endTimeSetStartTime();
			
		}
		
		/*法定労働時間(日単位)*/
		val dailyUnit = companyCommonSetting.getDailyUnit();
		
		/*休憩時間帯（遅刻早退用）*/
		 List<TimeSheetOfDeductionItem> breakTimeList = new ArrayList<>();
		 Optional<BreakTimeOfDailyPerformance> test = reflectBreakTimeOfDailyDomainService.getBreakTime(companyId, employeeId, targetDate,integrationOfDaily.getWorkInformation());
		if(test != null) {
			if(test.isPresent()) {
				breakTimeList = test.get().changeAllTimeSheetToDeductionItem();
			}
		}
		else {
			test = Optional.empty();
		}
		

	

		
		//---------------------------------Repositoryが整理されるまでの一時的な作成-------------------------------------------
		//休憩時間帯(BreakManagement)
		List<BreakTimeSheet> breakTimeSheet = new ArrayList<>();
		List<BreakTimeOfDailyPerformance> breakTimeOfDailyList = new ArrayList<>();
		//休憩回数
		int breakCount = 0;
		if(!integrationOfDaily.getBreakTime().isEmpty()) {
			Optional<BreakTimeOfDailyPerformance> breakTimeByBreakType = integrationOfDaily.getBreakTime().stream()
																		.filter(tc -> tc.getBreakType().isUse(timeSheetAtr))
																		.findFirst();
			breakTimeByBreakType.ifPresent(tc -> breakTimeSheet.addAll(tc.getBreakTimeSheets()));
			
			breakCount = breakTimeSheet.stream()
									   .filter(tc -> (tc.getStartTime() != null && tc.getEndTime()!=null && tc.getEndTime().greaterThan(tc.getStartTime())))
									   .collect(Collectors.toList())
									   .size();
		}
		
		breakTimeOfDailyList.add(new BreakTimeOfDailyPerformance(employeeId, 
																 timeSheetAtr.decisionBreakTime(), 
																 breakTimeSheet, 
																 targetDate));

		//外出時間帯
		WorkStamp goOut = new WorkStamp(new TimeWithDayAttr(780),new TimeWithDayAttr(780),new WorkLocationCD("01"), StampSourceInfo.CORRECTION_RECORD_SET);
		WorkStamp back  = new WorkStamp(new TimeWithDayAttr(780),new TimeWithDayAttr(780),new WorkLocationCD("01"), StampSourceInfo.CORRECTION_RECORD_SET);
		List<OutingTimeSheet> outingTimeSheets = new ArrayList<>();
		outingTimeSheets.add(new OutingTimeSheet(new OutingFrameNo(1),
												  Optional.of(new TimeActualStamp(goOut,goOut,1)),
												  new AttendanceTime(0),
												  new AttendanceTime(60),
												  GoingOutReason.PUBLIC,
												  Optional.of(new TimeActualStamp(back, back, 1))
												 ));
		OutingTimeOfDailyPerformance goOutTimeSheetList = new OutingTimeOfDailyPerformance(employeeId,targetDate,outingTimeSheets);
		
		MidNightTimeSheet midNightTimeSheet = new MidNightTimeSheet(companyId, 
																	new TimeWithDayAttr(1320),
																	new TimeWithDayAttr(1740));
		
		//流動勤務の休憩時間帯
		FlowWorkRestTimezone fluRestTime = new FlowWorkRestTimezone(
												  true,
												  new TimezoneOfFixedRestTimeSet(Collections.emptyList()),
												  new FlowRestTimezone(Collections.emptyList(),false,new FlowRestSetting(new AttendanceTime(0), new AttendanceTime(0)))
												  );
		
		//流動固定休憩設定
		FlowFixedRestSet fluidPrefixBreakTimeSet = new FlowFixedRestSet(false,false,false,FlowFixedRestCalcMethod.REFER_MASTER);
		
		//0時跨ぎ計算設定
		OverDayEndCalcSet overDayEndCalcSet = new OverDayEndCalcSet(companyId,
																	UseAtr.NOTUSE,
																	new OverDayEndAggregateFrameSet(Collections.emptyList(),Collections.emptyList(),Collections.emptyList()),
																	new OverDayEndCalcSetOfStatutoryHoliday(UseAtr.NOTUSE,UseAtr.NOTUSE,UseAtr.NOTUSE),
																	new OverDayEndCalcSetOfExcessHoliday(UseAtr.NOTUSE,UseAtr.NOTUSE,UseAtr.NOTUSE),
																	new OverDayEndCalcSetOfExcessSpecialHoliday(UseAtr.NOTUSE,UseAtr.NOTUSE,UseAtr.NOTUSE),
																	new OverDayEndCalcSetOfWeekDay(UseAtr.NOTUSE,UseAtr.NOTUSE,UseAtr.NOTUSE));
		
//		//日別実績の計算区分
		if(integrationOfDaily.getCalAttr() == null
				|| (integrationOfDaily.getCalAttr().getRasingSalarySetting() == null)
				|| (integrationOfDaily.getCalAttr().getOvertimeSetting() == null)
				|| (integrationOfDaily.getCalAttr().getLeaveEarlySetting() == null)
				|| (integrationOfDaily.getCalAttr().getHolidayTimeSetting() == null)
				|| (integrationOfDaily.getCalAttr().getFlexExcessTime() == null)
				|| (integrationOfDaily.getCalAttr().getDivergenceTime() == null))
		{
			val autoCalcSet = new AutoCalSetting(TimeLimitUpperLimitSetting.NOUPPERLIMIT,AutoCalAtrOvertime.CALCULATEMBOSS);
			val calAttr = new CalAttrOfDailyPerformance(employeeId, 
													targetDate,
													new AutoCalFlexOvertimeSetting(autoCalcSet),
													new AutoCalRaisingSalarySetting(true,true),
													new AutoCalRestTimeSetting(autoCalcSet,autoCalcSet),
													new AutoCalOvertimeSetting(autoCalcSet, 
																			   autoCalcSet, 
																			   autoCalcSet, 
																			   autoCalcSet, 
																			   autoCalcSet, 
																			   autoCalcSet),
													new AutoCalcOfLeaveEarlySetting(true, true),
													new AutoCalcSetOfDivergenceTime(DivergenceTimeAttr.USE));
			integrationOfDaily.setCalAttr(calAttr);
		}	
		
		//自動計算設定
		CalAttrOfDailyPerformance calcSetinIntegre = integrationOfDaily.getCalAttr();
		
		
		List<WorkTimezoneOtherSubHolTimeSet> subhol = new ArrayList<>();
		List<OverTimeFrameNo> statutoryOverFrameNoList = new ArrayList<>();
		
		Optional<FixRestTimezoneSet>fixRestTimeSet = Optional.empty();
		Optional<FixedWorkCalcSetting>ootsukaFixedWorkSet = Optional.empty();
		
		Optional<FlexWorkSetting> flexWorkSetOpt = shareContainer.getShared("FLEX_WORK" + companyId + workInfo.getRecordInfo().getWorkTimeCode().v(), 
				() -> flexWorkSettingRepository.find(companyId,workInfo.getRecordInfo().getWorkTimeCode().v()));
		if(timeSheetAtr.isSchedule()) {
			flexWorkSetOpt = shareContainer.getShared("PRE_FLEX_WORK" + companyId + workInfo.getRecordInfo().getWorkTimeCode().v(), 
					() -> flexWorkSettingRepository.find(companyId,workInfo.getRecordInfo().getWorkTimeCode().v()));
		}
		
		//---------------------------------Repositoryが整理されるまでの一時的な作成-------------------------------------------
			
		//休暇加算時間設定
		Optional<HolidayAddtionSet> holidayAddtionSetting = companyCommonSetting.getHolidayAdditionPerCompany();
		if(!holidayAddtionSetting.isPresent()) {
			throw new BusinessException(new RawErrorMessage("休暇加算時間設定が存在しません"));
		}
		HolidayAddtionSet holidayAddtionSet = holidayAddtionSetting.get();
		
		// 休暇クラス
		VacationClass vacation = new VacationClass(new HolidayOfDaily(new AbsenceOfDaily(new AttendanceTime(0)),
				new TimeDigestOfDaily(new AttendanceTime(0), new AttendanceTime(0)),
				new YearlyReservedOfDaily(new AttendanceTime(0)),
				new SubstituteHolidayOfDaily(new AttendanceTime(0), new AttendanceTime(0)),
				new OverSalaryOfDaily(new AttendanceTime(0), new AttendanceTime(0)),
				new SpecialHolidayOfDaily(new AttendanceTime(0), new AttendanceTime(0)),
				new AnnualOfDaily(new AttendanceTime(0), new AttendanceTime(0))));
		
		
		if (workTime.get().getWorkTimeDivision().getWorkTimeDailyAtr().isFlex()) {
			if(!flexWorkSetOpt.isPresent())return ManageReGetClass.cantCalc2(workType,integrationOfDaily,personalInfo,holidayCalcMethodSet,regularAddSetting,flexAddSetting,hourlyPaymentAddSetting,illegularAddSetting);
			/* フレックス勤務 */
			if(timeSheetAtr.isSchedule()) {
				flexWorkSetOpt.get().getOffdayWorkTime().getRestTimezone().restoreFixRestTime(true);
				flexWorkSetOpt.get().getRestSetting().getCommonRestSetting().changeCalcMethodToRecordUntilLeaveWork();
				flexWorkSetOpt.get().getRestSetting().getFlowRestSetting().getFlowFixedRestSetting().changeCalcMethodToSchedule();
			}

			/*大塚モード*/
			workType = Optional.of(ootsukaProcessService.getOotsukaWorkType(workType.get(), ootsukaFixedWorkSet, oneRange.getAttendanceLeavingWork(),flexWorkSetOpt.get().getCommonSetting().getHolidayCalculation()));
			//出退勤削除
			if(!ootsukaProcessService.decisionOotsukaMode(workType.get(), ootsukaFixedWorkSet, oneRange.getAttendanceLeavingWork(),flexWorkSetOpt.get().getCommonSetting().getHolidayCalculation())
				&& workType.get().getDailyWork().isHolidayType()) {
				WorkStamp attendance = new WorkStamp(new TimeWithDayAttr(0),new TimeWithDayAttr(0), new WorkLocationCD("01"), StampSourceInfo.CORRECTION_RECORD_SET );
				WorkStamp leaving = new WorkStamp(new TimeWithDayAttr(0),new TimeWithDayAttr(0), new WorkLocationCD("01"), StampSourceInfo.CORRECTION_RECORD_SET );
				TimeActualStamp stamp = new TimeActualStamp(attendance,leaving,1);
				TimeLeavingWork timeLeavingWork = new TimeLeavingWork(new nts.uk.ctx.at.shared.dom.worktime.common.WorkNo(1),stamp,stamp);
				List<TimeLeavingWork> timeLeavingWorkList = new ArrayList<>();
				timeLeavingWorkList.add(timeLeavingWork);
				TimeLeavingOfDailyPerformance timeLeavingOfDailyPerformance = new TimeLeavingOfDailyPerformance(employeeId,new WorkTimes(1),timeLeavingWorkList,targetDate);
				oneRange.setAttendanceLeavingWork(timeLeavingOfDailyPerformance);
			}
			List<OverTimeOfTimeZoneSet> flexOtSetting = Collections.emptyList();
			if(workType.get().getAttendanceHolidayAttr().isFullTime()) {
				flexOtSetting = flexWorkSetOpt.get().getLstHalfDayWorkTimezone().stream().filter(tc -> tc.getAmpmAtr().equals(AmPmAtr.ONE_DAY)).findFirst().get().getWorkTimezone().getLstOTTimezone();
			}
			else if(workType.get().getAttendanceHolidayAttr().isMorning()) {
				flexOtSetting = flexWorkSetOpt.get().getLstHalfDayWorkTimezone().stream().filter(tc -> tc.getAmpmAtr().equals(AmPmAtr.AM)).findFirst().get().getWorkTimezone().getLstOTTimezone();
			}
			else if(workType.get().getAttendanceHolidayAttr().isAfternoon()) {
				flexOtSetting = flexWorkSetOpt.get().getLstHalfDayWorkTimezone().stream().filter(tc -> tc.getAmpmAtr().equals(AmPmAtr.PM)).findFirst().get().getWorkTimezone().getLstOTTimezone();
			}
			
			statutoryOverFrameNoList = flexOtSetting.stream()
					   								.map(tc -> new OverTimeFrameNo(tc.getLegalOTframeNo().v()))
					   								.collect(Collectors.toList());
				
			/*前日の勤務情報取得  */
			val yesterDay = getWorkTypeByWorkInfo(yesterDayInfo,workType.get());
			/*翌日の勤務情報取得 */
			val tomorrow = getWorkTypeByWorkInfo(tomorrowDayInfo,workType.get());
			//先に1日埋める
			List<OverTimeOfTimeZoneSet> useLstTimeZone = flexWorkSetOpt.get().getLstHalfDayWorkTimezone().stream().filter(tc -> tc.getAmpmAtr().equals(AmPmAtr.ONE_DAY)).findFirst().get().getWorkTimezone().getLstOTTimezone();
			//午前勤務
			if(workType.get().getAttendanceHolidayAttr().isMorning()) {
				useLstTimeZone = flexWorkSetOpt.get().getLstHalfDayWorkTimezone().stream().filter(tc -> tc.getAmpmAtr().equals(AmPmAtr.AM)).findFirst().get().getWorkTimezone().getLstOTTimezone();
			}
			//午後勤務
			else if(workType.get().getAttendanceHolidayAttr().isAfternoon()){
				useLstTimeZone = flexWorkSetOpt.get().getLstHalfDayWorkTimezone().stream().filter(tc -> tc.getAmpmAtr().equals(AmPmAtr.PM)).findFirst().get().getWorkTimezone().getLstOTTimezone();
			}
			
			subhol = flexWorkSetOpt.get().getCommonSetting().getSubHolTimeSet();
				oneRange.createTimeSheetAsFlex(personalInfo.getWorkingSystem(),oneRange.getPredetermineTimeSetForCalc(),
												BonusPaySetting.createFromJavaType(companyId,
														"01"/*ここは聞く*/,
														"テスト加給設定"/*ここは聞く*/,
														Collections.emptyList(),
														Collections.emptyList()
														),
												flexWorkSetOpt.get().getOffdayWorkTime().getLstWorkTimezone(),
												useLstTimeZone,
											   /*休出時間帯リスト*/Collections.emptyList(),overDayEndCalcSet, yesterDay, workType.get(),tomorrow,
											   new BreakDownTimeDay(new AttendanceTime(4),new AttendanceTime(4),new AttendanceTime(8)),
												personalInfo.getStatutoryWorkTime(),calcSetinIntegre,LegalOTSetting.OUTSIDE_LEGAL_TIME,StatutoryPrioritySet.priorityNormalOverTimeWork,
												workTime.get(),flexWorkSetOpt.get(),goOutTimeSheetList,oneRange.getOneDayOfRange(),oneRange.getAttendanceLeavingWork(),
												workTime.get().getWorkTimeDivision(),breakTimeOfDailyList,midNightTimeSheet,personalInfo,
												holidayCalcMethodSet,
												Optional.of(flexWorkSetOpt.get().getCoreTimeSetting()),
												dailyUnit,breakTimeList,
												vacation, 
						                		oneRange.getTimeVacationAdditionRemainingTime().get(), //oneDay.getTimeVacationAdditionRemainingTime().get()
						                		Optional.of(workInfo.getRecordInfo().getWorkTimeCode()), 
						                		integrationOfDaily.getCalAttr().getLeaveEarlySetting().isLate(), 
						                		integrationOfDaily.getCalAttr().getLeaveEarlySetting().isLeaveEarly(),
						                		illegularAddSetting, 
						                		flexAddSetting, 
						                		regularAddSetting, 
						                		holidayAddtionSet,
						                		Optional.of(flexWorkSetOpt.get().getCommonSetting()),
						                		companyCommonSetting.getPersonInfo().get(),
						                		getPredByPersonInfo(companyCommonSetting.personInfo.isPresent()?
						                									companyCommonSetting.personInfo.get().getWorkCategory().getWeekdayTime().getWorkTimeCode()
						                									:Optional.empty())
												);
		} else {
			switch (workTime.get().getWorkTimeDivision().getWorkTimeMethodSet()) {
			case FIXED_WORK:
				/* 固定 */
				Optional<FixedWorkSetting> fixedWorkSetting = shareContainer.getShared("FIXED_WORK" + companyId + workInfo.getRecordInfo().getWorkTimeCode().v(), 
																					   () -> fixedWorkSettingRepository.findByKey(companyId, workInfo.getRecordInfo().getWorkTimeCode().v()));
				if(timeSheetAtr.isSchedule()) {
					fixedWorkSetting = shareContainer.getShared("PRE_FIXED_WORK" + companyId + workInfo.getRecordInfo().getWorkTimeCode().v(), 
							() -> fixedWorkSettingRepository.findByKey(companyId, workInfo.getRecordInfo().getWorkTimeCode().v()));
				}
				
				if(!fixedWorkSetting.isPresent()) return ManageReGetClass.cantCalc2(workType,integrationOfDaily,personalInfo,holidayCalcMethodSet,regularAddSetting,flexAddSetting,hourlyPaymentAddSetting,illegularAddSetting);
				List<OverTimeOfTimeZoneSet> fixOtSetting = Collections.emptyList();
				if(workType.get().getAttendanceHolidayAttr().isFullTime()) {
					fixOtSetting = fixedWorkSetting.get().getLstHalfDayWorkTimezone().stream().filter(tc -> tc.getDayAtr().equals(AmPmAtr.ONE_DAY)).findFirst().get().getWorkTimezone().getLstOTTimezone();
				}
				else if(workType.get().getAttendanceHolidayAttr().isMorning()) {
					fixOtSetting = fixedWorkSetting.get().getLstHalfDayWorkTimezone().stream().filter(tc -> tc.getDayAtr().equals(AmPmAtr.AM)).findFirst().get().getWorkTimezone().getLstOTTimezone();
				}
				else if(workType.get().getAttendanceHolidayAttr().isAfternoon()) {
					fixOtSetting = fixedWorkSetting.get().getLstHalfDayWorkTimezone().stream().filter(tc -> tc.getDayAtr().equals(AmPmAtr.PM)).findFirst().get().getWorkTimezone().getLstOTTimezone();
				}
					
				


				ootsukaFixedWorkSet = fixedWorkSetting.get().getCalculationSetting();
				
				List<nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime> a = new ArrayList<>();
				if(workType.get().getDailyWork().getAttendanceHolidayAttr().isHoliday()) {
					a = fixedWorkSetting.get().getOffdayWorkTimezone().getRestTimezone().getLstTimezone();
				}
				else {
					if(workType.get().getDailyWork().getWorkTypeUnit().isOneDay()) {
						a = fixedWorkSetting.get().getLstHalfDayWorkTimezone().stream()
								  .filter(tc -> tc.getDayAtr().equals(AmPmAtr.ONE_DAY))
								  .map(tc -> tc.getRestTimezone().getLstTimezone())
								  .flatMap(tc -> tc.stream())
								  .collect(Collectors.toList());
					}
					else {
						a = fixedWorkSetting.get().getLstHalfDayWorkTimezone().stream()
								  .filter(tc -> tc.getDayAtr().equals(AmPmAtr.AM)
										  		 || tc.getDayAtr().equals(AmPmAtr.PM))
								  .map(tc -> tc.getRestTimezone().getLstTimezone())
								  .flatMap(tc -> tc.stream())
								  .collect(Collectors.toList());
					}

				}
				fixRestTimeSet = Optional.of(new FixRestTimezoneSet(a));
				statutoryOverFrameNoList = fixOtSetting.stream()
													   .map(tc -> new OverTimeFrameNo(tc.getLegalOTframeNo().v()))
													   .collect(Collectors.toList());
				/*大塚モード*/
				workType = Optional.of(ootsukaProcessService.getOotsukaWorkType(workType.get(), ootsukaFixedWorkSet, oneRange.getAttendanceLeavingWork(),fixedWorkSetting.get().getCommonSetting().getHolidayCalculation()));
				//出退勤削除
				if(!ootsukaProcessService.decisionOotsukaMode(workType.get(), ootsukaFixedWorkSet, oneRange.getAttendanceLeavingWork(),fixedWorkSetting.get().getCommonSetting().getHolidayCalculation())
					&& workType.get().getDailyWork().isHolidayType()) {
					WorkStamp attendance = new WorkStamp(new TimeWithDayAttr(0),new TimeWithDayAttr(0), new WorkLocationCD("01"), StampSourceInfo.CORRECTION_RECORD_SET );
					WorkStamp leaving = new WorkStamp(new TimeWithDayAttr(0),new TimeWithDayAttr(0), new WorkLocationCD("01"), StampSourceInfo.CORRECTION_RECORD_SET );
					TimeActualStamp stamp = new TimeActualStamp(attendance,leaving,1);
					TimeLeavingWork timeLeavingWork = new TimeLeavingWork(new nts.uk.ctx.at.shared.dom.worktime.common.WorkNo(1),stamp,stamp);
					List<TimeLeavingWork> timeLeavingWorkList = new ArrayList<>();
					timeLeavingWorkList.add(timeLeavingWork);
					TimeLeavingOfDailyPerformance timeLeavingOfDailyPerformance = new TimeLeavingOfDailyPerformance(employeeId,new WorkTimes(1),timeLeavingWorkList,targetDate);
					oneRange.setAttendanceLeavingWork(timeLeavingOfDailyPerformance);
				}
				
				/*前日の勤務情報取得  */
				val yesterDay = getWorkTypeByWorkInfo(yesterDayInfo,workType.get());
				/*翌日の勤務情報取得 */
				val tomorrow = getWorkTypeByWorkInfo(tomorrowDayInfo,workType.get());
				if(timeSheetAtr.isSchedule())
					fixedWorkSetting.get().getFixedWorkRestSetting().changeCalcMethodToSche();
				
				subhol = fixedWorkSetting.get().getCommonSetting().getSubHolTimeSet();
				
				oneRange.createWithinWorkTimeSheet(personalInfo.getWorkingSystem(),
						workTime.get().getWorkTimeDivision().getWorkTimeMethodSet(),
						RestClockManageAtr.IS_CLOCK_MANAGE,
						goOutTimeSheetList,
						new CommonRestSetting(RestTimeOfficeWorkCalcMethod.OFFICE_WORK_APPROP_ALL),
						Optional.of(fixedWorkSetting.get().getFixedWorkRestSetting().getCalculateMethod()),
						workTime.get().getWorkTimeDivision(),
						oneRange.getPredetermineTimeSetForCalc(), fixedWorkSetting.get(), 
						BonusPaySetting.createFromJavaType(companyId,
						"01"/*ここは聞く*/,
						"テスト加給設定"/*ここは聞く*/,
						Collections.emptyList(),
						Collections.emptyList()
						)
						,fixOtSetting ,
						fixedWorkSetting.get().getOffdayWorkTimezone().getLstWorkTimezone(), 
						overDayEndCalcSet, 
						Collections.emptyList(), 
						yesterDay, 
						workType.get(),
						tomorrow, 
						oneRange.getPredetermineTimeSetForCalc().getAdditionSet().getPredTime(),
						personalInfo.getStatutoryWorkTime(), 
						calcSetinIntegre, 
						fixedWorkSetting.get().getLegalOTSetting(),
						StatutoryPrioritySet.priorityNormalOverTimeWork, 
						workTime.get(),
						breakTimeOfDailyList,
						midNightTimeSheet,
						personalInfo,
						Optional.empty(),
						holidayCalcMethodSet,
						dailyUnit,
						breakTimeList,
						vacation, 
                		oneRange.getTimeVacationAdditionRemainingTime().get(), 
                		Optional.of(workInfo.getRecordInfo().getWorkTimeCode()), 
                		integrationOfDaily.getCalAttr().getLeaveEarlySetting().isLate(), 
                		integrationOfDaily.getCalAttr().getLeaveEarlySetting().isLeaveEarly(),
                		illegularAddSetting, 
                		flexAddSetting, 
                		regularAddSetting, 
                		holidayAddtionSet,
                		Optional.of(fixedWorkSetting.get().getCommonSetting()),
                		companyCommonSetting.getPersonInfo().get(),
                		getPredByPersonInfo(companyCommonSetting.personInfo.isPresent()?
								companyCommonSetting.personInfo.get().getWorkCategory().getWeekdayTime().getWorkTimeCode()
								:Optional.empty())
						);
				//大塚モードの判定(緊急対応)
				if(ootsukaProcessService.decisionOotsukaMode(workType.get(), ootsukaFixedWorkSet, oneRange.getAttendanceLeavingWork(),fixedWorkSetting.get().getCommonSetting().getHolidayCalculation()))
					oneRange.cleanLateLeaveEarlyTimeForOOtsuka();
				break;
			case FLOW_WORK:
				/* 流動勤務 */
				val flowWorkSetOpt = shareContainer.getShared("FLOW_WORK" + companyId + workInfo.getRecordInfo().getWorkTimeCode().v(), 
															  () -> flowWorkSettingRepository.find(companyId,workInfo.getRecordInfo().getWorkTimeCode().v()));
				return ManageReGetClass.cantCalc2(workType,integrationOfDaily,personalInfo,holidayCalcMethodSet,regularAddSetting,flexAddSetting,hourlyPaymentAddSetting,illegularAddSetting);
			case DIFFTIME_WORK:
				/* 時差勤務 */
				val diffWorkSetOpt = shareContainer.getShared("FLOW_WORK" + companyId + workInfo.getRecordInfo().getWorkTimeCode().v(), 
						  									  () -> diffTimeWorkSettingRepository.find(companyId,workInfo.getRecordInfo().getWorkTimeCode().v()));
				return ManageReGetClass.cantCalc2(workType,integrationOfDaily,personalInfo,holidayCalcMethodSet,regularAddSetting,flexAddSetting,hourlyPaymentAddSetting,illegularAddSetting);
				// case Enum_Overtime_Work:
			default:
				throw new RuntimeException(
						"unknown workTimeMethodSet" + workTime.get().getWorkTimeDivision().getWorkTimeMethodSet());
			}
		}
		
		Optional<CoreTimeSetting> coreTimeSetting = Optional.empty();

		
		if(flexWorkSetOpt.isPresent()) {//暫定的にここに処理を記載しているが、本来は別のクラスにあるべき
			if(beforeWorkType.isPresent()) {
				if(beforeWorkType.get().isWeekDayAttendance()) {
					coreTimeSetting = Optional.of(flexWorkSetOpt.get().getCoreTimeSetting());
				}else {//出勤系ではない場合は最低勤務時間を0：00にする
					coreTimeSetting = Optional.of(flexWorkSetOpt.get().getCoreTimeSetting().changeZeroMinWorkTime());
				}
			}else {
				coreTimeSetting = Optional.of(flexWorkSetOpt.get().getCoreTimeSetting());
			}
		}
		
		return ManageReGetClass.canCalc(oneRange, 
										integrationOfDaily, 
										workTime,
										beforeWorkType , 
										subhol, 
										personalInfo ,
										dailyUnit ,
										fixRestTimeSet,
										ootsukaFixedWorkSet,
										holidayCalcMethodSet,
										breakCount,
										coreTimeSetting,
										regularAddSetting,
										flexAddSetting,
										hourlyPaymentAddSetting,
										illegularAddSetting,
										commonSet,
										statutoryOverFrameNoList);
	}

	/**
	 * 作成した時間帯から時間を計算する
	 * @param companyCommonSetting 
	 * @param schedule 
	 * 
	 * @param integrationOfDaily
	 *            日別実績(WORK)
	 * @return 日別実績(WORK)
	 */
	private IntegrationOfDaily calcRecord(ManageReGetClass recordReGetClass, 
										  ManageReGetClass scheduleReGetClass,
										  ManagePerCompanySet companyCommonSetting,
										  DailyRecordToAttendanceItemConverter converter) {
		String companyId = AppContexts.user().companyId();
		GeneralDate targetDate = recordReGetClass.getIntegrationOfDaily().getAffiliationInfor().getYmd();

		/* 日別実績(Work)の退避 */
		val copyIntegrationOfDaily = converter.setData(recordReGetClass.getIntegrationOfDaily()).toDomain();

		//加給時間計算設定
		BonusPayAutoCalcSet bonusPayAutoCalcSet = new BonusPayAutoCalcSet(new CompanyId(companyId),
																		   1,
																		   WorkingTimesheetCalculationSetting.CalculateAutomatic,
																		   OvertimeTimesheetCalculationSetting.CalculateAutomatic,
																		   HolidayTimesheetCalculationSetting.CalculateAutomatical);

		// 休暇クラス
		VacationClass vacation = CalcDefaultValue.DEFAULT_VACATION;
		
		Optional<SettingOfFlexWork> flexCalcMethod = Optional.of(new SettingOfFlexWork(new FlexCalcMethodOfHalfWork(new FlexCalcMethodOfEachPremiumHalfWork(FlexCalcMethod.OneDay, FlexCalcMethod.OneDay),
																													new FlexCalcMethodOfEachPremiumHalfWork(FlexCalcMethod.OneDay, FlexCalcMethod.OneDay))));

		//総拘束時間の計算
		Optional<CalculateOfTotalConstraintTime> optionalCalculateOfTotalConstraintTime = companyCommonSetting.getCalculateOfTotalCons();
		if(!optionalCalculateOfTotalConstraintTime.isPresent()) {
			throw new BusinessException(new RawErrorMessage("総拘束時間の計算が存在しません"));
		}
		CalculateOfTotalConstraintTime calculateOfTotalConstraintTime = optionalCalculateOfTotalConstraintTime.get();
		
		//会社別代休設定取得
		val compensLeaveComSet = companyCommonSetting.getCompensatoryLeaveComSet();
		List<CompensatoryOccurrenceSetting> eachCompanyTimeSet = new ArrayList<>();
		if(compensLeaveComSet != null)
			eachCompanyTimeSet = compensLeaveComSet.getCompensatoryOccurrenceSetting();
 
		//-------------------------計算用一時的クラス作成----------------------------
		

		
		val workType = recordReGetClass.getWorkType();
		//if(!workType.isPresent() || !recordReGetClass.getWorkTimeSetting().isPresent()) return recordReGetClass.getIntegrationOfDaily();
		if(!workType.isPresent()) return recordReGetClass.getIntegrationOfDaily();
		

		//乖離時間(AggregateRoot)取得
		List<DivergenceTime> divergenceTimeList = companyCommonSetting.getDivergenceTime();
		
		//乖離時間計算用　勤怠項目ID紐づけDto作成
		DailyRecordToAttendanceItemConverter forCalcDivergenceDto = converter.setData(copyIntegrationOfDaily);
		

		/*時間の計算*/
		recordReGetClass.setIntegrationOfDaily(
				AttendanceTimeOfDailyPerformance.calcTimeResult(
				    vacation,
				    workType.get(),
				    flexCalcMethod,
					bonusPayAutoCalcSet,
					eachCompanyTimeSet,
					forCalcDivergenceDto,
					divergenceTimeList,
					calculateOfTotalConstraintTime, 
					scheduleReGetClass,
					recordReGetClass,
					companyCommonSetting.getPersonInfo().get(),
            		getPredByPersonInfo(companyCommonSetting.personInfo.isPresent()?
							companyCommonSetting.personInfo.get().getWorkCategory().getWeekdayTime().getWorkTimeCode()
							:Optional.empty())
					));
	
	//  // 編集状態を取得（日別実績の編集状態が持つ勤怠項目IDのみのList作成）
		  List<Integer> attendanceItemIdList = recordReGetClass.getIntegrationOfDaily().getEditState().stream().filter(editState -> editState.getEmployeeId().equals(copyIntegrationOfDaily.getAffiliationInfor().getEmployeeId())
		       && editState.getYmd().equals(copyIntegrationOfDaily.getAffiliationInfor().getYmd()))
		        .map(editState -> editState.getAttendanceItemId())
		        .distinct()
		        .collect(Collectors.toList());
		  
		  IntegrationOfDaily calcResultIntegrationOfDaily = recordReGetClass.getIntegrationOfDaily();  
		  if(!attendanceItemIdList.isEmpty()) {
		   DailyRecordToAttendanceItemConverter beforDailyRecordDto = converter.setData(copyIntegrationOfDaily); 
		   List<ItemValue> itemValueList = beforDailyRecordDto.convert(attendanceItemIdList);  
		   DailyRecordToAttendanceItemConverter afterDailyRecordDto = converter.setData(recordReGetClass.getIntegrationOfDaily()); 
		   afterDailyRecordDto.merge(itemValueList);
		   
		   
		   
		   //手修正された項目の値を計算前に戻す   
		   calcResultIntegrationOfDaily = afterDailyRecordDto.toDomain();
		   
		   //手修正後の再計算
		   //calcResultIntegrationOfDaily = reCalc(calcResultIntegrationOfDaily,recordReGetClass.getCalculationRangeOfOneDay(),companyId, companyCommonSetting, converter,overTotalTime,holidayWorkTotalTime,attendanceItemIdList,targetDate);
		   calcResultIntegrationOfDaily = reCalc(calcResultIntegrationOfDaily,recordReGetClass.getCalculationRangeOfOneDay(),companyId, companyCommonSetting, converter,attendanceItemIdList,targetDate);
		   //手修正された項目の値を計算値に戻す(手修正再計算の後Ver)
		   DailyRecordToAttendanceItemConverter afterReCalcDto = converter.setData(calcResultIntegrationOfDaily); 
		   afterReCalcDto.merge(itemValueList);
		   calcResultIntegrationOfDaily = afterReCalcDto.toDomain();
		  }
			
		/*日別実績への項目移送*/
		return calcResultIntegrationOfDaily;
	}

	/**
	 * 手修正後の再計算
	 * @param calcResultIntegrationOfDaily
	 * @param calculationRangeOfOneDay
	 * @param companyId
	 * @param companyCommonSetting
	 * @param overTotalTime 手修正前の残業時間の合計
	 * @param attendanceItemIdList 
	 * @param holidayWorkTotalTime　手修正前の休出時間の合計
	 * @return
	 */
	private IntegrationOfDaily reCalc(IntegrationOfDaily calcResultIntegrationOfDaily,
									  CalculationRangeOfOneDay calculationRangeOfOneDay,
									  String companyId,
									  ManagePerCompanySet companyCommonSetting,
									  DailyRecordToAttendanceItemConverter converter,
									  List<Integer> attendanceItemIdList,GeneralDate targetDate) {
		//乖離時間(AggregateRoot)取得
		List<DivergenceTime> divergenceTimeList = companyCommonSetting.getDivergenceTime();
		if(calcResultIntegrationOfDaily.getAttendanceTimeOfDailyPerformance().isPresent()) {
			
			AttendanceTimeOfDailyPerformance attendanceTimeOfDailyPerformance = calcResultIntegrationOfDaily.getAttendanceTimeOfDailyPerformance().get();
			
			ActualWorkingTimeOfDaily actualWorkingTimeOfDaily = attendanceTimeOfDailyPerformance.getActualWorkingTimeOfDaily();
			
			calcResultIntegrationOfDaily.setAttendanceTimeOfDailyPerformance(Optional.of(
						attendanceTimeOfDailyPerformance.inssertActualWorkingTimeOfDaily(
								actualWorkingTimeOfDaily.inssertTotalWorkingTime(
										actualWorkingTimeOfDaily.getTotalWorkingTime().calcDiverGenceTime()))));

		}

//		//深夜時間
		if(calcResultIntegrationOfDaily.getAttendanceTimeOfDailyPerformance().isPresent()) {
			if(calcResultIntegrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime() != null) {; 
				calcResultIntegrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().reCalcMidNightTime();
			}
		}
		
		//総労働時間
		if(calcResultIntegrationOfDaily.getAttendanceTimeOfDailyPerformance().isPresent()) {
			if(calcResultIntegrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime() != null) {
				calcResultIntegrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().calcTotalWorkingTimeForReCalc();
			}
		}
		
		//予実差異時間
		AttendanceTimeOfExistMinus scheActDiffTime = new AttendanceTimeOfExistMinus(0);
		//総労働時間が編集している項目リストに含まれていなければ再計算
		if(calcResultIntegrationOfDaily.getAttendanceTimeOfDailyPerformance().isPresent() ) {
			AttendanceTimeOfExistMinus scheTime = new AttendanceTimeOfExistMinus(calcResultIntegrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getWorkScheduleTimeOfDaily().getWorkScheduleTime().getTotal().valueAsMinutes());
			scheActDiffTime = calcResultIntegrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getBudgetTimeVariance();
			if(calcResultIntegrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()!= null
					&&calcResultIntegrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime() != null
					&& !attendanceItemIdList.contains(new Integer(559))) {
				AttendanceTimeOfExistMinus totalWorkTime = new AttendanceTimeOfExistMinus(calcResultIntegrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getTotalTime().valueAsMinutes());
				scheActDiffTime = scheTime.minusMinutes(totalWorkTime.valueAsMinutes());
			}
		}
		//不就労時間
		AttendanceTimeOfExistMinus alreadlyDedBindTime = new AttendanceTimeOfExistMinus(0);
		//総労働時間が編集している項目リストに含まれていなければ再計算
		if(calcResultIntegrationOfDaily.getAttendanceTimeOfDailyPerformance().isPresent()) {
			alreadlyDedBindTime = calcResultIntegrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getUnEmployedTime();
			if(calcResultIntegrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime() != null
				&& calcResultIntegrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()!= null
				&& !attendanceItemIdList.contains(new Integer(559))) {
				//↓で総控除時間を引く
				alreadlyDedBindTime = new AttendanceTimeOfExistMinus(calcResultIntegrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getStayingTime().getStayingTime()
															.minusMinutes(calcResultIntegrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().calcTotalDedTime(calculationRangeOfOneDay).valueAsMinutes()).valueAsMinutes());
				alreadlyDedBindTime = alreadlyDedBindTime.minusMinutes(calcResultIntegrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().recalcActualTime().valueAsMinutes());
			}
		}
		
		//乖離時間計算用　勤怠項目ID紐づけDto作成
		DailyRecordToAttendanceItemConverter forCalcDivergenceDto = converter.setData(calcResultIntegrationOfDaily);
		
		if(calcResultIntegrationOfDaily != null && calcResultIntegrationOfDaily.getAttendanceTimeOfDailyPerformance().isPresent()) {
			
			//割増時間の計算
			PremiumTimeOfDailyPerformance premiumTimeOfDailyPerformance = ActualWorkingTimeOfDaily.createPremiumTimeOfDailyPerformance(personnelCostSettingAdapter.findAll(companyId, targetDate), Optional.of(forCalcDivergenceDto));
			
			val reCalcDivergence = ActualWorkingTimeOfDaily.createDivergenceTimeOfDaily(forCalcDivergenceDto,
																						divergenceTimeList,
																						calcResultIntegrationOfDaily.getCalAttr());
			
			val reCreateActual = ActualWorkingTimeOfDaily.of(calcResultIntegrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getConstraintDifferenceTime(),
												 			 calcResultIntegrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getConstraintTime(),
												 			 calcResultIntegrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTimeDifferenceWorkingHours(),
												 			 calcResultIntegrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime(),
												 			 reCalcDivergence,
//												 			 calcResultIntegrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getPremiumTimeOfDailyPerformance());
												 			 premiumTimeOfDailyPerformance);
			
			val reCreateAttendanceTime = new AttendanceTimeOfDailyPerformance(calcResultIntegrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getEmployeeId(),
																			  calcResultIntegrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getYmd(),
																			  calcResultIntegrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getWorkScheduleTimeOfDaily(),
																			  reCreateActual,
																			  calcResultIntegrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getStayingTime(),
																			  alreadlyDedBindTime,
																			  scheActDiffTime,
																			  calcResultIntegrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getMedicalCareTime());
			calcResultIntegrationOfDaily.setAttendanceTimeOfDailyPerformance(Optional.of(reCreateAttendanceTime));
		}
		//Repositoryが作成されたらそちらから取得(今は仮置き)
		Optional<UpperLimitTotalWorkingHour> upperControl = specificWorkRuleRepository.findUpperLimitWkHourByCid(AppContexts.user().companyId()); 
		upperControl.ifPresent(tc ->{
			tc.controlUpperLimit(calcResultIntegrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime());
		});
		
		return calcResultIntegrationOfDaily;
	}

	/**
	 * １日の範囲クラス作成
	 * 
	 * @param companyId
	 *            会社コード
	 * @param employeeId
	 *            社員ID
	 * @param targetDate
	 *            対象日
	 * @param integrationOfDaily
	 *            日別実績(Work)
	 * @return 1日の計算範囲
	 */
	private CalculationRangeOfOneDay createOneDayRange(IntegrationOfDaily integrationOfDaily,Optional<WorkTimezoneCommonSet> commonSet) {
		String companyId = AppContexts.user().companyId();
		String placeId = integrationOfDaily.getAffiliationInfor().getWplID();
		String employmentCd = integrationOfDaily.getAffiliationInfor().getEmploymentCode().toString();
		String employeeId = integrationOfDaily.getAffiliationInfor().getEmployeeId();
		GeneralDate targetDate = integrationOfDaily.getAffiliationInfor().getYmd(); 
		/*所定時間設定取得*/
		Optional<PredetemineTimeSetting> predetermineTimeSet = Optional.empty();
		String workTimeCode = null;
		if(integrationOfDaily.getWorkInformation().getRecordInfo().getWorkTimeCode() != null) {
			predetermineTimeSet = predetemineTimeSetRepository.findByWorkTimeCode(companyId,integrationOfDaily.getWorkInformation().getRecordInfo().getWorkTimeCode().toString());
			workTimeCode = integrationOfDaily.getWorkInformation().getRecordInfo().getWorkTimeCode().toString();
		}
		
		if(!predetermineTimeSet.isPresent()) {
			predetermineTimeSet = Optional.of(new PredetemineTimeSetting(companyId,
															new AttendanceTime(0),
															new WorkTimeCode(workTimeCode),
															new PredetermineTime(new BreakDownTimeDay(new AttendanceTime(0),new AttendanceTime(0),new AttendanceTime(0)),
																	  			 new BreakDownTimeDay(new AttendanceTime(0),new AttendanceTime(0),new AttendanceTime(0))),
															false,
															new PrescribedTimezoneSetting(new TimeWithDayAttr(0),new TimeWithDayAttr(0),Collections.emptyList()),
															new TimeWithDayAttr(0),
															false));
					
		}
		/*1日の計算範囲取得*/
		val calcRangeOfOneDay = new TimeSpanForCalc(predetermineTimeSet.get().getStartDateClock()
												   ,predetermineTimeSet.get().getStartDateClock().forwardByMinutes(predetermineTimeSet.get().getRangeTimeDay().valueAsMinutes()));
		
		WorkInfoOfDailyPerformance toDayWorkInfo = integrationOfDaily.getWorkInformation();
				
		/*日別実績の出退勤時刻セット*/
		Optional<TimeLeavingOfDailyPerformance> timeLeavingOfDailyPerformance = integrationOfDaily.getAttendanceLeave();
		if(!timeLeavingOfDailyPerformance.isPresent()) {
			WorkStamp attendance = new WorkStamp(new TimeWithDayAttr(0),new TimeWithDayAttr(0), new WorkLocationCD("01"), StampSourceInfo.CORRECTION_RECORD_SET );
			WorkStamp leaving = new WorkStamp(new TimeWithDayAttr(0),new TimeWithDayAttr(0), new WorkLocationCD("01"), StampSourceInfo.CORRECTION_RECORD_SET );
			TimeActualStamp stamp = new TimeActualStamp(attendance,leaving,1);
			TimeLeavingWork timeLeavingWork = new TimeLeavingWork(new nts.uk.ctx.at.shared.dom.worktime.common.WorkNo(1),stamp,stamp);
			List<TimeLeavingWork> timeLeavingWorkList = new ArrayList<>();
			timeLeavingWorkList.add(timeLeavingWork);
			timeLeavingOfDailyPerformance = Optional.of(new TimeLeavingOfDailyPerformance(employeeId,new WorkTimes(1),timeLeavingWorkList,targetDate)); 
		}
		
		/*ジャストタイムの判断するための設定取得*/
		boolean justLate = false;
		boolean justEarlyLeave = false;
		if(commonSet.isPresent()) {
			justLate = commonSet.get().getLateEarlySet().getOtherEmTimezoneLateEarlySet(LateEarlyAtr.LATE).isStampExactlyTimeIsLateEarly();
			justEarlyLeave = commonSet.get().getLateEarlySet().getOtherEmTimezoneLateEarlySet(LateEarlyAtr.EARLY).isStampExactlyTimeIsLateEarly();
		}
		//この区分は本来は引数として計算処理の呼出し口から渡されます（2018/6/6現在は引数として渡されていないので一時的に固定値を渡しています）
		JustCorrectionAtr justCorrectionAtr = JustCorrectionAtr.USE;
		//ジャスト遅刻、早退による時刻補正
		timeLeavingOfDailyPerformance.get().calcJustTime(justLate, justEarlyLeave, justCorrectionAtr);
		
		return new CalculationRangeOfOneDay(Finally.empty(),  
											Finally.empty(),
											calcRangeOfOneDay,
											timeLeavingOfDailyPerformance.get(),/*出退勤*/
											PredetermineTimeSetForCalc.convertMastarToCalc(predetermineTimeSet.get())/*所定時間帯(計算用)*/,
											Finally.of(new TimevacationUseTimeOfDaily(new AttendanceTime(0),new AttendanceTime(0),new AttendanceTime(0),new AttendanceTime(0))),
											toDayWorkInfo);
	}
	
	/**
	 * 予定時間帯の作成
	 * @param companyCommonSetting 
	 * @param tomorrowDayInfo 
	 * @param yesterDayInfo 
	 * @return 計画の日別実績(WOOR)
	 */
	private ManageReGetClass createSchedule(IntegrationOfDaily integrationOfDaily, ManagePerCompanySet companyCommonSetting, DailyRecordToAttendanceItemConverter converter, Optional<WorkInfoOfDailyPerformance> yesterDayInfo, Optional<WorkInfoOfDailyPerformance> tomorrowDayInfo) {
		val integrationOfDailyForSchedule = converter.setData(integrationOfDaily).toDomain();
		//予定時間１　ここで、「勤務予定を取得」～「休憩情報を変更」を行い、日別実績(Work)をReturnとして受け取る
		IntegrationOfDaily afterScheduleIntegration = SchedulePerformance.createScheduleTimeSheet(integrationOfDailyForSchedule);
		//予定時間2 　ここで、「時間帯を作成」を実施 Returnとして１日の計算範囲を受け取る
		return this.createRecord(afterScheduleIntegration,TimeSheetAtr.SCHEDULE, companyCommonSetting,yesterDayInfo,tomorrowDayInfo);
	}
	
	/**
	 * 労働制を取得する
	 * @return 日別計算用の個人情報
	 */
	private DailyCalculationPersonalInformation getPersonInfomation(IntegrationOfDaily integrationOfDaily, ManagePerCompanySet companyCommonSetting) {
		String companyId = AppContexts.user().companyId();
		String placeId = integrationOfDaily.getAffiliationInfor().getWplID();
		String employmentCd = integrationOfDaily.getAffiliationInfor().getEmploymentCode().toString();
		String employeeId = integrationOfDaily.getAffiliationInfor().getEmployeeId();
		GeneralDate targetDate = integrationOfDaily.getAffiliationInfor().getYmd();
		
		// ドメインモデル「個人労働条件」を取得する
		Optional<WorkingConditionItem> personalLablorCodition = companyCommonSetting.getPersonInfo();
		
		if (personalLablorCodition==null || !personalLablorCodition.isPresent()) {
			return null;
		}
		// 労働制
		return getOfStatutoryWorkTime.getDailyTimeFromStaturoyWorkTime(
				personalLablorCodition.get().getLaborSystem(), companyId, placeId, employmentCd, employeeId,
				targetDate);
	}
	
	/**
	 *　任意項目の計算
	 * @return
	 */
	private IntegrationOfDaily calcOptionalItem(IntegrationOfDaily integrationOfDaily,DailyRecordToAttendanceItemConverter converter, ManagePerCompanySet companyCommonSetting) {
		String companyId = AppContexts.user().companyId();
		String employeeId = integrationOfDaily.getAffiliationInfor().getEmployeeId();
		GeneralDate targetDate = integrationOfDaily.getAffiliationInfor().getYmd(); 
		// 「所属雇用履歴」を取得する
		Optional<BsEmploymentHistoryImport> bsEmploymentHistOpt = this.shareEmploymentAdapter.findEmploymentHistory(companyId, employeeId, targetDate);
	    //AggregateRoot「任意項目」取得
		List<OptionalItem> optionalItems = companyCommonSetting.getOptionalItems();
		//計算式を取得(任意項目NOで後から絞る必要あり)
		List<Formula> formulaList = companyCommonSetting.getFormulaList();
		//適用する雇用条件の取得
		List<EmpCondition> empCondition = companyCommonSetting.getEmpCondition();
		//項目選択による計算時に必要なので取得
		converter.setData(integrationOfDaily); 
			
		//任意項目の計算
		AnyItemValueOfDaily result = AnyItemValueOfDaily.caluculationAnyItem(companyId, employeeId, targetDate, optionalItems, formulaList,
				empCondition, Optional.of(converter),bsEmploymentHistOpt,integrationOfDaily.getAnyItemValue());
		
		
		// 編集状態を取得（日別実績の編集状態が持つ勤怠項目IDのみのList作成）
		List<Integer> attendanceItemIdList = integrationOfDaily.getEditState().stream().filter(editState -> editState.getEmployeeId().equals(employeeId)
		       && editState.getYmd().equals(targetDate))
		        .map(editState -> editState.getAttendanceItemId())
		        .distinct()
		        .collect(Collectors.toList());

		IntegrationOfDaily calcResultIntegrationOfDaily = integrationOfDaily;  
		if(!attendanceItemIdList.isEmpty()) {
			List<ItemValue> itemValueList = converter.convert(attendanceItemIdList);  
			converter.withAnyItems(result); 
			converter.merge(itemValueList);
			//手修正された項目の値を計算前に戻す   
			calcResultIntegrationOfDaily.setAnyItemValue(converter.toDomain().getAnyItemValue());
		  }
		
		return calcResultIntegrationOfDaily;
	}
	
	
	/**
	 * 就業時間帯の共通設定を取得する
	 * @return
	 */
	private Optional<WorkTimezoneCommonSet> getWorkTimezoneCommonSet(IntegrationOfDaily integrationOfDaily,String companyId,Optional<WorkTimeSetting> workTime, MasterShareContainer shareContainer) {
		
		/* 勤務種類の取得 */
		val workInfo = integrationOfDaily.getWorkInformation();
		
		if (workTime.get().getWorkTimeDivision().getWorkTimeDailyAtr().isFlex()) {
			val flexWorkSetOpt = shareContainer.getShared("FLEX_WORK" + companyId + workInfo.getRecordInfo().getWorkTimeCode().v(), 
														  () -> flexWorkSettingRepository.find(companyId,workInfo.getRecordInfo().getWorkTimeCode().v()));
			if(flexWorkSetOpt.isPresent()) {
				return Optional.of(flexWorkSetOpt.get().getCommonSetting());
			}
		}else{
		
			switch (workTime.get().getWorkTimeDivision().getWorkTimeMethodSet()) {
			case FIXED_WORK:
				/* 固定 */
				val fixedWorkSetting = shareContainer.getShared("FIXED_WORK" + companyId + workInfo.getRecordInfo().getWorkTimeCode().v(), 
																() -> fixedWorkSettingRepository.findByKey(companyId, workInfo.getRecordInfo().getWorkTimeCode().v()));
				if(fixedWorkSetting.isPresent()) {
					return Optional.of(fixedWorkSetting.get().getCommonSetting());
				}
			break;
			case FLOW_WORK:
				/* 流動勤務 */
				val flowWorkSetOpt = shareContainer.getShared("FLOW_WORK" + companyId + workInfo.getRecordInfo().getWorkTimeCode().v(), 
															  () -> flowWorkSettingRepository.find(companyId,workInfo.getRecordInfo().getWorkTimeCode().v()));
				if(flowWorkSetOpt.isPresent()) {
					return Optional.of(flowWorkSetOpt.get().getCommonSetting());
				}
				break;
			case DIFFTIME_WORK:
				/* 時差勤務 */
				val diffWorkSetOpt = shareContainer.getShared("FLOW_WORK" + companyId + workInfo.getRecordInfo().getWorkTimeCode().v(), 
						  									  () -> diffTimeWorkSettingRepository.find(companyId,workInfo.getRecordInfo().getWorkTimeCode().v()));
				if(diffWorkSetOpt.isPresent()) {
					return Optional.of(diffWorkSetOpt.get().getCommonSet());
				}
				break;
			default:
				throw new RuntimeException(
						"unknown workTimeMethodSet" + workTime.get().getWorkTimeDivision().getWorkTimeMethodSet());
			}
		}
		return Optional.empty();
	}
	
	private Optional<PredetermineTimeSetForCalc> getPredByPersonInfo(Optional<WorkTimeCode> workTimeCode){
		if(!workTimeCode.isPresent()) return Optional.empty();
		val predSetting = predetemineTimeSetRepository.findByWorkTimeCode(AppContexts.user().companyId(), workTimeCode.get().toString());
		if(!predSetting.isPresent()) return Optional.empty();
		return Optional.of(PredetermineTimeSetForCalc.convertFromAggregatePremiumTime(predSetting.get()));
				
	}
	
	private WorkType getWorkTypeByWorkInfo(Optional<WorkInfoOfDailyPerformance> otherDayWorkInfo, WorkType nowWorkType) {
		if(otherDayWorkInfo.isPresent()) {
			WorkTypeCode workTypeCode;
			try{
				workTypeCode = otherDayWorkInfo.get().getRecordInfo().getWorkTypeCode();
			}
			catch(Exception e) {
				workTypeCode = otherDayWorkInfo.get().getScheduleInfo().getWorkTypeCode();
			}
			return this.workTypeRepository.findByPK(AppContexts.user().companyCode(), workTypeCode.toString()).orElse(nowWorkType);
		}
		else {
			return nowWorkType;
		}
	}
	
}

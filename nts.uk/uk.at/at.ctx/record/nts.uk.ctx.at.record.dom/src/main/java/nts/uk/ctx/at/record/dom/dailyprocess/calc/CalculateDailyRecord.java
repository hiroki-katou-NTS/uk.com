//package nts.uk.ctx.at.record.dom.dailyprocess.calc;
//
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//import javax.ejb.Stateless;
//import javax.inject.Inject;
//
//import lombok.val;
//import nts.arc.error.BusinessException;
//import nts.arc.time.GeneralDate;
//import nts.gul.util.value.Finally;
//import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
//import nts.uk.ctx.at.record.dom.bonuspay.primitives.BonusPaySettingCode;
//import nts.uk.ctx.at.record.dom.bonuspay.primitives.BonusPaySettingName;
//import nts.uk.ctx.at.record.dom.bonuspay.setting.BonusPaySetting;
//import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeOfDailyPerformance;
//import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeOfDailyPerformance;
//import nts.uk.ctx.at.record.dom.daily.DeductionTotalTime;
//import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
//import nts.uk.ctx.at.record.dom.daily.WorkInformationOfDaily;
//import nts.uk.ctx.at.record.dom.daily.WorkInformationOfDailyRepository;
//import nts.uk.ctx.at.record.dom.daily.breaktimegoout.BreakTimeOfDaily;
//import nts.uk.ctx.at.record.dom.worklocation.WorkLocationCD;
//import nts.uk.ctx.at.record.dom.worktime.TimeActualStamp;
//import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
//import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;
//import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
//import nts.uk.ctx.at.record.dom.worktime.enums.StampSourceInfo;
//import nts.uk.ctx.at.record.dom.worktime.primitivevalue.WorkNo;
//import nts.uk.ctx.at.record.dom.worktime.primitivevalue.WorkTimes;
//import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
//import nts.uk.ctx.at.shared.dom.common.DailyTime;
//import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
//import nts.uk.ctx.at.shared.dom.common.time.BreakdownTimeDay;
//import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
//import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.employment.EmploymentContractHistory;
//import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.employment.EmploymentContractHistoryAdopter;
//import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.employment.WorkingSystem;
//import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalAtrOvertime;
//import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalSetting;
//import nts.uk.ctx.at.shared.dom.ot.autocalsetting.TimeLimitUpperLimitSetting;
//import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;
//import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
//import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalcSet;
//import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalculationCategoryOutsideHours;
//import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalculationOfOverTimeWork;
//import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.GetOfStatutoryWorkTime;
//import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.OverDayEndAggregateFrameSet;
//import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.OverDayEndCalcSet;
//import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.OverDayEndCalcSetOfExcessHoliday;
//import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.OverDayEndCalcSetOfExcessSpecialHoliday;
//import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.OverDayEndCalcSetOfStatutoryHoliday;
//import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.OverDayEndCalcSetOfWeekDay;
//import nts.uk.ctx.at.shared.dom.workrule.overtime.StatutoryPrioritySet;
//import nts.uk.ctx.at.shared.dom.workrule.statutoryworkTime.DailyCalculationPersonalInformation;
//import nts.uk.ctx.at.shared.dom.worktime.WorkTime;
//import nts.uk.ctx.at.shared.dom.worktime.WorkTimeDivision;
//import nts.uk.ctx.at.shared.dom.worktime.WorkTimeMethodSet;
//import nts.uk.ctx.at.shared.dom.worktime.WorkTimeRepository;
//import nts.uk.ctx.at.shared.dom.worktime.CommomSetting.BreakSetOfCommon;
//import nts.uk.ctx.at.shared.dom.worktime.CommomSetting.CalcMethodIfLeaveWorkDuringBreakTime;
//import nts.uk.ctx.at.shared.dom.worktime.CommomSetting.OverWorkSet.StatutoryOverTimeWorkSet;
//import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.FixOffdayWorkTime;
//import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.FixRestTime;
//import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.FixRestTimeSetting;
//import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.FixRestTimezoneSet;
//import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.FixWeekdayWorkTime;
//import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.FixedWorkSetting;
//import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.OverTimeHourSet;
//import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.WorkTimeCommonSet;
//import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.set.FixRestCalcMethod;
//import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.set.FixedWorkTimeSet;
//import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.timespan.TimeSpanWithRounding;
//import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.FluRestTime;
//import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.FluRestTimeGroup;
//import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.FluRestTimeSetting;
//import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.FluidPrefixBreakTimeSet;
//import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.fluidbreaktimeset.FlowRestCalcMethod;
//import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.fluidbreaktimeset.FluidPrefixBreakTimeOfCalcMethod;
//import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.fluidbreaktimeset.RestClockManageAtr;
//import nts.uk.ctx.at.shared.dom.worktime_old.AmPmClassification;
//import nts.uk.ctx.at.shared.dom.worktype.AttendanceHolidayAttr;
//import nts.uk.ctx.at.shared.dom.worktype.WorkType;
//import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
//import nts.uk.shr.com.time.TimeWithDayAttr;
//
//@Stateless
//public class CalculateDailyRecord {
//	
//	@Inject
//	private WorkTypeRepository workTypeRepository;
//	
//	@Inject
//	private EmploymentContractHistoryAdopter employmentContractHistoryAdopter;
//	
//	//@Inject
//	//private WorkTimeSetRepository workTimeSetRepository;
//
//	@Inject
//	private WorkTimeRepository workTimeRepository;
//	
//	@Inject
//	private GetOfStatutoryWorkTime getOfStatutoryWorkTime;
//	
//	@Inject
//	private WorkInformationOfDailyRepository workInformationOfDailyRepository;
//	
//	@Inject
//	private TimeLeavingOfDailyPerformanceRepository timeLeavingOfDailyPerformanceRepository;
//	/**
//	 * アルゴリズム(日別計算処理の窓口)
//	 * @param companyId
//	 * @param placeId
//	 * @param employmentCd
//	 * @param employeeId
//	 * @param targetDate
//	 * @param integrationOfDaily
//	 * @return
//	 */
//	public IntegrationOfDaily calculate(String companyId,String placeId, String employmentCd, String employeeId, GeneralDate targetDate, IntegrationOfDaily integrationOfDaily) {
//		/*日別実績(Work)の退避*/
//		val copyIntegrationOfDaily = integrationOfDaily;
//		// 実績データの計算
//		return this.calculateRecord(companyId,placeId, employmentCd, employeeId, targetDate, integrationOfDaily);
//	}
//	
//
//	/**
//	 * 実績データの計算
//	 * @param companyId 会社コード
//	 * @param employeeId 社員コード
//	 * @param targetDate 対象日
//	 * @param integrationOfDaily 
//	 */
//	private IntegrationOfDaily calculateRecord(String companyId,String placeId,String employmentCd,String employeeId,GeneralDate targetDate, IntegrationOfDaily integrationOfDaily) {
//
//		/*1日の計算範囲クラスを作成*/
//		val oneRange = createOneDayRange(companyId,employeeId,targetDate,integrationOfDaily);
//		/*勤務種類の取得*/
//		val workInfo = integrationOfDaily.getWorkInformation();
//		val workType = this.workTypeRepository.findByPK(companyId, workInfo.getRecordWorkInformation().getWorkTypeCode().v())
//				.get(); // 要確認：勤務種類マスタが削除されている場合は考慮しない？
//		
//		
//		
//		/*就業時間帯勤務区分*/
//		Optional<WorkTime> workTime = workTimeRepository.findByCode(companyId, integrationOfDaily.getWorkInformation().getScheduleWorkInformation().getWorkTimeCode().toString());
//		/*労働制*/
//		DailyCalculationPersonalInformation personalInfo = getPersonInfomation(companyId
//																				, placeId
//																				, employmentCd
//																				, employeeId
//																				, targetDate);
//		if(!workType.getAttendanceHolidayAttr().equals(AttendanceHolidayAttr.HOLIDAY)) {
////												WorkTimeDivision workTimeDivision,
//			//---------------------------------Repositoryが整理されるまでの一時的な作成-------------------------------------------
//			//休憩管理(BreakManagement)
//			DeductionTotalTime deductionTotalTime = DeductionTotalTime.of(TimeWithCalculation.sameTime(new AttendanceTime(0)),
//																		  TimeWithCalculation.sameTime(new AttendanceTime(0)),
//																		  TimeWithCalculation.sameTime(new AttendanceTime(0)));
//			//流動勤務の休憩時間帯
//			FluRestTime fluRestTime = new FluRestTime(new FixRestTimeSetting(Collections.emptyList()),
//													  new FluRestTimeGroup(Collections.emptyList(),false,new FluRestTimeSetting(new AttendanceTime(0), new AttendanceTime(0))),
//													  true);
//			
//			//流動固定休憩設定
//			FluidPrefixBreakTimeSet fluidPrefixBreakTimeSet = new FluidPrefixBreakTimeSet(false,FluidPrefixBreakTimeOfCalcMethod.ReferToMaster,false,false);
//			
//			/*所定時間設定取得*/
//			val predetermineTimeSet = //workTimeSetRepository.findByCode(companyId, integrationOfDaily.getWorkInformation().getEmployeeId());
//			
//			//固定勤務の設定
//			
//			  //就業、残業時間帯を作成時に使用する就業、残業時間帯
//			  //こいつがマスタからとってこれないと、上記時間帯が作れない(ここは聞く)
//			Map<AmPmClassification,FixWeekdayWorkTime> fixedWorkTimeSetMap = new HashMap<>();
////			fixedWorkTimeSetMap.put(AmPmClassification.ONE_DAY, new FixWeekdayWorkTime( new FixedWorkTimeSet(Collections.emptyList(),Collections.emptyList()) ,
////																						new FixRestTime(new FixRestTimeSetting(Collections.emptyList())) ,
////																						AmPmClassification.ONE_DAY));
//			
//			  //こいつが作れないと休出時間帯が作れない(ここは聞く)
//			FixOffdayWorkTime offDayWorkTime = new FixOffdayWorkTime(new FixRestTimezoneSet(Collections.emptyList()),Collections.emptyList());
//			
//			
//			FixedWorkSetting fixedWorkSetting = new FixedWorkSetting(integrationOfDaily.getWorkInformation().getScheduleWorkInformation().getWorkTimeCode().toString(),
//																	 offDayWorkTime,
//																 	 fixedWorkTimeSetMap,
//																 	 new WorkTimeCommonSet(false));
//			
//			//0時跨ぎ計算設定
//			OverDayEndCalcSet overDayEndCalcSet = new OverDayEndCalcSet(companyId,
//																		UseAtr.NOTUSE,
//																		new OverDayEndAggregateFrameSet(Collections.emptyList(),Collections.emptyList(),Collections.emptyList()),
//																		new OverDayEndCalcSetOfStatutoryHoliday(UseAtr.NOTUSE,UseAtr.NOTUSE,UseAtr.NOTUSE),
//																		new OverDayEndCalcSetOfExcessHoliday(UseAtr.NOTUSE,UseAtr.NOTUSE,UseAtr.NOTUSE),
//																		new OverDayEndCalcSetOfExcessSpecialHoliday(UseAtr.NOTUSE,UseAtr.NOTUSE,UseAtr.NOTUSE),
//																		new OverDayEndCalcSetOfWeekDay(UseAtr.NOTUSE,UseAtr.NOTUSE,UseAtr.NOTUSE));
//
//			//残業時間の自動計算設定
//			AutoCalcSet autoCalSetting = new AutoCalcSet(AutoCalculationCategoryOutsideHours.CalculateEmbossing);
//			AutoCalculationOfOverTimeWork autoCalcOverTimeWork = new AutoCalculationOfOverTimeWork(autoCalSetting,
//																									autoCalSetting,
//																									autoCalSetting,
//																									autoCalSetting,
//																									autoCalSetting,
//																									autoCalSetting); 
//			/*前日の勤務情報取得  */
//			WorkInformationOfDaily yestarDayWorkInfo = workInformationOfDailyRepository.find(companyId, employeeId, targetDate.addDays(-1)).orElse(workInformationOfDailyRepository.find(companyId, employeeId, targetDate).get());
//			val yesterDay = this.workTypeRepository.findByPK(companyId, yestarDayWorkInfo.getRecordWorkInformation().getWorkTypeCode().v());
//			/*翌日の勤務情報取得 */
//			WorkInformationOfDaily tomorrowDayWorkInfo = workInformationOfDailyRepository.find(companyId, employeeId, targetDate.addDays(1)).orElse(workInformationOfDailyRepository.find(companyId, employeeId, targetDate).get());
//			val tomorrow = this.workTypeRepository.findByPK(companyId, tomorrowDayWorkInfo.getRecordWorkInformation().getWorkTypeCode().v());
//			//---------------------------------Repositoryが整理されるまでの一時的な作成-------------------------------------------
//			
//			oneRange.decisionWorkClassification(workTime.get().getWorkTimeDivision(),
////												DailyCalculationPersonalInformation personalInfo,
//												personalInfo,
////												WorkTimeMethodSet  setMethod,
//												workTime.get().getWorkTimeDivision().getWorkTimeMethodSet(),
////												RestClockManageAtr clockManage, 固定勤務のみの時間帯計算であるため一旦固定値を渡しておく
//												RestClockManageAtr.isClockManage,
////												OutingTimeOfDailyPerformance dailyGoOutSheet,
//												new OutingTimeOfDailyPerformance(employeeId,targetDate,Collections.emptyList()),
////												BreakSetOfCommon  commonSet,
//												new BreakSetOfCommon(CalcMethodIfLeaveWorkDuringBreakTime.NotRecordAll),
////												FixRestCalcMethod fixedCalc,
//												FixRestCalcMethod.ReferToMaster,
////												FluidBreakTimeOfCalcMethod  fluidSet,
//												FlowRestCalcMethod.ReferToMaster,
////												BreakTimeManagement breakmanage,
//												new BreakTimeManagement(BreakTimeOfDaily.sameTotalTime(deductionTotalTime),Collections.emptyList()),
////												Optional<FluRestTime>  fluRestTime,
//												Optional.of(fluRestTime),
////												FluidPrefixBreakTimeSet fluidprefixBreakTimeSet,
//												fluidPrefixBreakTimeSet,
////												PredetermineTimeSet predetermineTimeSet,
//												predetermineTimeSet.get(),
////												FixedWorkSetting fixedWorkSetting,
//												fixedWorkSetting,//(repository確認中)
////												WorkTimeCommonSet workTimeCommonSet,
//												new WorkTimeCommonSet(false),
////												BonusPaySetting bonusPaySetting,
//												BonusPaySetting.createFromJavaType(companyId,
//																					"01"/*ここは聞く*/,
//																					"テスト加給設定"/*ここは聞く*/,
//																					Collections.emptyList(),
//																					Collections.emptyList()
//																					),
////												List<OverTimeHourSet> overTimeHourSetList , 残業時間の時間帯設定
//												Collections.emptyList(),//固定勤務の設定にぶら下がっている 
////												FixOffdayWorkTime fixOff, 固定勤務の休日出勤用勤務時間帯
//												offDayWorkTime,//固定勤務の設定にぶら下がっている
////												OverDayEndCalcSet dayEndSet,
//												overDayEndCalcSet,
////												List<HolidayWorkFrameTimeSheet> holidayTimeWorkItem,
//												Collections.emptyList(),
////												WorkType beforeDay,
//												yesterDay.get(),
////												WorkType toDay,
//												workType,
////												WorkType afterDay,
//												tomorrow.get(),
////												BreakdownTimeDay breakdownTimeDay,
//												new BreakdownTimeDay(new AttendanceTime(4),new AttendanceTime(4),new AttendanceTime(8)),
////												DailyTime dailyTime,
//												personalInfo.getStatutoryWorkTime(),
////										 		AutoCalculationOfOverTimeWork autoCalculationSet,
//												autoCalcOverTimeWork,
////												StatutoryOverTimeWorkSet statutorySet,
//												new StatutoryOverTimeWorkSet(false),
////												StatutoryPrioritySet prioritySet
//												StatutoryPrioritySet.priorityNormalOverTimeWork,
//												//WorkTime
//												workTime.get()
//												);
//
//
//		}
//		else {
//			/*1日休暇時の時間帯作成*/
//			/*出勤日の時間帯作成*/
//			//val calcRangeOfOneDay =　/*現在作業分の対応範囲外のため保留 2017.10.16*/;
//		}
//		/*時間の計算*/
//		integrationOfDaily = AttendanceTimeOfDailyPerformance.calcTimeResult(oneRange,integrationOfDaily);
//		
//		/*手修正、申請範囲された項目を元に戻す(ベトナムが作成している可能性があるため、確認後)*/
//		/*日別実績への項目移送*/
//		return integrationOfDaily;
//	}
//	
//	/**
//	 * １日の範囲クラス作成
//	 * @param companyId 会社コード
//	 * @param employeeId 社員ID
//	 * @param targetDate 対象日
//	 * @param integrationOfDaily 
//	 * @return
//	 */
//	private CalculationRangeOfOneDay createOneDayRange(String companyId, String employeeId, GeneralDate targetDate,IntegrationOfDaily integrationOfDaily) {
//		
//		/*所定時間設定取得*/
//		val predetermineTimeSet = workTimeSetRepository.findByCode(companyId, integrationOfDaily.getWorkInformation().getEmployeeId());
//		
//		/*1日の計算範囲取得*/
//		val calcRangeOfOneDay = new TimeSpanForCalc(predetermineTimeSet.get().getStartDateClock()
//												   ,predetermineTimeSet.get().getStartDateClock().forwardByMinutes(predetermineTimeSet.get().getRangeTimeDay().valueAsMinutes()));
//		
//		WorkInformationOfDaily toDayWorkInfo = workInformationOfDailyRepository.find(companyId, employeeId, targetDate).orElse(workInformationOfDailyRepository.find(companyId, employeeId, targetDate).get());
//		
//		/*ジャストタイムの判断するための設定取得*/
////		boolean justLate        = /*就業時間帯から固定・流動・フレックスの設定を取得してくるロジック*/;
////		boolean justEarlyLeave  = /*就業時間帯から固定・流動・フレックスの設定を取得してくるロジック*/;
////		/*日別実績の出退勤時刻セット*/
//		//TimeLeavingOfDailyPerformance attendanceLeavingOfDaily = timeLeavingOfDailyPerformanceRepository.
//		WorkStamp attendance = new WorkStamp(new TimeWithDayAttr(480),new TimeWithDayAttr(480), new WorkLocationCD("01"), StampSourceInfo.CORRECTION_RECORD_SET );
//		WorkStamp leaving = new WorkStamp(new TimeWithDayAttr(960),new TimeWithDayAttr(960), new WorkLocationCD("01"), StampSourceInfo.CORRECTION_RECORD_SET );
//		TimeActualStamp stamp = new TimeActualStamp(attendance,leaving,1);
//		TimeLeavingWork timeLeavingWork = new TimeLeavingWork(new WorkNo(BigDecimal.valueOf((long)1)),stamp,stamp);
//		List<TimeLeavingWork> timeLeavingWorkList = new ArrayList<>();
//		timeLeavingWorkList.add(timeLeavingWork);
//		TimeLeavingOfDailyPerformance attendanceLeavingOfDaily = new TimeLeavingOfDailyPerformance(employeeId,new WorkTimes(1),timeLeavingWorkList,targetDate); 
//		
//		return new CalculationRangeOfOneDay(Finally.empty(),  
//											Finally.empty(),
//											calcRangeOfOneDay,
//											attendanceLeavingOfDaily,/*出退勤*/
//											PredetermineTimeSetForCalc.convertMastarToCalc(predetermineTimeSet.get())/*所定時間帯(計算用)*/,
//											Finally.empty(),
//											toDayWorkInfo);
//	}
//	
//	/**
//	 * 労働制を取得する
//	 * @return 日別計算用の個人情報
//	 */
//	private DailyCalculationPersonalInformation getPersonInfomation(String companyId,String placeId,String employmentCd,String employeeId,GeneralDate targetDate) {
//		Optional<EmploymentContractHistory> employmentContractHistory = this.employmentContractHistoryAdopter.findByEmployeeIdAndBaseDate(employeeId, targetDate);
//		if(employmentContractHistory.isPresent()) {
//			throw new RuntimeException("Can't get WorkingSystem");
//		}
//		// 労働制
//		return getOfStatutoryWorkTime.getDailyTimeFromStaturoyWorkTime(employmentContractHistory.get().getWorkingSystem()
//																	, companyId
//																	, placeId
//																	, employmentCd
//																	, employeeId
//																	, targetDate);
//	}
//}

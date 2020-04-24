package nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.val;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeOfDaily;
import nts.uk.ctx.at.record.dom.calculationattribute.BonusPayAutoCalcSet;
import nts.uk.ctx.at.record.dom.calculationattribute.CalAttrOfDailyPerformance;
import nts.uk.ctx.at.record.dom.daily.AdditionAtr;
import nts.uk.ctx.at.record.dom.daily.LateTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.LeaveEarlyTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.record.dom.daily.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.bonuspaytime.BonusPayTime;
import nts.uk.ctx.at.record.dom.daily.calcset.CalcMethodOfNoWorkingDay;
import nts.uk.ctx.at.record.dom.daily.holidaypriorityorder.CompanyHolidayPriorityOrder;
import nts.uk.ctx.at.record.dom.daily.midnight.MidNightTimeSheet;
import nts.uk.ctx.at.record.dom.daily.withinworktime.WithinStatutoryTimeOfDaily;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.ActualWorkTimeSheetAtr;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.BonusPayAtr;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CommonFixedWorkTimezoneSet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.ConditionAtr;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.DeductionAtr;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.DeductionClassification;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.DeductionOffSetTime;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.DeductionTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.FlexWithinWorkTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.IntegrationOfDaily;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.LateLeaveEarlyManagementTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.ManagePerPersonDailySet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.ManageReGetClass;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.TimeSheetOfDeductionItem;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.VacationAddTime;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.VacationClass;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.valueobject.WorkHour;
import nts.uk.ctx.at.record.dom.raborstandardact.flex.SettingOfFlexWork;
import nts.uk.ctx.at.record.dom.raisesalarytime.SpecificDateAttrOfDailyPerfor;
import nts.uk.ctx.at.record.dom.stamp.GoOutReason;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.PremiumAtr;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.BonusPaySetting;
import nts.uk.ctx.at.shared.dom.common.TimeOfDay;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.CalcurationByActualTimeAtr;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.DeductLeaveEarly;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayAddtionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayCalcMethodSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.PremiumCalcMethodDetailOfHoliday;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkDeformedLaborAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkFlexAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkRegularAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkTimeCalcMethodDetailOfHoliday;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.TimeSpanForDailyCalc;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.DailyUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.HolidayAdditionAtr;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.StatutoryDivision;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalRaisingSalarySetting;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.LateEarlyAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneLateEarlySet;
import nts.uk.ctx.at.shared.dom.worktime.flexset.CoreTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDailyAtr;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceHolidayAttr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 就業時間内時間帯
 * @author keisuke_hoshina
 *
 */
//@AllArgsConstructor
@Getter
public class WithinWorkTimeSheet implements LateLeaveEarlyManagementTimeSheet{

	//就業時間内時間枠
	private final List<WithinWorkTimeFrame> withinWorkTimeFrame;
	//短時間時間帯
	private List<TimeSheetOfDeductionItem> shortTimeSheet;
	//早退判断時刻
	private List<LeaveEarlyDecisionClock> leaveEarlyDecisionClock = new ArrayList<>();
	//遅刻判断時刻
	private List<LateDecisionClock> lateDecisionClock = new ArrayList<>();
	//外出休暇使用残時間
	//private Optional<TimevacationUseTimeOfDaily> outingVacationUseTime = Optional.empty();
	
	private Map<GoOutReason,TimevacationUseTimeOfDaily> outingVacationUseTime = new HashMap<>();
	
	//休暇使用合計残時間未割当
	private Finally<AttendanceTime> timeVacationAdditionRemainingTime = Finally.of(new AttendanceTime(0));
	
	public WithinWorkTimeSheet(List<WithinWorkTimeFrame> withinWorkTimeFrame,List<TimeSheetOfDeductionItem> shortTimeSheets,Optional<LateDecisionClock> lateDecisionClock,Optional<LeaveEarlyDecisionClock> leaveEarlyDecisionClock) {
		this.withinWorkTimeFrame = withinWorkTimeFrame;
		this.shortTimeSheet = shortTimeSheets;
		if(lateDecisionClock != null
			&& lateDecisionClock.isPresent())
			this.lateDecisionClock.add(lateDecisionClock.get());
		if(leaveEarlyDecisionClock != null
			&& leaveEarlyDecisionClock.isPresent())
			this.leaveEarlyDecisionClock.add(leaveEarlyDecisionClock.get());
	}
	
	
	/**
	 * 就業時間内時間帯
	 * 
	 * @param workType 勤務種類クラス
	 * @param predetermineTimeSet 所定時間設定クラス
	 * @param fixedWorkSetting 固定勤務設定クラス
	 * @param workTimeCommonSet 就業時間帯の共通設定
	 * @param deductionTimeSheet 控除時間帯
	 * @param bonuspaySetting 加給設定
	 * @param personalCondition 
	 * @param vacationClass 
	 * @param late 
	 * @param late 
	 * @param leaveEarly 
	 * @param leaveEarly 
	 * @param workingSystem 
	 * @param workingSystem 
	 * @param illegularAddSetting 
	 * @param illegularAddSetting 
	 * @param flexAddSetting 
	 * @param flexAddSetting 
	 * @param regularAddSetting 
	 * @param regularAddSetting 
	 * @param holidayAddtionSet 
	 * @param holidayAddtionSet 
	 * @param calcMethod 
	 * @param calcMethod 
	 * @param autoCalcAtr 
	 * @param autoCalcAtr 
	 * @param flexCalcMethod 
	 * @param flexCalcMethod 
	 * @param workTimeDailyAtr 
	 * @param workTimeDailyAtr 
	 * @param workTimeCode 
	 * @param workTimeCode 
	 * @param preFlexTime 
	 * @param preFlexTime 
	 * @param timeVacationAdditionRemainingTime 
	 * @param predetermineTimeForSet 
	 * @param timeVacationAdditionRemainingTime 
	 * @param ootsukaIWFlag 
	 * @return 就業時間内時間帯
	 */
	public static WithinWorkTimeSheet createAsFixed(TimeLeavingWork timeLeavingWork,
													WorkType workType,
													PredetermineTimeSetForCalc predetermineTimeSetForCalc,
													CommonFixedWorkTimezoneSet lstHalfDayWorkTimezone,
													WorkTimezoneCommonSet workTimeCommonSet,
													DeductionTimeSheet deductionTimeSheet,
													Optional<BonusPaySetting> bonuspaySetting,
													MidNightTimeSheet midNightTimeSheet,
													int workNo,
													Optional<CoreTimeSetting> coreTimeSetting,
													HolidayCalcMethodSet holidayCalcMethodSet,
													WorkTimezoneLateEarlySet workTimezoneLateEarlySet,
													DailyUnit dailyUnit,List<TimeSheetOfDeductionItem> breakTimeList, 
													VacationClass vacationClass, boolean late, boolean leaveEarly, 
													WorkingSystem workingSystem, 
													WorkDeformedLaborAdditionSet illegularAddSetting, 
													WorkFlexAdditionSet flexAddSetting, 
													WorkRegularAdditionSet regularAddSetting, 
													HolidayAddtionSet holidayAddtionSet, 
													CalcMethodOfNoWorkingDay calcMethod, 
													Optional<SettingOfFlexWork> flexCalcMethod, 
													Optional<WorkTimeDailyAtr> workTimeDailyAtr, 
													Optional<WorkTimeCode> workTimeCode, 
													AttendanceTime preFlexTime, 
													Finally<AttendanceTime> timeVacationAdditionRemainingTime,
													Optional<WorkTimezoneCommonSet> commonSetting,WorkingConditionItem conditionItem,
													Optional<PredetermineTimeSetForCalc> predetermineTimeSetByPersonInfo,
													Optional<DeductLeaveEarly> deductLeaveEarly,
													Optional<SpecificDateAttrOfDailyPerfor> specificDateAttrSheets) {
		
		List<WithinWorkTimeFrame> timeFrames = new ArrayList<>();
		List<TimeSheetOfDeductionItem> shortTimeSheets = new ArrayList<>();
		//遅刻判断時刻
		Optional<LateDecisionClock> lateDesClock = Optional.empty();
		//遅刻判断時刻
		Optional<LeaveEarlyDecisionClock> leaveEarlyDesClock = Optional.empty();
	
		if(workType.isWeekDayAttendance()) {
			//遅刻判断時刻を求める
			lateDesClock = LateDecisionClock.create(
					workNo,
					predetermineTimeSetForCalc,
					workTimeCommonSet.getLateEarlySet().getOtherEmTimezoneLateEarlySet(LateEarlyAtr.LATE).getGraceTimeSet(),
					timeLeavingWork,
					coreTimeSetting,workType,breakTimeList);
			//早退判断時刻を求める
			leaveEarlyDesClock = LeaveEarlyDecisionClock.create(
					workNo,
					predetermineTimeSetForCalc,
					workTimeCommonSet.getLateEarlySet().getOtherEmTimezoneLateEarlySet(LateEarlyAtr.EARLY).getGraceTimeSet(),
					timeLeavingWork,
					coreTimeSetting,workType,breakTimeList);
				
			//就業時間内枠の作成
			timeFrames = isWeekDayProcess(
					timeLeavingWork,
					workType,
					predetermineTimeSetForCalc,
					 lstHalfDayWorkTimezone,
					 workTimeCommonSet,
					 deductionTimeSheet,
					 bonuspaySetting,
					 midNightTimeSheet,
					 workNo,
					lateDesClock,
					leaveEarlyDesClock,
					 holidayCalcMethodSet,
					 workTimezoneLateEarlySet,
					coreTimeSetting,
					dailyUnit,breakTimeList, 
					 vacationClass, 
					 late, 
					 leaveEarly, 
					 workingSystem, 
					 illegularAddSetting, 
					 flexAddSetting, 
					 regularAddSetting, 
					 holidayAddtionSet, 
					 calcMethod, 
					 flexCalcMethod, 
					 workTimeDailyAtr, 
					 workTimeCode, 
					 preFlexTime, 
					 timeVacationAdditionRemainingTime,
					 commonSetting,
					 conditionItem,
					 predetermineTimeSetByPersonInfo,
					 deductLeaveEarly, specificDateAttrSheets
					 );
			//短時間時間帯の取得
			shortTimeSheets = toHaveShortTime(timeFrames,deductionTimeSheet);
		}
		return new WithinWorkTimeSheet(timeFrames,shortTimeSheets,lateDesClock,leaveEarlyDesClock);
	}

	
	
	
	/**
	 * 就業時間内時間帯の作成
	 * @param personalCondition 
	 * @param vacationClass 
	 * @param late 
	 * @param leaveEarly 
	 * @param workingSystem 
	 * @param illegularAddSetting 
	 * @param flexAddSetting 
	 * @param regularAddSetting 
	 * @param holidayAddtionSet 
	 * @param calcMethod 
	 * @param autoCalcAtr 
	 * @param flexCalcMethod 
	 * @param workTimeDailyAtr 
	 * @param workTimeCode 
	 * @param preFlexTime 
	 * @param timeVacationAdditionRemainingTime 
	 * @param ootsukaIWFlag 
	 * @param workType　勤務種類クラス
	 * @param predetermineTimeSet 所定時間設定クラス
	 * @param fixedWorkSetting  固定勤務設定クラス
	 * @return 就業時間内時間帯クラス
	 */
	private static List<WithinWorkTimeFrame> isWeekDayProcess(
			TimeLeavingWork timeLeavingWork,
			WorkType workType,
			PredetermineTimeSetForCalc predetermineTimeForSet,
			CommonFixedWorkTimezoneSet lstHalfDayWorkTimezone,
			WorkTimezoneCommonSet workTimeCommonSet,
			DeductionTimeSheet deductionTimeSheet,
			Optional<BonusPaySetting> bonuspaySetting,
			MidNightTimeSheet midNightTimeSheet,
			int workNo,
			Optional<LateDecisionClock> lateDesClock,
			Optional<LeaveEarlyDecisionClock> leaveEarlyDesClock,
			HolidayCalcMethodSet holidayCalcMethodSet,
			WorkTimezoneLateEarlySet workTimezoneLateEarlySet,
			Optional<CoreTimeSetting> coreTimeSetting,
			DailyUnit dailyUnit,List<TimeSheetOfDeductionItem> breakTimeList, 
			VacationClass vacationClass, 
			boolean late, 
			boolean leaveEarly, 
			WorkingSystem workingSystem, 
			WorkDeformedLaborAdditionSet illegularAddSetting, 
			WorkFlexAdditionSet flexAddSetting, 
			WorkRegularAdditionSet regularAddSetting, 
			HolidayAddtionSet holidayAddtionSet, 
			CalcMethodOfNoWorkingDay calcMethod, 
			Optional<SettingOfFlexWork> flexCalcMethod, 
			Optional<WorkTimeDailyAtr> workTimeDailyAtr, 
			Optional<WorkTimeCode> workTimeCode, 
			AttendanceTime preFlexTime, 
			Finally<AttendanceTime> timeVacationAdditionRemainingTime,
			Optional<WorkTimezoneCommonSet> commonSetting,WorkingConditionItem conditionItem,
			Optional<PredetermineTimeSetForCalc> predetermineTimeSetByPersonInfo,
			Optional<DeductLeaveEarly> deductLeaveEarly,
			Optional<SpecificDateAttrOfDailyPerfor> specificDateAttrSheets
			) {
		
		val timeFrames = new ArrayList<WithinWorkTimeFrame>();
		//出退勤時刻と↑の重複時間帯と重複部分取得
		List<WithinWorkTimeFrame> withinWorkTimeFrame = duplicatedByStamp(timeLeavingWork,lstHalfDayWorkTimezone,workType);
		
		for(WithinWorkTimeFrame duplicateTimeSheet :withinWorkTimeFrame) {
			//就業時間内時間枠の作成
			timeFrames.add(WithinWorkTimeFrame.createWithinWorkTimeFrame(duplicateTimeSheet,
																		 deductionTimeSheet,
																		 bonuspaySetting,
																		 midNightTimeSheet,
																		 lateDesClock,
																		 leaveEarlyDesClock,
																		 timeLeavingWork,
																		 holidayCalcMethodSet,
																		 workNo,
																		 workTimezoneLateEarlySet,
																		 predetermineTimeForSet.getTimeSheets(workType.getDailyWork().decisionNeedPredTime(),workNo),
																		 coreTimeSetting,breakTimeList,workType,predetermineTimeForSet,commonSetting, specificDateAttrSheets,
																		 duplicateTimeSheet.getWorkingHoursTimeNo().v().intValue() == 1,
																		 duplicateTimeSheet.getWorkingHoursTimeNo().v().intValue() == withinWorkTimeFrame.size()
																		 ));
		}
		/*所定内割増時間の時間帯作成*/
		return predetermineWithinPremiumTime(dailyUnit.getDailyTime(),predetermineTimeForSet.getAdditionSet().getPredTime().getPredetermineWorkTime(),timeFrames, 
				vacationClass, 
				workType, 
				late, 
				leaveEarly, 
				workingSystem, 
				illegularAddSetting, 
				flexAddSetting, 
				regularAddSetting, 
				holidayAddtionSet, 
				holidayCalcMethodSet, 
				calcMethod, 
				flexCalcMethod, 
				workTimeDailyAtr, 
				workTimeCode, 
				preFlexTime, 
				coreTimeSetting, 
				predetermineTimeForSet, 
				timeVacationAdditionRemainingTime,
				lateDesClock, 
				leaveEarlyDesClock,
				dailyUnit,commonSetting,
				conditionItem,
				predetermineTimeSetByPersonInfo,
				deductLeaveEarly,
				deductionTimeSheet,
				NotUseAtr.NOT_USE
				);
	}
	
	
	/**
	 * 自身が持つ短時間勤務時間帯(控除)を収集
	 * @return　短時間勤務時間帯
	 */
	public static List<TimeSheetOfDeductionItem> toHaveShortTime(List<WithinWorkTimeFrame> afterWithinPremiumCreate,DeductionTimeSheet dedTimeSheet) {
		List<TimeSheetOfDeductionItem> returnList = new ArrayList<>();
		List<TimeSheetOfDeductionItem> allInFrameShortTimeSheets = new ArrayList<>();
		for(WithinWorkTimeFrame frame : afterWithinPremiumCreate) {
			allInFrameShortTimeSheets.addAll(frame.collectShortTimeSheetInFrame());
		}
		//マスタ側の控除時間帯ループ
		val loopList = dedTimeSheet.getForRecordTimeZoneList().stream().filter(tc -> tc.getDeductionAtr().isChildCare()).collect(Collectors.toList());
		for(TimeSheetOfDeductionItem masterDedItem : loopList) {
			List<TimeSheetOfDeductionItem> notDupShort = Arrays.asList(masterDedItem);
			for(TimeSheetOfDeductionItem dedItem:allInFrameShortTimeSheets) {
				//ループ中短時間のどこにも所属していない時間帯
				List<TimeSpanForDailyCalc> timeReplace = notDupShort.stream().map(tc -> tc.getTimeSheet().getNotDuplicationWith(dedItem.getTimeSheet())).flatMap(List::stream).collect(Collectors.toList());
				timeReplace = timeReplace.stream().filter(ts -> ts.lengthAsMinutes() > 0).collect(Collectors.toList());
				notDupShort = timeReplace.stream().map(ts -> dedItem.replaceTimeSpan(Optional.of(ts))).collect(Collectors.toList());
			}
			if(!notDupShort.isEmpty()) {
				returnList.addAll(notDupShort);
			}
		}		
		return returnList;
	}


	/**
	 * 所定内割増時間の分割
	 * @param timeFrames 
	 * @param statutoryTime　法定労働時間
	 * @param predTime　所定時間
	 */
	private static List<WithinWorkTimeFrame> predetermineWithinPremiumTime(TimeOfDay statutoryTime, int predTime, List<WithinWorkTimeFrame> timeFrames,
			 VacationClass vacationClass, 
			 WorkType workType, 
			 boolean late, 
			 boolean leaveEarly, 
			 WorkingSystem workingSystem, 
			 WorkDeformedLaborAdditionSet illegularAddSetting, 
			 WorkFlexAdditionSet flexAddSetting, 
			 WorkRegularAdditionSet regularAddSetting, 
			 HolidayAddtionSet holidayAddtionSet, 
			 HolidayCalcMethodSet holidayCalcMethodSet, 
			 CalcMethodOfNoWorkingDay calcMethod, 
			 Optional<SettingOfFlexWork> flexCalcMethod, 
			 Optional<WorkTimeDailyAtr> workTimeDailyAtr, 
			 Optional<WorkTimeCode> workTimeCode, 
			 AttendanceTime preFlexTime, 
			 Optional<CoreTimeSetting> coreTimeSetting, 
			 PredetermineTimeSetForCalc predetermineTimeSetForCalc, 
			 Finally<AttendanceTime> timeVacationAdditionRemainingTime,
			 Optional<LateDecisionClock> lateDesClock,
			 Optional<LeaveEarlyDecisionClock> leaveEarlyDesClock,
			 DailyUnit dailyUnit,Optional<WorkTimezoneCommonSet> commonSetting,WorkingConditionItem conditionItem,
			 Optional<PredetermineTimeSetForCalc> predetermineTimeSetByPersonInfo,Optional<DeductLeaveEarly> deductLeaveEarly,
			 DeductionTimeSheet dedTimeSheet,NotUseAtr lateEarlyMinusAtr) {
		AttendanceTime restPredeterminePremiumTime = calcPredeterminePremiumTime(predTime, statutoryTime,
				  new WithinWorkTimeSheet(timeFrames, toHaveShortTime(timeFrames,dedTimeSheet),lateDesClock, leaveEarlyDesClock),
				  vacationClass,
				  workType,
				  late, 
				  leaveEarly, 
				  workingSystem, 
				  illegularAddSetting, 
				  flexAddSetting, 
				  regularAddSetting, 
				  holidayAddtionSet, 
				  holidayCalcMethodSet, 
				  calcMethod, 
				  flexCalcMethod, 
				  workTimeDailyAtr, 
				  workTimeCode, 
				  preFlexTime, 
				  coreTimeSetting, 
				  predetermineTimeSetForCalc, 
				  timeVacationAdditionRemainingTime,
				  dailyUnit,commonSetting,conditionItem,
				  predetermineTimeSetByPersonInfo,
				  deductLeaveEarly,
				  lateEarlyMinusAtr);
		List<WithinWorkTimeFrame> copyWithinWorkTime = timeFrames.stream().sorted((first,second) -> second.getTimeSheet().getStart().compareTo(first.getTimeSheet().getStart())).collect(Collectors.toList());
		for(WithinWorkTimeFrame workTimeFrame : copyWithinWorkTime) {
			if(restPredeterminePremiumTime.greaterThan(0)) {
				AttendanceTime redeterminePremiumTime = (restPredeterminePremiumTime.greaterThan(new AttendanceTime(workTimeFrame.getTimeSheet().getTimeSpan().lengthAsMinutes())))
														?new AttendanceTime(workTimeFrame.getTimeSheet().lengthAsMinutes())
														:restPredeterminePremiumTime;
														
				Optional<TimeSpanForDailyCalc> timeSpan = workTimeFrame.createTimeSpan(workTimeFrame.getTimeSheet(), new TimeWithDayAttr(redeterminePremiumTime.valueAsMinutes()));
				if(!timeSpan.isPresent())
					continue;
				workTimeFrame.setPremiumTimeSheetInPredetermined(Optional.of(new WithinPremiumTimeSheetForCalc(new TimeSpanForDailyCalc(timeSpan.get().getEnd(),workTimeFrame.getTimeSheet().getEnd()))));
				
				//ここ注意　minusMinutes(XXXX) XXXは作成した所定内割増時間分？それともredeterminePremiumTime？
				restPredeterminePremiumTime = restPredeterminePremiumTime.minusMinutes(timeSpan.get().lengthAsMinutes());
				
			}
			else {
				break;
			}
		}
		return copyWithinWorkTime;
	}

	/**
	 * 所定内割増時間の計算
	 * @param predetermineTimeSet 計算用所定時 
	 * @param statutoryTime 法定労働時間
	 */
	public static AttendanceTime calcPredeterminePremiumTime(int predTime,TimeOfDay statutoryTime,
			 WithinWorkTimeSheet withinTimeSheet, 
			 VacationClass vacationClass, 
			 WorkType workType, 
			 boolean late, 
			 boolean leaveEarly, 
			 WorkingSystem workingSystem, 
			 WorkDeformedLaborAdditionSet illegularAddSetting, 
			 WorkFlexAdditionSet flexAddSetting, 
			 WorkRegularAdditionSet regularAddSetting, 
			 HolidayAddtionSet holidayAddtionSet, 
			 HolidayCalcMethodSet holidayCalcMethodSet, 
			 CalcMethodOfNoWorkingDay calcMethod, 
			 Optional<SettingOfFlexWork> flexCalcMethod, 
			 Optional<WorkTimeDailyAtr> workTimeDailyAtr, 
			 Optional<WorkTimeCode> workTimeCode, 
			 AttendanceTime preFlexTime, 
			 Optional<CoreTimeSetting> coreTimeSetting, 
			 PredetermineTimeSetForCalc predetermineTimeSetForCalc, 
			 Finally<AttendanceTime> timeVacationAdditionRemainingTime,
			 DailyUnit dailyUnit,Optional<WorkTimezoneCommonSet> commonSetting,WorkingConditionItem conditionItem,
			 Optional<PredetermineTimeSetForCalc> predetermineTimeSetByPersonInfo,
			 Optional<DeductLeaveEarly> deductLeaveEarly,
			 NotUseAtr lateEarlyMinusAtr) {
		if( statutoryTime.greaterThan(0)
			&& predTime > statutoryTime.valueAsMinutes()) {
			return decisionPredeterminePremiumTime(statutoryTime, 
												  withinTimeSheet,
												  vacationClass,
												  workType,
												  late, 
												  leaveEarly, 
												  workingSystem, 
												  illegularAddSetting, 
												  flexAddSetting, 
												  regularAddSetting, 
												  holidayAddtionSet, 
												  holidayCalcMethodSet, 
												  calcMethod, 
												  flexCalcMethod, 
												  workTimeDailyAtr, 
												  workTimeCode, 
												  preFlexTime, 
												  coreTimeSetting, 
												  predetermineTimeSetForCalc, 
												  timeVacationAdditionRemainingTime,
												  dailyUnit,commonSetting,
												  conditionItem,
												  predetermineTimeSetByPersonInfo,
												  deductLeaveEarly,
												  lateEarlyMinusAtr
												  );
		}
		else {
			return new AttendanceTime(0);
		}
	}
	
	/**
	 * 所定内割増時間を計算
	 * @param predetermineTime　計算用所定時間
	 * @param statutoryTime 法定労働時間
	 * @param withinTimeSheet 
	 * @param personalCondition 
	 * @param vacationClass 
	 * @param workType 
	 * @param late 
	 * @param leaveEarly 
	 * @param workingSystem 
	 * @param illegularAddSetting 
	 * @param flexAddSetting 
	 * @param regularAddSetting 
	 * @param holidayAddtionSet 
	 * @param holidayCalcMethodSet 
	 * @param calcMethod 
	 * @param autoCalcAtr 
	 * @param flexCalcMethod 
	 * @param workTimeDailyAtr 
	 * @param workTimeCode 
	 * @param preFlexTime 
	 * @param coreTimeSetting 
	 * @param predetermineTimeSetForCalc 
	 * @param timeVacationAdditionRemainingTime 
	 * @return 所定内割増時間
	 */
	public static AttendanceTime decisionPredeterminePremiumTime(TimeOfDay statutoryTime, 
																 WithinWorkTimeSheet withinTimeSheet, 
																 VacationClass vacationClass, 
																 WorkType workType, 
																 boolean late, 
																 boolean leaveEarly, 
																 WorkingSystem workingSystem, 
																 WorkDeformedLaborAdditionSet illegularAddSetting, 
																 WorkFlexAdditionSet flexAddSetting, 
																 WorkRegularAdditionSet regularAddSetting, 
																 HolidayAddtionSet holidayAddtionSet, 
																 HolidayCalcMethodSet holidayCalcMethodSet, 
																 CalcMethodOfNoWorkingDay calcMethod, 
																 Optional<SettingOfFlexWork> flexCalcMethod, 
																 Optional<WorkTimeDailyAtr> workTimeDailyAtr, 
																 Optional<WorkTimeCode> workTimeCode, 
																 AttendanceTime preFlexTime, 
																 Optional<CoreTimeSetting> coreTimeSetting, 
																 PredetermineTimeSetForCalc predetermineTimeSetForCalc, 
																 Finally<AttendanceTime> timeVacationAdditionRemainingTime,
																 DailyUnit dailyUnit,Optional<WorkTimezoneCommonSet> commonSetting,
																 WorkingConditionItem conditionItem,
																 Optional<PredetermineTimeSetForCalc> predetermineTimeSetByPersonInfo,
																 Optional<DeductLeaveEarly> deductLeaveEarly,
																 NotUseAtr lateEarlyMinusAtr) {
		//就業時間計算
		AttendanceTime workTime = WithinStatutoryTimeOfDaily.calcWithinStatutoryTime(withinTimeSheet,
														   vacationClass,
														   workType,
														   late, 
														   leaveEarly, 
														   workingSystem, 
														   illegularAddSetting, 
														   flexAddSetting, 
														   regularAddSetting, 
														   holidayAddtionSet, 
														   holidayCalcMethodSet, 
														   calcMethod, 
														   flexCalcMethod, 
														   workTimeDailyAtr, 
														   workTimeCode, 
														   preFlexTime, 
														   coreTimeSetting, 
														   predetermineTimeSetForCalc, 
														   timeVacationAdditionRemainingTime,
														   dailyUnit,commonSetting,
														   conditionItem,
														   predetermineTimeSetByPersonInfo,
														   deductLeaveEarly,
														   lateEarlyMinusAtr
														   );
		return workTime.minusMinutes(statutoryTime.valueAsMinutes());
	}
	
	/***
	 * 出勤、退勤時刻との重複部分を調べる
	 * @param workingHourSet 就業時間枠の時間帯
	 * @param timeLeavingWork　出退勤
	 * @return　時間枠の時間帯と出退勤の重複時間
	 */
	private static List<WithinWorkTimeFrame> duplicatedByStamp(
			TimeLeavingWork timeLeavingWork,CommonFixedWorkTimezoneSet lstHalfDayWorkTimezone,WorkType workType) {
		List<WithinWorkTimeFrame> returnList = new ArrayList<>();
		Optional<TimeSpanForCalc> duplicatedRange = Optional.empty(); 
		val attendanceHolidayAttr = workType.getAttendanceHolidayAttr();
		val emTimeZoneSet = getWorkingHourSetByAmPmClass(lstHalfDayWorkTimezone, attendanceHolidayAttr);
		
		for(EmTimeZoneSet timeZone:emTimeZoneSet) {
			duplicatedRange = timeZone.getTimezone().getDuplicatedWith(timeLeavingWork.getTimespan());
			if(duplicatedRange.isPresent()) {
				returnList.add(new WithinWorkTimeFrame(timeZone.getEmploymentTimeFrameNo(),
														new TimeSpanForDailyCalc(duplicatedRange.get()),
														timeZone.getTimezone().getRounding(),
														new ArrayList<>(),
														new ArrayList<>(),
														new ArrayList<>(),
														Optional.empty(),
														new ArrayList<>(),
														Optional.empty(),
														Optional.empty()));
			}else {
				returnList.add(new WithinWorkTimeFrame(timeZone.getEmploymentTimeFrameNo(),
													   new TimeSpanForDailyCalc(timeZone.getTimezone().getStart(),timeZone.getTimezone().getStart()),
													   timeZone.getTimezone().getRounding(),
													   new ArrayList<>(),
													   new ArrayList<>(),
													   new ArrayList<>(),
													   Optional.empty(),
													   new ArrayList<>(),
													   Optional.empty(),
													   Optional.empty()));
			}
		}
		return returnList;
	}

	/**
	 * 平日出勤の出勤時間帯を取得
	 * @param fixedWorkSetting 固定勤務設定クラス
	 * @param attendanceHolidayAttr 出勤休日区分
	 * @return 出勤時間帯
	 */
	private static List<EmTimeZoneSet> getWorkingHourSetByAmPmClass(
			CommonFixedWorkTimezoneSet lstHalfDayWorkTimezone,
			AttendanceHolidayAttr attendanceHolidayAttr) {
		
		switch (attendanceHolidayAttr) {
		case FULL_TIME:
		case HOLIDAY:
			return lstHalfDayWorkTimezone.getFixedWorkTimezoneMap().get(AmPmAtr.ONE_DAY).getLstWorkingTimezone();
		case MORNING:
			return lstHalfDayWorkTimezone.getFixedWorkTimezoneMap().get(AmPmAtr.AM).getLstWorkingTimezone();
		case AFTERNOON:
			return lstHalfDayWorkTimezone.getFixedWorkTimezoneMap().get(AmPmAtr.PM).getLstWorkingTimezone();
		default:
			throw new RuntimeException("unknown attendanceHolidayAttr" + attendanceHolidayAttr);
		}
	}
	
//	/**
//	 * 引数のNoと一致する遅刻判断時刻を取得する
//	 * @param workNo
//	 * @return　遅刻判断時刻
//	 */
//	public LateDecisionClock getlateDecisionClock(int workNo) {
//		List<LateDecisionClock> clockList = this.lateDecisionClock.stream().filter(tc -> tc.getWorkNo()==workNo).collect(Collectors.toList());
//		if(clockList.size()>1) {
//			throw new RuntimeException("Exist duplicate workNo : " + workNo);
//		}
//		return clockList.get(0);
//	}
	
//	/**
//	 * 引数のNoと一致する早退判断時刻を取得する
//	 * @param workNo
//	 * @return　早退判断時刻
//	 */
//	public LeaveEarlyDecisionClock getleaveEarlyDecisionClock(int workNo) {
//		List<LeaveEarlyDecisionClock> clockList = this.leaveEarlyDecisionClock.stream().filter(tc -> tc.getWorkNo()==workNo).collect(Collectors.toList());
//		if(clockList.size()>1) {
//			throw new RuntimeException("Exist duplicate workNo : " + workNo);
//		}
//		return clockList.get(0);
//	}
//	
	/**
	 * コアタイムのセット
	 * @param coreTimeSetting コアタイム時間設定
	 */
	public WithinWorkTimeSheet createWithinFlexTimeSheet(CoreTimeSetting coreTimeSetting,DeductionTimeSheet dedTimeSheet) {
		List<TimeSpanForDailyCalc> duplicateCoreTimeList = new ArrayList<>();
		for(WithinWorkTimeFrame workTimeFrame : this.withinWorkTimeFrame) {
			Optional<TimeSpanForDailyCalc> duplicateSpan = workTimeFrame.getTimeSheet().getDuplicatedWith(new TimeSpanForDailyCalc(coreTimeSetting.getCoreTimeSheet().getStartTime(),
																									 					 coreTimeSetting.getCoreTimeSheet().getEndTime())); 
			if(duplicateSpan.isPresent()) {
				duplicateCoreTimeList.add(duplicateSpan.get());
			}
		}
		TimeWithDayAttr startTime = new TimeWithDayAttr(0);
		TimeWithDayAttr endTime = new TimeWithDayAttr(0);
		if(!duplicateCoreTimeList.isEmpty()) {
			startTime = duplicateCoreTimeList.stream().sorted((first,second)-> first.getStart().compareTo(second.getStart())).collect(Collectors.toList()).get(0).getStart();
			endTime = duplicateCoreTimeList.stream().sorted((first,second)-> first.getStart().compareTo(second.getStart())).collect(Collectors.toList()).get(duplicateCoreTimeList.size() - 1).getEnd();
			/*フレックス時間帯に入れる*/
			return new FlexWithinWorkTimeSheet(this.withinWorkTimeFrame,toHaveShortTime(this.withinWorkTimeFrame,dedTimeSheet),Optional.of(new TimeSpanForDailyCalc(startTime, endTime)));
		}
		else {
			return new FlexWithinWorkTimeSheet(this.withinWorkTimeFrame,toHaveShortTime(this.withinWorkTimeFrame,dedTimeSheet),Optional.empty());
		}
	}
	
	
	/**
	 * 就業時間(法定内用)の計算
	 * @param calcActualTime 実働のみで計算する
	 * @param dedTimeSheet　控除時間帯
	 * @return 就業時間の計算結果
	 */
	public AttendanceTime calcWorkTimeForStatutory(PremiumAtr premiumAtr,
			   CalcurationByActualTimeAtr calcActualTime,
			   AttendanceTime timevacationUseTimeOfDaily,
			   VacationClass vacationClass,
			   StatutoryDivision statutoryDivision,
			   WorkType workType,
			   PredetermineTimeSetForCalc predetermineTimeSet,
			   Optional<WorkTimeCode> siftCode,
			   boolean late,  //日別実績の計算区分.遅刻早退の自動計算設定.遅刻
			   boolean leaveEarly,  //日別実績の計算区分.遅刻早退の自動計算設定.早退
			   WorkingSystem workingSystem,
			   WorkDeformedLaborAdditionSet illegularAddSetting,
			   WorkFlexAdditionSet flexAddSetting,
			   WorkRegularAdditionSet regularAddSetting,
			   HolidayAddtionSet holidayAddtionSet,
			   HolidayCalcMethodSet holidayCalcMethodSet, DailyUnit dailyUnit, Optional<WorkTimezoneCommonSet> commonSetting,
			   WorkingConditionItem conditionItem,Optional<PredetermineTimeSetForCalc> predetermineTimeSetByPersonInfo,Optional<CoreTimeSetting> coreTimeSetting
			   ,DeductLeaveEarly deductLeaveEarly,NotUseAtr lateEarlyMinusAtr) {
		return calcWorkTime(
					premiumAtr,
					calcActualTime,
				    vacationClass,
				    timevacationUseTimeOfDaily,
				    statutoryDivision,
				    workType,
				    predetermineTimeSet,
				   siftCode,
				    late,  //日別実績の計算区分.遅刻早退の自動計算設定.遅刻
				    leaveEarly,  //日別実績の計算区分.遅刻早退の自動計算設定.早退
				    workingSystem,
				    illegularAddSetting,
				    flexAddSetting,
				    regularAddSetting,
				    holidayAddtionSet,
				    holidayCalcMethodSet,
				    dailyUnit,commonSetting,
				    conditionItem,
				    predetermineTimeSetByPersonInfo,coreTimeSetting
				    ,HolidayAdditionAtr.HolidayAddition.convertFromCalcByActualTimeToHolidayAdditionAtr(calcActualTime),
				    deductLeaveEarly,
				    lateEarlyMinusAtr
				).getWorkTime();
	}
	
	
	/**
	 * 就業時間の計算(控除時間差し引いた後)
	 * @param dailyUnit 
	 * @param leaveEarlyTime 
	 * @param lateTime 
	 * @param withinpremiumTime 
	 * @return 就業時間
	 */
	public WorkHour calcWorkTime(PremiumAtr premiumAtr,
									   CalcurationByActualTimeAtr calcActualTime,
									   VacationClass vacationClass,
									   AttendanceTime timevacationUseTimeOfDaily,
									   StatutoryDivision statutoryDivision,
									   WorkType workType,
									   PredetermineTimeSetForCalc predetermineTimeSet,
									   Optional<WorkTimeCode> siftCode,
									   boolean late,  //日別実績の計算区分.遅刻早退の自動計算設定.遅刻
									   boolean leaveEarly,  //日別実績の計算区分.遅刻早退の自動計算設定.早退
									   WorkingSystem workingSystem,
									   WorkDeformedLaborAdditionSet illegularAddSetting,
									   WorkFlexAdditionSet flexAddSetting,
									   WorkRegularAdditionSet regularAddSetting,
									   HolidayAddtionSet holidayAddtionSet,
									   HolidayCalcMethodSet holidayCalcMethodSet, DailyUnit dailyUnit, Optional<WorkTimezoneCommonSet> commonSetting,
									   WorkingConditionItem conditionItem,Optional<PredetermineTimeSetForCalc> predetermineTimeSetByPersonInfo,Optional<CoreTimeSetting> coreTimeSetting
									   ,HolidayAdditionAtr holidayAddition,DeductLeaveEarly deductLeaveEarly,NotUseAtr lateEarlyMinusAtr
									   ) {
		
		//就業時間計算
		AttendanceTime workTime = calcWorkTimeBeforeDeductPremium(holidayAddition,
																  timevacationUseTimeOfDaily,
																  workingSystem,
																  regularAddSetting,
																  illegularAddSetting, 
																  flexAddSetting,
																  holidayAddtionSet,
																  late,  //日別実績の計算区分.遅刻早退の自動計算設定.遅刻
																  leaveEarly,  //日別実績の計算区分.遅刻早退の自動計算設定.早退
																  holidayCalcMethodSet,
																  premiumAtr,commonSetting,coreTimeSetting,
																  calcActualTime,
																  vacationClass,
																  statutoryDivision,
																  workType,
																  predetermineTimeSet,
																  siftCode,
																  dailyUnit,
																  conditionItem,
																  predetermineTimeSetByPersonInfo,
																  lateEarlyMinusAtr
																  );
		//フレの時は上限値制御をしたくない。
		//フレの時は法定労働時間が0として設定されてきているため↓のif文でフレをスキップセル
		if(dailyUnit.getDailyTime().greaterThan(0)) {
			AttendanceTime withinpremiumTime =  predetermineTimeSet != null 
												&& predetermineTimeSet.getpredetermineTime(workType.getDailyWork()).greaterThan(dailyUnit.getDailyTime().valueAsMinutes())
					?new AttendanceTime(this.withinWorkTimeFrame.stream()
																					  .filter(tc -> tc.getPremiumTimeSheetInPredetermined().isPresent())
																					  .map(tc -> tc.getPremiumTimeSheetInPredetermined().get().getWithinPremiumtimeSheet().lengthAsMinutes())
																					  .collect(Collectors.summingInt(tc -> tc)))
																					  
					:new AttendanceTime(0);
			//就業時間に含まれてしまっている所定内割増時間を差し引く
			workTime = workTime.minusMinutes(withinpremiumTime.valueAsMinutes());
		}
		
		int holidayAddTime = 0;
		if(holidayAddition.isHolidayAddition()) {
			//休暇加算時間を計算
			VacationAddTime vacationAddTime = vacationClass.calcVacationAddTime(premiumAtr,
																				workType,
																				workingSystem,
																				siftCode, 
																				conditionItem,
																				Optional.of(holidayAddtionSet),
																				holidayCalcMethodSet,
																				predetermineTimeSet == null ? Optional.empty():Optional.of(predetermineTimeSet),
																				predetermineTimeSetByPersonInfo
																				);
			holidayAddTime = vacationAddTime.calcTotaladdVacationAddTime();
			//休暇加算時間を加算
			workTime = workTime.addMinutes(holidayAddTime);
		}
		
		//コア無しフレックスで遅刻した場合の時間補正
		if(holidayCalcMethodSet.getWorkTimeCalcMethodOfHoliday().getAdvancedSet().isPresent()&&coreTimeSetting.isPresent()&&!coreTimeSetting.get().isUseTimeSheet()) {
			//遅刻時間を就業時間から控除しない場合
			if(!holidayCalcMethodSet.getWorkTimeCalcMethodOfHoliday().getAdvancedSet().get().isDeductLateLeaveEarly(commonSetting)) {
				TimeWithCalculation calcedLateTime = calcNoCoreCalcLateTimeForWorkTime(workTime,
																			DeductionAtr.Appropriate,
																			late,
																			holidayCalcMethodSet,
																			coreTimeSetting,
																			commonSetting
																			);
				//コア無しフレックス遅刻時間　＞　0 の場合
				if(calcedLateTime.getCalcTime().greaterThan(0)) {
					workTime = coreTimeSetting.get().getMinWorkTime();
				}
			}
		}
//		if(!deductLeaveEarly.isDeduct()) {
//			val calcLateLeaveTime = calcLateLeaveEarlyinWithinWorkTime();
//			workTime = workTime.minusMinutes(calcLateLeaveTime.getCalcTime().valueAsMinutes());
//		}
		
			
		return new WorkHour(workTime, new AttendanceTime(holidayAddTime));
	}
	

	
	/**
	 * 就業時間内時間枠の全枠分の就業時間を算出する
	 * (所定内割増時間を差し引く前)
	 * @return 就業時間
	 */
	public AttendanceTime calcWorkTimeBeforeDeductPremium(HolidayAdditionAtr holidayAdditionAtr,
														  AttendanceTime timevacationUseTimeOfDaily,
														  WorkingSystem workingSystem,
														  WorkRegularAdditionSet addSettingOfRegularWork,
														  WorkDeformedLaborAdditionSet addSettingOfIrregularWork, 
														  WorkFlexAdditionSet addSettingOfFlexWork,
														  HolidayAddtionSet holidayAddtionSet,
														  boolean late,  //日別実績の計算区分.遅刻早退の自動計算設定.遅刻
														  boolean leaveEarly,  //日別実績の計算区分.遅刻早退の自動計算設定.早退
														  HolidayCalcMethodSet holidayCalcMethodSet,
														  PremiumAtr premiumAtr,Optional<WorkTimezoneCommonSet> commonSetting,Optional<CoreTimeSetting> coreTimeSetting,
														  
														  CalcurationByActualTimeAtr calcActualTime,
														   VacationClass vacationClass,
														   StatutoryDivision statutoryDivision,
														   WorkType workType,
														   PredetermineTimeSetForCalc predetermineTimeSet,
														   Optional<WorkTimeCode> siftCode,DailyUnit dailyUnit,WorkingConditionItem conditionItem,
														   Optional<PredetermineTimeSetForCalc> predetermineTimeSetByPersonInfo,
														   NotUseAtr lateEarlyMinusAtr
														  ) {
		boolean decisionDeductChild = decisionDeductChild(premiumAtr,holidayCalcMethodSet,commonSetting);
		AttendanceTime workTime = new AttendanceTime(0);
		for(WithinWorkTimeFrame copyItem: withinWorkTimeFrame) {
			//workTime.addMinutes(copyItem.calcActualWorkTimeAndWorkTime(holidayAdditionAtr,dedTimeSheet).v());
			workTime = new AttendanceTime(workTime.v()+copyItem.calcActualWorkTimeAndWorkTime(holidayAdditionAtr,
																							  timevacationUseTimeOfDaily,
																							  workingSystem,
																							  addSettingOfRegularWork,
																							  addSettingOfIrregularWork, 
																							  addSettingOfFlexWork,
																							  holidayAddtionSet,
																							  late,  //日別実績の計算区分.遅刻早退の自動計算設定.遅刻
																							  leaveEarly,  //日別実績の計算区分.遅刻早退の自動計算設定.早退
																							  holidayCalcMethodSet,
																							  premiumAtr,commonSetting,
																							  lateEarlyMinusAtr
																							  ).v());
			if(decisionDeductChild) {
				//遅刻が持つ育児or介護時間
				if(copyItem.getLateTimeSheet().isPresent()&&copyItem.getLateTimeSheet().get().getDecitionTimeSheet(DeductionAtr.Deduction).isPresent()) {
					workTime = workTime.addMinutes(copyItem.getLateTimeSheet().get().getDecitionTimeSheet(DeductionAtr.Deduction).get().calcDedTimeByAtr(DeductionAtr.Deduction,ConditionAtr.Child).valueAsMinutes());
					workTime = workTime.addMinutes(copyItem.getLateTimeSheet().get().getDecitionTimeSheet(DeductionAtr.Deduction).get().calcDedTimeByAtr(DeductionAtr.Deduction,ConditionAtr.Care).valueAsMinutes());
				}
				//早退が持つ育児or介護時間
				if(copyItem.getLeaveEarlyTimeSheet().isPresent()&&copyItem.getLeaveEarlyTimeSheet().get().getDecitionTimeSheet(DeductionAtr.Deduction).isPresent()) {
					workTime = workTime.addMinutes(copyItem.getLeaveEarlyTimeSheet().get().getDecitionTimeSheet(DeductionAtr.Deduction).get().calcDedTimeByAtr(DeductionAtr.Deduction,ConditionAtr.Child).valueAsMinutes());
					workTime = workTime.addMinutes(copyItem.getLeaveEarlyTimeSheet().get().getDecitionTimeSheet(DeductionAtr.Deduction).get().calcDedTimeByAtr(DeductionAtr.Deduction,ConditionAtr.Care).valueAsMinutes());
				}
			}		
		}
		return workTime;
	}
	
	/**
	 * 遅刻早退が保持する育児時間を控除するか判定
	 * @param premiumAtr
	 * @param holidayCalcMethodSet
	 * @param commonSetting
	 * @return
	 */
	private boolean decisionDeductChild(PremiumAtr premiumAtr,HolidayCalcMethodSet holidayCalcMethodSet,Optional<WorkTimezoneCommonSet> commonSetting) {
		boolean decisionDeductChild = false;
		if(premiumAtr.isRegularWork()) {			
			Optional<WorkTimeCalcMethodDetailOfHoliday> advancedSet = holidayCalcMethodSet.getWorkTimeCalcMethodOfHoliday().getAdvancedSet();	
				if(advancedSet.isPresent()
					&& advancedSet.get().getNotDeductLateLeaveEarly().isEnableSetPerWorkHour()
					&&commonSetting.isPresent()) {
					if(advancedSet.isPresent()&&advancedSet.get().getCalculateIncludCareTime()==NotUseAtr.USE
							&&commonSetting.get().getLateEarlySet().getCommonSet().isDelFromEmTime()) {
						decisionDeductChild = true;
					}
				}else {
					if(advancedSet.isPresent()&&advancedSet.get().getCalculateIncludCareTime()==NotUseAtr.USE
							&&advancedSet.get().getNotDeductLateLeaveEarly().isDeduct()) {
						decisionDeductChild = true;
					}
				}
		}else {
			Optional<PremiumCalcMethodDetailOfHoliday> advanceSet = holidayCalcMethodSet.getPremiumCalcMethodOfHoliday().getAdvanceSet();
				if(advanceSet.isPresent()){
					if(advanceSet.get().getNotDeductLateLeaveEarly().isEnableSetPerWorkHour()&&commonSetting.isPresent()) {
						if(advanceSet.get().getCalculateIncludCareTime()==NotUseAtr.USE
								&&commonSetting.get().getLateEarlySet().getCommonSet().isDelFromEmTime()) {
							decisionDeductChild = true;
						}
					}else {
						if(advanceSet.get().getCalculateIncludCareTime()==NotUseAtr.USE&&
								advanceSet.get().getNotDeductLateLeaveEarly().isDeduct()) {
							decisionDeductChild = true;
						}
					}
				}
		}
		return decisionDeductChild;
	}
	
	/**コア無しフレックス遅刻時間の計算
	 * 
	 * @return
	 */
	public TimeWithCalculation calcNoCoreCalcLateTimeForWorkTime(AttendanceTime workTime,
													  DeductionAtr deductionAtr,
													   boolean late,  //日別実績の計算区分.遅刻早退の自動計算設定.遅刻
													   HolidayCalcMethodSet holidayCalcMethodSet,
													   Optional<CoreTimeSetting> coreTimeSetting,
													   Optional<WorkTimezoneCommonSet> commonSetting
													  ){		
		//遅刻時間の計算
		AttendanceTime lateTime = calcLateTimeForWorkTime(workTime,deductionAtr,
											   holidayCalcMethodSet,
											   coreTimeSetting,
											   commonSetting
											   );
		//時間休暇との相殺処理(いずれ実装が必要)
		
		//遅刻早退の自動計算設定．遅刻をチェック
		if(late) {
			return TimeWithCalculation.sameTime(lateTime);
		}
		return TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(0), lateTime);
	}
	
	/**
	 * 遅刻時間の計算
	 * フレックスのコア無しの場合専用の遅刻時間の計算処理(就業時間計算用)
	 * @param workTime
	 * @param deductionAtr
	 * @param coreTimeSetting
	 * @param holidayCalcMethodSet
	 * @param commonSetting
	 * @return
	 */
	public AttendanceTime calcLateTimeForWorkTime(AttendanceTime workTime,DeductionAtr deductionAtr,
			   HolidayCalcMethodSet holidayCalcMethodSet,
			   Optional<CoreTimeSetting> coreTimeSetting,
			   Optional<WorkTimezoneCommonSet> commonSetting
			) {
		//パラメータ「控除区分」＝”控除”　かつ　控除しない
		if(deductionAtr.isDeduction()&&holidayCalcMethodSet.getWorkTimeCalcMethodOfHoliday().getAdvancedSet().isPresent()&&!holidayCalcMethodSet.getWorkTimeCalcMethodOfHoliday().getAdvancedSet().get().isDeductLateLeaveEarly(commonSetting)) {
			return new AttendanceTime(0);
		}
		//遅刻時間の計算   (最低勤務時間　－　パラメータで受け取った就業時間)
		AttendanceTime result = coreTimeSetting.get().getMinWorkTime().minusMinutes(workTime.valueAsMinutes());
		//計算結果がマイナスの場合は0
		if(result.valueAsMinutes()<0) {
			return new AttendanceTime(0);
		}
		return result;
	}
	
	
	
	/**コア無しフレックス遅刻時間の計算
	 * 
	 * @return
	 */
	public TimeWithCalculation calcNoCoreCalcLateTime(DeductionAtr deductionAtr,
													  
													   PremiumAtr premiumAtr, 
													   CalcurationByActualTimeAtr calcActualTime,
													   VacationClass vacationClass,
													   AttendanceTime timevacationUseTimeOfDaily,
													   StatutoryDivision statutoryDivision,
													   WorkType workType,
													   PredetermineTimeSetForCalc predetermineTimeSet,
													   Optional<WorkTimeCode> siftCode,
													   boolean late,  //日別実績の計算区分.遅刻早退の自動計算設定.遅刻
													   boolean leaveEarly,  //日別実績の計算区分.遅刻早退の自動計算設定.早退
													   WorkingSystem workingSystem,
													   WorkDeformedLaborAdditionSet illegularAddSetting,
													   WorkFlexAdditionSet flexAddSetting,
													   WorkRegularAdditionSet regularAddSetting,
													   HolidayAddtionSet holidayAddtionSet,
													   HolidayCalcMethodSet holidayCalcMethodSet,
													   Optional<CoreTimeSetting> coreTimeSetting,
													   DailyUnit dailyUnit,
													   Optional<WorkTimezoneCommonSet> commonSetting,
													   WorkingConditionItem conditionItem,
													   Optional<PredetermineTimeSetForCalc> predetermineTimeSetByPersonInfo,
		   												List<LateTimeOfDaily> lateTimes, 
		   												List<LeaveEarlyTimeOfDaily> leaveEarlyTime,
		   												Optional<DeductLeaveEarly> deductLeaveEarly,
		   												NotUseAtr lateEarlyMinusAtr
													  ){
		
		//遅刻時間の計算
		AttendanceTime lateTime = calcLateTime(deductionAtr,
											   premiumAtr,
											   calcActualTime,
											   vacationClass,
											   timevacationUseTimeOfDaily,
											   statutoryDivision,
											   workType,
											   predetermineTimeSet,
											   siftCode,
											   late,
											   leaveEarly,
											   workingSystem,
											   illegularAddSetting,
											   flexAddSetting,
											   regularAddSetting,
											   holidayAddtionSet,
											   holidayCalcMethodSet,
											   coreTimeSetting,
											   dailyUnit,
											   commonSetting,
											   conditionItem,
											   predetermineTimeSetByPersonInfo,
											   lateTimes,
											   leaveEarlyTime,
											   lateEarlyMinusAtr
											   );
		//時間休暇との相殺処理(いずれ実装が必要)
		//丸め
//		if(commonSetting.isPresent()) {
//			val setting = commonSetting.get().getLateEarlySet().getOtherEmTimezoneLateEarlySet(LateEarlyAtr.LATE).getRoundingSetByDedAtr(deductionAtr.isDeduction());
//			lateTime = new AttendanceTime(setting.round(lateTime.valueAsMinutes()));
//		}
//		
		//遅刻早退の自動計算設定．遅刻をチェック
		if(late) {
			return TimeWithCalculation.sameTime(lateTime);
		}
		return TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(0), lateTime);
	}
	
	/**
	 * 遅刻時間の計算
	 * フレックスのコア無しの場合専用の遅刻時間の計算処理
	 * @param workTime
	 * @param deductionAtr
	 * @param coreTimeSetting
	 * @param holidayCalcMethodSet
	 * @param commonSetting
	 * @return
	 */
	public AttendanceTime calcLateTime(DeductionAtr deductionAtr,
			   PremiumAtr premiumAtr, 
			   CalcurationByActualTimeAtr calcActualTime,
			   VacationClass vacationClass,
			   AttendanceTime timevacationUseTimeOfDaily,
			   StatutoryDivision statutoryDivision,
			   WorkType workType,
			   PredetermineTimeSetForCalc predetermineTimeSet,
			   Optional<WorkTimeCode> siftCode,
			   boolean late,  //日別実績の計算区分.遅刻早退の自動計算設定.遅刻
			   boolean leaveEarly,  //日別実績の計算区分.遅刻早退の自動計算設定.早退
			   WorkingSystem workingSystem,
			   WorkDeformedLaborAdditionSet illegularAddSetting,
			   WorkFlexAdditionSet flexAddSetting,
			   WorkRegularAdditionSet regularAddSetting,
			   HolidayAddtionSet holidayAddtionSet,
			   HolidayCalcMethodSet holidayCalcMethodSet,
			   Optional<CoreTimeSetting> coreTimeSetting,
			   DailyUnit dailyUnit,
			   Optional<WorkTimezoneCommonSet> commonSetting,
			   WorkingConditionItem conditionItem,
			   Optional<PredetermineTimeSetForCalc> predetermineTimeSetByPersonInfo,
			   List<LateTimeOfDaily> lateTime, 
			   List<LeaveEarlyTimeOfDaily> leaveEarlyTime,
			   NotUseAtr lateEarlyMinusAtr
			) {
		//パラメータ「控除区分」＝”控除”　かつ　控除しない
		if(deductionAtr.isDeduction()&&holidayCalcMethodSet.getWorkTimeCalcMethodOfHoliday().getAdvancedSet().isPresent()&&!holidayCalcMethodSet.getWorkTimeCalcMethodOfHoliday().getAdvancedSet().get().isDeductLateLeaveEarly(commonSetting)) {
			return new AttendanceTime(0);
		}
		
		DeductLeaveEarly leaveLateset = new DeductLeaveEarly(1,1);
		if(flexAddSetting.getVacationCalcMethodSet().getWorkTimeCalcMethodOfHoliday().getAdvancedSet().isPresent()) {
			leaveLateset = flexAddSetting.getVacationCalcMethodSet().getWorkTimeCalcMethodOfHoliday().getAdvancedSet().get().getNotDeductLateLeaveEarly();
		}
		//コア無し計算遅刻時間
		AttendanceTime noCore = clacNoCoreWorkTime(premiumAtr,
				   								   calcActualTime,
				   								   vacationClass,
				   								   timevacationUseTimeOfDaily,
				   								   statutoryDivision,
				   								   workType,
				   								   predetermineTimeSet,
				   								   siftCode,
				   								   late,
				   								   leaveEarly,
				   								   workingSystem,
				   								   illegularAddSetting,
				   								   flexAddSetting,
				   								   regularAddSetting,
				   								   holidayAddtionSet,
				   								   holidayCalcMethodSet,
				   								   coreTimeSetting,
				   								   dailyUnit,
				   								   commonSetting,
				   								   conditionItem,
				   								   predetermineTimeSetByPersonInfo,
												   lateTime, 
												   leaveEarlyTime,
												   leaveLateset,
												   lateEarlyMinusAtr);
		//遅刻時間の計算   (最低勤務時間　－　パラメータで受け取った就業時間)
		AttendanceTime result = coreTimeSetting.get().getMinWorkTime().minusMinutes(noCore.valueAsMinutes());
		//計算結果がマイナスの場合は0
		if(result.valueAsMinutes()<0) {
			return new AttendanceTime(0);
		}
		return result;
	}
	
	/**
	 * コアタイム無し遅刻時間計算用の就業時間の計算
	 * 遅刻時間の計算時にのみ利用する（就業時間計算時には利用しない）
	 * @return
	 */
	public AttendanceTime clacNoCoreWorkTime(PremiumAtr premiumAtr, 
			   								 CalcurationByActualTimeAtr calcActualTime,
			   								 VacationClass vacationClass,
			   								AttendanceTime timevacationUseTimeOfDaily,
			   								 StatutoryDivision statutoryDivision,
			   								 WorkType workType,
			   								 PredetermineTimeSetForCalc predetermineTimeSet,
			   								 Optional<WorkTimeCode> siftCode,
			   								 boolean late,  //日別実績の計算区分.遅刻早退の自動計算設定.遅刻
			   								 boolean leaveEarly,  //日別実績の計算区分.遅刻早退の自動計算設定.早退
			   								 WorkingSystem workingSystem,
			   								 WorkDeformedLaborAdditionSet illegularAddSetting,
			   								 WorkFlexAdditionSet flexAddSetting,
			   								 WorkRegularAdditionSet regularAddSetting,
			   								 HolidayAddtionSet holidayAddtionSet,
			   								 HolidayCalcMethodSet holidayCalcMethodSet,
			   								 Optional<CoreTimeSetting> coreTimeSetting,
			   								 DailyUnit dailyUnit,
			   								 Optional<WorkTimezoneCommonSet> commonSetting,
			   								 WorkingConditionItem conditionItem,
			   								 Optional<PredetermineTimeSetForCalc> predetermineTimeSetByPersonInfo,
											 List<LateTimeOfDaily> lateTime, 
											 List<LeaveEarlyTimeOfDaily> leaveEarlyTime,
											 DeductLeaveEarly deductLeaveEarly,
											 NotUseAtr lateEarlyMinusAtr
			   								 ) {
		
		//遅刻、早退の控除設定を「控除する」に変更する
		HolidayCalcMethodSet changeHolidayCalcMethodSet = new HolidayCalcMethodSet(holidayCalcMethodSet.getPremiumCalcMethodOfHoliday(),holidayCalcMethodSet.getWorkTimeCalcMethodOfHoliday().changeDeduct());
		//就業時間帯の遅刻早退を控除するかを見る場合、就業時間帯の遅刻、早退の控除設定を「控除する」に変更する
		Optional<WorkTimezoneCommonSet> changeCommonSetting = commonSetting;
		if(holidayCalcMethodSet.getWorkTimeCalcMethodOfHoliday().getAdvancedSet().isPresent()&&
		   holidayCalcMethodSet.getWorkTimeCalcMethodOfHoliday().getAdvancedSet().get().getNotDeductLateLeaveEarly().isEnableSetPerWorkHour()&&
		   commonSetting.isPresent()) {
			 changeCommonSetting = Optional.of(commonSetting.get().changeWorkTimezoneLateEarlySet());
		}
		
		//就業時間（法定内用）の計算
		AttendanceTime result = calcWorkTime(premiumAtr,
											 calcActualTime,
											 vacationClass,
											 timevacationUseTimeOfDaily,
											 statutoryDivision,
											 workType,
											 predetermineTimeSet,
											 siftCode,
											 late,
											 leaveEarly,
											 workingSystem,
											 illegularAddSetting,
											 flexAddSetting,
											 regularAddSetting,
											 holidayAddtionSet,
											 changeHolidayCalcMethodSet,
											 dailyUnit,
											 changeCommonSetting,
											 conditionItem,
											 predetermineTimeSetByPersonInfo,
											 coreTimeSetting
											 ,HolidayAdditionAtr.HolidayAddition.convertFromCalcByActualTimeToHolidayAdditionAtr(calcActualTime),
											 deductLeaveEarly,
											 lateEarlyMinusAtr
				).getWorkTime();
		
		return result;
	}
	
	
//	/**
//	 * 遅刻早退時間の計算
//	 * @return
//	 */
//	public TimeWithCalculation calcLateLeaveEarlyinWithinWorkTime() {
//		TimeWithCalculation totalValue = TimeWithCalculation.sameTime(new AttendanceTime(0));
//		for(WithinWorkTimeFrame workTimeFrame : withinWorkTimeFrame) {
//			if(workTimeFrame.getLateTimeSheet().isPresent()) {
//				TimeWithCalculation timeValue = workTimeFrame.getLateTimeSheet().get().calcDedctionTime(true, NotUseAtr.USE);
//				totalValue = totalValue.addMinutes(timeValue.getTime(), timeValue.getCalcTime());
//			}
//			if(workTimeFrame.getLeaveEarlyTimeSheet().isPresent()) {
//				TimeWithCalculation timeValue = workTimeFrame.getLeaveEarlyTimeSheet().get().calcDedctionTime(true, NotUseAtr.USE);
//				totalValue = totalValue.addMinutes(timeValue.getTime(), timeValue.getCalcTime());
//			}
//		}
//		return totalValue;
//	}
	
//	//＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊
//	
//	//就業時間内時間帯クラスを作成　　（流動勤務）
//	public WithinWorkTimeSheet createAsFluidWork(
//			PredetermineTimeSetForCalc predetermineTimeSetForCalc,
//			WorkType workType,
//			WorkInfoOfDailyPerformance workInformationOfDaily,
//			FluidWorkSetting fluidWorkSetting,
//			DeductionTimeSheet deductionTimeSheet) {
//		//開始時刻を取得
//		TimeWithDayAttr startClock = getStartClock();
//		//所定時間帯、残業開始を補正
//		cllectPredetermineTimeAndOverWorkTimeStart();
//		//残業開始となる経過時間を取得
//		AttendanceTime elapsedTime = fluidWorkSetting.getWeekdayWorkTime().getWorkTimeSheet().getMatchWorkNoOverTimeWorkSheet(1).getFluidWorkTimeSetting().getElapsedTime();
//		//経過時間から終了時刻を計算
//		TimeWithDayAttr endClock = startClock.backByMinutes(elapsedTime.valueAsMinutes());
//		//就業時間帯の作成（一時的に作成）
//		TimeSpanForDailyCalc workTimeSheet = new TimeSpanForDailyCalc(startClock,endClock);
//		//控除時間帯を取得 (控除時間帯分ループ）
//		for(TimeSheetOfDeductionItem timeSheetOfDeductionItem : deductionTimeSheet.getForDeductionTimeZoneList()) {
//			//就業時間帯に重複する控除時間を計算
//			TimeSpanForDailyCalc duplicateTime = workTimeSheet.getDuplicatedWith(timeSheetOfDeductionItem.getTimeSheet().getSpan()).orElse(null);
//			//就業時間帯と控除時間帯が重複しているかチェック
//			if(duplicateTime!=null) {
//				//控除項目の時間帯に法定内区分をセット
//				timeSheetOfDeductionItem = new TimeSheetOfDeductionItem(
//						timeSheetOfDeductionItem.getTimeSheet().getSpan(),
//						timeSheetOfDeductionItem.getGoOutReason(),
//						timeSheetOfDeductionItem.getBreakAtr(),
//						timeSheetOfDeductionItem.getDeductionAtr(),
//						WithinStatutoryAtr.WithinStatutory);
//				//控除時間分、終了時刻をズラす
//				endClock.backByMinutes(duplicateTime.lengthAsMinutes());
//				//休暇加算するかチェックしてズラす
//				
//			}		
//		}
//		//就業時間内時間帯クラスを作成
//		
//		
//		
//	}
//	
//	/**
//	 * 開始時刻を取得　　（流動勤務（平日・就内））
//	 * @return
//	 */
//	public TimeWithDayAttr getStartClock() {
//		
//	}
//	
//	
//	//所定時間帯、残業開始を補正
//	public void cllectPredetermineTimeAndOverWorkTimeStart(
//			PredetermineTimeSetForCalc predetermineTimeSetForCalc,
//			WorkType workType,
//			WorkInfoOfDailyPerformance workInformationOfDaily) {
//		//所定時間帯を取得
//		predetermineTimeSetForCalc.correctPredetermineTimeSheet(workType.getDailyWork());
//		//予定所定時間が変更された場合に所定時間を変更するかチェック
//		//勤務予定と勤務実績の勤怠情報を比較
//		//勤務種類が休日出勤でないかチェック
//		if(
//				!workInformationOfDaily.isMatchWorkInfomation()||
//				workType.getDailyWork().isHolidayWork()
//				) {
//			return;
//		}
//		//就業時間帯の所定時間と予定時間を比較
//			
//		//計算用所定時間設定を所定終了ずらす時間分ズラす
//		
//		//流動勤務時間帯設定の残業時間帯を所定終了ずらす時間分ズラす
//		
//	}
//	
//	

	
//	/**
//	 * 渡した時間帯(List)を1つの時間帯に結合する
//	 * @param list
//	 * @return
//	 */
//	public TimeSpanForDailyCalc bondTimeSpan(List<TimeSpanForDailyCalc> list) {
//		TimeWithDayAttr start = list.stream().map(ts -> ts.getStart()).min(Comparator.naturalOrder()).get();
//		TimeWithDayAttr end =  list.stream().map(ts -> ts.getEnd()).max(Comparator.naturalOrder()).get();
//		TimeSpanForDailyCalc bondTimeSpan = new TimeSpanForDailyCalc(start, end);
//		return bondTimeSpan;
//	}


//	/**
//	 * 指定された計算区分を基に計算付き時間帯を作成する
//	 * @return
//	 */
//	public TimeWithCalculation calcClacificationjudge(boolean clacification , int calcTime) {
//		if(clacification) {
//			return TimeWithCalculation.sameTime(new AttendanceTime(calcTime));
//		}else {
//			return TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(0),new AttendanceTime(calcTime));
//		}
//	}
	
//	//遅刻早退時間帯（List）を1つの遅刻早退時間帯に結合する
//	public LateLeaveEarlyTimeSheet connect(List<LateLeaveEarlyTimeSheet> timeSheetList){
//		 return new LeaveEarlyTimeSheet(createMinStartMaxEndSpanForTimeSheet(timeSheetList),
//		 								createMinStartMaxEndSpanForCalcRange(timeSheetList),
//		 								extract(List<LeaveEarlyTimeSheet> timeSheetList),
//		 								Optional.empty(),
//		 								Optional.empty(),
//		 								Optional.empty());
//	}
//	//遅刻早退時間帯（List）の時間帯（丸め付）を1つの時間帯（丸め付）にする
//	public TimeSpanWithRounding createMinStartMaxEndSpanForTimeSheet(List<LateLeaveEarlyTimeSheet> timeSheetList){
//		TimeWithDayAttr start = timeSheetList.stream().map(ts -> ts.getTimeSheet().getStart()).min(Comparator.naturalOrder()).get();
//		TimeWithDayAttr end =  timeSheetList.stream().map(ts -> ts.getTimeSheet().getEnd()).max(Comparator.naturalOrder()).get();
//		return new TimeSpanWithRounding(start, end, rounding);//丸めはどうする？
//	}
//	//遅刻早退時間帯（List）の計算範囲を1つの計算範囲にする
//	public TimeSpanForDailyCalc createMinStartMaxEndSpanForCalcRange(List<LateLeaveEarlyTimeSheet> timeSheetList){
//		TimeWithDayAttr start = timeSheetList.stream().map(ts -> ts.getCalcrange().getStart()).min(Comparator.naturalOrder()).get();
//		TimeWithDayAttr end =  timeSheetList.stream().map(ts -> ts.getCalcrange().getEnd()).max(Comparator.naturalOrder()).get();
//		return new TimeSpanForDailyCalc(start,end);
//	}
//	//遅刻早退時間帯（List）の控除項目の時間帯（List）を1つの控除項目の時間帯（List）にする
//	public List<TimeSheetOfDeductionItem> extract(List<LateLeaveEarlyTimeSheet> timeSheetList){
//		return timeSheetList.stream().map(tc -> tc.getDeductionTimeSheets()).collect(Collectors.toList());
//	}
	
	
//	/**
//	 * 遅刻時間の休暇時間相殺
//	 * @return
//	 */
//	public DeductionOffSetTime calcDeductionOffSetTime(
//			TimevacationUseTimeOfDaily TimeVacationAdditionRemainingTime,//時間休暇使用残時間を取得する
//			LateTimeSheet lateTimeSheet,
//			DeductionAtr deductionAtr) {
//		TimeSpanForDailyCalc calcRange;
//		//計算範囲の取得
//		if(deductionAtr.isDeduction()) {//パラメータが控除の場合
//			calcRange = lateTimeSheet.getForDeducationTimeSheet().get().getTimeSheet().getSpan();
//		}else {//パラメータが計上の場合
//			calcRange = lateTimeSheet.getForRecordTimeSheet().get().getTimeSheet().getSpan();
//		}
//		//遅刻時間を求める
//		int lateRemainingTime = calcRange.lengthAsMinutes();
//		//時間休暇相殺を利用して相殺した各時間を求める
//		DeductionOffSetTime deductionOffSetTime = createDeductionOffSetTime(lateRemainingTime,TimeVacationAdditionRemainingTime);
//		
//		return 	deductionOffSetTime;
//	}
	
	
//	/**
//	 * 時間休暇相殺を利用して相殺した各時間を求める  （一時的に作成）
//	 * @return
//	 */
//	public DeductionOffSetTime createDeductionOffSetTime(int lateRemainingTime,TimevacationUseTimeOfDaily TimeVacationAdditionRemainingTime) {
//		
//		AttendanceTime timeAnnualLeaveUseTime = calcOffSetTime(lateRemainingTime,TimeVacationAdditionRemainingTime.getTimeAnnualLeaveUseTime());
//		lateRemainingTime -= timeAnnualLeaveUseTime.valueAsMinutes();
//
//		AttendanceTime timeCompensatoryLeaveUseTime = new AttendanceTime(0);
//		AttendanceTime sixtyHourExcessHolidayUseTime = new AttendanceTime(0);
//		AttendanceTime timeSpecialHolidayUseTime = new AttendanceTime(0);
//		
//		if(lateRemainingTime > 0) {
//			timeCompensatoryLeaveUseTime = calcOffSetTime(lateRemainingTime,TimeVacationAdditionRemainingTime.getTimeCompensatoryLeaveUseTime());
//			lateRemainingTime -= timeCompensatoryLeaveUseTime.valueAsMinutes();
//		}
//		
//		if(lateRemainingTime > 0) {
//			sixtyHourExcessHolidayUseTime = calcOffSetTime(lateRemainingTime,TimeVacationAdditionRemainingTime.getSixtyHourExcessHolidayUseTime());
//			lateRemainingTime -= sixtyHourExcessHolidayUseTime.valueAsMinutes();
//		}
//		
//		if(lateRemainingTime > 0) {
//			timeSpecialHolidayUseTime = calcOffSetTime(lateRemainingTime,TimeVacationAdditionRemainingTime.getTimeSpecialHolidayUseTime());
//			lateRemainingTime -= timeSpecialHolidayUseTime.valueAsMinutes();
//		}
//				
//		return new DeductionOffSetTime(
//				timeAnnualLeaveUseTime,
//				timeCompensatoryLeaveUseTime,
//				sixtyHourExcessHolidayUseTime,
//				timeSpecialHolidayUseTime);
//	}

	
	/**
	 * 
	 * @param lateRemainingTime 遅刻残数
	 * @param timeVacationUseTime　時間休暇使用時間
	 * @return
	 */
	public AttendanceTime calcOffSetTime(int lateRemainingTime,AttendanceTime timeVacationUseTime) {
		int offSetTime;
		//相殺する時間を計算（比較）する
		if(timeVacationUseTime.lessThanOrEqualTo(lateRemainingTime)) {
			offSetTime = timeVacationUseTime.valueAsMinutes();
		}else {
			offSetTime = lateRemainingTime;
		}
		return new AttendanceTime(offSetTime);
	}

		
	//＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊
	
	/**
	 * 就業時間内時間帯に入っている加給時間の計算
	 */
	public List<BonusPayTime> calcBonusPayTimeInWithinWorkTime(AutoCalRaisingSalarySetting raisingAutoCalcSet,BonusPayAutoCalcSet bonusPayAutoCalcSet,BonusPayAtr bonusPayAtr,CalAttrOfDailyPerformance calcAtrOfDaily) {
		List<BonusPayTime> bonusPayList = new ArrayList<>();
		for(WithinWorkTimeFrame timeFrame : withinWorkTimeFrame) {
			bonusPayList.addAll(timeFrame.calcBonusPay(ActualWorkTimeSheetAtr.WithinWorkTime,raisingAutoCalcSet,bonusPayAutoCalcSet,calcAtrOfDaily,bonusPayAtr));
		}
		//同じNo同士はここで加算し、Listのサイズを減らす
		return sumBonusPayTime(bonusPayList);
	}
	/**
	 * 就業時間内時間帯に入っている特定加給時間の計算
	 */
	public List<BonusPayTime> calcSpecifiedBonusPayTimeInWithinWorkTime(AutoCalRaisingSalarySetting raisingAutoCalcSet,BonusPayAutoCalcSet bonusPayAutoCalcSet,BonusPayAtr bonusPayAtr,CalAttrOfDailyPerformance calcAtrOfDaily) {
		List<BonusPayTime> bonusPayList = new ArrayList<>();
		for(WithinWorkTimeFrame timeFrame : withinWorkTimeFrame) {
			bonusPayList.addAll(timeFrame.calcSpacifiedBonusPay(ActualWorkTimeSheetAtr.WithinWorkTime,raisingAutoCalcSet,bonusPayAutoCalcSet, calcAtrOfDaily,bonusPayAtr));
		}
		//同じNo同士はここで加算し、Listのサイズを減らす
		return sumBonusPayTime(bonusPayList);
	}
	
	/**
	 * 同じ加給時間Ｎｏを持つものを１つにまとめる
	 * @param bonusPayTime　加給時間
	 * @return　Noでユニークにした加給時間List
	 */
	private List<BonusPayTime> sumBonusPayTime(List<BonusPayTime> bonusPayTime){
		List<BonusPayTime> returnList = new ArrayList<>();
		List<BonusPayTime> refineList = new ArrayList<>();
		for(int bonusPayNo = 1 ; bonusPayNo <= 10 ; bonusPayNo++) {
			refineList = getByBonusPayNo(bonusPayTime, bonusPayNo);
			if(refineList.size()>0) {
				returnList.add(new BonusPayTime(bonusPayNo,
												new AttendanceTime(refineList.stream().map(tc -> tc.getBonusPayTime().valueAsMinutes()).collect(Collectors.summingInt(tc -> tc))),
												TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(refineList.stream().map(tc -> tc.getWithinBonusPay().getTime().valueAsMinutes()).collect(Collectors.summingInt(tc -> tc))),
																							  new AttendanceTime(refineList.stream().map(tc -> tc.getWithinBonusPay().getCalcTime().valueAsMinutes()).collect(Collectors.summingInt(tc -> tc)))),
												TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(refineList.stream().map(tc -> tc.getExcessBonusPayTime().getTime().valueAsMinutes()).collect(Collectors.summingInt(tc -> tc))),
																							  new AttendanceTime(refineList.stream().map(tc -> tc.getExcessBonusPayTime().getCalcTime().valueAsMinutes()).collect(Collectors.summingInt(tc -> tc))))
												));
			}
		}
		return returnList;
	}
	
	/**
	 * 受け取った加給時間Ｎｏを持つ加給時間を取得
	 * @param bonusPayTime 加給時間
	 * @param bonusPayNo　加給時間Ｎｏ
	 * @return　加給時間リスト
	 */
	private List<BonusPayTime> getByBonusPayNo(List<BonusPayTime> bonusPayTime,int bonusPayNo){
		return bonusPayTime.stream().filter(tc -> tc.getBonusPayTimeItemNo() == bonusPayNo).collect(Collectors.toList());
	}
	
	/**
	 * 法定内深夜時間の計算
	 * @return　法定内深夜時間
	 */
	public AttendanceTime calcMidNightTime() {
		int totalMidNightTime = 0;
//		int totalDedTime = 0;
		totalMidNightTime = withinWorkTimeFrame.stream()
											   .filter(tc -> tc.getMidNightTimeSheet().isPresent())
											   .map(ts -> ts.getMidNightTimeSheet().get().calcTotalTime().v())
											   .collect(Collectors.summingInt(tc -> tc));
		
//		for(WithinWorkTimeFrame frametime : withinWorkTimeFrame) {
//			val a = frametime.getDedTimeSheetByAtr(DeductionAtr.Deduction, ConditionAtr.BREAK);
//			if(frametime.getMidNightTimeSheet().isPresent()) {
//				totalDedTime += a.stream().filter(tc -> tc.getCalcrange().getDuplicatedWith(frametime.getMidNightTimeSheet().get().getCalcrange()).isPresent())
//										 .map(tc -> tc.getCalcrange().getDuplicatedWith(frametime.getMidNightTimeSheet().get().getCalcrange()).get().lengthAsMinutes())
//										 .collect(Collectors.summingInt(tc->tc));
//			}
//		}
//		
//		totalMidNightTime -= totalDedTime;
		return new AttendanceTime(totalMidNightTime);
	}
	
	/**
	 * 全枠の中に入っている控除時間(控除区分に従って)を合計する
	 * @return 控除合計時間
	 */
	public AttendanceTime calculationAllFrameDeductionTime(DeductionAtr dedAtr,ConditionAtr atr,PremiumAtr premiumAtr,HolidayCalcMethodSet holidayCalcMethodSet,Optional<WorkTimezoneCommonSet> commonSetting) {
		AttendanceTime totalTime = new AttendanceTime(0);
		for(WithinWorkTimeFrame frameTime : this.withinWorkTimeFrame) {
			val addTime = frameTime.forcs(atr,dedAtr).valueAsMinutes();
			int forLateAddTime = 0;
			int forLeaveAddTime = 0;
			//遅刻が保持する控除時間の合計取得
			if(decisionGetLateLeaveHaveChild(premiumAtr,holidayCalcMethodSet,commonSetting,atr)) {
				if(frameTime.getLateTimeSheet().isPresent()) {
					if(frameTime.getLateTimeSheet().get().getDecitionTimeSheet(dedAtr).isPresent()) {
						forLateAddTime = frameTime.getLateTimeSheet().get().getDecitionTimeSheet(dedAtr).get().forcs(atr, dedAtr).valueAsMinutes();
					}
				}
				//早退が保持する控除時間の合計取得
				if(frameTime.getLeaveEarlyTimeSheet().isPresent()) {
					if(frameTime.getLeaveEarlyTimeSheet().get().getDecitionTimeSheet(dedAtr).isPresent()) {
						forLeaveAddTime = frameTime.getLeaveEarlyTimeSheet().get().getDecitionTimeSheet(dedAtr).get().forcs(atr, dedAtr).valueAsMinutes();
					}
				}
			}
			totalTime = totalTime.addMinutes(addTime+forLateAddTime+forLeaveAddTime);
		}
		if(dedAtr.isAppropriate() && (atr.isCare() || atr.isChild()))
				totalTime = totalTime.addMinutes(this.shortTimeSheet.stream().map(tc -> tc.calcTotalTime().valueAsMinutes()).collect(Collectors.summingInt(ts -> ts)));
		
		return totalTime;
	}
	
	/**
	 * 遅刻早退が保持する育児時間を取得するか判断
	 * 遅刻早退が就業時間から控除されない場合は就業時間が育児時間を保持しているので遅刻早退が保持する育児を取得する必要がない
	 * @param premiumAtr
	 * @param holidayCalcMethodSet
	 * @param commonSetting
	 * @param atr 
	 * @return
	 */
	private boolean decisionGetLateLeaveHaveChild(PremiumAtr premiumAtr,HolidayCalcMethodSet holidayCalcMethodSet,Optional<WorkTimezoneCommonSet> commonSetting, ConditionAtr atr) {
		boolean decisionGetChild = false;
		if(!(atr.isCare() || atr.isChild()))
				return false;
		if(premiumAtr.isRegularWork()) {			
			Optional<WorkTimeCalcMethodDetailOfHoliday> advancedSet = holidayCalcMethodSet.getWorkTimeCalcMethodOfHoliday().getAdvancedSet();	
				if(advancedSet.isPresent() &&
					advancedSet.get().getNotDeductLateLeaveEarly().isEnableSetPerWorkHour()&&commonSetting.isPresent()) {
					if(advancedSet.isPresent()&&commonSetting.get().getLateEarlySet().getCommonSet().isDelFromEmTime()) {
						decisionGetChild = true;
					}
				}else {
					if(advancedSet.isPresent()&&advancedSet.get().getNotDeductLateLeaveEarly().isDeduct()) {
						decisionGetChild = true;
					}
				}
		}else {
			Optional<PremiumCalcMethodDetailOfHoliday> advanceSet = holidayCalcMethodSet.getPremiumCalcMethodOfHoliday().getAdvanceSet();
			
				if(advanceSet.isPresent()&&advanceSet.get().getNotDeductLateLeaveEarly().isEnableSetPerWorkHour()&&commonSetting.isPresent()) {
					if(commonSetting.get().getLateEarlySet().getCommonSet().isDelFromEmTime()) {
						decisionGetChild = true;
					}
				}else {
					if(advanceSet.isPresent()&&advanceSet.get().getNotDeductLateLeaveEarly().isDeduct()) {
						decisionGetChild = true;
					}
				}
		}
		return decisionGetChild;
	}
	
	
	/**
	 * 休憩未取得用の処理(大塚要件)
	 * 就業時間内時間帯に含まれている休憩時間を計算する
	 * @param restTimeSheet　就業時間帯マスタの休憩時間帯
	 * @return　取得した休憩時間
	 */
	public AttendanceTime getDupRestTime(DeductionTime restTimeSheet) {
		List<TimeSpanForDailyCalc> notDupSpanList = Arrays.asList(new TimeSpanForDailyCalc(restTimeSheet.getStart(), restTimeSheet.getEnd()));
		for(WithinWorkTimeFrame frame : this.getWithinWorkTimeFrame()) {
			notDupSpanList = notDupSpanList.stream()
										   .filter(tc -> tc.getDuplicatedWith(frame.getTimeSheet()).isPresent())
										   .map(tc -> tc.getDuplicatedWith(frame.getTimeSheet()).get())
										   .collect(Collectors.toList());
		}
		return new AttendanceTime(notDupSpanList.stream().map(ts -> ts.lengthAsMinutes()).collect(Collectors.summingInt(tc -> tc)));
	}
	/**
	 * 大塚モード使用時専用の遅刻、早退削除処理
	 */
	public void cleanLateLeaveEarlyTimeForOOtsuka() {
		this.withinWorkTimeFrame.forEach(tc -> tc.cleanLateLeaveEarlyTimeForOOtsuka());
		cleanLateTime();
		cleanLeaveEarly();
	}
	
	private void cleanLateTime() {
		this.lateDecisionClock = Collections.emptyList();
	}
	
	private void cleanLeaveEarly() {
		this.leaveEarlyDecisionClock = Collections.emptyList();
	}

	/**
	 * 大塚所定内割増→残業への変換後に所定内割増リセット
	 */
	public void resetPremiumTimeSheet() {
		this.withinWorkTimeFrame.forEach(tc -> {
			tc.setPremiumTimeSheetInPredetermined(Optional.empty());
		});
	}
	
	 /**
	 * 遅刻控除
	 * @param timeLeavingWork 出退勤
	 * @param predetermineTimeSet 所定時間設定(計算用クラス)
	 * @param deductionTimeSheet 控除時間帯
	 * @param workType 勤務種類
	 * @param flowWorkSetting 流動勤務設定
	 * @param holidayCalcMethodSet 休暇の計算方法の設定
	 * @param flowRestTime 流動勤務の休憩時間帯
	 * @return attendanceLeavingWork 出退勤
	 */
	public TimeLeavingWork calcLateTimeDeduction(
		TimeLeavingWork timeLeavingWork,
		PredetermineTimeSetForCalc predetermineTimeSet,
		List<TimeSheetOfDeductionItem> forDeductionTimeZones,
		WorkType workType,
		FlowWorkSetting flowWorkSetting,
		HolidayCalcMethodSet holidayCalcMethodSet,
		FlowWorkRestTimezone flowRestTime){

		//遅刻判断時刻を取得
		Optional<LateDecisionClock> lateDecisionClock = LateDecisionClock.create(
						timeLeavingWork.getWorkNo().v(),
						predetermineTimeSet,
						flowWorkSetting.getCommonSetting().getLateEarlySet().getOtherEmTimezoneLateEarlySet(LateEarlyAtr.LATE).getGraceTimeSet(),
						timeLeavingWork,
						Optional.empty(),
						workType,
						forDeductionTimeZones);
		
		//遅刻時間帯の作成
		this.withinWorkTimeFrame.get(timeLeavingWork.getWorkNo().v() - 1).createLateTimeSheet(
				timeLeavingWork,
				predetermineTimeSet,
				forDeductionTimeZones,
				lateDecisionClock,
				flowWorkSetting.getCommonSetting(),
				flowRestTime);

		//控除判断処理
		boolean isDeductLateTime = holidayCalcMethodSet.getWorkTimeCalcMethodOfHoliday().getAdvancedSet().get().decisionLateDeductSetting(
										AttendanceTime.ZERO,
										flowWorkSetting.getCommonSetting().getLateEarlySet().getOtherEmTimezoneLateEarlySet(LateEarlyAtr.LATE).getGraceTimeSet(),
										Optional.of(flowWorkSetting.getCommonSetting()),
										workType);

		//控除する場合
		if(isDeductLateTime 
				&& this.withinWorkTimeFrame.get(timeLeavingWork.getWorkNo().v() - 1).getLateTimeSheet().isPresent()){
			//出退勤．出勤 ← 遅刻時間帯終了時刻 
			if(!timeLeavingWork.getAttendanceStamp().isPresent()) return timeLeavingWork;
			if(!timeLeavingWork.getAttendanceStamp().get().getStamp().isPresent()) return timeLeavingWork;
			
			timeLeavingWork.getAttendanceStamp().get().getStamp().get().setTimeWithDay(
				this.withinWorkTimeFrame.get(timeLeavingWork.getWorkNo().v() - 1).getLateTimeSheet().get().getForDeducationTimeSheet().get().getTimeSheet().getEnd());
		}
		return timeLeavingWork;
	}
	
	 /**
	 * 早退控除
	 * @param timeLeavingWork 出退勤
	 * @param predetermineTimeSet 所定時間設定(計算用クラス)
	 * @param forDeductionTimeZones 控除項目の時間帯
	 * @param workType 勤務種類
	 * @param flowWorkSetting 流動勤務設定
	 * @param holidayCalcMethodSet 休暇の計算方法の設定
	 * @return TimeLeavingWork 出退勤
	 */
	public TimeLeavingWork calcLeaveEarlyTimeDeduction(
		TimeLeavingWork timeLeavingWork,
		PredetermineTimeSetForCalc predetermineTimeSet,
		List<TimeSheetOfDeductionItem> forDeductionTimeZones,
		WorkType workType,
		FlowWorkSetting flowWorkSetting,
		HolidayCalcMethodSet holidayCalcMethodSet){
	
		//早退判断時刻を取得
		Optional<LeaveEarlyDecisionClock> leaveEarlyDecisionClock = LeaveEarlyDecisionClock.create(
						timeLeavingWork.getWorkNo().v() - 1,
						predetermineTimeSet,
						flowWorkSetting.getCommonSetting().getLateEarlySet().getOtherEmTimezoneLateEarlySet(LateEarlyAtr.EARLY).getGraceTimeSet(),
						timeLeavingWork,
						Optional.empty(),
						workType,
						forDeductionTimeZones);
		
		//早退時間帯の作成
		this.withinWorkTimeFrame.get(timeLeavingWork.getWorkNo().v() - 1).createLeaveEarlyTimeSheet(
				timeLeavingWork,
				predetermineTimeSet,
				forDeductionTimeZones,
				leaveEarlyDecisionClock,
				flowWorkSetting.getCommonSetting(),
				workType.isWeekDayAttendance()?flowWorkSetting.getHalfDayWorkTimezone().getRestTimezone():flowWorkSetting.getOffdayWorkTimezone().getRestTimeZone());
	
		//控除判断処理
		boolean isDeductLateTime = holidayCalcMethodSet.getWorkTimeCalcMethodOfHoliday().getAdvancedSet().get().decisionLateDeductSetting(
										AttendanceTime.ZERO,
										flowWorkSetting.getCommonSetting().getLateEarlySet().getOtherEmTimezoneLateEarlySet(LateEarlyAtr.LATE).getGraceTimeSet(),
										Optional.of(flowWorkSetting.getCommonSetting()),
										workType);
	
		//控除する場合
		if(isDeductLateTime 
				&& this.withinWorkTimeFrame.get(timeLeavingWork.getWorkNo().v() - 1).getLeaveEarlyTimeSheet().isPresent()){
			if(!timeLeavingWork.getLeaveStamp().isPresent()) return timeLeavingWork;
			if(!timeLeavingWork.getLeaveStamp().get().getStamp().isPresent()) return timeLeavingWork;
			
			//出退勤．退勤 ← 早退時間帯終了時刻 
			timeLeavingWork.getLeaveStamp().get().getStamp().get().setTimeWithDay(
				 this.withinWorkTimeFrame.get(timeLeavingWork.getWorkNo().v() - 1).getLeaveEarlyTimeSheet().get().getForDeducationTimeSheet().get().getTimeSheet().getStart());
		}
		return timeLeavingWork;
	}
	
	/**
	 * B.流動勤務(平日・就内)
	 * @param holidayAdditionPerCompany 休暇加算時間設定
	 * @param integrationOfDaily 日別実績(Work)
	 * @param predetermineTimeSet 所定時間設定(計算用クラス)
	 * @param timeSheetOfDeductionItems 控除項目の時間帯
	 * @param workType 勤務種類
	 * @param flowWorkSetting 流動勤務設定
	 * @param holidayCalcMethodSet 休暇の計算方法の設定
	 * @param holidayPriorityOrder 時間休暇相殺優先順位
	 * @param midNightTimeSheet 深夜時間帯
	 * @param bonuspaySetting 加給設定
	 * @param personCommonSetting 毎日変更の可能性のあるマスタ管理クラス
	 * @param vacation 休暇クラス
	 * @param illegularAddSetting 変形労働勤務の加算設定
	 * @param flexAddSetting フレックス勤務の加算設定
	 * @param regularAddSetting 通常勤務の加算設定
	 * @param deductionTimeSheet 控除時間帯
	 * @return timeVacationAdditionRemainingTime 休暇使用合計残時間未割当
	 */
	public void createAsFlow(
			Optional<HolidayAddtionSet> holidayAdditionPerCompany,
			IntegrationOfDaily integrationOfDaily,
			PredetermineTimeSetForCalc predetermineTimeSet,
			List<TimeSheetOfDeductionItem> timeSheetOfDeductionItems,
			WorkType workType,
			FlowWorkSetting flowWorkSetting,
			HolidayCalcMethodSet holidayCalcMethodSet,
			CompanyHolidayPriorityOrder companyholidayPriorityOrder,
			MidNightTimeSheet midNightTimeSheet,
			Optional<BonusPaySetting> bonuspaySetting,
			//共通処理呼ぶ用
			ManagePerPersonDailySet personCommonSetting,
			VacationClass vacation,
			WorkDeformedLaborAdditionSet illegularAddSetting,
			WorkFlexAdditionSet flexAddSetting,
			WorkRegularAdditionSet regularAddSetting,
			DeductionTimeSheet deductionTimeSheet) {
		
		//1回目の開始
		TimeWithDayAttr startTime = this.withinWorkTimeFrame.get(0).getTimeSheet().getStart();
		
		//休暇使用時間の取得
		if(integrationOfDaily.getAttendanceTimeOfDailyPerformance().isPresent()) {
			this.setVacationUseTime(
					integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getLateTimeOfDaily(),
					integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getLeaveEarlyTimeOfDaily(),
					integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getOutingTimeOfDailyPerformance());
		}
		
		//控除時間中の時間休暇相殺時間の計算
		this.calcTimeVacationOffsetTime(companyholidayPriorityOrder, timeSheetOfDeductionItems);
		
		//就業時間内時間帯を作成
		this.createWithinWorkTimeSheetAsFlowWork(
				startTime,
				predetermineTimeSet,
				timeSheetOfDeductionItems,
				workType,
				flowWorkSetting,
				holidayCalcMethodSet);
		
		//所定内割増時間の時間帯を作成
		this.withinWorkTimeFrame.get(0).setPremiumTimeSheetInPredetermined(
				WithinWorkTimeSheet.predetermineWithinPremiumTime(
						personCommonSetting.getDailyUnit().getDailyTime(),//1日の法定時間
						predetermineTimeSet.getAdditionSet().getPredTime().getOneDay().minute(),//所定時間1日
						this.withinWorkTimeFrame,//就業時間内時間枠
						vacation,//休暇クラス
						workType,//勤務種類
						integrationOfDaily.getCalAttr().getLeaveEarlySetting().isLate(),
						integrationOfDaily.getCalAttr().getLeaveEarlySetting().isLeaveEarly(),
						personCommonSetting.personInfo.get().getLaborSystem(),//労働制
						illegularAddSetting,//変形
						flexAddSetting,//フレ
						regularAddSetting,//通常
						holidayAdditionPerCompany.get(),//休暇加算設定
						holidayCalcMethodSet,//休暇の計算方法の設定
						CalcMethodOfNoWorkingDay.isNotCalculateFlexTime,//フレックス時間を計算しない
						Optional.empty(),//フレックス勤務の設定
						Optional.of(WorkTimeDailyAtr.REGULAR_WORK),//勤務形態区分 通常勤務・変形労働用
						Optional.of(integrationOfDaily.getWorkInformation().getRecordInfo().getWorkTimeCode()),//就業時間帯コード
						AttendanceTime.ZERO,//preFlexTime？
						Optional.empty(),//コアタイム時間帯設定
						predetermineTimeSet,//計算用所定時間
						Finally.empty(),//日別実績の時間休暇使用時間 どの時間渡せばいいかわからない。
						Optional.empty(),//遅刻判断時刻
						Optional.empty(),//早退判断時刻
						DailyUnit.zero(),//DailyUnit 何を渡すのか不明
						Optional.of(flowWorkSetting.getCommonSetting()),//共通設定
						personCommonSetting.getPersonInfo().get(),//労働条件
						Optional.empty(),//predetermineTimeSetByPersonInfo 何の為？個人用っぽい引数名。
						Optional.empty(),//遅刻早退を控除する
						deductionTimeSheet,//控除時間帯
						NotUseAtr.NOT_USE//lateEarlyMinusAtr
				).get(0).getPremiumTimeSheetInPredetermined());
		
		//predetermineWithinPremiumTime()はnullになる可能性がある為、Optionalに変換
		if(this.withinWorkTimeFrame.get(0).getPremiumTimeSheetInPredetermined().equals(null)) {
			this.withinWorkTimeFrame.get(0).setPremiumTimeSheetInPredetermined(Optional.empty());
		}
		
		//時間休暇溢れ分の割り当て（流動就内）
		this.timeVacationAdditionRemainingTime = Finally.of(this.allocateOverflowTimeVacation(
				personCommonSetting.getDailyUnit().getDailyTime(),//1日の法定時間 共通処理呼ぶ用のpersonCommonSettingだけ渡せばいけるが、一旦設計に合わせている
				holidayAdditionPerCompany,
				//共通処理呼ぶ用
				vacation,
				workType,
				predetermineTimeSet,
				integrationOfDaily,
				personCommonSetting,
				illegularAddSetting,
				flexAddSetting,
				regularAddSetting,
				holidayCalcMethodSet,
				flowWorkSetting));
		
		//控除時間帯に丸め設定を付与
		this.withinWorkTimeFrame.get(0).grantRoundingToDeductionTimeSheet(ActualWorkTimeSheetAtr.WithinWorkTime, flowWorkSetting.getCommonSetting());
		
		//加給時間帯を作成
		this.withinWorkTimeFrame.get(0).createBonusPayTimeSheet(bonuspaySetting, integrationOfDaily.getSpecDateAttr());
		
		//深夜時間帯を作成
		this.withinWorkTimeFrame.get(0).createMidNightTimeSheet(midNightTimeSheet, Optional.of(flowWorkSetting.getCommonSetting()));
	}
	
	/**
	 * 休暇使用時間の取得
	 * @param lateTimeOfDaily 日別実績の遅刻時間
	 * @param leaveEarlyTimeOfDaily 日別実績の早退時間
	 * @param outingTimeOfDailyPerformance 日別実績の外出時間
	 */
	private void setVacationUseTime(
			List<LateTimeOfDaily> lateTimeOfDaily,
			List<LeaveEarlyTimeOfDaily> leaveEarlyTimeOfDaily,
			List<OutingTimeOfDaily> outingTimeOfDailyPerformance) {
		
		//遅刻早退休暇使用時間
		for(int i = 0; i<this.withinWorkTimeFrame.size(); i++) {
			//枠No取得
			EmTimeFrameNo frameNo = new EmTimeFrameNo(this.withinWorkTimeFrame.get(i).getWorkingHoursTimeNo().v());
			
			//就業時間内時間枠の枠Noと日別実績の遅刻時間の勤務Noが一致しているものをセットする。遅刻早退は勤務Noが2しかない場合がある為。
			this.withinWorkTimeFrame.get(i).setLateVacationUseTime(
					lateTimeOfDaily.stream()
							.filter(late -> late.getWorkNo().v().equals(frameNo.v()))
							.map(late -> late.getTimePaidUseTime())
							.findFirst());
			
			this.withinWorkTimeFrame.get(i).setLeaveEarlyVacationUseTime(
					lateTimeOfDaily.stream()
							.filter(leaveEarly -> leaveEarly.getWorkNo().v().equals(frameNo.v()))
							.map(leaveEarly -> leaveEarly.getTimePaidUseTime())
							.findFirst());
		}
		
		//外出休暇使用時間
		for(OutingTimeOfDaily outing : outingTimeOfDailyPerformance) {
			//私用、組合のみセット 控除する外出しか時間休暇で相殺されない為
			if(outing.getReason().anyMatch(GoOutReason.SUPPORT, GoOutReason.OFFICAL)) {
				this.outingVacationUseTime.put(outing.getReason(), outing.getTimeVacationUseOfDaily());
			}
		}
	}
	
	/**
	 * 控除時間中の時間休暇相殺時間の計算
	 * @param holidayPriorityOrder 時間休暇相殺優先順位
	 * @param timeSheetOfDeductionItems 控除項目の時間帯 この中の外出を相殺する
	 */
	private void calcTimeVacationOffsetTime(
			CompanyHolidayPriorityOrder holidayPriorityOrder,
			List<TimeSheetOfDeductionItem> timeSheetOfDeductionItems) {
		
		for(int i = 0; i<this.withinWorkTimeFrame.size(); i++) {
			//遅刻相殺時間の計算
			if(this.withinWorkTimeFrame.get(i).getLateTimeSheet().isPresent() && this.withinWorkTimeFrame.get(i).getLateVacationUseTime().isPresent()) {
				this.withinWorkTimeFrame.get(i).getLateTimeSheet().get().calcLateOffsetTime(
						DeductionAtr.Deduction,
						holidayPriorityOrder,
						this.withinWorkTimeFrame.get(i).getLateVacationUseTime().get());
			}
			//早退相殺時間の計算
			if(this.withinWorkTimeFrame.get(i).getLeaveEarlyTimeSheet().isPresent() && this.withinWorkTimeFrame.get(i).getLeaveEarlyVacationUseTime().isPresent()) {
				this.withinWorkTimeFrame.get(i).getLeaveEarlyTimeSheet().get().calcLeaveEarlyOffsetTime(
						DeductionAtr.Deduction,
						holidayPriorityOrder,
						this.withinWorkTimeFrame.get(i).getLeaveEarlyVacationUseTime().get());
			}
		}
		//外出相殺時間の計算
		this.calcOutOffsetTime(DeductionAtr.Deduction, holidayPriorityOrder, this.outingVacationUseTime, timeSheetOfDeductionItems);
	}
	
	 /**
	 * 外出時間の休暇時間相殺
	 * @param deductionAtr 控除 or 計上
	 * @param companyholidayPriorityOrder 時間休暇相殺優先順位
	 * @param goOutTimeVacationUseTimes 日別実績の時間休暇使用時間
	 * @param timeSheetOfDeductionItems 控除項目の時間帯
	 */
	public void calcOutOffsetTime(
		DeductionAtr deductionAtr,
		CompanyHolidayPriorityOrder companyholidayPriorityOrder,
		Map<GoOutReason,TimevacationUseTimeOfDaily> goOutTimeVacationUseTimes,
		List<TimeSheetOfDeductionItem> timeSheetOfDeductionItems) {
		
		for(int i = 0; i<timeSheetOfDeductionItems.size(); i++) {
			//初期化
			if(!timeSheetOfDeductionItems.get(i).getDeductionOffSetTime().isPresent()
					&& timeSheetOfDeductionItems.get(i).getDeductionAtr().isGoOut()
					&& timeSheetOfDeductionItems.get(i).getGoOutReason().get().isPrivateOrUnion()) {
				timeSheetOfDeductionItems.get(i).setDeductionOffSetTime(Optional.of(DeductionOffSetTime.createAllZero()));
			}
			//私用
			if(timeSheetOfDeductionItems.get(i).getDeductionAtr().isGoOut() && timeSheetOfDeductionItems.get(i).getGoOutReason().get().isPrivate()){
				timeSheetOfDeductionItems.get(i).offsetProcessInPriorityOrder(
						deductionAtr,
						companyholidayPriorityOrder,
						goOutTimeVacationUseTimes.get(GoOutReason.SUPPORT),
						timeSheetOfDeductionItems.get(i).getDeductionOffSetTime().get());
			}
			//組合
			if(timeSheetOfDeductionItems.get(i).getDeductionAtr().isGoOut() && timeSheetOfDeductionItems.get(i).getGoOutReason().get().isUnion()){
				timeSheetOfDeductionItems.get(i).offsetProcessInPriorityOrder(
						deductionAtr,
						companyholidayPriorityOrder,
						goOutTimeVacationUseTimes.get(GoOutReason.OFFICAL),
						timeSheetOfDeductionItems.get(i).getDeductionOffSetTime().get());
			}
		}
	}
	
	/**
	 * 就業時間内時間帯を作成
	 * @param startTime 開始時刻
	 * @param attendanceLeavingWorks 出退勤
	 * @param predetermineTimeSet 所定時間設定(計算用クラス)
	 * @param forDeductionTimeZones 控除項目の時間帯
	 * @param workType 勤務種類
	 * @param flowWorkSetting 流動勤務設定
	 * @param holidayCalcMethodSet 休暇の計算方法の設定
	 */
	private void createWithinWorkTimeSheetAsFlowWork(
			TimeWithDayAttr startTime,
			PredetermineTimeSetForCalc predetermineTimeSet,
			List<TimeSheetOfDeductionItem> forDeductionTimeZones,
			WorkType workType,
			FlowWorkSetting flowWorkSetting,
			HolidayCalcMethodSet holidayCalcMethodSet) {
		
		//残業開始となる経過時間を取得
		AttendanceTime elapsedTime = flowWorkSetting.getHalfDayWorkTimezone().getWorkTimeZone().getLstOTTimezone().get(0).getFlowTimeSetting().getElapsedTime();
		
		//経過時間から終了時刻を計算
		TimeWithDayAttr endTime = this.withinWorkTimeFrame.get(0).getTimeSheet().getStart().forwardByMinutes(elapsedTime.valueAsMinutes());
		
		//重複している控除項目の時間帯
		List<TimeSheetOfDeductionItem> overlapptingDeductionTimeSheets = new ArrayList<>();
		
		for(TimeSheetOfDeductionItem item : forDeductionTimeZones) {
			//重複している時間帯
			Optional<TimeSpanForDailyCalc> overlapptingTime = Optional.empty();
			overlapptingTime = item.getTimeSheet().getDuplicatedWith(new TimeSpanForDailyCalc(startTime, endTime));
			if(!overlapptingTime.isPresent()) continue;
			
			//控除時間分、終了時刻をズラす
			if(item.getDeductionAtr() == DeductionClassification.GO_OUT) {
				endTime = endTime.forwardByMinutes(overlapptingTime.get().lengthAsMinutes() - item.getDeductionOffSetTime().get().getTotalOffSetTime());
				if(endTime.isNegative()) endTime = TimeWithDayAttr.THE_PRESENT_DAY_0000;
			}
			else {
				endTime = endTime.forwardByMinutes(overlapptingTime.get().lengthAsMinutes());
			}
			//重複している控除項目の時間帯に追加
			overlapptingDeductionTimeSheets.add(
					item.replaceTimeSpan(Optional.of(new TimeSpanForDailyCalc(overlapptingTime.get().getStart(), overlapptingTime.get().getEnd()))));
		}
		//退勤時刻の補正
		this.correctleaveTimeForFlow(endTime);
		
		//就業時間内時間枠クラスを作成（更新）
		this.createWithinWorkTimeFramesAsFlowWork(overlapptingDeductionTimeSheets, endTime);
	}
	
	/**
	 * 退勤時刻の補正（流動勤務で経過時間より退勤時刻が早い場合に補正する）
	 * @param endTime 就業時間内時間帯終了時刻
	 */
	private void correctleaveTimeForFlow(TimeWithDayAttr endTime){
		
		TimeWithDayAttr leaveTime = TimeWithDayAttr.THE_PRESENT_DAY_0000;
		
		//退勤時刻を求める
		//2勤務目がある場合、2勤務目の退勤時刻
		if(this.withinWorkTimeFrame.size() >= 2) {
			leaveTime = this.withinWorkTimeFrame.get(1).getTimeSheet().getEnd();
		}
		//1勤務のみの場合、1勤務目の退勤時刻
		else {
			leaveTime = this.withinWorkTimeFrame.get(0).getTimeSheet().getEnd();
		}
		//就業時間内時間帯終了時刻←退勤時刻
		if(leaveTime.lessThan(endTime)) {
			endTime = endTime.backByMinutes(endTime.valueAsMinutes() - leaveTime.valueAsMinutes());
		}
	}
	
	/**
	 * 就業時間内時間枠を作成（更新）
	 * @param timeSheetOfDeductionItems 控除項目の時間帯(List)
	 * @param endTime 就業時間内時間帯終了時刻
	 */
	private void createWithinWorkTimeFramesAsFlowWork(List<TimeSheetOfDeductionItem> timeSheetOfDeductionItems, TimeWithDayAttr endTime){
		
		List<WithinWorkTimeFrame> frames = new ArrayList<WithinWorkTimeFrame>();
		
		for(WithinWorkTimeFrame frame : this.withinWorkTimeFrame) {	
			//時間帯に含まれている控除時間帯を保持する
			frame.getDeductionTimeSheet().addAll(frame.getDupliRangeTimeSheet(timeSheetOfDeductionItems));
			
			//指定時刻が時間帯に含まれているか判断
			if(frame.getTimeSheet().contains(endTime)) {
				frame.changeEnd(endTime);
				frames.add(frame);
				break;
			}
			frames.add(frame);
		}
		this.withinWorkTimeFrame.clear();
		this.withinWorkTimeFrame.addAll(frames);
	}
	
	/**
	 * 時間休暇溢れ分の割り当て（流動就内）
	 * @param legalTime 法定労働時間
	 * @param holidayAdditionPerCompany 休暇加算時間設定
	 * @param vacation 休暇クラス
	 * @param workType 勤務種類
	 * @param predetermineTimeSet 所定時間設定(計算用クラス)
	 * @param integrationOfDaily 日別実績(Work)
	 * @param personCommonSetting 毎日変更の可能性のあるマスタ管理クラス
	 * @param illegularAddSetting 変形労働勤務の加算設定
	 * @param flexAddSetting フレックス勤務の加算設定
	 * @param regularAddSetting 通常勤務の加算設定
	 * @param holidayCalcMethodSet 休暇の計算方法の設定
	 * @param flowWorkSetting 流動勤務設定
	 * @return AttendanceTime 休暇使用合計残時間未割当
	 */
	private AttendanceTime allocateOverflowTimeVacation(
			TimeOfDay legalTime,
			Optional<HolidayAddtionSet> holidayAdditionPerCompany,
			//共通処理呼ぶ用
			VacationClass vacation,
			WorkType workType,
			PredetermineTimeSetForCalc predetermineTimeSet,
			IntegrationOfDaily integrationOfDaily,
			ManagePerPersonDailySet personCommonSetting,
			WorkDeformedLaborAdditionSet illegularAddSetting,
			WorkFlexAdditionSet flexAddSetting,
			WorkRegularAdditionSet regularAddSetting,
			HolidayCalcMethodSet holidayCalcMethodSet,
			FlowWorkSetting flowWorkSetting){
		
		//休暇使用合計残時間を取得
		AttendanceTime totalTime = this.calcTotalVacationUseRemainingTime(holidayAdditionPerCompany);
		
		//就業時間内時間枠に入れる
		this.withinWorkTimeFrame.get(0).setTimeVacationOverflowTime(Optional.of(
				this.allocateOverflowVacationTimeToLegalTime(
						legalTime,
						holidayAdditionPerCompany,
						totalTime,
						vacation,
						workType,
						predetermineTimeSet,
						integrationOfDaily,
						personCommonSetting,
						illegularAddSetting,
						flexAddSetting,
						regularAddSetting,
						holidayCalcMethodSet,
						flowWorkSetting)));
		
		//まだ残っている残時間を計算する
		totalTime = totalTime.minusMinutes(this.withinWorkTimeFrame.get(0).getTimeVacationOverflowTime().get().valueAsMinutes());
		
		//所定内割増に時間休暇溢れ時間を割り当て
		if(this.withinWorkTimeFrame.get(0).getPremiumTimeSheetInPredetermined().isPresent()) {
			this.withinWorkTimeFrame.get(0).getPremiumTimeSheetInPredetermined().get().setTimeVacationOverflowTime(Optional.of(
					this.allocateOverflowVacationTimeToPredetermineTime(
							legalTime,
							predetermineTimeSet,
							totalTime,
							//共通処理呼ぶ用
							vacation,
							workType,
							holidayAdditionPerCompany,
							integrationOfDaily,
							personCommonSetting,
							illegularAddSetting,
							flexAddSetting,
							regularAddSetting,
							holidayCalcMethodSet,
							flowWorkSetting)));
			
			//まだ残っている残時間を計算する
			totalTime = totalTime.minusMinutes(
					this.withinWorkTimeFrame.get(0).getPremiumTimeSheetInPredetermined().get().getTimeVacationOverflowTime().get().valueAsMinutes());
		}
		return totalTime;
	}
	
	/**
	 * 休暇使用合計残時間を取得
	 * @param holidayAdditionPerCompany 休暇加算時間設定
	 * @return AttendanceTime 休暇使用合計残時間
	 */
	private AttendanceTime calcTotalVacationUseRemainingTime(Optional<HolidayAddtionSet> holidayAdditionPerCompany){
		
		AttendanceTime totalTime = AttendanceTime.ZERO;
		
		//遅刻休暇使用時間の合計
		totalTime.addMinutes(this.withinWorkTimeFrame.stream()
				.filter(frame -> frame.getLateVacationUseTime().isPresent())
				.mapToInt(frame -> frame.getLateVacationUseTime().get().calcTotalVacationAddTime(holidayAdditionPerCompany, AdditionAtr.All))
				.sum());
		
		//早退休暇使用時間の合計
		totalTime.addMinutes(this.withinWorkTimeFrame.stream()
				.filter(frame -> frame.getLeaveEarlyVacationUseTime().isPresent())
				.mapToInt(frame -> frame.getLeaveEarlyVacationUseTime().get().calcTotalVacationAddTime(holidayAdditionPerCompany, AdditionAtr.All))
				.sum());

		//外出休暇使用時間の合計
		totalTime.addMinutes(this.outingVacationUseTime.values().stream()
				.mapToInt(outing -> outing.calcTotalVacationAddTime(holidayAdditionPerCompany, AdditionAtr.All))
				.sum());
		
		return totalTime;
	}
	
	/**
	 * 法定労働時間に時間休暇溢れ時間割り当て（流動就内）
	 * @param legalTime 法定労働時間
	 * @param holidayAdditionPerCompany 休暇加算時間設定
	 * @param totalTime 休暇使用合計残時間
	 * @param vacation 休暇クラス
	 * @param workType 勤務種類
	 * @param predetermineTimeSet 所定時間設定(計算用クラス)
	 * @param integrationOfDaily 日別実績(Work)
	 * @param personCommonSetting 毎日変更の可能性のあるマスタ管理クラス
	 * @param illegularAddSetting 変形労働勤務の加算設定
	 * @param flexAddSetting フレックス勤務の加算設定
	 * @param regularAddSetting 通常勤務の加算設定
	 * @param holidayCalcMethodSet 休暇の計算方法の設定
	 * @param flowWorkSetting 流動勤務設定
	 * @return
	 */
	private AttendanceTime allocateOverflowVacationTimeToLegalTime(
			TimeOfDay legalTime,
			Optional<HolidayAddtionSet> holidayAdditionPerCompany,
			AttendanceTime totalTime,
			//共通処理呼ぶ用
			VacationClass vacation,
			WorkType workType,
			PredetermineTimeSetForCalc predetermineTimeSet,
			IntegrationOfDaily integrationOfDaily,
			ManagePerPersonDailySet personCommonSetting,
			WorkDeformedLaborAdditionSet illegularAddSetting,
			WorkFlexAdditionSet flexAddSetting,
			WorkRegularAdditionSet regularAddSetting,
			HolidayCalcMethodSet holidayCalcMethodSet,
			FlowWorkSetting flowWorkSetting){
		
		if(totalTime.lessThanOrEqualTo(AttendanceTime.ZERO)) return AttendanceTime.ZERO;
		
		AttendanceTime workTime = calcWorkTimeForStatutory(
				PremiumAtr.RegularWork,
				CalcurationByActualTimeAtr.CALCULATION_OTHER_THAN_ACTUAL_TIME,
				AttendanceTime.ZERO,//this.lateVacationUseTime.get(),//どの日別実績の休暇使用時間を渡せばいいのか不明。
				vacation,
				StatutoryDivision.Nomal,
				workType,
				predetermineTimeSet,
				Optional.of(integrationOfDaily.getWorkInformation().getRecordInfo().getWorkTimeCode()),
				integrationOfDaily.getCalAttr().getLeaveEarlySetting().isLate(),
				integrationOfDaily.getCalAttr().getLeaveEarlySetting().isLeaveEarly(),
				personCommonSetting.personInfo.get().getLaborSystem(),
				illegularAddSetting,
				flexAddSetting,
				regularAddSetting,
				holidayAdditionPerCompany.get(),
				holidayCalcMethodSet,
				DailyUnit.zero(),//DailyUnit 何を渡すのか不明
				Optional.of(flowWorkSetting.getCommonSetting()),
				personCommonSetting.getPersonInfo().get(),
				Optional.empty(),//predetermineTimeSetByPersonInfo 何の為？個人用っぽい引数名。
				Optional.empty(),//コアタイム時間帯設定
				holidayCalcMethodSet.getWorkTimeCalcMethodOfHoliday().getAdvancedSet().get().getNotDeductLateLeaveEarly(),//DeductLeaveEarly 遅刻早退を控除する
				NotUseAtr.NOT_USE//lateEarlyMinusAtr
				);
		
		//就業時間 >= 法定労働時間
		if(workTime.greaterThanOrEqualTo(personCommonSetting.getDailyUnit().getDailyTime())) return AttendanceTime.ZERO;
		
		//法定労働時間不足時間を計算
		AttendanceTime missingTime = new AttendanceTime(
				personCommonSetting.getDailyUnit().getDailyTime().minusMinutes(workTime.valueAsMinutes()).valueAsMinutes());
		
		//法定労働時間不足分 < 時間休暇加算残時間未割当
		if(missingTime.lessThanOrEqualTo(totalTime)) return missingTime;
		
		return totalTime;
	}
	
	/**
	 * 所定労働時間に時間休暇溢れ時間割り当て（流動就内）
	 * @param legalTime 法定労働時間
	 * @param predetermineTimeSet 所定時間設定(計算用クラス)
	 * @param totalTime 休暇使用合計残時間
	 * @param vacation 休暇クラス
	 * @param workType 勤務種類
	 * @param holidayAdditionPerCompany 休暇加算時間設定
	 * @param integrationOfDaily 日別実績(Work)
	 * @param personCommonSetting 毎日変更の可能性のあるマスタ管理クラス
	 * @param illegularAddSetting 変形労働勤務の加算設定
	 * @param flexAddSetting フレックス勤務の加算設定
	 * @param regularAddSetting 通常勤務の加算設定
	 * @param holidayCalcMethodSet 休暇の計算方法の設定
	 * @param flowWorkSetting 流動勤務設定
	 * @return 割り当て時間
	 */
	private AttendanceTime allocateOverflowVacationTimeToPredetermineTime(
			TimeOfDay legalTime,
			PredetermineTimeSetForCalc predetermineTimeSet,
			AttendanceTime totalTime,
			//共通処理呼ぶ用
			VacationClass vacation,
			WorkType workType,
			Optional<HolidayAddtionSet> holidayAdditionPerCompany,
			IntegrationOfDaily integrationOfDaily,
			ManagePerPersonDailySet personCommonSetting,
			WorkDeformedLaborAdditionSet illegularAddSetting,
			WorkFlexAdditionSet flexAddSetting,
			WorkRegularAdditionSet regularAddSetting,
			HolidayCalcMethodSet holidayCalcMethodSet,
			FlowWorkSetting flowWorkSetting){
		
		if(totalTime.lessThanOrEqualTo(AttendanceTime.ZERO)) return AttendanceTime.ZERO;
		
		//所定内割増時間の上限を計算（所定時間-法定労働時間）
		AttendanceTime upperLimitTime = new AttendanceTime(
				legalTime.minusMinutes(predetermineTimeSet.getAdditionSet().getPredTime().getOneDay().minute()).v());
		if(upperLimitTime.isNegative()) upperLimitTime = AttendanceTime.ZERO;
		
		//所定内割増合計時間の計算
		AttendanceTime totalPredeterminePremiumTime = this.calcWorkTimeBeforeDeductPremium(
				HolidayAdditionAtr.HolidayAddition,//不明
				AttendanceTime.ZERO,//this.lateVacationUseTime.get(),//どの日別実績の休暇使用時間を渡せばいいのか不明。
				personCommonSetting.personInfo.get().getLaborSystem(),//労働制
				regularAddSetting,//通常
				illegularAddSetting,//変形
				flexAddSetting,//フレ
				holidayAdditionPerCompany.get(),
				integrationOfDaily.getCalAttr().getLeaveEarlySetting().isLate(),
				integrationOfDaily.getCalAttr().getLeaveEarlySetting().isLeaveEarly(),
				holidayCalcMethodSet,
				PremiumAtr.RegularWork,
				Optional.of(flowWorkSetting.getCommonSetting()),//共通設定
				Optional.empty(),//コアタイム時間帯設定
				CalcurationByActualTimeAtr.CALCULATION_BY_ACTUAL_TIME,//不明
				vacation,
				StatutoryDivision.Nomal,
				workType,
				predetermineTimeSet,
				Optional.of(integrationOfDaily.getWorkInformation().getRecordInfo().getWorkTimeCode()),//就業時間帯コード
				DailyUnit.zero(),//DailyUnit 何を渡すのか不明
				personCommonSetting.getPersonInfo().get(),//労働条件
				Optional.empty(),//predetermineTimeSetByPersonInfo 何の為？個人用っぽい引数名。
				NotUseAtr.NOT_USE//lateEarlyMinusAtr
				);
		return new AttendanceTime(upperLimitTime.minusMinutes(totalPredeterminePremiumTime.valueAsMinutes()).valueAsMinutes());
	}
	
	/**
	 * 就業時間内時間枠(List)の最初の開始時刻～最後の終了時刻を求める
	 * @param withinWorkTimeFrame 就業時間内時間枠(List)
	 * @return 最初の開始時刻～最後の終了時刻
	 */
	public Optional<TimeSpanForDailyCalc> getStartEndToWithinWorkTimeFrame() {
		
		if(this.withinWorkTimeFrame.isEmpty()) return Optional.empty();
		TimeWithDayAttr start = this.withinWorkTimeFrame.get(0).getTimeSheet().getStart();
		TimeWithDayAttr end = this.withinWorkTimeFrame.get(this.withinWorkTimeFrame.size()-1).getTimeSheet().getEnd();
		return Optional.of(new TimeSpanForDailyCalc(start, end));
	}
}

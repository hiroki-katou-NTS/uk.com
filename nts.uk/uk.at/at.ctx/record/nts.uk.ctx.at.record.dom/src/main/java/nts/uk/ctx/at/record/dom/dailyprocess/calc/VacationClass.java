package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Value;
import lombok.val;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.LateTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.LeaveEarlyTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.vacationusetime.AbsenceOfDaily;
import nts.uk.ctx.at.record.dom.daily.vacationusetime.AnnualOfDaily;
import nts.uk.ctx.at.record.dom.daily.vacationusetime.HolidayOfDaily;
import nts.uk.ctx.at.record.dom.daily.vacationusetime.OverSalaryOfDaily;
import nts.uk.ctx.at.record.dom.daily.vacationusetime.SpecialHolidayOfDaily;
import nts.uk.ctx.at.record.dom.daily.vacationusetime.SubstituteHolidayOfDaily;
import nts.uk.ctx.at.record.dom.daily.vacationusetime.TimeDigestOfDaily;
import nts.uk.ctx.at.record.dom.daily.vacationusetime.YearlyReservedOfDaily;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.AddSettingOfFlexWork;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.AddSettingOfIrregularWork;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.AddSettingOfRegularWork;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.CalculationByActualTimeAtr;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.StatutoryDivision;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrule.addsettingofworktime.AddVacationSet;
import nts.uk.ctx.at.shared.dom.workrule.addsettingofworktime.NotUseAtr;
import nts.uk.ctx.at.shared.dom.workrule.addsettingofworktime.VacationAddTimeSet;
import nts.uk.ctx.at.shared.dom.workrule.waytowork.PersonalLaborCondition;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.predset.BreakDownTimeDay;
import nts.uk.ctx.at.shared.dom.worktype.VacationCategory;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;

/**
 * 休暇クラス
 * @author keisuke_hoshina
 *
 */
@Value
public class VacationClass {
	private HolidayOfDaily holidayOfDaily;
	
	/**
	 * 休暇使用時間の計算
	 * @return
	 */
	public static HolidayOfDaily calcUseRestTime(WorkType workType,
			 							  PredetermineTimeSetForCalc predetermineTimeSet,
			 							  Optional<WorkTimeCode> siftCode,
			 							  Optional<PersonalLaborCondition> personalCondition,
			 							  VacationAddTimeSet vacationAddTimeSet,
			 							  List<OutingTimeOfDaily> goOutTimeOfDaily,
			 							  List<LateTimeOfDaily> lateTimeOfDaily,
			 							  List<LeaveEarlyTimeOfDaily> leaveEarlyTimeOfDaily) {
		
		//欠勤
		AttendanceTime absenceUseTime = vacationTimeOfcalcDaily(workType,VacationCategory.Absence,
				  											    predetermineTimeSet,siftCode,
				  											    personalCondition,vacationAddTimeSet);
		val absenceOfDaily = new AbsenceOfDaily(absenceUseTime);
		
		//時間消化休暇
		AttendanceTime timeDigest = new AttendanceTime(0); 
		val timeDigestOfDaily = new TimeDigestOfDaily(timeDigest, new AttendanceTime(0));
		
		//積立年休使用時間
		AttendanceTime yearlyReservedTime = vacationTimeOfcalcDaily(workType,VacationCategory.YearlyReserved,
				  											  		predetermineTimeSet,siftCode,
				  											  		personalCondition,vacationAddTimeSet);
		val yearlyReservedOfDaily = new YearlyReservedOfDaily(yearlyReservedTime);
		//代休
		AttendanceTime substituUseTime = vacationTimeOfcalcDaily(workType,VacationCategory.SubstituteHoliday,
																 predetermineTimeSet,siftCode,
																 personalCondition,vacationAddTimeSet);
		int sumSubTime = goOutTimeOfDaily.stream()
										 .map(tc -> tc.getTimeVacationUseOfDaily().getTimeCompensatoryLeaveUseTime().valueAsMinutes())
										 .collect(Collectors.summingInt(tc -> tc))
						+ lateTimeOfDaily.stream()
										 .map(tc -> tc.getTimePaidUseTime().getTimeCompensatoryLeaveUseTime().valueAsMinutes())
										 .collect(Collectors.summingInt(tc -> tc))
						+ leaveEarlyTimeOfDaily.stream()
						 					   .map(tc -> tc.getTimePaidUseTime().getTimeCompensatoryLeaveUseTime().valueAsMinutes())
						 					   .collect(Collectors.summingInt(tc -> tc));
		substituUseTime = substituUseTime.addMinutes(sumSubTime);
		
		val substituteOfDaily = new SubstituteHolidayOfDaily(substituUseTime,new AttendanceTime(0));
		
		//振休
//		AttendanceTime pauseUseTime = vacationTimeOfcalcDaily(workType,VacationCategory.Pause,
//				 											  predetermineTimeSet,siftCode,
//				 											  personalCondition,vacationAddTimeSet);
		
		//超過有休
		AttendanceTime overSalaryTime = vacationTimeOfcalcDaily(workType,VacationCategory.SpecialHoliday,
			  	 												 predetermineTimeSet,siftCode,
			  	 												 personalCondition,vacationAddTimeSet);
		int sumOverTime = goOutTimeOfDaily.stream()
				 						  .map(tc -> tc.getTimeVacationUseOfDaily().getSixtyHourExcessHolidayUseTime().valueAsMinutes())
				 						  .collect(Collectors.summingInt(tc -> tc))
				 		  + lateTimeOfDaily.stream()
				 		  				  .map(tc -> tc.getTimePaidUseTime().getSixtyHourExcessHolidayUseTime().valueAsMinutes())
				 		  				  .collect(Collectors.summingInt(tc -> tc))
						  + leaveEarlyTimeOfDaily.stream()
							 			  .map(tc -> tc.getTimePaidUseTime().getSixtyHourExcessHolidayUseTime().valueAsMinutes())
							 			  .collect(Collectors.summingInt(tc -> tc));
		overSalaryTime = overSalaryTime.addMinutes(sumOverTime);
		
		val overSalaryOfDaily = new OverSalaryOfDaily(overSalaryTime, new AttendanceTime(0));
		
		//特別休暇
		AttendanceTime specHolidayTime = vacationTimeOfcalcDaily(workType,VacationCategory.SpecialHoliday,
				 											  	 predetermineTimeSet,siftCode,
				 											  	 personalCondition,vacationAddTimeSet);
		int sumSpecTime = goOutTimeOfDaily.stream()
				 						  .map(tc -> tc.getTimeVacationUseOfDaily().getTimeSpecialHolidayUseTime().valueAsMinutes())
				 						  .collect(Collectors.summingInt(tc -> tc))
				 		  + lateTimeOfDaily.stream()
				 		  				  .map(tc -> tc.getTimePaidUseTime().getTimeSpecialHolidayUseTime().valueAsMinutes())
										  .collect(Collectors.summingInt(tc -> tc))
						  + leaveEarlyTimeOfDaily.stream()
							 			  .map(tc -> tc.getTimePaidUseTime().getTimeSpecialHolidayUseTime().valueAsMinutes())
							 			  .collect(Collectors.summingInt(tc -> tc));
		specHolidayTime = specHolidayTime.addMinutes(sumSpecTime);
		
		val specHolidayOfDaily = new SpecialHolidayOfDaily(specHolidayTime, new AttendanceTime(0));
		
		//年休
		AttendanceTime annualUseTime = vacationTimeOfcalcDaily(workType,VacationCategory.AnnualHoliday,
				 												 predetermineTimeSet,siftCode,
				 												 personalCondition,vacationAddTimeSet);
		int sumAnnTime = goOutTimeOfDaily.stream()
				 						 .map(tc -> tc.getTimeVacationUseOfDaily().getTimeAnnualLeaveUseTime().valueAsMinutes())
				 						 .collect(Collectors.summingInt(tc -> tc))
				 		 + lateTimeOfDaily.stream()
										 .map(tc -> tc.getTimePaidUseTime().getTimeAnnualLeaveUseTime().valueAsMinutes())
										 .collect(Collectors.summingInt(tc -> tc))
						 + leaveEarlyTimeOfDaily.stream()
						 					   .map(tc -> tc.getTimePaidUseTime().getTimeAnnualLeaveUseTime().valueAsMinutes())
						 					   .collect(Collectors.summingInt(tc -> tc));
		annualUseTime = annualUseTime.addMinutes(sumAnnTime);

		val annualOfDaily = new AnnualOfDaily(annualUseTime, new AttendanceTime(0));
		

		return new HolidayOfDaily(absenceOfDaily,
								  timeDigestOfDaily,
								  yearlyReservedOfDaily,
								  substituteOfDaily,
								  overSalaryOfDaily,
								  specHolidayOfDaily,
								  annualOfDaily
								  );
	}
	
	
	/**
	 * 日数単位の休暇時間計算
	 * @return　1日の時間内訳時間
	 */
	public static AttendanceTime vacationTimeOfcalcDaily(WorkType workType,
														 VacationCategory vacationCategory,
														 PredetermineTimeSetForCalc predetermineTimeSet,
														 Optional<WorkTimeCode> siftCode,
			 											 Optional<PersonalLaborCondition> personalCondition,
			 											 VacationAddTimeSet vacationAddTimeSet) {
		//referenceAtr 実績の就業時間帯を参照する(休暇加算時間設定クラスのメンバ)
		BreakDownTimeDay breakDownTimeDay = getVacationAddSet(predetermineTimeSet, siftCode, true);
		switch(workType.getDailyWork().decisionMatchWorkType(vacationCategory.convertWorkTypeClassification())) {
			case FULL_TIME:
			case HOLIDAY:
				return breakDownTimeDay.getOneDay();
			case MORNING:
				return breakDownTimeDay.getMorning();
			case AFTERNOON:
				return breakDownTimeDay.getAfternoon();
			default:
				throw new RuntimeException("unknown WorkType");
		}
	}
	
	/**
	 * 休暇加算設定の取得
	 * @param referenceAtr 実績の就業時間帯を参照する(休暇加算時間設定クラスのメンバ)
	 * @return
	 */
	private static BreakDownTimeDay getVacationAddSet(PredetermineTimeSetForCalc predetermineTimeSet,Optional<WorkTimeCode> siftCode
											 ,boolean referenceAtr) {
		//参照する
		if(referenceAtr) {
			if(siftCode.isPresent()) {
				//return predetermineTimeSet.getAdditionSet().getAddTime();
				return predetermineTimeSet.getAdditionSet().getPredTime();
			}
			else {
				if(true/*ここには就業時間帯が存在しない場合の参照先(フィールド)が実装されてきたら、そこを見るようにする*/) {
					/****ここには「勤務実績を参照する」クラスが実装されたら、会社一律設定を返すようにする*****/
					return predetermineTimeSet.getAdditionSet().getAddTime();
				}
				else {
					return predetermineTimeSet.getAdditionSet().getAddTime();
				}
			}
		}
		//社員設定から取得
		else {
			//**時間がある時にちゃんと実装する(今は仮置き↓)**//
			return predetermineTimeSet.getAdditionSet().getAddTime();
		}
	}

	/**
	 * 日単位の休暇加算時間の計算
	 * @author ken_takasu
	 * 
	 * @param statutoryDivision  割増区分（"通常"、"割増")
	 * @param workingSystem　　　　　労働制
	 * @param addSettingOfRegularWork　　
	 * @param vacationAddTimeSet
	 * @param workType
	 * @param predetermineTimeSet
	 * @param siftCode
	 * @param personalCondition
	 * @param addSettingOfIrregularWork
	 * @param addSettingOfFlexWork
	 * @return
	 */
	public VacationAddTime calcVacationAddTime(StatutoryDivision statutoryDivision,
											   WorkingSystem workingSystem,
											   AddSettingOfRegularWork addSettingOfRegularWork,
											   VacationAddTimeSet vacationAddTimeSet,
											   WorkType workType,
											   PredetermineTimeSetForCalc predetermineTimeSet,
											   Optional<WorkTimeCode> siftCode,
											   Optional<PersonalLaborCondition> personalCondition, 
											   AddSettingOfIrregularWork addSettingOfIrregularWork,
											   AddSettingOfFlexWork addSettingOfFlexWork
											   ) {
		VacationAddTime vacationAddTime;
		if (getCalculationByActualTimeAtr(workingSystem, statutoryDivision, addSettingOfRegularWork,
				addSettingOfIrregularWork, addSettingOfFlexWork).isCalclationByActualTime()) {// 実働時間以外も含めて計算する 場合
			// 加算時間の設定を取得
			//referenceAtr 実績の就業時間帯を参照する(休暇加算時間設定クラスのメン)　VN待ちのため、仮でtrueを置く
			BreakDownTimeDay breakdownTimeDay = getVacationAddSet(predetermineTimeSet, siftCode,true);
			// 休暇加算時間を加算するかどうか判断
			vacationAddTime = judgeVacationAddTime(breakdownTimeDay, workingSystem, statutoryDivision,
					addSettingOfRegularWork, vacationAddTimeSet, workType, addSettingOfIrregularWork,
					addSettingOfFlexWork);
		} else {// 実働時間のみで計算する 場合
				// 休暇加算時間を全て 0 で返す
			vacationAddTime = new VacationAddTime(new AttendanceTime(0), new AttendanceTime(0), new AttendanceTime(0));
		}
		return vacationAddTime;
	}
	
	/**
	 * 休暇加算時間の計算
	 * @author ken_takasu
	 * @return
	 */
	public VacationAddTime judgeVacationAddTime(BreakDownTimeDay breakdownTimeDay, 
												WorkingSystem workingSystem,
												StatutoryDivision statutoryDivision, 
												AddSettingOfRegularWork addSettingOfRegularWork,
												VacationAddTimeSet vacationAddTimeSet, 
												WorkType workType,
												AddSettingOfIrregularWork addSettingOfIrregularWork, 
												AddSettingOfFlexWork addSettingOfFlexWork) {
		VacationAddTime vacationAddTime = new VacationAddTime(new AttendanceTime(0), new AttendanceTime(0),
				new AttendanceTime(0));
		// 加算する休暇設定を取得
		AddVacationSet addVacationSet = getAddVacationSet(workingSystem, statutoryDivision, addSettingOfRegularWork,
				vacationAddTimeSet, addSettingOfIrregularWork, addSettingOfFlexWork);
		// 勤務区分をチェックする
		if (workType.isOneDay()) {
			val addTimes = checkVacationToAdd(addVacationSet, workType.getDailyWork().getOneDay(),breakdownTimeDay.getOneDay());
			vacationAddTime = vacationAddTime.addVacationAddTime(addTimes);
		} else {
			vacationAddTime = vacationAddTime.addVacationAddTime(checkVacationToAdd(addVacationSet, workType.getDailyWork().getMorning(),
					breakdownTimeDay.getMorning()));
			vacationAddTime = vacationAddTime.addVacationAddTime(checkVacationToAdd(addVacationSet,
					workType.getDailyWork().getAfternoon(), breakdownTimeDay.getAfternoon()));
		}
		return vacationAddTime;
	}

	/***
	 * 加算する休暇の種類を取得 
	 * @author ken_takasu
	 * @return
	 */
	public AddVacationSet getAddVacationSet(WorkingSystem workingSystem, 
											StatutoryDivision statutoryDivision,
											AddSettingOfRegularWork addSettingOfRegularWork,
											VacationAddTimeSet vacationAddTimeSet,
											AddSettingOfIrregularWork addSettingOfIrregularWork, 
											AddSettingOfFlexWork addSettingOfFlexWork) {
		AddVacationSet addVacationSet = new AddVacationSet(NotUseAtr.Donot, NotUseAtr.Donot, NotUseAtr.Donot);// 下のif文に入らない場合は全てしないを返す
		// 休暇加算設定の取得
		if (getUseAtr(workingSystem, statutoryDivision, addSettingOfRegularWork, addSettingOfIrregularWork,
				addSettingOfFlexWork).isUse()) {// 加算する場合
			addVacationSet = vacationAddTimeSet.getAddVacationSet();
		}
		return addVacationSet;
	}

	/**
	 * 休暇加算設定の取得
	 * @author ken_takasu
	 * @param workingSystem
	 * @return
	 */
	private NotUseAtr getUseAtr(WorkingSystem workingSystem, 
								StatutoryDivision statutoryDivision,
								AddSettingOfRegularWork addSettingOfRegularWork,
								AddSettingOfIrregularWork addSettingOfIrregularWork,
								AddSettingOfFlexWork addSettingOfFlexWork) {
		switch (workingSystem) {
		case REGULAR_WORK:
			return addSettingOfRegularWork.getNotUseAtr(statutoryDivision);

		case FLEX_TIME_WORK:
			return addSettingOfFlexWork.getNotUseAtr(statutoryDivision);

		case VARIABLE_WORKING_TIME_WORK:
			return addSettingOfIrregularWork.getNotUseAtr(statutoryDivision);

		case EXCLUDED_WORKING_CALCULATE:
			throw new RuntimeException("不正な労働制です");
		default:
			throw new RuntimeException("不正な労働制です");
		}
	}

	/**
	 * 休暇加算時間を加算するかチェックする
	 * @author ken_takasu
	 * @return
	 */
	private VacationAddTime checkVacationToAdd(AddVacationSet addVacationSet,
											   WorkTypeClassification workTypeClassification, 
											   AttendanceTime attendanceTime) {
		VacationAddTime vacationAddTime = new VacationAddTime(new AttendanceTime(0), new AttendanceTime(0),
				new AttendanceTime(0));
		if (workTypeClassification.isAnnualLeave()) {// 年休の場合
			if (addVacationSet.getAnnualLeave().isUse()) {
				vacationAddTime = new VacationAddTime(attendanceTime, new AttendanceTime(0), new AttendanceTime(0));
			}
		} else if (workTypeClassification.isYearlyReserved()) {// 積立年休の場合
			if (addVacationSet.getＲetentionYearly().isUse()) {
				vacationAddTime = new VacationAddTime(new AttendanceTime(0), attendanceTime, new AttendanceTime(0));
			}
		} else if (workTypeClassification.isSpecialHoliday()) {// 特別休暇の場合
			if (addVacationSet.getSpecialHoliday().isUse()) {
				vacationAddTime = new VacationAddTime(new AttendanceTime(0), new AttendanceTime(0), attendanceTime);
			}
		}
		return vacationAddTime;
	}

	/**
	 * 休暇加算設定の取得
	 * @author ken_takasu
	 * @param workingSystem
	 * @return
	 */
	private CalculationByActualTimeAtr getCalculationByActualTimeAtr(WorkingSystem workingSystem,
																	 StatutoryDivision statutoryDivision,
																	 AddSettingOfRegularWork addSettingOfRegularWork,
																	 AddSettingOfIrregularWork addSettingOfIrregularWork, 
																	 AddSettingOfFlexWork addSettingOfFlexWork) {
		switch (workingSystem) {
		case REGULAR_WORK:
			return addSettingOfRegularWork.getCalculationByActualTimeAtr(statutoryDivision);

		case FLEX_TIME_WORK:
			return addSettingOfFlexWork.getCalculationByActualTimeAtr(statutoryDivision);

		case VARIABLE_WORKING_TIME_WORK:
			return addSettingOfIrregularWork.getCalculationByActualTimeAtr(statutoryDivision);

		case EXCLUDED_WORKING_CALCULATE:
			throw new RuntimeException("不正な労働制です");
		default:
			throw new RuntimeException("不正な労働制です");
		}
	}



}

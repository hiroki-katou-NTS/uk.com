package nts.uk.ctx.at.record.dom.daily;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.record.dom.daily.calcset.CalcMethodOfNoWorkingDay;
import nts.uk.ctx.at.record.dom.daily.holidayworktime.HolidayMidnightWork;
import nts.uk.ctx.at.record.dom.daily.holidayworktime.HolidayWorkFrameTime;
import nts.uk.ctx.at.record.dom.daily.holidayworktime.HolidayWorkMidNightTime;
import nts.uk.ctx.at.record.dom.daily.holidayworktime.HolidayWorkTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.overtimework.FlexTime;
import nts.uk.ctx.at.record.dom.daily.overtimework.OverTimeOfDaily;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.AttendanceItemDictionaryForCalc;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.IntegrationOfDaily;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.ManageReGetClass;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.OverTimeFrameTime;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.VacationClass;
import nts.uk.ctx.at.record.dom.raborstandardact.flex.SettingOfFlexWork;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.StatutoryDivision;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryOccurrenceSetting;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.StaturoryAtrOfHolidayWork;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneOtherSubHolTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.flexset.CoreTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDailyAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.context.AppContexts;

/**
 * 日別実績の所定外時間
 * @author keisuke_hoshina
 *
 */
@Getter
public class ExcessOfStatutoryTimeOfDaily {
	@Setter
	private ExcessOfStatutoryMidNightTime excessOfStatutoryMidNightTime;
	private Optional<OverTimeOfDaily> overTimeWork;
	private Optional<HolidayWorkTimeOfDaily> workHolidayTime;
	
	
	/**
	 * Constructor
	 * @param excessOfStatutoryMidNightTime
	 * @param overTimeWork
	 * @param workHolidayTime
	 */
	public ExcessOfStatutoryTimeOfDaily(
			ExcessOfStatutoryMidNightTime excessOfStatutoryMidNightTime,
			Optional<OverTimeOfDaily> overTimeWork,
			Optional<HolidayWorkTimeOfDaily> workHolidayTime) {
		super();
		this.excessOfStatutoryMidNightTime = excessOfStatutoryMidNightTime;
		this.overTimeWork = overTimeWork;
		this.workHolidayTime = workHolidayTime;
	}
	
	public void updateOverTime(OverTimeOfDaily ot){
		this.overTimeWork = Optional.ofNullable(ot);
	}
	
	public void updateHoliday(HolidayWorkTimeOfDaily ht){
		this.workHolidayTime = Optional.ofNullable(ht);
	}
	
	/**
	 * 各時間の計算を指示するクラス
	 * @param integrationOfDaily 
	 * @param flexPreAppTime 
	 * @param flexAutoCalSet 
	 * @return
	 */
	public static ExcessOfStatutoryTimeOfDaily calculationExcessTime(ManageReGetClass recordReget,
																	 CalcMethodOfNoWorkingDay calcMethod,
																	 WorkType workType,
																	 Optional<SettingOfFlexWork> flexCalcMethod,
			   														 VacationClass vacationClass,
			   														 StatutoryDivision statutoryDivision,
			   														 Optional<WorkTimeCode> siftCode,
			   														 Optional<WorkTimeDailyAtr> workTimeDailyAtr,
			   														 List<CompensatoryOccurrenceSetting> eachCompanyTimeSet, 
			   														 AttendanceTime flexPreAppTime,
			   														 WorkingConditionItem conditionItem,
			   														Optional<PredetermineTimeSetForCalc> predetermineTimeSetByPersonInfo,Optional<CoreTimeSetting> coreTimeSetting,AttendanceTime beforeApplicationTime) {
		//残業時間
		val overTime = calculationOverTime(recordReget,calcMethod,
										   workType,flexCalcMethod,
										   vacationClass,
				   						   statutoryDivision,siftCode,
				   						   recordReget.getSubHolTransferSetList() == null
				   						   			  ? Optional.empty()
				   							   		  :recordReget.getSubHolTransferSetList().stream().filter(tc -> tc.getOriginAtr().isOverTime()).findFirst(),
										   eachCompanyTimeSet.stream().filter(tc -> tc.getOccurrenceType().isOverTime()).findFirst(),
										   flexPreAppTime,conditionItem,predetermineTimeSetByPersonInfo,coreTimeSetting,beforeApplicationTime);
		//休出時間
		val workHolidayTime = calculationHolidayTime(recordReget,recordReget.getIntegrationOfDaily().getCalAttr().getHolidayTimeSetting().getRestTime(),workType,
				   									 recordReget.getSubHolTransferSetList() == null
				   									 ? Optional.empty()
													 : recordReget.getSubHolTransferSetList().stream().filter(tc -> !tc.getOriginAtr().isOverTime()).findFirst(),
													 eachCompanyTimeSet.stream().filter(tc -> !tc.getOccurrenceType().isOverTime()).findFirst(),
													 recordReget.getIntegrationOfDaily(),beforeApplicationTime);
		//所定外深夜
		val excessOfStatutoryMidNightTime = ExcessOfStatutoryMidNightTime.calcExcessTime(Optional.of(overTime),Optional.of(workHolidayTime));
		
		return new ExcessOfStatutoryTimeOfDaily(excessOfStatutoryMidNightTime, Optional.of(overTime), Optional.of(workHolidayTime));
	}
	


	/**
	 * 残業時間の計算
	 * @param oneDay 
	 * @param integrationOfDaily 
	 * @param flexPreAppTime 
	 * @param flexAutoCalSet 
	 */
	private static OverTimeOfDaily calculationOverTime(ManageReGetClass oneDay,
													   CalcMethodOfNoWorkingDay calcMethod,
													   WorkType workType,
													   Optional<SettingOfFlexWork> flexCalcMethod,
													   VacationClass vacationClass,
													   StatutoryDivision statutoryDivision,Optional<WorkTimeCode> siftCode,
													   Optional<WorkTimezoneOtherSubHolTimeSet> eachWorkTimeSet,
													   Optional<CompensatoryOccurrenceSetting> eachCompanyTimeSet,
													   AttendanceTime flexPreAppTime,
													   WorkingConditionItem conditionItem,
													   Optional<PredetermineTimeSetForCalc> predetermineTimeSetByPersonInfo,Optional<CoreTimeSetting> coreTimeSetting,AttendanceTime beforeApplicationTime) {
		if(oneDay.getCalculationRangeOfOneDay() != null && oneDay.getCalculationRangeOfOneDay().getOutsideWorkTimeSheet().isPresent()) {
			if(oneDay.getCalculationRangeOfOneDay().getOutsideWorkTimeSheet().get().getOverTimeWorkSheet().isPresent()) {
				return OverTimeOfDaily.calculationTime(oneDay,
													   calcMethod,
													   workType,
													   flexCalcMethod,
													   vacationClass,
													   statutoryDivision,siftCode,
													   eachWorkTimeSet,
													   eachCompanyTimeSet,
													   flexPreAppTime,
													   conditionItem,
													   predetermineTimeSetByPersonInfo,coreTimeSetting,beforeApplicationTime);
			}
		}
		//残業時間帯が存在せず、時間を求められない場合
		List<OverTimeFrameTime> calcOverTimeWorkTimeList = new ArrayList<>();
		return new OverTimeOfDaily(Collections.emptyList(),
								   calcOverTimeWorkTimeList,
								   Finally.of(new ExcessOverTimeWorkMidNightTime(TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0)))),
								   new AttendanceTime(0),
								   new FlexTime(TimeDivergenceWithCalculationMinusExist.sameTime(new AttendanceTimeOfExistMinus(0)), new AttendanceTime(0)),
								   new AttendanceTime(0));
	}
	
	/**
	 * 休出時間の計算
	 * @param integrationOfDaily 
	 * @return
	 */
	private static HolidayWorkTimeOfDaily calculationHolidayTime(ManageReGetClass recordReget,AutoCalSetting autoCalSetting,
			 													 WorkType workType,
			 													Optional<WorkTimezoneOtherSubHolTimeSet> eachWorkTimeSet,
			 													 Optional<CompensatoryOccurrenceSetting> eachCompanyTimeSet, IntegrationOfDaily integrationOfDaily,AttendanceTime beforeApplicationTime) {
		if(recordReget.getCalculatable() && recordReget.getCalculationRangeOfOneDay().getOutsideWorkTimeSheet().isPresent()) {
			if(recordReget.getCalculationRangeOfOneDay().getOutsideWorkTimeSheet().get().getHolidayWorkTimeSheet().isPresent()) {
				return HolidayWorkTimeOfDaily.calculationTime(recordReget.getCalculationRangeOfOneDay().getOutsideWorkTimeSheet().get().getHolidayWorkTimeSheet().get(), 
															  autoCalSetting,
															  workType,
															  eachWorkTimeSet,
															  eachCompanyTimeSet,
															  integrationOfDaily,beforeApplicationTime,
															  recordReget.getIntegrationOfDaily().getCalAttr().getHolidayTimeSetting().getLateNightTime());
			}
		}
		
		//休日出勤時間帯が存在せず、時間を求められない場合
		List<HolidayWorkFrameTime> calcHolidayTimeWorkTimeList = new ArrayList<>();
		List<HolidayWorkMidNightTime> addList = new ArrayList<>(); 
		addList.add(new HolidayWorkMidNightTime(TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0)), StaturoryAtrOfHolidayWork.ExcessOfStatutoryHolidayWork));
		addList.add(new HolidayWorkMidNightTime(TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0)), StaturoryAtrOfHolidayWork.PublicHolidayWork));
		addList.add(new HolidayWorkMidNightTime(TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0)), StaturoryAtrOfHolidayWork.WithinPrescribedHolidayWork));
		return new HolidayWorkTimeOfDaily(Collections.emptyList(),
										  calcHolidayTimeWorkTimeList,
				   						  Finally.of(new HolidayMidnightWork(addList)),
				   						  new AttendanceTime(0));
	}
	
	/**
	 * 残業時間超過 
	 */
	public List<EmployeeDailyPerError> checkOverTimeExcess(String employeeId,
														   GeneralDate targetDate,
														   String searchWord,
														   AttendanceItemDictionaryForCalc attendanceItemDictionary,
														   ErrorAlarmWorkRecordCode errorCode) {
		List<EmployeeDailyPerError> returnErrorItem = new ArrayList<>();
		if(this.getOverTimeWork().isPresent())
			returnErrorItem = this.getOverTimeWork().get().checkOverTimeExcess(employeeId,targetDate,searchWord, attendanceItemDictionary,errorCode);
		return returnErrorItem;
	}
	
	/**
	 * 事前残業申請超過 
	 */
	public List<EmployeeDailyPerError> checkPreOverTimeExcess(String employeeId,
														   GeneralDate targetDate,
														   String searchWord,
														   AttendanceItemDictionaryForCalc attendanceItemDictionary,
														   ErrorAlarmWorkRecordCode errorCode) {
		List<EmployeeDailyPerError> returnErrorItem = new ArrayList<>();
		if(this.getOverTimeWork().isPresent())
			returnErrorItem = this.getOverTimeWork().get().checkPreOverTimeExcess(employeeId,targetDate,searchWord, attendanceItemDictionary, errorCode);
		return returnErrorItem;
	}
	/**
	 * フレ時間超過 
	 */
	public List<EmployeeDailyPerError> checkFlexTimeExcess(String employeeId,
			   											   GeneralDate targetDate,
														   String searchWord,
														   AttendanceItemDictionaryForCalc attendanceItemDictionary,
														   ErrorAlarmWorkRecordCode errorCode) {
		List<EmployeeDailyPerError> returnErrorItem = new ArrayList<>();
		if(this.getOverTimeWork().isPresent())
			returnErrorItem = this.getOverTimeWork().get().checkFlexTimeExcess(employeeId,targetDate,searchWord, attendanceItemDictionary, errorCode);
		return returnErrorItem;
	}
	
	/**
	 * フレ時間超過 
	 */
	public List<EmployeeDailyPerError> checkPreFlexTimeExcess(String employeeId,
			   											   GeneralDate targetDate,
														   String searchWord,
														   AttendanceItemDictionaryForCalc attendanceItemDictionary,
														   ErrorAlarmWorkRecordCode errorCode) {
		List<EmployeeDailyPerError> returnErrorItem = new ArrayList<>();
		if(this.getOverTimeWork().isPresent())
			returnErrorItem = this.getOverTimeWork().get().checkPreFlexTimeExcess(employeeId,targetDate,searchWord, attendanceItemDictionary, errorCode);
		return returnErrorItem;
	}
	
	/**
	 * 休出時間超過 
	 */
	public List<EmployeeDailyPerError> checkHolidayWorkTimeExcess(String employeeId,
														   		  GeneralDate targetDate,
																   String searchWord,
																   AttendanceItemDictionaryForCalc attendanceItemDictionary,
																   ErrorAlarmWorkRecordCode errorCode) {
		List<EmployeeDailyPerError> returnErrorItem = new ArrayList<>();
		if(this.getWorkHolidayTime().isPresent())
			returnErrorItem = this.getWorkHolidayTime().get().checkHolidayWorkExcess(employeeId,targetDate,searchWord, attendanceItemDictionary, errorCode);
		return returnErrorItem;
	}
	
	/**
	 * 事前休出申請超過 
	 */
	public List<EmployeeDailyPerError> checkPreHolidayWorkTimeExcess(String employeeId,
														   		  GeneralDate targetDate,
																   String searchWord,
																   AttendanceItemDictionaryForCalc attendanceItemDictionary,
																   ErrorAlarmWorkRecordCode errorCode) {
		List<EmployeeDailyPerError> returnErrorItem = new ArrayList<>();
		if(this.getWorkHolidayTime().isPresent())
			returnErrorItem = this.getWorkHolidayTime().get().checkPreHolidayWorkExcess(employeeId,targetDate,searchWord, attendanceItemDictionary, errorCode);
		return returnErrorItem;
	}
	
	/**
	 * 実績所定外深夜時間超過
	 */
	public List<EmployeeDailyPerError> checkMidNightExcess(String employeeId,
			   											   GeneralDate targetDate,
														   String searchWord,
														   AttendanceItemDictionaryForCalc attendanceItemDictionary,
														   ErrorAlarmWorkRecordCode errorCode) {
		List<EmployeeDailyPerError> returnErrorList = new ArrayList<>();
		if(this.getExcessOfStatutoryMidNightTime().isOverLimitDivergenceTime()) {
			val itemId = attendanceItemDictionary.findId(searchWord);
			if(itemId.isPresent())
				returnErrorList.add(new EmployeeDailyPerError(AppContexts.user().companyCode(), employeeId, targetDate, errorCode, itemId.get()));
		}
		//残業深夜
		if(this.getOverTimeWork().isPresent()) {
			returnErrorList.addAll(this.getOverTimeWork().get().checkNightTimeExcess(employeeId,targetDate, "就外残業深夜時間", attendanceItemDictionary, errorCode));
		}
		//休出深夜
		if(this.getWorkHolidayTime().isPresent()) {
			returnErrorList.addAll(this.getWorkHolidayTime().get().checkNightTimeExcess(employeeId,targetDate, attendanceItemDictionary, errorCode));
		}
		return returnErrorList;
	}
	
	/**
	 * 事前所定外深夜申請時間超過 
	 */
	public List<EmployeeDailyPerError> checkPreMidNightExcess(String employeeId,
			   											   GeneralDate targetDate,
														   String searchWord,
														   AttendanceItemDictionaryForCalc attendanceItemDictionary,
														   ErrorAlarmWorkRecordCode errorCode) {
		List<EmployeeDailyPerError> returnErrorList = new ArrayList<>();
		if(this.getExcessOfStatutoryMidNightTime().isPreOverLimitDivergenceTime()) {
			val itemId = attendanceItemDictionary.findId(searchWord);
			if(itemId.isPresent())
				returnErrorList.add(new EmployeeDailyPerError(AppContexts.user().companyCode(), employeeId, targetDate, errorCode, itemId.get()));
		}
		return returnErrorList;
	}
	
	/**
	 * 乖離時間のみ再計算
	 * @return
	 */
	public ExcessOfStatutoryTimeOfDaily calcDiverGenceTime() {
		Optional<OverTimeOfDaily> overtime = this.overTimeWork.isPresent()?Optional.of(this.overTimeWork.get().calcDiverGenceTime()):Optional.empty();
		Optional<HolidayWorkTimeOfDaily> holiday = this.workHolidayTime.isPresent()?Optional.of(this.workHolidayTime.get().calcDiverGenceTime()):Optional.empty();
		ExcessOfStatutoryMidNightTime excessOfStatutoryMidNightTime = this.excessOfStatutoryMidNightTime!=null?this.excessOfStatutoryMidNightTime.calcDiverGenceTime():this.excessOfStatutoryMidNightTime;
		return new ExcessOfStatutoryTimeOfDaily(excessOfStatutoryMidNightTime,overtime,holiday); 
	}
	
	/**
	 *　所定外深夜時間の手修正後の再計算
	 * @param diffHolidayWorkTime 手修正前後の残業時間の差
	 * @param diffOverTime 手修正前後の休出時間の差
	 */
	public void reCalcMidNightTime() {
		ExcessOverTimeWorkMidNightTime overMidTime = new ExcessOverTimeWorkMidNightTime(TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0)));
		TimeDivergenceWithCalculation holidayMidTime = TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0));
		if(this.getOverTimeWork().isPresent()
			&& this.getOverTimeWork().get().getExcessOverTimeWorkMidNightTime().isPresent()) {
			overMidTime = this.getOverTimeWork().get().getExcessOverTimeWorkMidNightTime().get();
		}
		if(this.getWorkHolidayTime().isPresent()
			&& this.getWorkHolidayTime().get().getHolidayMidNightWork().isPresent()) {
			holidayMidTime = this.getWorkHolidayTime().get().getHolidayMidNightWork().get().calcAllMidTime();
		}
		this.excessOfStatutoryMidNightTime = new ExcessOfStatutoryMidNightTime(TimeDivergenceWithCalculation.createTimeWithCalculation(overMidTime.getTime().getTime().addMinutes(holidayMidTime.getTime().valueAsMinutes()),
																																	   overMidTime.getTime().getCalcTime().addMinutes(holidayMidTime.getCalcTime().valueAsMinutes())),
																			   this.excessOfStatutoryMidNightTime.getBeforeApplicationTime());
	}
	
	/**
	 * 深夜時間の上限時間調整処理
	 * @param upperTime 上限時間
	 */
	public void controlMidTimeUpper(AttendanceTime upperTime) {
		this.excessOfStatutoryMidNightTime.controlUpperTime(upperTime);
	}
	
	/**
	 * 全残業枠の残業時間と振替残業時間の合計時間を算出する
	 * @return　残業合計時間　＋　振替残業合計時間
	 */
	public AttendanceTime calcOverTime() {
		if(this.overTimeWork != null
		&& this.overTimeWork.isPresent()) {
			return new AttendanceTime(this.getOverTimeWork().get().calcTotalFrameTime() + this.getOverTimeWork().get().calcTransTotalFrameTime());
		}
		return new AttendanceTime(0);
	}
}

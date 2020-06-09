package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.DailyTime;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.AddSetting;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.DeductLeaveEarly;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayAddtionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayCalcMethodSet;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.breakorgoout.enums.BreakType;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.DailyUnit;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;
import nts.uk.ctx.at.shared.dom.workrule.statutoryworktime.DailyCalculationPersonalInformation;
import nts.uk.ctx.at.shared.dom.worktime.IntegrationOfWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.OverTimeOfTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneOtherSubHolTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixRestTimezoneSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkCalcSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.CoreTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexCalcSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * 時間帯作成、時間計算で再取得が必要になっているクラスたちの管理クラス
 * @author keisuke_hoshina
 *
 */
@Getter
public class ManageReGetClass {
	
	/** 1日の範囲 */
	private CalculationRangeOfOneDay calculationRangeOfOneDay;
	
	/** 日別実績(WORK) */
	@Setter
	private IntegrationOfDaily integrationOfDaily;
	
	/** 勤務種類 */
	@Setter
	private Optional<WorkType> workType;
	
	/** 会社別設定管理 */
	private ManagePerCompanySet companyCommonSetting;
	
	/** 社員設定管理 */
	private ManagePerPersonDailySet personDailySetting;
	
	/** 統合就業時間帯 */
	private IntegrationOfWorkTime integrationOfWorkTime;
	
	/**
	 * Constructor
	 * @param calculationRangeOfOneDay 1日の計算範囲
	 * @param companyCommonSetting 会社別設定管理
	 * @param personDailySetting 社員設定管理
	 * @param workType 勤務種類
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param integrationOfDaily 日別実績(Work)
	 */
	public ManageReGetClass(
			CalculationRangeOfOneDay calculationRangeOfOneDay,
			ManagePerCompanySet companyCommonSetting,
			ManagePerPersonDailySet personDailySetting,
			Optional<WorkType> workType,
			IntegrationOfWorkTime integrationOfWorkTime,
			IntegrationOfDaily integrationOfDaily) {
		super();
		this.calculationRangeOfOneDay = calculationRangeOfOneDay;
		this.companyCommonSetting = companyCommonSetting;
		this.personDailySetting = personDailySetting;
		this.workType = workType;
		this.integrationOfWorkTime = integrationOfWorkTime;
		this.integrationOfDaily = integrationOfDaily;
	}
	
	/**
	 * 就業時間帯の設定を取得する
	 * @return 就業時間帯の設定
	 */
	public Optional<WorkTimeSetting> getWorkTimeSetting() {
		return Optional.of(this.integrationOfWorkTime.getWorkTimeSetting());
	}
	
	/**
	 * 就業時間帯別代休時間設定(List)を取得する
	 * @return 就業時間帯別代休時間設定(List)
	 */
	public List<WorkTimezoneOtherSubHolTimeSet> getSubHolTransferSetList() {
		return this.integrationOfWorkTime.getCommonSetting().getSubHolTimeSet();
	}
	
	/**
	 * 日別計算用の個人情報を取得する
	 * @return 日別計算用の個人情報
	 */
	public DailyCalculationPersonalInformation getPersonalInfo() {
		return new DailyCalculationPersonalInformation(
				Optional.of(new DailyTime(this.personDailySetting.getPredetermineTimeSetByPersonWeekDay().getAdditionSet().getPredTime().getOneDay().valueAsMinutes())),
				new DailyTime(this.personDailySetting.getDailyUnit().getDailyTime().valueAsMinutes()),
				this.personDailySetting.getPersonInfo().getLaborSystem());
	}
	
	/**
	 * 法定労働時間を取得する
	 * @return 法定労働時間
	 */
	public DailyUnit getDailyUnit() {
		return this.personDailySetting.getDailyUnit();
	}
	
	/**
	 * 固定勤務の休憩時間帯を取得する
	 * @return 固定勤務の休憩時間帯
	 */
	public Optional<FixRestTimezoneSet> getFixRestTimeSetting() {
		return Optional.of(this.integrationOfWorkTime.getFixedWorkSetting().get().getLstHalfDayWorkTimezone().get(0).getRestTimezone());
	}
	
	/**
	 * 就業時間の時間帯設定(List)を取得する
	 * @return 就業時間の時間帯設定
	 */
	public List<EmTimeZoneSet> getFixWoSetting() {
		return this.integrationOfWorkTime.getEmTimeZoneSetList(this.workType.get());
	}
	
	/**
	 * 固定勤務の計算設定を取得する
	 * @return 固定勤務の計算設定
	 */
	public Optional<FixedWorkCalcSetting> getOotsukaFixedWorkSet() {
		return this.integrationOfWorkTime.getFixedWorkSetting().get().getCalculationSetting();
	}
	
	/**
	 * 休暇の計算方法の設定を取得する
	 * @return 休暇の計算方法の設定
	 */
	public HolidayCalcMethodSet getHolidayCalcMethodSet() {
		return this.personDailySetting.getAddSetting().getVacationCalcMethodSet();
	}
	
	/**
	 * 計算処理に入ることができるかフラグ
	 * (造語)
	 * 計算処理に入る場合にのみManageReGetClassを作成する為、常に計算するを返す
	 * @return true
	 */
	public boolean getCalculatable() {
		return true;
	}
	
	/**
	 * 休憩回数を取得する
	 * @return 休憩回数
	 */
	public int getBreakCount() {
		//常に実績から取得する
		Optional<BreakTimeOfDailyPerformance> record = integrationOfDaily.getBreakTime().stream()
				.filter(dailyPerformance -> dailyPerformance.getBreakType().equals(BreakType.REFER_WORK_TIME))
				.findFirst();
		if(!record.isPresent()) return 0;
		
		return record.get().getBreakTimeSheets().stream()
				.filter(timeSheet -> (timeSheet.getStartTime() != null && timeSheet.getEndTime() != null && timeSheet.getEndTime().greaterThan(timeSheet.getStartTime())))
				.collect(Collectors.toList()).size();
	}
	
	/**
	 * コアタイム時間帯設定を取得する
	 * (出勤系ではない場合は最低勤務時間を0：00にする)
	 * @return コアタイム時間帯設定
	 */
	public Optional<CoreTimeSetting> getCoreTimeSetting() {
		if (!this.integrationOfWorkTime.getFlexWorkSetting().isPresent())
			return Optional.empty();
			
		if (!this.workType.isPresent())
			return Optional.of(this.integrationOfWorkTime.getFlexWorkSetting().get().getCoreTimeSetting());
		
		if (this.workType.get().isWeekDayAttendance()) {
			return Optional.of(this.integrationOfWorkTime.getFlexWorkSetting().get().getCoreTimeSetting());
		} else {// 出勤系ではない場合は最低勤務時間を0：00にする
			return Optional.of(this.integrationOfWorkTime.getFlexWorkSetting().get().getCoreTimeSetting().changeZeroMinWorkTime());
		}
	}
	
	//AddSetting
	
	/**
	 * 休暇加算時間設定を取得する
	 * @return 休暇加算時間設定
	 */
	public Optional<HolidayAddtionSet> getHolidayAddtionSet(){
		return this.companyCommonSetting.getHolidayAdditionPerCompany();
	}
	
	/**
	 * 就業時間帯の共通設定を取得する
	 * @return 就業時間帯の共通設定
	 */
	public Optional<nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet> getWorkTimezoneCommonSet() {
		return Optional.of(this.integrationOfWorkTime.getCommonSetting());
	}
	
	/**
	 * 法定内残業枠No(List)を取得する
	 * @return 法定内残業枠No(List)
	 */
	public  List<OverTimeFrameNo> getStatutoryFrameNoList() {
		return this.integrationOfWorkTime.getLegalOverTimeFrameNoList(this.workType.get());
	}
	
	/**
	 * フレックス計算設定を取得する
	 * @return フレックス計算設定
	 */
	public Optional<FlexCalcSetting> getFlexCalcSetting() {
		return Optional.of(this.integrationOfWorkTime.getFlexWorkSetting().get().getCalculateSetting());
	}
	
	/**
	 * 「遅刻早退を控除する」を取得する
	 * @return 遅刻早退を控除する
	 */
	public Optional<DeductLeaveEarly> getLeaveLateSet() {
		return Optional.of(this.personDailySetting.getAddSetting().getVacationCalcMethodSet().getWorkTimeCalcMethodOfHoliday().getAdvancedSet().get().getNotDeductLateLeaveEarly());
	}
	
	/**
	 * 残業時間の時間帯設定(List)を取得する
	 * @return 残業時間の時間帯設定(List)
	 */
	public List<OverTimeOfTimeZoneSet> getOverTimeSheetSetting() {
		return this.integrationOfWorkTime.getOverTimeOfTimeZoneSetList(this.workType.get());
	}
	
	/**
	 * 計算用所定時間設定を取得する
	 * @return 計算用所定時間設定
	 */
	public Optional<PredetermineTimeSetForCalc> getPredSetForOOtsuka() {
		return Optional.of(this.calculationRangeOfOneDay.getPredetermineTimeSetForCalc());
	}

//ichioka削除
//	public ManageReGetClass(CalculationRangeOfOneDay calculationRangeOfOneDay, IntegrationOfDaily integrationOfDaily,
//			Optional<WorkTimeSetting> workTimeSetting, Optional<WorkType> workType,
//			List<WorkTimezoneOtherSubHolTimeSet> subHolTransferSetList,
//			DailyCalculationPersonalInformation personalInfo, DailyUnit dailyUnit,
//			Optional<FixRestTimezoneSet> fixRestTimeSetting, 
//			List<EmTimeZoneSet> fixWoSetting,
//			Optional<FixedWorkCalcSetting> ootsukaFixedWorkSet,
//			HolidayCalcMethodSet holidayCalcMethodSet, 
//			Boolean calculatable, 
//			int breakCount,
//			Optional<CoreTimeSetting> coreTimeSetting, 
//			WorkRegularAdditionSet workRegularAdditionSet,
//			WorkFlexAdditionSet workFlexAdditionSet, 
//			HourlyPaymentAdditionSet hourlyPaymentAdditionSet,
//			WorkDeformedLaborAdditionSet workDeformedLaborAdditionSet, 
//			Optional<nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet> workTimezoneCommonSet,
//			List<OverTimeFrameNo> statutoryFrameNoList,
//			Optional<FlexCalcSetting> flexCalcSetting,
//			Optional<DeductLeaveEarly> leaveLateSet,
//			List<OverTimeOfTimeZoneSet> overTimeSheetSetting,
//			Optional<PredetermineTimeSetForCalc> predSetForOotsuka) {
//		super();
//		this.calculationRangeOfOneDay = calculationRangeOfOneDay;
//		this.integrationOfDaily = integrationOfDaily;
//		this.workTimeSetting = workTimeSetting;
//		this.workType = workType;
//		this.subHolTransferSetList = subHolTransferSetList;
//		this.personalInfo = personalInfo;
//		this.dailyUnit = dailyUnit;
//		this.fixRestTimeSetting = fixRestTimeSetting;
//		this.fixWoSetting = fixWoSetting;
//		this.ootsukaFixedWorkSet = ootsukaFixedWorkSet;
//		this.holidayCalcMethodSet = holidayCalcMethodSet;
//		this.calculatable = calculatable;
//		this.breakCount = breakCount;
//		this.coreTimeSetting = coreTimeSetting;
//		this.workRegularAdditionSet = workRegularAdditionSet;
//		this.workFlexAdditionSet = workFlexAdditionSet;
//		this.hourlyPaymentAdditionSet = hourlyPaymentAdditionSet;
//		this.workDeformedLaborAdditionSet = workDeformedLaborAdditionSet;
//		this.WorkTimezoneCommonSet = workTimezoneCommonSet;
//		this.statutoryFrameNoList = statutoryFrameNoList;
//		this.flexCalcSetting = flexCalcSetting;
//		this.leaveLateSet = leaveLateSet;
//		this.overTimeSheetSetting = overTimeSheetSetting;
//		this.predSetForOOtsuka = predSetForOotsuka;
//	}
//	
//	public ManageReGetClass(
//			CalculationRangeOfOneDay calculationRangeOfOneDay,
//			ManagePerCompanySet companyCommonSetting,
//			ManagePerPersonDailySet personDailySetting,
//			WorkType workType,
//			IntegrationOfWorkTime integrationOfWorkTime,
//			IntegrationOfDaily integrationOfDaily) {
//		super();
//		this.calculationRangeOfOneDay = calculationRangeOfOneDay;
//		this.integrationOfDaily = integrationOfDaily;
//		this.workTimeSetting = Optional.of(integrationOfWorkTime.getWorkTimeSetting());
//		this.workType = Optional.of(workType);
//		this.subHolTransferSetList = integrationOfWorkTime.getCommonSetting().getSubHolTimeSet();
//		this.personalInfo = new DailyCalculationPersonalInformation(
//				Optional.of(new DailyTime(personDailySetting.getPredetermineTimeSetByPersonWeekDay().getAdditionSet().getPredTime().getOneDay().valueAsMinutes())),
//				new DailyTime(personDailySetting.getDailyUnit().getDailyTime().valueAsMinutes()),
//				personDailySetting.getPersonInfo().getLaborSystem());
//		this.dailyUnit = personDailySetting.getDailyUnit();
//		this.fixRestTimeSetting = Optional.of(integrationOfWorkTime.getFixedWorkSetting().get().getLstHalfDayWorkTimezone().get(0).getRestTimezone());
//		this.fixWoSetting = integrationOfWorkTime.getEmTimeZoneSetList(workType);
//		this.ootsukaFixedWorkSet = integrationOfWorkTime.getFixedWorkSetting().get().getCalculationSetting();
//		this.holidayCalcMethodSet = personDailySetting.getAddSetting().getVacationCalcMethodSet();
//		this.calculatable = true;
//		this.breakCount = 0;
//		this.coreTimeSetting = integrationOfWorkTime.getCoreTimeSetting();
//		this.workRegularAdditionSet = workRegularAdditionSet;
//		this.workFlexAdditionSet = workFlexAdditionSet;
//		this.hourlyPaymentAdditionSet = hourlyPaymentAdditionSet;
//		this.workDeformedLaborAdditionSet = workDeformedLaborAdditionSet;
//		this.WorkTimezoneCommonSet = Optional.of(integrationOfWorkTime.getCommonSetting());
//		this.statutoryFrameNoList = integrationOfWorkTime.getLegalOverTimeFrameNoList(workType);
//		this.flexCalcSetting = Optional.of(integrationOfWorkTime.getFlexWorkSetting().get().getCalculateSetting());
//		this.leaveLateSet = Optional.of(personDailySetting.getAddSetting().getVacationCalcMethodSet().getWorkTimeCalcMethodOfHoliday().getAdvancedSet().get().getNotDeductLateLeaveEarly());
//		this.overTimeSheetSetting = integrationOfWorkTime.getOverTimeOfTimeZoneSetList(workType);
//		this.predSetForOOtsuka = Optional.of(calculationRangeOfOneDay.getPredetermineTimeSetForCalc());
//	}
	
	
	
//	/**
//	 * 計算処理に入ることができないと判断できた時Factory Method
//	 * @param personalInfo2 
//	 */
//	public static ManageReGetClass cantCalc(Optional<WorkType> workType,IntegrationOfDaily integration, DailyCalculationPersonalInformation personalInfo) {
//		return new ManageReGetClass(new CalculationRangeOfOneDay(Finally.of(new FlexWithinWorkTimeSheet(Arrays.asList(new WithinWorkTimeFrame(new EmTimeFrameNo(5), 
//																																			  new TimeSpanForDailyCalc(new TimeWithDayAttr(0), new TimeWithDayAttr(0)),
//																																			  new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN),
//																																			  Collections.emptyList(), 
//																																			  Collections.emptyList(), 
//																																			  Collections.emptyList(), 
//																																			  Optional.empty(), 
//																																			  Collections.emptyList(), 
//																																			  Optional.empty(), 
//																																			  Optional.empty())), 
//																										Collections.emptyList(),
//																										Optional.empty())),
//																 Finally.of(new OutsideWorkTimeSheet(Optional.empty(),Optional.empty())), 
//																 null, 
//																 integration.getAttendanceLeave().orElse(null), 
//																 null, 
//																 integration.getWorkInformation(),
//																 Optional.empty()), 
//									integration,
//									Optional.empty(),
//									workType, 
//									Collections.emptyList(), 
//									personalInfo,
//									null,
//									Optional.empty(),
//									Collections.emptyList(), 
//									Optional.empty(),
//									null,
//									false,
//									0,
//									Optional.empty(),
//									null,
//									null,
//									null,
//									null,
//									Optional.empty(),
//									Collections.emptyList(),
//									Optional.empty(),
//									Optional.empty(),
//									Collections.emptyList(),
//									Optional.empty());
//				
//	}
//	
//	/**
//	 * 計算処理に入ることができないと判断できた時Factory Method
//	 * @param personalInfo2 
//	 */
//	public static ManageReGetClass cantCalc2(Optional<WorkType> workType,
//											 IntegrationOfDaily integration,
//											 DailyCalculationPersonalInformation personalInfo,
//											 HolidayCalcMethodSet holidayCalcMethodSet, 
//											 WorkRegularAdditionSet workRegularAdditionSet,
//											 WorkFlexAdditionSet workFlexAdditionSet, 
//											 HourlyPaymentAdditionSet hourlyPaymentAdditionSet,
//											 WorkDeformedLaborAdditionSet workDeformedLaborAdditionSet,
//											 Optional<DeductLeaveEarly> lateLeave,
//											 Optional<PredetermineTimeSetForCalc> predSetForOotsuka
//			) {
//		return new ManageReGetClass(new CalculationRangeOfOneDay(Finally.of(new FlexWithinWorkTimeSheet(Arrays.asList(new WithinWorkTimeFrame(new EmTimeFrameNo(5), 
//																																			  new TimeSpanForDailyCalc(new TimeWithDayAttr(0), new TimeWithDayAttr(0)),
//																																			  new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN),
//																																			  Collections.emptyList(), 
//																																			  Collections.emptyList(), 
//																																			  Collections.emptyList(), 
//																																			  Optional.empty(), 
//																																			  Collections.emptyList(), 
//																																			  Optional.empty(), 
//																																			  Optional.empty())), 
//																										Collections.emptyList(),
//																										Optional.empty())),
//																 Finally.of(new OutsideWorkTimeSheet(Optional.empty(),Optional.empty())), 
//																 null, 
//																 integration.getAttendanceLeave().orElse(null), 
//																 null, 
//																 integration.getWorkInformation(),
//																 Optional.empty()), 
//									integration,
//									Optional.empty(),
//									workType, 
//									Collections.emptyList(), 
//									personalInfo,
//									null,
//									Optional.empty(),
//									Collections.emptyList(), 
//									Optional.empty(),
//									holidayCalcMethodSet,
//									false,
//									0,
//									Optional.empty(),
//									workRegularAdditionSet,
//									workFlexAdditionSet,
//									hourlyPaymentAdditionSet,
//									workDeformedLaborAdditionSet,
//									Optional.empty(),
//									Collections.emptyList(),
//									Optional.empty(),
//									lateLeave,
//									Collections.emptyList(),
//									predSetForOotsuka);
//				
//	}
//	
//		
//	/**
//	 * 計算処理に入ることができると判断できた時Factory Method
//	 */
//	public static ManageReGetClass canCalc(CalculationRangeOfOneDay calculationRangeOfOneDay, IntegrationOfDaily integrationOfDaily,
//											Optional<WorkTimeSetting> workTimeSetting, Optional<WorkType> workType,
//											List<WorkTimezoneOtherSubHolTimeSet> subHolTransferSetList,
//											DailyCalculationPersonalInformation personalInfo, DailyUnit dailyUnit,
//											Optional<FixRestTimezoneSet> fixRestTimeSetting, 
//											List<EmTimeZoneSet> fixWoSetting,
//											Optional<FixedWorkCalcSetting> ootsukaFixedWorkSet,
//											HolidayCalcMethodSet holidayCalcMethodSet, 
//											int breakCount,
//											Optional<CoreTimeSetting> coreTimeSetting, 
//											WorkRegularAdditionSet workRegularAdditionSet,
//											WorkFlexAdditionSet workFlexAdditionSet, 
//											HourlyPaymentAdditionSet hourlyPaymentAdditionSet,
//											WorkDeformedLaborAdditionSet workDeformedLaborAdditionSet,
//											Optional<nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet> workTimezoneCommonSet,
//											List<OverTimeFrameNo> statutoryFrameNoList,
//											Optional<FlexCalcSetting> flexCalcSetting,
//											Optional<DeductLeaveEarly> lateLeave,
//											List<OverTimeOfTimeZoneSet> overTimeSheetSetting,
//											Optional<PredetermineTimeSetForCalc> predSetForOotsuka) {
//		
//		return new ManageReGetClass(calculationRangeOfOneDay,
//									integrationOfDaily,
//									workTimeSetting,
//									workType,
//									subHolTransferSetList,
//									personalInfo,
//									dailyUnit,
//									fixRestTimeSetting,
//									fixWoSetting,
//									ootsukaFixedWorkSet,
//									holidayCalcMethodSet,
//									true,
//									breakCount,
//									coreTimeSetting,
//									workRegularAdditionSet,
//									workFlexAdditionSet,
//									hourlyPaymentAdditionSet,
//									workDeformedLaborAdditionSet,
//									workTimezoneCommonSet,
//									statutoryFrameNoList,
//									flexCalcSetting,
//									lateLeave,
//									overTimeSheetSetting,
//									predSetForOotsuka);
//	
//	}
	
//	/**
//	 * 会社共通で使いまわしをする設定をこのクラスにも設定する
//	 */
//	public void setCompanyCommonSetting(ManagePerCompanySet managePerCompany, ManagePerPersonDailySet managePerPerson) {
//		this.holidayAddtionSet = managePerCompany.getHolidayAdditionPerCompany();
//		this.dailyUnit = managePerPerson.getDailyUnit();
//	}

	/**
	 * 加算設定を取得する
	 * @return 自身の労働制の加算設定
	 */
	public AddSetting getAddSetting() {
		return this.personDailySetting.getAddSetting();
	}
}

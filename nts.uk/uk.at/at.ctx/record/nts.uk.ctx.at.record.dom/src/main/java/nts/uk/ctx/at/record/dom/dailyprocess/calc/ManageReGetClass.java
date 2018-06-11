package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.calculation.holiday.HolidayAddtionSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.HourlyPaymentAdditionSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.WorkDeformedLaborAdditionSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.WorkFlexAdditionSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.WorkRegularAdditionSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.kmk013_splitdomain.HolidayCalcMethodSet;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.DailyUnit;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;
import nts.uk.ctx.at.shared.dom.workrule.statutoryworktime.DailyCalculationPersonalInformation;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneOtherSubHolTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixRestTimezoneSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkCalcSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.CoreTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * 時間帯作成、時間計算で再取得が必要になっているクラスたちの管理クラス
 * @author keisuke_hoshina
 *
 */
@Getter
public class ManageReGetClass {
	
	//1日の範囲
	private CalculationRangeOfOneDay calculationRangeOfOneDay;
	
	//日別実績(WORK)
	@Setter
	private IntegrationOfDaily integrationOfDaily;
	
	//就業時間帯設定
	private Optional<WorkTimeSetting> workTimeSetting;
	
	//勤務種類
	private Optional<WorkType> workType;
	
	//就業時間帯別代休時間設定
	private List<WorkTimezoneOtherSubHolTimeSet> subHolTransferSetList;
	
	//労働制
	private DailyCalculationPersonalInformation personalInfo;
	
	//法定労働時間(日単位)
	private DailyUnit dailyUnit;
	
	//大塚用　固定勤務の休憩時間帯保持
	private Optional<FixRestTimezoneSet> fixRestTimeSetting;
	
	//大塚要ケインで使用する固定計算設定クラス
	private Optional<FixedWorkCalcSetting> ootsukaFixedWorkSet;
	
	//休暇の計算方法の設定
	private HolidayCalcMethodSet holidayCalcMethodSet;
	
	//計算処理に入ることができるかフラグ
	//(造語)
	private Boolean calculatable;
	
	//休憩回数
	int breakCount;
	
	//フレックス勤務設定
	Optional<CoreTimeSetting> coreTimeSetting;

	//各種加算設定
	private WorkRegularAdditionSet workRegularAdditionSet;
	private WorkFlexAdditionSet workFlexAdditionSet;
	private HourlyPaymentAdditionSet hourlyPaymentAdditionSet;
	private WorkDeformedLaborAdditionSet workDeformedLaborAdditionSet;
	private HolidayAddtionSet holidayAddtionSet;
	
	//法て内残業枠ＮＯリスト
	List<OverTimeFrameNo> statutoryFrameNoList;
	/**
	 * Constructor 
	 */
	private ManageReGetClass(CalculationRangeOfOneDay calculationRangeOfOneDay, IntegrationOfDaily integrationOfDaily,
			Optional<WorkTimeSetting> workTimeSetting, Optional<WorkType> workType,
			List<WorkTimezoneOtherSubHolTimeSet> subHolTransferSetList,
			DailyCalculationPersonalInformation personalInfo, DailyUnit dailyUnit,
			Optional<FixRestTimezoneSet> fixRestTimeSeting,
			Optional<FixedWorkCalcSetting> ootsukaFixedWorkSet,
			HolidayCalcMethodSet holidayCalcMethodSet,Boolean calculatable,
			int breakCount,WorkRegularAdditionSet workRegularAdditionSet,
			WorkFlexAdditionSet workFlexAdditionSet,HourlyPaymentAdditionSet hourlyPaymentAdditionSet, WorkDeformedLaborAdditionSet workDeformedLaborAdditionSet,
			HolidayAddtionSet holidayAddtionSet,Optional<CoreTimeSetting> coreTimeSetting,List<OverTimeFrameNo> statutoryFrameNoList) {
		super();
		this.calculationRangeOfOneDay = calculationRangeOfOneDay;
		this.integrationOfDaily = integrationOfDaily;
		this.workTimeSetting = workTimeSetting;
		this.workType = workType;
		this.subHolTransferSetList = subHolTransferSetList;
		this.personalInfo = personalInfo;
		this.fixRestTimeSetting = fixRestTimeSeting;
		this.dailyUnit = dailyUnit;
		this.ootsukaFixedWorkSet = ootsukaFixedWorkSet;
		this.holidayCalcMethodSet = holidayCalcMethodSet;
		this.calculatable = calculatable;
		this.breakCount = breakCount;
		this.workRegularAdditionSet = workRegularAdditionSet;
		this.workFlexAdditionSet = workFlexAdditionSet;
		this.hourlyPaymentAdditionSet = hourlyPaymentAdditionSet;
		this.workDeformedLaborAdditionSet = workDeformedLaborAdditionSet;
		this.holidayAddtionSet = holidayAddtionSet;
		this.coreTimeSetting = coreTimeSetting;
	}
	
	/**
	 * 計算処理に入ることができないと判断できた時Factory Method
	 */
	public static ManageReGetClass cantCalc() {
		return new ManageReGetClass(null, 
									null, 
									null, 
									null, 
									null, 
									null,
									null,
									Optional.empty(),
									Optional.empty(),
									null,
									false,
									0,
									null,
									null,
									null,
									null,
									null,
									Optional.empty(),
									null);
				
	}
	
	/**
	 * 計算処理に入ることができると判断できた時Factory Method
	 */
	public static ManageReGetClass canCalc(CalculationRangeOfOneDay calculationRangeOfOneDay, IntegrationOfDaily integrationOfDaily,
										  Optional<WorkTimeSetting> workTimeSetting, Optional<WorkType> workType,
										  List<WorkTimezoneOtherSubHolTimeSet> subHolTransferSetList,
										  DailyCalculationPersonalInformation personalInfo, DailyUnit dailyUnit,
										  Optional<FixRestTimezoneSet> fixRestTimeSeting,
										  Optional<FixedWorkCalcSetting> ootsukaFixedWorkSet,
										  HolidayCalcMethodSet holidayCalcMethodSet,
										  int breakCount,WorkRegularAdditionSet workRegularAdditionSet,
										  WorkFlexAdditionSet workFlexAdditionSet,HourlyPaymentAdditionSet hourlyPaymentAdditionSet, WorkDeformedLaborAdditionSet workDeformedLaborAdditionSet,
										  HolidayAddtionSet holidayAddtionSet, Optional<CoreTimeSetting> coreTimeSetting,List<OverTimeFrameNo> statutoryFrameNoList) {
		return new ManageReGetClass(calculationRangeOfOneDay,
									integrationOfDaily,
									workTimeSetting,
									workType,
									subHolTransferSetList,
									personalInfo,
									dailyUnit,
									fixRestTimeSeting,
									ootsukaFixedWorkSet,
									holidayCalcMethodSet,
									true,
									breakCount,
									workRegularAdditionSet,
									workFlexAdditionSet,
									hourlyPaymentAdditionSet,
									workDeformedLaborAdditionSet,
									holidayAddtionSet,
									coreTimeSetting,
									statutoryFrameNoList);
	
	}
}

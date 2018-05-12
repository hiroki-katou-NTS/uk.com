package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.calculation.holiday.kmk013_splitdomain.HolidayCalcMethodSet;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.DailyUnit;
import nts.uk.ctx.at.shared.dom.workrule.statutoryworktime.DailyCalculationPersonalInformation;
import nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSet;
import nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneOtherSubHolTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkCalcSetting;
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
	CalculationRangeOfOneDay calculationRangeOfOneDay;
	
	//日別実績(WORK)
	@Setter
	IntegrationOfDaily integrationOfDaily;
	
	//就業時間帯設定
	Optional<WorkTimeSetting> workTimeSetting;
	
	//勤務種類
	Optional<WorkType> workType;
	
	//就業時間帯別代休時間設定
	List<WorkTimezoneOtherSubHolTimeSet> subHolTransferSetList;
	
	//労働制
	DailyCalculationPersonalInformation personalInfo;
	
	//法定労働時間(日単位)
	DailyUnit dailyUnit;
	
	//大塚用　固定勤務の休憩時間帯保持
	Optional<TimezoneOfFixedRestTimeSet> fixRestTimeSetting;
	
	//大塚要ケインで使用する固定計算設定クラス
	Optional<FixedWorkCalcSetting> ootsukaFixedWorkSet;
	
	//休暇の計算方法の設定
	HolidayCalcMethodSet holidayCalcMethodSet;
	
	//計算処理に入ることができるかフラグ
	//(造語)
	Boolean calculatable;

	/**
	 * Constructor 
	 */
	private ManageReGetClass(CalculationRangeOfOneDay calculationRangeOfOneDay, IntegrationOfDaily integrationOfDaily,
			Optional<WorkTimeSetting> workTimeSetting, Optional<WorkType> workType,
			List<WorkTimezoneOtherSubHolTimeSet> subHolTransferSetList,
			DailyCalculationPersonalInformation personalInfo, DailyUnit dailyUnit,
			Optional<TimezoneOfFixedRestTimeSet> fixRestTimeSeting,
			Optional<FixedWorkCalcSetting> ootsukaFixedWorkSet,
			HolidayCalcMethodSet holidayCalcMethodSet,Boolean calculatable) {
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
									false);
				
	}
	
	/**
	 * 計算処理に入ることができると判断できた時Factory Method
	 */
	public static ManageReGetClass canCalc(CalculationRangeOfOneDay calculationRangeOfOneDay, IntegrationOfDaily integrationOfDaily,
										  Optional<WorkTimeSetting> workTimeSetting, Optional<WorkType> workType,
										  List<WorkTimezoneOtherSubHolTimeSet> subHolTransferSetList,
										  DailyCalculationPersonalInformation personalInfo, DailyUnit dailyUnit,
										  Optional<TimezoneOfFixedRestTimeSet> fixRestTimeSeting,
										  Optional<FixedWorkCalcSetting> ootsukaFixedWorkSet,
										  HolidayCalcMethodSet holidayCalcMethodSet) {
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
									true);
	
	}
}

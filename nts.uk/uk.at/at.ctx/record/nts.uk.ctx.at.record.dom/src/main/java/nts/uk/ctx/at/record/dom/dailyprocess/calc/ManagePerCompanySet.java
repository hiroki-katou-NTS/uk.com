package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.adapter.personnelcostsetting.PersonnelCostSettingImport;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceTime;
import nts.uk.ctx.at.record.dom.divergencetime.service.MasterShareBus.MasterShareContainer;
import nts.uk.ctx.at.record.dom.optitem.OptionalItem;
import nts.uk.ctx.at.record.dom.optitem.applicable.EmpCondition;
import nts.uk.ctx.at.record.dom.optitem.calculation.Formula;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecord;
import nts.uk.ctx.at.record.dom.workrule.specific.CalculateOfTotalConstraintTime;
import nts.uk.ctx.at.record.dom.workrule.specific.UpperLimitTotalWorkingHour;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.BPUnitUseSetting;
import nts.uk.ctx.at.shared.dom.calculation.holiday.HolidayAddtionSet;
import nts.uk.ctx.at.shared.dom.ot.zerotime.ZeroTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.DailyUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;

/**
 * 
 * @author keisuke_hoshina
 *
 */
@Getter
public class ManagePerCompanySet {

	//休暇加算設定
	Map<String, AggregateRoot> holidayAddition;
	
	//会社別の休暇加算設定
	Optional<HolidayAddtionSet> holidayAdditionPerCompany;
	
	//総拘束時間
	Optional<CalculateOfTotalConstraintTime> calculateOfTotalCons;
	
	//会社ごとの代休設定
	CompensatoryLeaveComSetting compensatoryLeaveComSet;
	
	//乖離時間
	List<DivergenceTime> divergenceTime;
	
	//エラーアラームマスタ
	List<ErrorAlarmWorkRecord> errorAlarm; 
	
	//加給利用単位
	Optional<BPUnitUseSetting> bpUnitSetting;
	
	//労働条件
	@Setter
	Optional<WorkingConditionItem> personInfo;
	//法定労働
	@Setter
	DailyUnit dailyUnit;
	
	@Setter
	MasterShareContainer shareContainer;
	
	//任意項目
	@Setter
	List<OptionalItem> optionalItems;
	
	// 任意項目計算式
	@Setter
	List<Formula> formulaList;
	
	//適用する雇用条件
	@Setter
	List<EmpCondition> empCondition;
	
	//0時跨ぎの設定
	Optional<ZeroTime> zeroTime;
	
	@Setter
	List<PersonnelCostSettingImport> personnelCostSettings;

	@Setter
	Optional<UpperLimitTotalWorkingHour> upperControl;
	

	public ManagePerCompanySet(Map<String, AggregateRoot> holidayAddition,
			Optional<HolidayAddtionSet> holidayAdditionPerCompany,
			Optional<CalculateOfTotalConstraintTime> calculateOfTotalCons,
			CompensatoryLeaveComSetting compensatoryLeaveComSet,
			List<DivergenceTime> divergenceTime,
			List<ErrorAlarmWorkRecord> errorAlarm,
			Optional<BPUnitUseSetting> bpUnitSetting,
			Optional<ZeroTime> zeroTime) {
		super();
		this.holidayAddition = holidayAddition;
		this.holidayAdditionPerCompany = holidayAdditionPerCompany;
		this.calculateOfTotalCons = calculateOfTotalCons;
		this.compensatoryLeaveComSet = compensatoryLeaveComSet;
		this.divergenceTime = divergenceTime;
		this.errorAlarm = errorAlarm;
		this.bpUnitSetting = bpUnitSetting;
		this.optionalItems = new ArrayList<>();
		this.formulaList = new ArrayList<>();
		this.empCondition = new ArrayList<>();
		this.zeroTime = zeroTime;
	}
}
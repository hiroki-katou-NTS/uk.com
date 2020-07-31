package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.adapter.personnelcostsetting.PersonnelCostSettingImport;
import nts.uk.ctx.at.record.dom.daily.calcset.CalcMethodOfNoWorkingDayForCalc;
import nts.uk.ctx.at.record.dom.daily.midnight.MidNightTimeSheet;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceTime;
import nts.uk.ctx.at.record.dom.optitem.OptionalItem;
import nts.uk.ctx.at.record.dom.optitem.applicable.EmpCondition;
import nts.uk.ctx.at.record.dom.optitem.calculation.Formula;
import nts.uk.ctx.at.record.dom.optitem.calculation.disporder.FormulaDispOrder;
import nts.uk.ctx.at.record.dom.raborstandardact.flex.SettingOfFlexWork;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecord;
import nts.uk.ctx.at.record.dom.workrule.specific.CalculateOfTotalConstraintTime;
import nts.uk.ctx.at.record.dom.workrule.specific.UpperLimitTotalWorkingHour;
import nts.uk.ctx.at.shared.dom.attendance.MasterShareBus.MasterShareContainer;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.BPUnitUseSetting;
import nts.uk.ctx.at.shared.dom.calculation.holiday.flex.FlexSet;
import nts.uk.ctx.at.shared.dom.calculation.setting.DeformLaborOT;
import nts.uk.ctx.at.shared.dom.ot.zerotime.ZeroTime;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayAddtionSet;
import nts.uk.ctx.at.shared.dom.statutory.worktime.UsageUnitSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;

/**
 * 会社別設定管理
 * @author keisuke_hoshina
 *
 */
@Getter
public class ManagePerCompanySet {

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
	
	@Setter
	MasterShareContainer<String> shareContainer;
	
	//任意項目
	List<OptionalItem> optionalItems;
	
	// 任意項目計算式
	List<Formula> formulaList;
	
	// 任意項目計算式の並び順
	List<FormulaDispOrder> formulaOrderList;
	
	//適用する雇用条件
	List<EmpCondition> empCondition;
	
	//0時跨ぎの設定
	Optional<ZeroTime> zeroTime;
	
	@Setter
	List<PersonnelCostSettingImport> personnelCostSettings;

	@Setter
	Optional<UpperLimitTotalWorkingHour> upperControl;
	
	Optional<UsageUnitSetting> usageSetting;
		
	/** 深夜時間帯 */
	MidNightTimeSheet midNightTimeSheet;
	
	/** フレックス勤務の日別計算設定 */
	FlexSet flexSet;
	
	/** 変形労働の法定内残業計算 */
	DeformLaborOT deformLaborOT;
	
	
	public ManagePerCompanySet(
			Optional<HolidayAddtionSet> holidayAdditionPerCompany,
			Optional<CalculateOfTotalConstraintTime> calculateOfTotalCons,
			CompensatoryLeaveComSetting compensatoryLeaveComSet,
			List<DivergenceTime> divergenceTime,
			List<ErrorAlarmWorkRecord> errorAlarm,
			Optional<BPUnitUseSetting> bpUnitSetting,
			List<OptionalItem> optionalItems,
			List<Formula> formulaList,
			List<FormulaDispOrder> formulaOrderList,
			List<EmpCondition> empCondition,
			Optional<ZeroTime> zeroTime,
			Optional<UpperLimitTotalWorkingHour> upperControl,
			Optional<UsageUnitSetting> usageSetting,
			MidNightTimeSheet midNightTimeSheet,
			FlexSet flexSet,
			DeformLaborOT deformLaborOT) {
		super();
		this.holidayAdditionPerCompany = holidayAdditionPerCompany;
		this.calculateOfTotalCons = calculateOfTotalCons;
		this.compensatoryLeaveComSet = compensatoryLeaveComSet;
		this.divergenceTime = divergenceTime;
		this.errorAlarm = errorAlarm;
		this.bpUnitSetting = bpUnitSetting;
		this.optionalItems = optionalItems;
		this.formulaList = formulaList;
		this.formulaOrderList = formulaOrderList;
		this.empCondition = empCondition;
		this.zeroTime = zeroTime;
		this.personnelCostSettings = Collections.emptyList();
		this.upperControl = upperControl;
		this.usageSetting = usageSetting;
		this.midNightTimeSheet = midNightTimeSheet;
		this.flexSet = flexSet;
		this.deformLaborOT = deformLaborOT;
	}
}
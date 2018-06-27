package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceTime;
import nts.uk.ctx.at.record.dom.divergencetime.service.MasterShareBus.MasterShareContainer;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecord;
import nts.uk.ctx.at.record.dom.workrule.specific.CalculateOfTotalConstraintTime;
import nts.uk.ctx.at.shared.dom.calculation.holiday.HolidayAddtionSet;
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
	
	//労働条件
	@Setter
	Optional<WorkingConditionItem> personInfo;
	//法定労働
	@Setter
	DailyUnit dailyUnit;
	
	@Setter
	MasterShareContainer shareContainer;

	public ManagePerCompanySet(Map<String, AggregateRoot> holidayAddition,
			Optional<HolidayAddtionSet> holidayAdditionPerCompany,
			Optional<CalculateOfTotalConstraintTime> calculateOfTotalCons,
			CompensatoryLeaveComSetting compensatoryLeaveComSet,
			List<DivergenceTime> divergenceTime,
			List<ErrorAlarmWorkRecord> errorAlarm) {
		super();
		this.holidayAddition = holidayAddition;
		this.holidayAdditionPerCompany = holidayAdditionPerCompany;
		this.calculateOfTotalCons = calculateOfTotalCons;
		this.compensatoryLeaveComSet = compensatoryLeaveComSet;
		this.divergenceTime = divergenceTime;
		this.errorAlarm = errorAlarm;
	}
}
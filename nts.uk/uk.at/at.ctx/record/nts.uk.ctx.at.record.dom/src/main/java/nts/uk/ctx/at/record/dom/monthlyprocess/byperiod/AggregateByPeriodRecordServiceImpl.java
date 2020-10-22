package nts.uk.ctx.at.record.dom.monthlyprocess.byperiod;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.AnyAggrPeriod;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.AgreementTimeByPeriod;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.AnyItemByPeriod;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.AttendanceTimeOfAnyPeriod;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.ExcessOutsideByPeriod;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.MonthlyCalculationByPeriod;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.anyaggrperiod.AnyAggrFrameCode;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonAggrEmployeeSettings;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonthlyCalculatingDailys;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.totalcount.TotalCountByPeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.VerticalTotalOfMonthly;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageContent;
import nts.uk.shr.com.i18n.TextResource;

/**
 * 任意期間集計Mgr　（アルゴリズム）
 * @author shuichu_ishida
 */
@Stateless
public class AggregateByPeriodRecordServiceImpl implements AggregateByPeriodRecordService {

	@Inject 
	private RecordDomRequireService requireService;
	
	/** アルゴリズム */
	@Override
	public AggregateByPeriodRecordValue algorithm(String companyId, String employeeId, DatePeriod period,
			AnyAggrPeriod optionalPeriod,
			MonAggrCompanySettings companySets, MonAggrEmployeeSettings employeeSets) {
		val require = requireService.createRequire();
		
		AggregateByPeriodRecordValue result = new AggregateByPeriodRecordValue();
		
		// 社員を取得する
		EmployeeImport employee = employeeSets.getEmployee();
		
		// 入社前、退職後を期間から除く　→　集計期間
		val termInOffice = new DatePeriod(employee.getEntryDate(), employee.getRetiredDate());
		DatePeriod aggrPeriod = this.confirmProcPeriod(period, termInOffice);
		if (aggrPeriod == null) {
			// 処理期間全体が、入社前または退職後の時
			return result;
		}

		// 計算に必要なデータを準備する
		MonthlyCalculatingDailys calcDailys = MonthlyCalculatingDailys.loadData(require,
				employeeId, aggrPeriod, employeeSets);

		// 労働制を取得
		Optional<WorkingConditionItem> workingConditionItemOpt = require.workingConditionItem(employeeId, aggrPeriod.end());
		if (!workingConditionItemOpt.isPresent()){
			result.addErrorInfos("001", new ErrMessageContent(TextResource.localize("Msg_430")));
			return result;
		}
		WorkingSystem workingSystem = workingConditionItemOpt.get().getLaborSystem();
		
		// 月の計算
		MonthlyCalculationByPeriod monthlyAggregation = new MonthlyCalculationByPeriod();
		monthlyAggregation.calculation(require, aggrPeriod, workingSystem, calcDailys, companySets);
		
		// 縦計
		VerticalTotalOfMonthly verticalTotal = new VerticalTotalOfMonthly();
		verticalTotal.verticalTotal(require, companyId, employeeId, aggrPeriod, workingSystem,
				companySets, employeeSets, calcDailys);
		
		// 時間外超過
		ExcessOutsideByPeriod excessOutside = new ExcessOutsideByPeriod();
		excessOutside.aggregate(monthlyAggregation, companySets);
		
		// 36協定時間の集計
		AgreementTimeByPeriod agreementTime = new AgreementTimeByPeriod();
		agreementTime.aggregate(excessOutside);
		
		// 回数集計
		TotalCountByPeriod totalCount = new TotalCountByPeriod();
		totalCount.totalize(require, companyId, employeeId, aggrPeriod, companySets, calcDailys);
		
		// 任意項目を集計する
		AnyItemByPeriod anyItem = new AnyItemByPeriod();
		anyItem.aggregate(aggrPeriod, calcDailys, companySets, employeeSets);
		
		// 任意期間別実績の勤怠時間を返す
		AttendanceTimeOfAnyPeriod attendanceTime = AttendanceTimeOfAnyPeriod.of(
				employeeId,
				new AnyAggrFrameCode(optionalPeriod.getAggrFrameCode().v()),
				monthlyAggregation,
				excessOutside,
				agreementTime,
				verticalTotal,
				totalCount,
				anyItem);
		result.setAttendanceTime(Optional.of(attendanceTime));
		
		return result;
	}
	
	/**
	 * 処理期間との重複を確認する　（重複期間を取り出す）
	 * @param target 処理期間
	 * @param comparison 比較対象期間
	 * @return 重複期間　（null = 重複なし）
	 */
	private DatePeriod confirmProcPeriod(DatePeriod target, DatePeriod comparison){

		DatePeriod overlap = null;		// 重複期間
		
		// 開始前
		if (target.isBefore(comparison)) return overlap;
		
		// 終了後
		if (target.isAfter(comparison)) return overlap;
		
		// 重複あり
		overlap = target;
		
		// 開始日より前を除外
		if (overlap.contains(comparison.start())){
			overlap = overlap.cutOffWithNewStart(comparison.start());
		}
		
		// 終了日より後を除外
		if (overlap.contains(comparison.end())){
			overlap = overlap.cutOffWithNewEnd(comparison.end());
		}

		return overlap;
	}
}

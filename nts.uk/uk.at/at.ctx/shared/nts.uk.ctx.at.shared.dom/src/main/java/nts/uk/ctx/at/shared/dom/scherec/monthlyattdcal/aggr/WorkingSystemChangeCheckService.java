package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;

/** 次の集計期間で労働制が変更されるかを確認する */
public class WorkingSystemChangeCheckService {

	/** 次の期間の労働制が処理期間と一緒かを確認する */
	public static WorkingSystemChangeState isSameWorkingSystemWithNextPeriod(Require require, String sid, DatePeriod period, WorkingSystem workingSystem) {
		
		/** 次の期間の労働制が処理期間と一緒かを確認する */
		val workCondition = require.workingConditionItem(sid, period.end().addDays(1));
		
		/** パラメータ。労働制と次の労働制を比較する */
		return workCondition.map(c -> c.getLaborSystem() == workingSystem).orElse(false) ? WorkingSystemChangeState.NO_CHANGE : WorkingSystemChangeState.CHANGED;
	}
	
	/** 次の集計期間で同じ労働制で集計するかを確認する */
	public static WorkingSystemChangeState isSameWorkingSystemWithNextAggrPeriod(RequireM1 require, CacheCarrier cacheCarrier, String sid, DatePeriod period, WorkingSystem workingSystem) {
		
		val employee = require.employeeInfo(cacheCarrier, sid);
		
		/** 期間中に退職しているかどうかの判断 */
		if (employee.isRetired(period)) return WorkingSystemChangeState.CHANGED;
		
		/** 次の期間の労働制が処理期間と一緒かを確認する */
		val workingSystemChangeState = WorkingSystemChangeCheckService.isSameWorkingSystemWithNextPeriod(require, sid, period, workingSystem);
		return workingSystemChangeState;
	}
	
	@AllArgsConstructor
	/**　労働制変更状態　*/
	public static enum WorkingSystemChangeState {
		
		/** 変更されていない */
		NO_CHANGE(0),
		/** 変更された */
		CHANGED(1);
		
		int value;
	}

	
	public static interface RequireM1 extends Require {
		
		EmployeeImport employeeInfo(CacheCarrier cacheCarrier, String empId);
	}
	
	public static interface Require {
		
		Optional<WorkingConditionItem> workingConditionItem(String employeeId, GeneralDate baseDate);
	}
}

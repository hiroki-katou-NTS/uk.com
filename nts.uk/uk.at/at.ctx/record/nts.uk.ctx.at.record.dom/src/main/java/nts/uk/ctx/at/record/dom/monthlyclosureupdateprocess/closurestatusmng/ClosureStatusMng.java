package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.closurestatusmng;

import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.AggrPeriodEachActualClosure;
import nts.uk.ctx.at.shared.dom.scherec.closurestatus.ClosureStatusManagement;

/**
 * 
 * @author HungTT - <<Work>> 締め状態管理
 *
 */
public class ClosureStatusMng {

	public static AtomTask closureStatusManage(RequireM1 require, AggrPeriodEachActualClosure period, String empId) {
		ClosureStatusManagement statusMng = new ClosureStatusManagement(period.getYearMonth(), empId,
				period.getClosureId().value, period.getClosureDate(), period.getPeriod());
		
		return AtomTask.of(() -> require.addClosureStatusManagement(statusMng));
	}
	
	public static interface RequireM1 {
		
		void addClosureStatusManagement(ClosureStatusManagement domain);
	}
}

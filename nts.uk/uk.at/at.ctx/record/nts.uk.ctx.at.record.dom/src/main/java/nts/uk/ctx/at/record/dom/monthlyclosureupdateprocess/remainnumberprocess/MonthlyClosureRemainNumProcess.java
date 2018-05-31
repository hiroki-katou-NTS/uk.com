package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.annualleave.AnnualLeaveProcess;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.AggrPeriodEachActualClosure;

/**
 * 
 * @author HungTT - 月締め更新残数処理
 *
 */

@Stateless
public class MonthlyClosureRemainNumProcess {

	@Inject
	private AnnualLeaveProcess annualLeaveProc;

	/**
	 * 
	 * @param period
	 * @param empId
	 */
	public void remainNumberProcess(AggrPeriodEachActualClosure period, String empId) {
		annualLeaveProc.annualHolidayProcess(period, empId);
	}
}

package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.annualleave.AnnualLeaveProcess;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.compensatoryholiday.CompensatoryHolidayProcess;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.substitutionholiday.SubstitutionHolidayProcess;
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
	
	@Inject
	private SubstitutionHolidayProcess substitutionProc;
	
	@Inject
	private CompensatoryHolidayProcess compensatoryProc;

	/**
	 * 
	 * @param AggrPeriodEachActualClosure
	 * @param empId
	 */
	public void remainNumberProcess(AggrPeriodEachActualClosure period, String empId) {
		annualLeaveProc.annualHolidayProcess(period, empId);
		substitutionProc.substitutionHolidayProcess(period, empId);
		compensatoryProc.compensatoryHolidayProcess(period, empId);
	}
}

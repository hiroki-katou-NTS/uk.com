package nts.uk.ctx.at.record.ac.createmonthlyapprover;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.adapter.createmonthlyapprover.CreateMonthlyApproverAdapter;
import nts.uk.ctx.workflow.pub.resultrecord.IntermediateDataPub;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * 実装：承認状態の作成（月次）
 * @author shuichi_ishida
 */
@Stateless
public class CreateMonthlyApproverAc implements CreateMonthlyApproverAdapter {

	@Inject
	private IntermediateDataPub intermediateDataPub;
	
	@Override
	public void createApprovalStatusMonth(String employeeID, GeneralDate date, YearMonth yearMonth, Integer closureID,
			ClosureDate closureDate) {
		this.intermediateDataPub.createApprovalStatusMonth(employeeID, date, yearMonth, closureID, closureDate);
	}
}

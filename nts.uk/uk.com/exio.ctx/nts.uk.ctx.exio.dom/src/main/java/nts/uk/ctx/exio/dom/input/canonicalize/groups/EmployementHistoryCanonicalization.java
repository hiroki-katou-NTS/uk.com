package nts.uk.ctx.exio.dom.input.canonicalize.groups;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistory;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.CanonicalizationMethod;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.employee.history.EmployeeContinuousHistoryCanonicalization;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.history.History;

/**
 * 雇用履歴グループの正準化
 */
public class EmployementHistoryCanonicalization
		extends EmployeeContinuousHistoryCanonicalization
		implements GroupCanonicalization {

	@Override
	public void canonicalize(GroupCanonicalization.Require require, ExecutionContext context) {
		
		canonicalize(require, context, result -> {
			require.save(result.complete(context));
		});
	}
	
	@Override
	protected History<DateHistoryItem, DatePeriod, GeneralDate> getHistory(
			CanonicalizationMethod.Require require,
			String employeeId) {
		
		return require.getEmploymentHistory(employeeId);
	}
	
	public static interface RequireGetHistory {
		
		EmploymentHistory getEmploymentHistory(String employeeId);
	}
}

package nts.uk.ctx.exio.dom.input.canonicalize.groups;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistory;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.employee.EmployeeCodeCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.employee.history.EmployeeContinuousHistoryCanonicalization;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.history.History;

/**
 * 雇用履歴グループの正準化
 */
public class EmploymentHistoryCanonicalization
		extends EmployeeContinuousHistoryCanonicalization
		implements GroupCanonicalization {

	public EmploymentHistoryCanonicalization(
			int itemNoStartDate,
			int itemNoEndDate,
			int itemNoHistoryId,
			EmployeeCodeCanonicalization employeeCodeCanonicalization) {
		
		super(itemNoStartDate, itemNoEndDate, itemNoHistoryId, employeeCodeCanonicalization);
	}
	
	@Override
	protected History<DateHistoryItem, DatePeriod, GeneralDate> getHistory(
			GroupCanonicalization.RequireCanonicalize require,
			String employeeId) {
		
		return require.getEmploymentHistory(employeeId);
	}
	
	public static interface RequireGetHistory {
		
		EmploymentHistory getEmploymentHistory(String employeeId);
	}

	@Override
	protected void adjustChanging(RequireAdjsut require, ExecutionContext context, EmployeeHistoryItem historyItem) {
		require.changeEmploymentHistory(
				context.getCompanyId(),
				historyItem.getEmployeeId(),
				historyItem.toDateHistoryItem());
	}

	@Override
	protected void adjustDeleting(RequireAdjsut require, ExecutionContext context, EmployeeHistoryItem historyItem) {
		require.deleteEmploymentHistory(
				context.getCompanyId(),
				historyItem.getEmployeeId(),
				historyItem.toDateHistoryItem());
	}

	public static interface RequireAdjust {
		
		/** 雇用履歴を変更する */
		void changeEmploymentHistory(String companyId, String employeeId, DateHistoryItem historyItem);
		
		/** 雇用履歴を削除する */
		void deleteEmploymentHistory(String companyId, String employeeId, DateHistoryItem historyItem);
	}
}

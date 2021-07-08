package nts.uk.ctx.exio.dom.input.canonicalize.groups;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistory;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.employee.EmployeeCodeCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.employee.history.EmployeeContinuousHistoryCanonicalization;
import nts.uk.ctx.exio.dom.input.workspace.group.GroupWorkspace;
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

	public EmploymentHistoryCanonicalization(GroupWorkspace workspace) {
		super(workspace, new EmployeeCodeCanonicalization(workspace));
	}
	
	@Override
	protected History<DateHistoryItem, DatePeriod, GeneralDate> getHistory(
			GroupCanonicalization.RequireCanonicalize require,
			String employeeId) {
		
		return require.getEmploymentHistory(employeeId).get();
	}
	
	public static interface RequireGetHistory {
		
		Optional<EmploymentHistory> getEmploymentHistory(String employeeId);
	}

	@Override
	protected void adjustChanging(RequireAdjsut require, EmployeeHistoryItem historyItem) {
		require.changeEmploymentHistory(
				historyItem.getEmployeeId(),
				historyItem.toDateHistoryItem());
	}

	@Override
	protected void adjustDeleting(RequireAdjsut require, EmployeeHistoryItem historyItem) {
		require.deleteEmploymentHistory(
				historyItem.getEmployeeId(),
				historyItem.toDateHistoryItem());
	}

	public static interface RequireAdjust {
		
		/** 雇用履歴を変更する */
		void changeEmploymentHistory(String employeeId, DateHistoryItem historyItem);
		
		/** 雇用履歴を削除する */
		void deleteEmploymentHistory(String employeeId, DateHistoryItem historyItem);
	}

	@Override
	public int getItemNoOfEmployeeId() {
		return this.getEmployeeCodeCanonicalization().getItemNoEmployeeId();
	}
}

package nts.uk.ctx.exio.dom.input.canonicalize.groups;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.bs.employee.dom.classification.affiliate.AffClassHistory;
import nts.uk.ctx.exio.dom.input.canonicalize.groups.generic.EmployeeContinuousHistoryCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.EmployeeCodeCanonicalization;
import nts.uk.ctx.exio.dom.input.workspace.group.GroupWorkspace;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.history.History;

public class AffClassHistoryCanonicalization 
		extends EmployeeContinuousHistoryCanonicalization
		implements GroupCanonicalization {

	public AffClassHistoryCanonicalization(
			int itemNoStartDate,
			int itemNoEndDate,
			int itemNoHistoryId,
			EmployeeCodeCanonicalization employeeCodeCanonicalization) {
		super(itemNoStartDate, itemNoEndDate, itemNoHistoryId, employeeCodeCanonicalization);
	}
	
	public AffClassHistoryCanonicalization(GroupWorkspace workspace) {
		super(workspace, new EmployeeCodeCanonicalization(workspace));
	}

	@Override
	protected History<DateHistoryItem, DatePeriod, GeneralDate> getHistory(
			GroupCanonicalization.RequireCanonicalize require,
			String employeeId) {
		
		return require.getAffClassHistory(employeeId).get();
	}
	
	public static interface RequireGetHistory {
		
		Optional<AffClassHistory> getAffClassHistory(String employeeId);
	}

	@Override
	protected void adjustChanging(RequireAdjsut require, EmployeeHistoryItem historyItem) {
		require.changeAffClassHistory(
				historyItem.getEmployeeId(),
				historyItem.toDateHistoryItem());
	}

	@Override
	protected void adjustDeleting(RequireAdjsut require, EmployeeHistoryItem historyItem) {
		require.deleteAffClassHistory(
				historyItem.getEmployeeId(),
				historyItem.toDateHistoryItem());
	}
	
	public static interface RequireAdjust {
		
		/** 分類履歴を変更する */
		void changeAffClassHistory(String employeeId, DateHistoryItem historyItem);
		
		/** 分類履歴を削除する */
		void deleteAffClassHistory(String employeeId, DateHistoryItem historyItem);
	}

	@Override
	public int getItemNoOfEmployeeId() {
		return this.getEmployeeCodeCanonicalization().getItemNoEmployeeId();
	}
}

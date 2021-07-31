package nts.uk.ctx.exio.dom.input.canonicalize.groups;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.AffJobTitleHistory;
import nts.uk.ctx.exio.dom.input.canonicalize.groups.generic.EmployeeContinuousHistoryCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.EmployeeCodeCanonicalization;
import nts.uk.ctx.exio.dom.input.workspace.group.GroupWorkspace;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.history.History;

public class AffJobTitleHistoryCanonicalization
		extends EmployeeContinuousHistoryCanonicalization
		implements GroupCanonicalization {

	public AffJobTitleHistoryCanonicalization(
			int itemNoStartDate,
			int itemNoEndDate,
			int itemNoHistoryId,
			EmployeeCodeCanonicalization employeeCodeCanonicalization) {
		super(itemNoStartDate, itemNoEndDate, itemNoHistoryId, employeeCodeCanonicalization);
	}
	
	public AffJobTitleHistoryCanonicalization(GroupWorkspace workspace) {
		super(workspace, new EmployeeCodeCanonicalization(workspace));
	}



	@Override
	protected History<DateHistoryItem, DatePeriod, GeneralDate> getHistory(
			GroupCanonicalization.RequireCanonicalize require,
			String employeeId) {
		
		return require.getAffJobTitleHistory(employeeId).get();
	}
	
	public static interface RequireGetHistory {
		
		Optional<AffJobTitleHistory> getAffJobTitleHistory(String employeeId);
	}

	@Override
	protected void adjustChanging(RequireAdjsut require, EmployeeHistoryItem historyItem) {
		require.changeAffJobTitleHistory(
				historyItem.getEmployeeId(),
				historyItem.toDateHistoryItem());
	}

	@Override
	protected void adjustDeleting(RequireAdjsut require, EmployeeHistoryItem historyItem) {
		require.deleteAffJobTitleHistory(
				historyItem.getEmployeeId(),
				historyItem.toDateHistoryItem());
	}
	
	public static interface RequireAdjust {
		
		/** 職位履歴を変更する */
		void changeAffJobTitleHistory(String employeeId, DateHistoryItem historyItem);
		
		/** 職位履歴を削除する */
		void deleteAffJobTitleHistory(String employeeId, DateHistoryItem historyItem);
	}
	
	@Override
	public int getItemNoOfEmployeeId() {
		return this.getEmployeeCodeCanonicalization().getItemNoEmployeeId();
	}

}

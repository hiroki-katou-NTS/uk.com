package nts.uk.ctx.at.function.dom.adapter.approvalrootstate;

import java.util.List;

import nts.arc.time.calendar.period.DatePeriod;

public interface ApprovalRootStateAdapter {
	/**
	 * 
	 * @param empIds
	 * @param period
	 * @param rootType ルート種類: 0,"就業申請"/1:就業日別確認/2:就業月別確認
	 * @return
	 */
	List<ApprovalRootStateImport> findByEmployeesAndPeriod(List<String> empIds, DatePeriod period, int rootType);
	
	List<ApprovalRootStateImport> findByAgentApproverAndPeriod(String companyId, List<String> approvers, DatePeriod period);
	
	List<ApproverStateImport> findApprovalRootStateIds(String companyId, List<String> approverIds, DatePeriod period);
}

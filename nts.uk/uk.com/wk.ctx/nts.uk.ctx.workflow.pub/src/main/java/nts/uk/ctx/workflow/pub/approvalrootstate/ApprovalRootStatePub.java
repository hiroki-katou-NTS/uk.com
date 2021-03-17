package nts.uk.ctx.workflow.pub.approvalrootstate;

import java.util.List;

import nts.arc.time.calendar.period.DatePeriod;

public interface ApprovalRootStatePub {

	List<ApprovalRootStateExport> findByEmployeesAndPeriod(List<String> empIds, DatePeriod period, int rootType);
	
	List<ApprovalRootStateExport> findByAgentApproverAndPeriod(String companyId, List<String> approverIds, DatePeriod period);
	
	/**
	 * [RQ611]承認すべき申請IDリストを取得する
	 * @param companyId
	 * @param approverIds
	 * @param period
	 * @return
	 */
	List<ApproverStateExport> findApprovalRootStateIds(String companyId, List<String> approverIds, DatePeriod period);
}

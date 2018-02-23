package nts.uk.ctx.workflow.pub.agent;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface AgentPub {
	
	AgentPubExport getApprovalAgencyInformation(String companyID, List<String> approverList);
	/**
	 * Request No 244
	 * @param companyId
	 * @param employeeId
	 * @param baseDate
	 * @return 代行者、期間から承認代行情報を取得する
	 */
	List<AgentDataPubExport> getBySidDate(String companyId, String employeeId, GeneralDate startDate, GeneralDate endDate);

	List<AgentExport> getApprovalAgencyInfoByPeriod(String companyId, String employeeId, GeneralDate startDate, GeneralDate endDate);
}

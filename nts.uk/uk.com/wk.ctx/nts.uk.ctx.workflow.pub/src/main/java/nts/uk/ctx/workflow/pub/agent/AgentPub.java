package nts.uk.ctx.workflow.pub.agent;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface AgentPub {
	
	AgentPubExport getApprovalAgencyInformation(String companyID, List<String> approverList);
	/**
	 * RequestList244
	 * @param companyId
	 * @param employeeId
	 * @param baseDate
	 * @return 代行者、期間から承認代行情報を取得する
	 */
	List<AgentDataPubExport> getBySidDate(String companyId, String employeeId, GeneralDate startDate, GeneralDate endDate);

	List<AgentExport> getApprovalAgencyInfoByPeriod(String companyId, String employeeId, GeneralDate startDate, GeneralDate endDate);
	
	/**
	 * RequestList310
	 * RequestList #310
	 * @param companyID
	 * @param listApprover
	 * @param startDate
	 * @param endDate
	 * @param agentType
	 * @return
	 */
	List<AgentInfoExport> findAgentByPeriod(String companyID, List<String> listApprover, 
			GeneralDate startDate, GeneralDate endDate, Integer agentType);
	
	List<AgentDataPubExport> getAgentBySidDate(String companyId, String employeeId, GeneralDate startDate, GeneralDate endDate);
}

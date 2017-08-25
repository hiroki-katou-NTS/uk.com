package nts.uk.ctx.workflow.pub.agent;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface AgentPub {
	/**
	 * Find agent by condition:
	 * ・会社ID
	 * ・代行依頼者 = 承認ID
	 * ・期間．開始日 <= システム日付
	 * ・期間．終了日 >= システム日付
	 * @param companyId company id
	 * @param employeeId employee id
	 * @param startDate start date
	 * @param endDate end date
	 * @return list of agent
	 */
	List<AgentPubDto> find(String companyId, String employeeId, GeneralDate startDate, GeneralDate endDate);
}

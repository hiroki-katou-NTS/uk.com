package nts.uk.ctx.at.function.dom.adapter.agent;

import lombok.Getter;
import nts.arc.time.GeneralDate;

/**
 * 代行承認
 */
@Getter
public class AgentApprovalImport {

	/**
	 * 承認者社員ID
	 */
	private String approverID;
	
	/**
	 * 代行依頼者ID = 社員ID
	 */
	private String agentID;
	
	/**
	 * 開始日
	 */
	private GeneralDate startDate;
	
	/**
	 * 終了日
	 */
	private GeneralDate endDate;

	public AgentApprovalImport(String approverID, String agentID, GeneralDate startDate, GeneralDate endDate) {
		this.approverID = approverID;
		this.agentID = agentID;
		this.startDate = startDate;
		this.endDate = endDate;
	}
}

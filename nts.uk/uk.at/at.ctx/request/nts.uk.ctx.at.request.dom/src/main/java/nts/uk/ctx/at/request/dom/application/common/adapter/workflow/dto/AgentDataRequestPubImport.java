package nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
@Getter
@AllArgsConstructor
public class AgentDataRequestPubImport {
	private String companyId;

	private String employeeId;
	
	private UUID requestId;

	private GeneralDate startDate;

	private GeneralDate endDate;

	/**就業承認: 承認代行者	 */
	private String agentSid1;

	/**
	 * 就業承認: 代行承認種類
	 */
	private AgentAppTypeRequestImport agentAppType1;

	/**人事承認: 	承認代行者 */
	private String agentSid2;

	/**人事承認: 	 代行承認種類*/
	private AgentAppTypeRequestImport agentAppType2;

	/**給与承認: 承認代行者	 */
	private String agentSid3;

	/**経理承認: 	代行承認種類 */
	private AgentAppTypeRequestImport agentAppType3;

	/**経理承認:	承認代行者 */
	private String agentSid4;
	/**経理承認:	代行承認種類 */
	private AgentAppTypeRequestImport agentAppType4;
}

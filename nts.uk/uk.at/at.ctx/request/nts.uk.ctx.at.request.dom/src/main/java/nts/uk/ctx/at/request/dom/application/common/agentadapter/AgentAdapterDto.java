package nts.uk.ctx.at.request.dom.application.common.agentadapter;

import java.util.UUID;

import lombok.Value;
import nts.arc.time.GeneralDate;
@Value
public class AgentAdapterDto {
	/**会社ID	 */
	private String companyId;

	private String employeeId;
	
	private UUID requestId;

	private GeneralDate startDate;

	private GeneralDate endDate;
	/**就業承認: 承認代行者 */
	private String agentSid1;
	/**就業承認: 代行承認種類	 */
	private AgentAppType agentAppType1;

	private String agentSid2;

	private AgentAppType agentAppType2;

	private String agentSid3;

	private AgentAppType agentAppType3;

	private String agentSid4;

	private AgentAppType agentAppType4;
}

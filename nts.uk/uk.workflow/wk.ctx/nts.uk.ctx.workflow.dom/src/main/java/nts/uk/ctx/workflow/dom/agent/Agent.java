package nts.uk.ctx.workflow.dom.agent;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.enums.EnumAdaptor;

@Getter
@Setter
public class Agent extends AggregateRoot {

	private String companyId;

	private String employeeId;

	private GeneralDate startDate;

	private GeneralDate endDate;

	private AgentSid agentSid1;

	private AgentAppType agentAppType1;

	private AgentSid agentSid2;

	private AgentAppType agentAppType2;

	private AgentSid agentSid3;

	private AgentAppType agentAppType3;

	private AgentSid agentSid4;

	private AgentAppType agentAppType4;

	public Agent(String companyId, String employeeId, GeneralDate startDate, GeneralDate endDate,
			AgentSid agentSid1, AgentAppType agentAppType1, AgentSid agentSid2, AgentAppType agentAppType2,
			AgentSid agentSid3, AgentAppType agentAppType3, AgentSid agentSid4, AgentAppType agentAppType4) {
		super();

		this.companyId = companyId;
		this.employeeId = employeeId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.agentSid1 = agentSid1;
		this.agentAppType1 = agentAppType1;
		this.agentSid2 = agentSid2;
		this.agentAppType2 = agentAppType2;
		this.agentSid3 = agentSid3;
		this.agentAppType3 = agentAppType3;
		this.agentSid4 = agentSid4;
		this.agentAppType4 = agentAppType4;

	}
	
	public static Agent createFromJavaType(
			String companyId, 
			String employeeId, 
			GeneralDate endDate, 
			GeneralDate startDate,
			String agentSid1,
			int agentAppType1,
			String agentSid2,
			int agentAppType2,
			String agentSid3,
			int agentAppType3,
			String agentSid4,
			int agentAppType4
			) {
		return new Agent(
				companyId, 
				employeeId, 
				endDate, 
				startDate,
				new AgentSid(agentSid1),
				EnumAdaptor.valueOf(agentAppType1, AgentAppType.class),
				new AgentSid(agentSid2),
				EnumAdaptor.valueOf(agentAppType2, AgentAppType.class),
				new AgentSid(agentSid3),
				EnumAdaptor.valueOf(agentAppType3, AgentAppType.class),
				new AgentSid(agentSid4),
				EnumAdaptor.valueOf(agentAppType4, AgentAppType.class)
				);
	}
	
}

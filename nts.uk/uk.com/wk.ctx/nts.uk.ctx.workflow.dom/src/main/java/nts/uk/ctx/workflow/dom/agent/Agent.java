package nts.uk.ctx.workflow.dom.agent;

import java.util.List;
import java.util.UUID;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;

@Getter

public class Agent extends AggregateRoot {

	private String companyId;

	private String employeeId;
	
	private UUID requestId;

	private GeneralDate startDate;

	private GeneralDate endDate;

	private String agentSid1;

	private AgentAppType agentAppType1;

	private String agentSid2;

	private AgentAppType agentAppType2;

	private String agentSid3;

	private AgentAppType agentAppType3;

	private String agentSid4;

	private AgentAppType agentAppType4;

	public Agent(String companyId, String employeeId, UUID requestId, GeneralDate startDate, GeneralDate endDate,
			String agentSid1, AgentAppType agentAppType1, String agentSid2, AgentAppType agentAppType2,
			String agentSid3, AgentAppType agentAppType3, String agentSid4, AgentAppType agentAppType4) {
		super();

		this.companyId = companyId;
		this.employeeId = employeeId;
		this.requestId = requestId;
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
	
	/**
	 * Create from java type of agent
	 * @param companyId
	 * @param employeeId
	 * @param endDate
	 * @param startDate
	 * @param agentSid1
	 * @param agentAppType1
	 * @param agentSid2
	 * @param agentAppType2
	 * @param agentSid3
	 * @param agentAppType3
	 * @param agentSid4
	 * @param agentAppType4
	 * @return
	 */
	public static Agent createFromJavaType(
			String companyId, 
			String employeeId, 
			String requestId,
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
				UUID.fromString(requestId),
				endDate, 
				startDate,
				agentSid1,
				EnumAdaptor.valueOf(agentAppType1, AgentAppType.class),
				agentSid2,
				EnumAdaptor.valueOf(agentAppType2, AgentAppType.class),
				agentSid3,
				EnumAdaptor.valueOf(agentAppType3, AgentAppType.class),
				agentSid4,
				EnumAdaptor.valueOf(agentAppType4, AgentAppType.class)
				);
	}
	
	/**
	 * create new request id
	 * @return
	 */
	public static UUID createRequestId() {
		return UUID.randomUUID();
	}
	
	/**
	 * Validate date 
	 * @param rangeDateList list range date ordered
	 */
	public void validateDate(List<RangeDate> rangeDateList) {
		if (this.startDate == null || this.endDate == null) {
			throw new RuntimeException("Start date and End date are required");
		}
		
		if (this.startDate.after(this.endDate)) {
			throw new RuntimeException("Start date must before End date");
		}
		
		GeneralDate toDay = GeneralDate.today();
		
		if (this.endDate.before(toDay)) {
			throw new BusinessException("Msg_11");
		}
		
		if (rangeDateList == null) {
			return;
		}
		
		rangeDateList.stream().forEach(rangeDate -> {
			if (!checkStartDate(rangeDate)) {
				throw new BusinessException("Msg_12");
			}
		});
	}
	
	/**
	 * check start date by range date latest
	 * @param rangeDateLatest range date latest
	 * @return false if start date before end date in range date latest, else true
	 */
	private boolean checkStartDate(RangeDate rangeDate) {		
		if ((this.startDate.afterOrEquals(rangeDate.getStartDate()) && this.endDate.beforeOrEquals(rangeDate.getEndDate()))) {
			return false;
		}
				
		if ((this.startDate.after(rangeDate.getStartDate()) && this.startDate.before(rangeDate.getEndDate()))) {
			return false;
		}
		
		if ((this.endDate.after(rangeDate.getStartDate()) && this.endDate.before(rangeDate.getEndDate()))) {
			return false;
		}
		
		return true;
	}
}

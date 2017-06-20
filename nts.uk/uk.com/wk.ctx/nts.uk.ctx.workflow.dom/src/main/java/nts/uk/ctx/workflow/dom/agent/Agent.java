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
		RangeDate rangeDateLastest = null;
		RangeDate rangeDateLast = null;
		
		if (rangeDateList == null) {
			return;
		}
		
		if (rangeDateList.size() == 1) {
			rangeDateLastest = rangeDateList.get(0);
			rangeDateLast = rangeDateList.get(0);
		} else if (rangeDateList.size() > 1) {
			// lastest
			rangeDateLastest = rangeDateList.get(0);
			// last
			rangeDateLast = rangeDateList.get(rangeDateList.size() - 1);
		} else {
			// if rangeDateList == 0 then 
			return;
		}
		
		// check start date and end date
		if (!checkDateLatest(rangeDateLastest) || !checkDateLast(rangeDateLast)) {
			throw new BusinessException("Msg_012"); // had error
		}
	}
	
	/**
	 * check start date by range date latest
	 * @param rangeDateLatest range date latest
	 * @return false if start date before end date in range date latest, else true
	 */
	private boolean checkDateLatest(RangeDate rangeDateLatest) {		
		if (this.startDate.before(rangeDateLatest.getEndDate()) && this.endDate.after(rangeDateLatest.getStartDate())) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * check end date by range date latest
	 * @param rangeDateLast range date last
	 * @return false if end date after end date in range date latest, else true
	 */
	private boolean checkDateLast(RangeDate rangeDateLast) {	
		if (this.startDate.before(rangeDateLast.getEndDate()) && this.endDate.after(rangeDateLast.getStartDate())) {
			return false;
		}
		
		return true;
	}
}

package nts.uk.ctx.workflow.dom.agent;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;

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
			// latest
			rangeDateLastest = rangeDateList.get(0);
			// last
			rangeDateLast = rangeDateList.get(rangeDateList.size() - 1);
		} else {
			// if rangeDateList == 0 then 
			return;
		}
		
		// check start date and end date
		if (!checkDateLatest(rangeDateLastest) || !checkDateLast(rangeDateLast)) {
			throw new BusinessException(""); // had error
		}
	}
	
	/**
	 * check start date by range date latest
	 * @param rangeDateLatest range date latest
	 * @return false if start date before end date in range date latest, else true
	 */
	private boolean checkDateLatest(RangeDate rangeDateLatest) {		
		if (this.startDate.before(rangeDateLatest.getEndDate())) {
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
		if (this.endDate.after(rangeDateLast.getStartDate())) {
			return false;
		}
		
		return true;
	}
}

package nts.uk.ctx.workflow.dom.agent;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface AgentRepository {
	/**
	 * find all Agent
	 * 
	 * @param companyCode
	 * @param employeeId
	 * @return
	 */
	List<Agent> findAllAgent(String companyId, String employeeId);

	/**
	 * add Agent
	 * 
	 * @param agent
	 */
	void add(Agent agent);

	/**
	 * update Agent
	 * 
	 * @param agent
	 */
	void update(Agent agent);

	/**
	 * delete Agent
	 * 
	 * @param companyId
	 * @param employeeId
	 * @param startDate
	 */
	void delete(String companyId, String employeeId, GeneralDate startDate);
	/**
	 * get agent by start date
	 * 
	 * @param companyId
	 * @param employeeId
	 * @param startDate
	 * @return
	 */
	Optional<Agent> getAgentByStartDate(String companyId, String employeeId, GeneralDate startDate);

	/**
	 * 
	 * @param companyId
	 * @param employeeId
	 * @param startDate
	 * @return
	 */
	boolean isExisted(String companyId, String employeeId, GeneralDate startDate);
}

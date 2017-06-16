package nts.uk.ctx.workflow.dom.agent;

import java.util.List;
import java.util.Optional;

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
	 * @param requestId
	 */
	void delete(String companyId, String employeeId, String requestId);
	/**
	 * get agent by start date
	 * 
	 * @param companyId
	 * @param employeeId
	 * @param requestId
	 * @return
	 */
	Optional<Agent> find(String companyId, String employeeId, String requestId);
}

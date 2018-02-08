package nts.uk.ctx.workflow.infra.repository.agent;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.agent.Agent;
import nts.uk.ctx.workflow.dom.agent.AgentRepository;
import nts.uk.ctx.workflow.infra.entity.agent.CmmmtAgent;
import nts.uk.ctx.workflow.infra.entity.agent.CmmmtAgentPK;

@Stateless
public class JpaAgentRepository extends JpaRepository implements AgentRepository {
	private static final String SELECT_ALL;
	
	private static final String SELECT_ALL_AGENT;
	
	private static final String SELECT_AGENT_BY_DATE;
	
	private static final String SELECT_AGENT_SID;

	private static final String SELECT_AGENT_SID_DATE;
	
	private static final String SELECT_AGENT_ALL_DATE;
	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM CmmmtAgent e");
		builderString.append(" WHERE e.cmmmtAgentPK.companyId = :companyId");	
		SELECT_ALL = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM CmmmtAgent e");
		builderString.append(" WHERE e.cmmmtAgentPK.companyId = :companyId");
		builderString.append(" AND e.cmmmtAgentPK.employeeId = :employeeId");
		builderString.append(" ORDER BY e.startDate DESC");
		SELECT_ALL_AGENT = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM CmmmtAgent e");
		builderString.append(" WHERE e.cmmmtAgentPK.companyId = :companyId");
		builderString.append(" AND NOT (e.startDate > :endDate");
		builderString.append(" OR e.endDate < :startDate)");
		builderString.append(" ORDER BY e.startDate DESC");
		SELECT_AGENT_BY_DATE = builderString.toString(); 
		
		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM CmmmtAgent e");
		builderString.append(" WHERE e.cmmmtAgentPK.companyId = :companyId");
		builderString.append(" AND e.agentSid = :agentSid");
		SELECT_AGENT_SID = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM CmmmtAgent e");
		builderString.append(" WHERE e.cmmmtAgentPK.companyId = :companyId"); 
		builderString.append(" AND e.cmmmtAgentPK.employeeId IN :employeeIds");
		builderString.append(" AND e.startDate <= :baseDate");
		builderString.append(" AND e.endDate >= :baseDate");
		SELECT_AGENT_SID_DATE = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM CmmmtAgent e");
		builderString.append(" WHERE e.cmmmtAgentPK.companyId = :companyId"); 
		builderString.append(" AND e.agentSid1 = :employeeId");
		builderString.append(" OR e.agentSid2 = :employeeId");
		builderString.append(" OR e.agentSid3 = :employeeId");
		builderString.append(" OR e.agentSid4 = :employeeId");
		builderString.append(" AND e.startDate <= :baseDate");
		builderString.append(" AND e.endDate >= :baseDate");
		SELECT_AGENT_ALL_DATE = builderString.toString();
		
		}
	
		
	/**
	 * Convert Data to Domain
	 * @param cmmmtAgent
	 * @return
	 */
	private Agent convertToDomain(CmmmtAgent cmmmtAgent){
		Agent agent = Agent.createFromJavaType(
				cmmmtAgent.cmmmtAgentPK.companyId,
				cmmmtAgent.cmmmtAgentPK.employeeId,
				cmmmtAgent.cmmmtAgentPK.requestId,
				cmmmtAgent.startDate,
				cmmmtAgent.endDate,
				cmmmtAgent.agentSid1,
				cmmmtAgent.agentAppType1,
				cmmmtAgent.agentSid2,
				cmmmtAgent.agentAppType2,
				cmmmtAgent.agentSid3,
				cmmmtAgent.agentAppType3,
				cmmmtAgent.agentSid4,
				cmmmtAgent.agentAppType4);
		return agent;
	}
	
	/**
	 * Convert Data to Database Type
	 * @param agent
	 * @return
	 */
	private CmmmtAgent convertToDbType(Agent agent) {
		CmmmtAgent cmmmtAgent = new CmmmtAgent();
		CmmmtAgentPK cmmmtAgentPK = new CmmmtAgentPK(
				agent.getCompanyId(),
				agent.getEmployeeId(),
				agent.getRequestId().toString());
		cmmmtAgent.startDate = agent.getStartDate();
		cmmmtAgent.endDate = agent.getEndDate();
		cmmmtAgent.agentSid1 = agent.getAgentSid1();
		cmmmtAgent.agentAppType1 = agent.getAgentAppType1().value;
		cmmmtAgent.agentSid2 = agent.getAgentSid2();
		cmmmtAgent.agentAppType2 = agent.getAgentAppType2().value;
		cmmmtAgent.agentSid3 = agent.getAgentSid3();
		cmmmtAgent.agentAppType3 = agent.getAgentAppType3().value;
		cmmmtAgent.agentSid4 = agent.getAgentSid4();
		cmmmtAgent.agentAppType4 = agent.getAgentAppType4().value;
		cmmmtAgent.cmmmtAgentPK = cmmmtAgentPK;
		return cmmmtAgent;
	}
	
	/**
	 * Find all Agent
	 */
	@Override
	public List<Agent> findAllAgent(String companyId, String employeeId) {
		return this.queryProxy().query(SELECT_ALL_AGENT, CmmmtAgent.class)
				.setParameter("companyId", companyId).setParameter("employeeId", employeeId)
				.getList(c -> convertToDomain(c));
	}
	
	/**
	 * Find all Agent by CompanyId
	 */
	@Override
	public List<Agent> findByCid(String companyId) {
		return this.queryProxy().query(SELECT_ALL, CmmmtAgent.class)
				.setParameter("companyId", companyId)
				.getList(c -> convertToDomain(c));
	}

	@Override
	public List<Agent> find(String companyId, List<String> employeeIds, GeneralDate baseDate) {
		return this.queryProxy().query(SELECT_AGENT_SID_DATE, CmmmtAgent.class)
				.setParameter("companyId", companyId)
				.setParameter("employeeIds", employeeIds)
				.setParameter("baseDate", baseDate)
				.getList(c -> convertToDomain(c));
	}
	
	/**
	 * Add Agent
	 */
	@Override
	public void add(Agent agent) {
		this.commandProxy().insert(convertToDbType(agent));		
	}

	/**
	 * Update Agent
	 */
	@Override
	public void update(Agent agent) {
		CmmmtAgentPK primaryKey = new CmmmtAgentPK(agent.getCompanyId(), agent.getEmployeeId(), agent.getRequestId().toString());
		CmmmtAgent entity = this.queryProxy().find(primaryKey, CmmmtAgent.class).get();
		entity.startDate = agent.getStartDate();
		entity.endDate = agent.getEndDate();
		entity.agentSid1 = agent.getAgentSid1();
		entity.agentAppType1 = agent.getAgentAppType1().value;
		entity.agentSid2 = agent.getAgentSid2();
		entity.agentAppType2 = agent.getAgentAppType2().value;
		entity.agentSid3 = agent.getAgentSid3();
		entity.agentAppType3 = agent.getAgentAppType3().value;
		entity.agentSid4 = agent.getAgentSid4();
		entity.agentAppType4 = agent.getAgentAppType4().value;
		entity.cmmmtAgentPK = primaryKey;
		this.commandProxy().update(entity);	
	}

	/**
	 * Delete Agent
	 */
	@Override
	public void delete(String companyId, String employeeId, String requestId) {
		CmmmtAgentPK cmmmtAgentPK = new CmmmtAgentPK(companyId, employeeId, requestId);
		this.commandProxy().remove(CmmmtAgent.class, cmmmtAgentPK);
	}

	/**
	 * Find Agent by Request Id
	 */
	@Override
	public Optional<Agent> find(String companyId, String employeeId, String requestId) {
		CmmmtAgentPK primaryKey = new CmmmtAgentPK(companyId, employeeId, requestId);
		return this.queryProxy().find(primaryKey, CmmmtAgent.class)
				.map(x -> convertToDomain(x));
	}
	
	/**
	 * Find All Agent by Date
	 */
	@Override
	public List<Agent> findAll(String companyId,GeneralDate startDate, GeneralDate endDate) {
		return this.queryProxy().query(SELECT_AGENT_BY_DATE, CmmmtAgent.class)
				.setParameter("companyId", companyId)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.getList(c -> convertToDomain(c));
	}
	
	/**
	 * Find All Agent by Agent Sid
	 */
	@Override
	public List<Agent> findByAgentSid(String companyId, String agentSid) {
		return this.queryProxy().query(SELECT_AGENT_SID, CmmmtAgent.class)
				.setParameter("companyId", companyId)
				.setParameter("agentSid", agentSid)
				.getList(c -> convertToDomain(c));
	}
	
	/**
	 * Find All Agent by Agent Sid
	 */
	@Override
	public List<Agent> findBySidDate(String companyId, String employeeId, GeneralDate baseDate) {
		return this.queryProxy().query(SELECT_AGENT_ALL_DATE, CmmmtAgent.class)
				.setParameter("companyId", companyId)
				.setParameter("employeeId", employeeId)
				.setParameter("baseDate", baseDate)
				.getList(c -> convertToDomain(c));
	}

}

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
	
	private static final String QUERY_IS_EXISTED;
	
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
		builderString.append(" AND e.cmmmtAgentPK.employeeId = :employeeId");
		builderString.append(" AND e.cmmmtAgentPK.requestId = :requestId");
		QUERY_IS_EXISTED = builderString.toString();
		
		}
	
		
	
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
	 * find all Agent
	 */
	@Override
	public List<Agent> findAllAgent(String companyId, String employeeId) {
		return this.queryProxy().query(SELECT_ALL_AGENT, CmmmtAgent.class)
				.setParameter("companyId", companyId).setParameter("employeeId", employeeId)
				.getList(c -> convertToDomain(c));
	}

	/**
	 * add Agent
	 */
	@Override
	public void add(Agent agent) {
		this.commandProxy().insert(convertToDbType(agent));		
	}

	/**
	 * update Agent
	 */
	@Override
	public void update(Agent agent) {
		this.commandProxy().update(convertToDbType(agent));
	}

	/**
	 * delete Agent
	 */
	@Override
	public void delete(String companyId, String employeeId, String requestId) {
		CmmmtAgentPK cmmmtAgentPK = new CmmmtAgentPK(companyId, employeeId, requestId);
		this.commandProxy().remove(CmmmtAgent.class, cmmmtAgentPK);
	}


	@Override
	public Optional<Agent> find(String companyId, String employeeId, String requestId) {
		CmmmtAgentPK primaryKey = new CmmmtAgentPK(companyId, employeeId, requestId);
		return this.queryProxy().find(primaryKey, CmmmtAgent.class)
				.map(x -> convertToDomain(x));
	}
	@Override
	public List<Agent> findAll(String companyId, GeneralDate startDate, GeneralDate endDate) {
		return this.queryProxy().query(SELECT_ALL, CmmmtAgent.class)
				.setParameter("companyId", companyId)
				.setParameter("companyId", startDate)
				.setParameter("companyId", endDate)
				.getList(c -> convertToDomain(c));
	}
}

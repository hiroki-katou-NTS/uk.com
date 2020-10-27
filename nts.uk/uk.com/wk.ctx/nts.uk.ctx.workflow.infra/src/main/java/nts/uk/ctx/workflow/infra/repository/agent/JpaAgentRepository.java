package nts.uk.ctx.workflow.infra.repository.agent;

import java.util.*;
import java.sql.PreparedStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.ejb.Stateless;
import lombok.SneakyThrows;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.workflow.dom.agent.Agent;
import nts.uk.ctx.workflow.dom.agent.AgentRepository;
import nts.uk.ctx.workflow.dom.agent.output.AgentInfoOutput;
import nts.uk.ctx.workflow.infra.entity.agent.WwfmtAgent;
import nts.uk.ctx.workflow.infra.entity.agent.WwfmtAgentPK;

@Stateless
public class JpaAgentRepository extends JpaRepository implements AgentRepository {
	private static final String SELECT_ALL;
	
	private static final String SELECT_ALL_AGENT;
	
	private static final String SELECT_AGENT_BY_DATE;
	
	private static final String SELECT_AGENT_SID;

	private static final String SELECT_AGENT_SID_DATE;
	
	private static final String SELECT_AGENT_ALL_DATE;
	
	private static final String SELECT_AGENT_BY_APPROVER_DATE;
	
	private static final String SELECT_AGENT_BY_TYPE1;
	private static final String SELECT_AGENT_BY_TYPE2;
	private static final String SELECT_AGENT_BY_TYPE3;
	private static final String SELECT_AGENT_BY_TYPE4;
	
	private static final String SELECT_AGENT_BY_SID_DATE;
	
	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM WwfmtAgent e");
		builderString.append(" WHERE e.wwfmtAgentPK.companyId = :companyId");	
		SELECT_ALL = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM WwfmtAgent e");
		builderString.append(" WHERE e.wwfmtAgentPK.companyId = :companyId");
		builderString.append(" AND e.wwfmtAgentPK.employeeId = :employeeId");
		builderString.append(" ORDER BY e.startDate DESC");
		SELECT_ALL_AGENT = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM WwfmtAgent e");
		builderString.append(" WHERE e.wwfmtAgentPK.companyId = :companyId");
		builderString.append(" AND NOT (e.startDate > :endDate");
		builderString.append(" OR e.endDate < :startDate)");
		builderString.append(" ORDER BY e.startDate DESC");
		SELECT_AGENT_BY_DATE = builderString.toString(); 
		
		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM WwfmtAgent e");
		builderString.append(" WHERE e.wwfmtAgentPK.companyId = :companyId");
		builderString.append(" AND e.agentSid = :agentSid");
		SELECT_AGENT_SID = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM WwfmtAgent e");
		builderString.append(" WHERE e.wwfmtAgentPK.companyId = :companyId"); 
		builderString.append(" AND e.wwfmtAgentPK.employeeId IN :employeeIds");
		builderString.append(" AND e.startDate <= :baseDate");
		builderString.append(" AND e.endDate >= :baseDate");
		SELECT_AGENT_SID_DATE = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM WwfmtAgent e");
		builderString.append(" WHERE e.wwfmtAgentPK.companyId = :companyId"); 
		builderString.append(" AND (e.agentSid1 = :employeeId");
		builderString.append(" OR e.agentSid2 = :employeeId");
		builderString.append(" OR e.agentSid3 = :employeeId");
		builderString.append(" OR e.agentSid4 = :employeeId)");
		builderString.append(" AND e.startDate <= :startDate");
		builderString.append(" AND e.endDate >= :endDate");
		SELECT_AGENT_ALL_DATE = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM WwfmtAgent e");
		builderString.append(" WHERE e.wwfmtAgentPK.companyId = :companyId");
		builderString.append(" AND e.agentSid1 = :employeeId");
		builderString.append(" AND e.startDate <= :endDate");
		builderString.append(" OR e.endDate >= :startDate");
		SELECT_AGENT_BY_APPROVER_DATE = builderString.toString(); 
		
		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM WwfmtAgent e");
		builderString.append(" WHERE e.wwfmtAgentPK.companyId = :companyId");
		builderString.append(" AND e.agentSid1 = :employeeId");
		builderString.append(" AND e.startDate <= :endDate");
		builderString.append(" AND e.endDate >= :startDate");
		SELECT_AGENT_BY_TYPE1 = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM WwfmtAgent e");
		builderString.append(" WHERE e.wwfmtAgentPK.companyId = :companyId");
		builderString.append(" AND e.agentSid2 = :employeeId");
		builderString.append(" AND e.startDate <= :endDate");
		builderString.append(" AND e.endDate >= :startDate");
		SELECT_AGENT_BY_TYPE2 = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM WwfmtAgent e");
		builderString.append(" WHERE e.wwfmtAgentPK.companyId = :companyId");
		builderString.append(" AND e.agentSid3 = :employeeId");
		builderString.append(" AND e.startDate <= :endDate");
		builderString.append(" AND e.endDate >= :startDate");
		SELECT_AGENT_BY_TYPE3 = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM WwfmtAgent e");
		builderString.append(" WHERE e.wwfmtAgentPK.companyId = :companyId");
		builderString.append(" AND e.agentSid4 = :employeeId");
		builderString.append(" AND e.startDate <= :endDate");
		builderString.append(" AND e.endDate >= :startDate");
		SELECT_AGENT_BY_TYPE4 = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM WwfmtAgent e");
		builderString.append(" WHERE e.wwfmtAgentPK.companyId = :companyId"); 
		builderString.append(" AND e.wwfmtAgentPK.employeeId = :employeeId");
		builderString.append(" AND e.startDate <= :startDate");
		builderString.append(" AND e.endDate >= :endDate");
		SELECT_AGENT_BY_SID_DATE = builderString.toString();
		
		}
	private static final String GET_LST_BY_AGENT_TYPE1 = "SELECT c  FROM WwfmtAgent c"
			+ " WHERE c.wwfmtAgentPK.companyId = :companyId"
			+ " AND c.agentSid1 = :agentId";
	private static final String GET_LST_BY_SID_REQID = "SELECT c  FROM WwfmtAgent c"
			+ " WHERE c.wwfmtAgentPK.companyId = :companyId"
			+ " AND c.wwfmtAgentPK.employeeId = :employeeId"
			+ " AND c.wwfmtAgentPK.requestId != :requestId";
		
	/**
	 * Convert Data to Domain
	 * @param wwfmtAgent
	 * @return
	 */
	private Agent convertToDomain(WwfmtAgent wwfmtAgent){
		Agent agent = Agent.createFromJavaType(
				wwfmtAgent.wwfmtAgentPK.companyId,
				wwfmtAgent.wwfmtAgentPK.employeeId,
				wwfmtAgent.wwfmtAgentPK.requestId,
				wwfmtAgent.startDate,
				wwfmtAgent.endDate,
				wwfmtAgent.agentSid1,
				wwfmtAgent.agentAppType1,
				wwfmtAgent.agentSid2,
				wwfmtAgent.agentAppType2,
				wwfmtAgent.agentSid3,
				wwfmtAgent.agentAppType3,
				wwfmtAgent.agentSid4,
				wwfmtAgent.agentAppType4);
		return agent;
	}
	
	/**
	 * Convert Data to Database Type
	 * @param agent
	 * @return
	 */
	private WwfmtAgent convertToDbType(Agent agent) {
		WwfmtAgent wwfmtAgent = new WwfmtAgent();
		WwfmtAgentPK wwfmtAgentPK = new WwfmtAgentPK(
				agent.getCompanyId(),
				agent.getEmployeeId(),
				agent.getRequestId().toString());
		wwfmtAgent.startDate = agent.getStartDate();
		wwfmtAgent.endDate = agent.getEndDate();
		wwfmtAgent.agentSid1 = agent.getAgentSid1();
		wwfmtAgent.agentAppType1 = agent.getAgentAppType1().value;
		wwfmtAgent.agentSid2 = agent.getAgentSid2();
		wwfmtAgent.agentAppType2 = agent.getAgentAppType2().value;
		wwfmtAgent.agentSid3 = agent.getAgentSid3();
		wwfmtAgent.agentAppType3 = agent.getAgentAppType3().value;
		wwfmtAgent.agentSid4 = agent.getAgentSid4();
		wwfmtAgent.agentAppType4 = agent.getAgentAppType4().value;
		wwfmtAgent.wwfmtAgentPK = wwfmtAgentPK;
		return wwfmtAgent;
	}
	
	/**
	 * Find all Agent
	 */
	@Override
	public List<Agent> findAllAgent(String companyId, String employeeId) {
		return this.queryProxy().query(SELECT_ALL_AGENT, WwfmtAgent.class)
				.setParameter("companyId", companyId).setParameter("employeeId", employeeId)
				.getList(c -> convertToDomain(c));
	}
	
	/**
	 * Find all Agent by CompanyId
	 */
	@Override
	public List<Agent> findByCid(String companyId) {
		return this.queryProxy().query(SELECT_ALL, WwfmtAgent.class)
				.setParameter("companyId", companyId)
				.getList(c -> convertToDomain(c));
	}

	@Override
	public List<Agent> find(String companyId, List<String> employeeIds, GeneralDate baseDate) {
		List<Agent> results = new ArrayList<>();
		
		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			String sql = "select * from WWFMT_AGENT"
					+ " where CID = ?"
					+ " and SID in (" + NtsStatement.In.createParamsString(subList) + ")"
					+ " and START_DATE <= ?"
					+ " and END_DATE >= ?";
			
			try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
				stmt.setString(1, companyId);
				
				for (int i = 0; i < subList.size(); i++) {
					stmt.setString(2 + i, subList.get(i));
				}

				stmt.setDate(2 + subList.size(), Date.valueOf(baseDate.localDate()));
				stmt.setDate(3 + subList.size(), Date.valueOf(baseDate.localDate()));
				
				List<Agent> subResults = new NtsResultSet(stmt.executeQuery())
						.getList(rec -> convertToDomain(WwfmtAgent.fromJdbc(rec)));
				results.addAll(subResults);
				
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		});
		return results;
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
		WwfmtAgentPK primaryKey = new WwfmtAgentPK(agent.getCompanyId(), agent.getEmployeeId(), agent.getRequestId().toString());
		WwfmtAgent entity = this.queryProxy().find(primaryKey, WwfmtAgent.class).get();
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
		entity.wwfmtAgentPK = primaryKey;
		this.commandProxy().update(entity);	
	}

	/**
	 * Delete Agent
	 */
	@Override
	public void delete(String companyId, String employeeId, String requestId) {
		WwfmtAgentPK wwfmtAgentPK = new WwfmtAgentPK(companyId, employeeId, requestId);
		this.commandProxy().remove(WwfmtAgent.class, wwfmtAgentPK);
	}

	/**
	 * Find Agent by Request Id
	 */
	@Override
	@SneakyThrows
	public Optional<Agent> find(String companyId, String employeeId, String requestId) {
		
		String sql = "select * from WWFMT_AGENT"
				+ " where CID = ?"
				+ " and SID = ?"
				+ " and REQUEST_ID = ?";
		try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
			stmt.setString(1, companyId);
			stmt.setString(2, employeeId);
			stmt.setString(3, requestId);
			
			return new NtsResultSet(stmt.executeQuery()).getSingle(rec -> WwfmtAgent.fromJdbc(rec))
					.map(e -> convertToDomain(e));
		}
		
	}
	
	/**
	 * Find All Agent by Date
	 */
	@Override
	public List<Agent> findAll(String companyId,GeneralDate startDate, GeneralDate endDate) {
		return this.queryProxy().query(SELECT_AGENT_BY_DATE, WwfmtAgent.class)
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
		return this.queryProxy().query(SELECT_AGENT_SID, WwfmtAgent.class)
				.setParameter("companyId", companyId)
				.setParameter("agentSid", agentSid)
				.getList(c -> convertToDomain(c));
	}
	
	/**
	 * Find All Agent by Agent Sid
	 */
	@Override
	public List<Agent> findBySidDate(String companyId, String employeeId, GeneralDate startDate, GeneralDate endDate) {
		return this.queryProxy().query(SELECT_AGENT_ALL_DATE, WwfmtAgent.class)
				.setParameter("companyId", companyId)
				.setParameter("employeeId", employeeId)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.getList(c -> convertToDomain(c));
	}

	@Override
	public List<Agent> findByApproverAndDate(String companyId, String approverID, GeneralDate startDate,
			GeneralDate endDate) {
		return this.queryProxy().query(SELECT_AGENT_BY_APPROVER_DATE, WwfmtAgent.class)
				.setParameter("companyId", companyId)
				.setParameter("employeeId", approverID)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.getList(c -> convertToDomain(c));
	}

	@Override
	public List<AgentInfoOutput> findAgentByPeriod(String companyID, List<String> listApprover, GeneralDate startDate,
			GeneralDate endDate, int agentType) {
		
		return NtsStatement.In.split(listApprover, approverIds -> {
			
			String agentSidColumn = "AGENT_SID" + agentType;
			String sql = "select * from WWFMT_AGENT"
					+ " where CID = ?"
					+ " and " + agentSidColumn + " in (" + NtsStatement.In.createParamsString(approverIds) + ")"
					+ " and START_DATE <= ?"
					+ " and END_DATE >= ?";
			
			try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
				
				stmt.setString(1, companyID);
				for (int i = 0; i < approverIds.size(); i++) {
					stmt.setString(2 + i, approverIds.get(i));
				}
				stmt.setDate(2 + approverIds.size(), Date.valueOf(endDate.localDate()));
				stmt.setDate(3 + approverIds.size(), Date.valueOf(startDate.localDate()));
				
				return new NtsResultSet(stmt.executeQuery()).getList(rec -> {
					WwfmtAgent c = WwfmtAgent.MAPPER.toEntity(rec);
					return new AgentInfoOutput(
							rec.getString(agentSidColumn),
							c.wwfmtAgentPK.employeeId,
							c.startDate,
							c.endDate);
				});
				
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		});
	}

	@Override
	public List<Agent> getAgentBySidDate(String companyId, String employeeId, GeneralDate startDate, GeneralDate endDate) {
		return this.queryProxy().query(SELECT_AGENT_BY_SID_DATE, WwfmtAgent.class)
				.setParameter("companyId", companyId)
				.setParameter("employeeId", employeeId)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.getList(c -> convertToDomain(c));
	}

	@Override
	public List<Agent> findAgentForSpr(String companyId, String approverID, GeneralDate startDate,
			GeneralDate endDate) {
		return this.queryProxy().query(SELECT_AGENT_BY_TYPE1, WwfmtAgent.class)
				.setParameter("companyId", companyId)
				.setParameter("employeeId", approverID)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.getList(c -> convertToDomain(c));
	}

	@Override
	public List<Agent> getListByAgentType1(String companyId, String agentId) {
		return this.queryProxy().query(GET_LST_BY_AGENT_TYPE1, WwfmtAgent.class)
				.setParameter("companyId", companyId)
				.setParameter("agentId", agentId)
				.getList(c -> convertToDomain(c));
	}

	@Override
	public List<Agent> getListAgentBySidReqId(String companyId, String employeeId, String requestId) {
		return this.queryProxy().query(GET_LST_BY_SID_REQID, WwfmtAgent.class)
				.setParameter("companyId", companyId)
				.setParameter("employeeId", employeeId)
				.setParameter("requestId", requestId)
				.getList(c -> convertToDomain(c));
	}

}

package nts.uk.ctx.workflow.infra.repository.agent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.Query;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.workflow.dom.agent.Agent;
import nts.uk.ctx.workflow.dom.agent.AgentRepository;
import nts.uk.ctx.workflow.dom.agent.output.AgentInfoOutput;
import nts.uk.ctx.workflow.dom.export.agent.AgentExportData;
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
	
	private static final String SELECT_AGENT_BY_APPROVER_DATE;
	
	private static final String SELECT_AGENT_BY_TYPE1;
	private static final String SELECT_AGENT_BY_TYPE2;
	private static final String SELECT_AGENT_BY_TYPE3;
	private static final String SELECT_AGENT_BY_TYPE4;
	
	private static final String SELECT_AGENT_BY_SID_DATE;

	private static final String SELECT_AGENT_BY_EMPLOYEE_ID;
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
		builderString.append(" AND e.startDate <= :startDate");
		builderString.append(" AND e.endDate >= :endDate");
		SELECT_AGENT_ALL_DATE = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM CmmmtAgent e");
		builderString.append(" WHERE e.cmmmtAgentPK.companyId = :companyId");
		builderString.append(" AND e.agentSid1 = :employeeId");
		builderString.append(" AND e.startDate <= :endDate");
		builderString.append(" OR e.endDate >= :startDate");
		SELECT_AGENT_BY_APPROVER_DATE = builderString.toString(); 
		
		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM CmmmtAgent e");
		builderString.append(" WHERE e.cmmmtAgentPK.companyId = :companyId");
		builderString.append(" AND e.agentSid1 = :employeeId");
		builderString.append(" AND e.startDate <= :endDate");
		builderString.append(" AND e.endDate >= :startDate");
		SELECT_AGENT_BY_TYPE1 = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM CmmmtAgent e");
		builderString.append(" WHERE e.cmmmtAgentPK.companyId = :companyId");
		builderString.append(" AND e.agentSid2 = :employeeId");
		builderString.append(" AND e.startDate <= :endDate");
		builderString.append(" AND e.endDate >= :startDate");
		SELECT_AGENT_BY_TYPE2 = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM CmmmtAgent e");
		builderString.append(" WHERE e.cmmmtAgentPK.companyId = :companyId");
		builderString.append(" AND e.agentSid3 = :employeeId");
		builderString.append(" AND e.startDate <= :endDate");
		builderString.append(" AND e.endDate >= :startDate");
		SELECT_AGENT_BY_TYPE3 = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM CmmmtAgent e");
		builderString.append(" WHERE e.cmmmtAgentPK.companyId = :companyId");
		builderString.append(" AND e.agentSid4 = :employeeId");
		builderString.append(" AND e.startDate <= :endDate");
		builderString.append(" AND e.endDate >= :startDate");
		SELECT_AGENT_BY_TYPE4 = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM CmmmtAgent e");
		builderString.append(" WHERE e.cmmmtAgentPK.companyId = :companyId"); 
		builderString.append(" AND e.cmmmtAgentPK.employeeId = :employeeId");
		builderString.append(" AND e.startDate <= :startDate");
		builderString.append(" AND e.endDate >= :endDate");
		SELECT_AGENT_BY_SID_DATE = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT emp.SCD, p.BUSINESS_NAME, ag.START_DATE, ag.END_DATE, ag.AGENT_APP_TYPE1, ag.AGENT_SID1, ag.SCD as EMPCD, ag.BUSINESS_NAME as PersonName FROM BSYMT_EMP_DTA_MNG_INFO emp JOIN BPSMT_PERSON p ON emp.PID = p.PID LEFT JOIN (Select cag.SID, cag.AGENT_APP_TYPE1,  cag.START_DATE, cag.END_DATE, cag.AGENT_SID1, empp.SCD, pp.BUSINESS_NAME FROM CMMMT_AGENT cag  JOIN BSYMT_EMP_DTA_MNG_INFO empp ON cag.AGENT_SID1 = empp.SID JOIN BPSMT_PERSON pp  ON empp.PID = pp.PID) ag ON emp.SID = ag.SID WHERE emp.SID IN (%s) AND emp.CID = ?");
		SELECT_AGENT_BY_EMPLOYEE_ID = builderString.toString();



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
		List<Agent> results = new ArrayList<>();
		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			results.addAll(this.queryProxy().query(SELECT_AGENT_SID_DATE, CmmmtAgent.class)
				.setParameter("companyId", companyId)
				.setParameter("employeeIds", subList)
				.setParameter("baseDate", baseDate)
				.getList(c -> convertToDomain(c)));
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
	public List<Agent> findBySidDate(String companyId, String employeeId, GeneralDate startDate, GeneralDate endDate) {
		return this.queryProxy().query(SELECT_AGENT_ALL_DATE, CmmmtAgent.class)
				.setParameter("companyId", companyId)
				.setParameter("employeeId", employeeId)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.getList(c -> convertToDomain(c));
	}

	@Override
	public List<Agent> findByApproverAndDate(String companyId, String approverID, GeneralDate startDate,
			GeneralDate endDate) {
		return this.queryProxy().query(SELECT_AGENT_BY_APPROVER_DATE, CmmmtAgent.class)
				.setParameter("companyId", companyId)
				.setParameter("employeeId", approverID)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.getList(c -> convertToDomain(c));
	}

	@Override
	public List<AgentInfoOutput> findAgentByPeriod(String companyID, List<String> listApprover, GeneralDate startDate,
			GeneralDate endDate, Integer agentType) {
		List<AgentInfoOutput> resultList = new ArrayList<>();
		listApprover.forEach(x -> {
			switch (agentType) {
			case 1:
				List<AgentInfoOutput> findList1 = this.queryProxy().query(SELECT_AGENT_BY_TYPE1, CmmmtAgent.class)
				.setParameter("companyId", companyID)
				.setParameter("employeeId", x)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.getList(c -> { 
					return new AgentInfoOutput(x, c.agentSid1, c.startDate, c.endDate);
				});
				resultList.addAll(findList1);
				break;
			case 2:
				List<AgentInfoOutput> findList2 = this.queryProxy().query(SELECT_AGENT_BY_TYPE2, CmmmtAgent.class)
				.setParameter("companyId", companyID)
				.setParameter("employeeId", x)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.getList(c -> { 
					return new AgentInfoOutput(x, c.agentSid2, c.startDate, c.endDate);
				});
				resultList.addAll(findList2);
				break;
			case 3:
				List<AgentInfoOutput> findList3 = this.queryProxy().query(SELECT_AGENT_BY_TYPE3, CmmmtAgent.class)
				.setParameter("companyId", companyID)
				.setParameter("employeeId", x)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.getList(c -> { 
					return new AgentInfoOutput(x, c.agentSid3, c.startDate, c.endDate);
				});
				resultList.addAll(findList3);
				break;
			default:
				List<AgentInfoOutput> findList4 = this.queryProxy().query(SELECT_AGENT_BY_TYPE4, CmmmtAgent.class)
				.setParameter("companyId", companyID)
				.setParameter("employeeId", x)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.getList(c -> { 
					return new AgentInfoOutput(x, c.agentSid4, c.startDate, c.endDate);
				});
				resultList.addAll(findList4);
			}
		});
		return resultList;
	}

	@Override
	public List<Agent> getAgentBySidDate(String companyId, String employeeId, GeneralDate startDate, GeneralDate endDate) {
		return this.queryProxy().query(SELECT_AGENT_BY_SID_DATE, CmmmtAgent.class)
				.setParameter("companyId", companyId)
				.setParameter("employeeId", employeeId)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.getList(c -> convertToDomain(c));
	}

	@Override
	public List<AgentExportData> getAgentByEmployeeID(String companyId, List<String> employeeIds) {
		if (CollectionUtil.isEmpty(employeeIds)) {
			return new ArrayList<>();
		}

		List<AgentExportData> listAgentExportData = new ArrayList<AgentExportData>();
		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT,(sublist)->{
			String params = String.join("','",sublist);
			//String sql = "SELECT emp.SID, emp.SCD, p.BUSINESS_NAME, ag.START_DATE, ag.END_DATE, ag.AGENT_SID1, ag.AGENT_APP_TYPE1, ag.AGENT_SID2, ag.AGENT_APP_TYPE2, ag.AGENT_SID3, ag.AGENT_APP_TYPE3, ag.AGENT_SID4, ag.AGENT_APP_TYPE4, ag.BUSINESS_NAME as PersonName FROM BSYMT_EMP_DTA_MNG_INFO emp JOIN BPSMT_PERSON p ON emp.PID = p.PID LEFT JOIN (Select cag.SID,  cag.START_DATE, cag.END_DATE, cag.AGENT_SID1, cag.AGENT_APP_TYPE1, cag.AGENT_SID2, cag.AGENT_APP_TYPE2, cag.AGENT_SID3, cag.AGENT_APP_TYPE3, cag.AGENT_SID4, cag.AGENT_APP_TYPE4,  empp.SCD, pp.BUSINESS_NAME FROM CMMMT_AGENT cag  JOIN BSYMT_EMP_DTA_MNG_INFO empp ON cag.AGENT_SID1 = empp.SID JOIN BPSMT_PERSON pp  ON empp.PID = pp.PID) ag ON emp.SID = ag.SID WHERE emp.SID IN ('%s') AND emp.CID = ?";
			//sql = String.format(sql,params);
			String sql = "SELECT emp.companyId, emp.bsymtEmployeeDataMngInfoPk.sId, emp.employeeCode, emp.bsymtEmployeeDataMngInfoPk.pId, p.businessName, cag.startDate, cag.endDate, cag.agentSid1, cag.agentAppType1, pp.businessName as Personame FROM BsymtEmployeeDataMngInfo emp JOIN BpsmtPerson p ON emp.bsymtEmployeeDataMngInfoPk.pId = p.bpsmtPersonPk.pId LEFT JOIN CmmmtAgent cag ON cag.cmmmtAgentPK.employeeId = emp.bsymtEmployeeDataMngInfoPk.sId LEFT JOIN BsymtEmployeeDataMngInfo empp ON cag.agentSid1 = empp.bsymtEmployeeDataMngInfoPk.sId LEFT JOIN BpsmtPerson pp ON empp.bsymtEmployeeDataMngInfoPk.pId = pp.bpsmtPersonPk.pId WHERE emp.bsymtEmployeeDataMngInfoPk.sId IN :employeeIds AND emp.companyId = :companyId";
			//String sql = "SELECT emp,cag,pp FROM BsymtEmployeeDataMngInfo emp JOIN BpsmtPerson p ON emp.bsymtEmployeeDataMngInfoPk.pId = p.bpsmtPersonPk.pId LEFT JOIN CmmmtAgent cag ON cag.cmmmtAgentPK.employeeId = emp.bsymtEmployeeDataMngInfoPk.sId LEFT JOIN BsymtEmployeeDataMngInfo empp ON cag.agentSid1 = empp.bsymtEmployeeDataMngInfoPk.sId LEFT JOIN BpsmtPerson pp ON empp.bsymtEmployeeDataMngInfoPk.pId = pp.bpsmtPersonPk.pId WHERE emp.bsymtEmployeeDataMngInfoPk.sId IN :employeeIds AND emp.companyId = :companyId";
			//Query query = this.getEntityManager().createNativeQuery(sql);
            //

            listAgentExportData.addAll(this.queryProxy().query(sql,Object[].class)
					.setParameter("employeeIds",employeeIds)
					.setParameter("companyId",companyId)
					.getList(x ->this.toReportData(x)));

		});
		return listAgentExportData;
	}

	public List<AgentExportData> toReportData(List<Object[]> entitys){

        List<AgentExportData> listEntity = new ArrayList<>();
	    if(!CollectionUtil.isEmpty(entitys)){
            for(Object[] entity : entitys){
                listEntity.add(new AgentExportData(entity[0] == null ? null : entity[0].toString(),
                        entity[1] == null ? null : entity[1].toString(),
                        entity[2] == null ? null : entity[2].toString(),
                        entity[3] == null ? null : entity[3].toString().toString().substring(0,10),
                        entity[4] == null ? null : entity[4].toString().toString().substring(0,10),
                        entity[5] == null ? null : entity[5].toString(),
                        entity[6] == null ? null : Integer.valueOf(entity[6].toString()),
                        entity[7] == null ? null : entity[7].toString(),
                        entity[8] == null ? null : Integer.valueOf(entity[8].toString()),
                        entity[9] == null ? null : entity[9].toString(),
                        entity[10] == null ? null : Integer.valueOf(entity[10].toString()),
                        entity[11] == null ? null : entity[11].toString(),
                        entity[12] == null ? null : Integer.valueOf(entity[12].toString()),
                        entity[13] == null ? null : entity[13].toString()
                ));
            }
        }

        return  listEntity;
	}


	public AgentExportData toReportData( Object[] entity){

		/*return new AgentExportData(entity[0] == null ? null : entity[0].toString(),
				entity[1] == null ? null : entity[1].toString(),
				entity[2] == null ? null : entity[2].toString(),
				entity[3] == null ? null : entity[3].toString().toString().substring(0,10),
				entity[4] == null ? null : entity[4].toString().toString().substring(0,10),
				entity[5] == null ? null : entity[5].toString(),
				entity[6] == null ? null : Integer.valueOf(entity[6].toString()),
				entity[7] == null ? null : entity[7].toString(),
				entity[8] == null ? null : Integer.valueOf(entity[8].toString()),
				entity[9] == null ? null : entity[9].toString(),
				entity[10] == null ? null : Integer.valueOf(entity[10].toString()),
				entity[11] == null ? null : entity[11].toString(),
				entity[12] == null ? null : Integer.valueOf(entity[12].toString()),
				entity[13] == null ? null : entity[13].toString()
		);*/
		return new AgentExportData(null,null,null,null,null,null,null,null,null,null,null,null,null,null);
	}

}

package nts.uk.ctx.workflow.infra.repository.agent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

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
import sun.management.resources.agent;

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
		//builderString.append("SELECT tb1.sId, tb1.pId, tb1.employeeCode , ag FROM  CmmmtAgent ag, (SELECT e.bsymtEmployeeDataMngInfoPk.sId, e.bsymtEmployeeDataMngInfoPk.pId,e.employeeCode FROM BsymtEmployeeDataMngInfo e WHERE e.bsymtEmployeeDataMngInfoPk.sId IN :employeeIds AND e.companyId = :companyId) tb1 WHERE 1 = 1");
		//builderString.append("SELECT emp.employeeCode, p.businessName, ag.startDate, ag.endDate, ag.agentSid1, ag.agentAppType1, ag.businessName FROM BsymtEmployeeDataMngInfo emp JOIN BpsmtPerson p ON emp.bsymtEmployeeDataMngInfoPk.pId = p.bpsmtPersonPk.pId LEFT JOIN (SELECT cag.cmmmtAgentPK.employeeId, cag.agentSid1, cag.agentAppType1, cag.startDate, cag.endDate,emp1.employeeCode, p1.businessName FROM CmmmtAgent cag JOIN BsymtEmployeeDataMngInfo emp1 ON cag.agentSid1 = emp1.bsymtEmployeeDataMngInfoPk.sId JOIN BpsmtPerson p1 ON emp1.bsymtEmployeeDataMngInfoPk.pId = p1.bpsmtPersonPk.pId) ag ON emp.bsymtEmployeeDataMngInfoPk.sId = ag.employeeId WHERE emp.bsymtEmployeeDataMngInfoPk.sId IN :employeeIds AND emp.companyId = :companyId");
       // builderString.append("SELECT emp.SCD, p.BUSINESS_NAME, ag.START_DATE, ag.END_DATE, ag.AGENT_APP_TYPE1, ag.AGENT_SID1, ag.SCD as EMPCD, ag.BUSINESS_NAME as PersonName FROM BSYMT_EMP_DTA_MNG_INFO emp JOIN BPSMT_PERSON p ON emp.PID = p.PID LEFT JOIN (Select cag.SID, cag.AGENT_APP_TYPE1, cag.START_DATE, cag.END_DATE, cag.AGENT_SID1, empp.SCD, pp.BUSINESS_NAME FROM CMMMT_AGENT cag  JOIN BSYMT_EMP_DTA_MNG_INFO empp ON cag.AGENT_SID1 = empp.SID JOIN BPSMT_PERSON pp  ON empp.PID = pp.PID) ag ON emp.SID = ag.SID WHERE emp.SID IN ? AND emp.CID = ?");

		/*builderString.append(" SELECT e.companyId, e.bsymtEmployeeDataMngInfoPk.sId, e.employeeCode, p.bpsmtPersonPk.pId, p.businessName ");
        builderString.append(" FROM BsymtEmployeeDataMngInfo e INNER JOIN BpsmtPerson p ON e.bsymtEmployeeDataMngInfoPk.pId = p.bpsmtPersonPk.pId ");
		builderString.append(" WHERE e.bsymtEmployeeDataMngInfoPk.sId IN :employeeIds AND e.companyId = :companyId");*/

		/*builderString.append("SELECT tb1.sId, tb2.employeeId ");
		builderString.append(" FROM ");

		builderString.append(" (SELECT g.cmmmtAgentPK.sId, g.agentSid1, g.agentAppType1, p.businessName ");
		builderString.append(" FROM CmmmtAgent g INNER JOIN BsymtEmployeeDataMngInfo e ON g.agentSid1 = e.bsymtEmployeeDataMngInfoPk.sId INNER JOIN BpsmtPerson p ON e.bsymtEmployeeDataMngInfoPk.pId = p.bpsmtPersonPk.pId ");
		builderString.append(" WHERE g.cmmmtAgentPK.sId IN :employeeIds AND g.cmmmtAgentPK.companyId = :companyId ) tb1");

		builderString.append(" LEFT JOIN ");

		builderString.append(" (SELECT g.cmmmtAgentPK.employeeId, g.agentSid1, g.agentAppType1 ");
		builderString.append(" FROM CmmmtAgent g INNER JOIN BsymtEmployeeDataMngInfo e1 ON g.agentSid1 = e1.bsymtEmployeeDataMngInfoPk.sId INNER JOIN BpsmtPerson p1 ON e1.bsymtEmployeeDataMngInfoPk.pId = p1.bpsmtPersonPk.pId ");
		builderString.append(" WHERE g.cmmmtAgentPK.employeeId IN :employeeIds1 AND g.cmmmtAgentPK.companyId = :companyId1 ) tb2");

		builderString.append(" ON tb1.sId = tb2.employeeId");*/

		builderString.append("SELECT tb1.CID, tb1.SID, tb1.SCD, tb1.PID, tb1.BUSINESS_NAME AS EMP_NAME, tb2.AGENT_SID1, tb2.AGENT_APP_TYPE1,tb2.BUSINESS_NAME AS PERSON_NAME FROM (SELECT e.SID, e.CID, e.SCD, p.PID, p.BUSINESS_NAME FROM BSYMT_EMP_DTA_MNG_INFO e INNER JOIN BPSMT_PERSON p ON e.PID  = p.PID  WHERE e.SID IN ? AND e.CID = ? ) tb1 LEFT JOIN (SELECT g.SID, g.AGENT_SID1, g.AGENT_APP_TYPE1, p1.BUSINESS_NAME FROM CMMMT_AGENT g INNER JOIN BSYMT_EMP_DTA_MNG_INFO e1 ON g.AGENT_SID1 = e1.SID INNER JOIN BPSMT_PERSON p1 ON e1.PID  = p1.PID WHERE g.SID IN ? AND g.CID = ?) tb2 ON tb1.SID = tb2.SID");
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
		/*CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT,(sublist)->{
			listAgentExportData.addAll(this.queryProxy().query(SELECT_AGENT_BY_EMPLOYEE_ID,Object[].class)
					.setParameter("employeeIds",sublist)
					.setParameter("companyId",companyId)
					.setParameter("employeeIds1",sublist)
					.setParameter("companyId1",companyId)
					.getList(x->toReportData(x)));
		});*/
		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT,(sublist)->{
			//String temp  = "ae7fe82e-a7bd-4ce3-adeb-5cd403a9d570, 4420a05e-2aef-4b93-889d-f98f4bb53517";
            String temp  = "\"ae7fe82e-a7bd-4ce3-adeb-5cd403a9d570\", \"4420a05e-2aef-4b93-889d-f98f4bb53517\"";
			//temp = "ae7fe82e-a7bd-4ce3-adeb-5cd403a9d570";
			//String sql = "SELECT tb1.CID, tb1.SID, tb1.SCD, tb1.PID, tb1.BUSINESS_NAME AS EMP_NAME, tb2.AGENT_SID1, tb2.AGENT_APP_TYPE1,tb2.BUSINESS_NAME AS PERSON_NAME FROM (SELECT e.SID, e.CID, e.SCD, p.PID, p.BUSINESS_NAME FROM BSYMT_EMP_DTA_MNG_INFO e INNER JOIN BPSMT_PERSON p ON e.PID  = p.PID  WHERE e.SID IN (?empIds) AND e.CID = ?companyId ) tb1 LEFT JOIN (SELECT g.SID, g.AGENT_SID1, g.AGENT_APP_TYPE1, p1.BUSINESS_NAME FROM CMMMT_AGENT g INNER JOIN BSYMT_EMP_DTA_MNG_INFO e1 ON g.AGENT_SID1 = e1.SID INNER JOIN BPSMT_PERSON p1 ON e1.PID  = p1.PID WHERE g.SID IN (?empIds1) AND g.CID = ?companyId1 ) tb2 ON tb1.SID = tb2.SID";
			String sql = "SELECT g.SID, g.AGENT_SID1, g.AGENT_APP_TYPE1, p1.BUSINESS_NAME FROM CMMMT_AGENT g INNER JOIN BSYMT_EMP_DTA_MNG_INFO e1 ON g.AGENT_SID1 = e1.SID INNER JOIN BPSMT_PERSON p1 ON e1.PID  = p1.PID WHERE g.SID IN (?) AND g.CID = ?";
			listAgentExportData.addAll(this.getEntityManager().createNativeQuery(sql)
                    .setParameter(1,temp)
                    .setParameter(2,companyId)
					//.setParameter("empIds1",temp)
					//.setParameter("companyId1",companyId)
					.getResultList());

		});
		return listAgentExportData;
	}

	public AgentExportData toReportData(Object[] entity){

		/*return new AgentExportData(entity[0].toString(),
				(GeneralDate) entity[2],
				(GeneralDate) entity[3],
				entity[4].toString(),
				Integer.valueOf(entity[5].toString()),
				entity[6].toString(),
				Integer.valueOf(entity[7].toString()),
				entity[8].toString(),
				Integer.valueOf(entity[9].toString()),
				entity[10].toString(),
				Integer.valueOf(entity[11].toString()),
				entity[12].toString(),
				entity[13].toString()
				);*/
		return new AgentExportData(null,null,null,null,null,null,null,null,null,null,null,null,null);
	}

}

package nts.uk.ctx.at.record.infra.repository.executionstatusmanage.optionalperiodprocess;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodExcution;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodExcutionRepository;
import nts.uk.ctx.at.record.infra.entity.executionstatusmanage.optionalperiodprocess.KrcmtAggrPeriodExcution;
import nts.uk.ctx.at.record.infra.entity.executionstatusmanage.optionalperiodprocess.KrcmtAggrPeriodExcutionPK;

/**
 * 
 * @author phongtq
 *
 */
@Stateless
public class JpaAggrPeriodExcutionRepository extends JpaRepository
implements AggrPeriodExcutionRepository{
	
	private static final String FIND_ALL;
	private static final String FIND_AGGR_CODE;
	private static final String FIND_EXECUTION_STATUS;
	private static final String FIND_EXECUTION_EMP;
	private static final String FIND_EXECUTION_INFOR;
	private static final String FIND_EXECUTION;
	private static final String FIND_EXECUTION_AGGR;

	
	static{
	StringBuilder builderString = new StringBuilder();
	builderString.append("SELECT e");
	builderString.append(" FROM KrcmtAggrPeriodExcution e");
	builderString.append(" WHERE e.krcmtAggrPeriodExcutionPK.companyId = :companyId");
	FIND_ALL = builderString.toString(); 
	
	builderString = new StringBuilder();
	builderString.append("SELECT e");
	builderString.append(" FROM KrcmtAggrPeriodExcution e");
	builderString.append(" WHERE e.krcmtAggrPeriodExcutionPK.companyId = :companyId");
	builderString.append(" AND e.aggrFrameCode = :aggrFrameCode");
	builderString.append(" ORDER BY e.startDateTime DESC");
	FIND_AGGR_CODE = builderString.toString();
	
	builderString = new StringBuilder();
	builderString.append("SELECT e");
	builderString.append(" FROM KrcmtAggrPeriodExcution e");
	builderString.append(" WHERE e.krcmtAggrPeriodExcutionPK.companyId = :companyId");
	builderString.append(" AND e.aggrFrameCode = :aggrFrameCode");
	builderString.append(" AND e.executionStatus = :executionStatus");
	FIND_EXECUTION_STATUS = builderString.toString();
	
	builderString = new StringBuilder();
	builderString.append("SELECT e");
	builderString.append(" FROM KrcmtAggrPeriodExcution e");
	builderString.append(" WHERE e.krcmtAggrPeriodExcutionPK.companyId = :companyId");
	builderString.append(" AND e.krcmtAggrPeriodExcutionPK.executionEmpId = :executionEmpId");
	builderString.append(" AND e.aggrFrameCode = :aggrFrameCode");
	FIND_EXECUTION_EMP = builderString.toString();
	
	builderString = new StringBuilder();
	builderString.append("SELECT e");
	builderString.append(" FROM KrcmtAggrPeriodExcution e");
	builderString.append(" WHERE e.krcmtAggrPeriodExcutionPK.companyId = :companyId");
	builderString.append(" AND e.krcmtAggrPeriodExcutionPK.aggrId = :aggrId");
	FIND_EXECUTION_INFOR = builderString.toString(); 
	
	builderString = new StringBuilder();
	builderString.append("SELECT e");
	builderString.append(" FROM KrcmtAggrPeriodExcution e");
	builderString.append(" WHERE e.krcmtAggrPeriodExcutionPK.companyId = :companyId");
	builderString.append(" AND e.krcmtAggrPeriodExcutionPK.aggrId = :aggrId");
	FIND_EXECUTION = builderString.toString(); 
	
	builderString = new StringBuilder();
	builderString.append("SELECT e");
	builderString.append(" FROM KrcmtAggrPeriodExcution e");
	builderString.append(" WHERE e.krcmtAggrPeriodExcutionPK.companyId = :companyId");
	builderString.append(" AND e.krcmtAggrPeriodExcutionPK.executionEmpId = :executionEmpId");
	builderString.append(" AND e.aggrFrameCode = :aggrFrameCode");
	FIND_EXECUTION_AGGR = builderString.toString(); 
	
	
	}
	
	@Override
	public List<AggrPeriodExcution> findAll(String companyId) {
		return this.queryProxy().query(FIND_ALL, KrcmtAggrPeriodExcution.class)
				.setParameter("companyId", companyId)
				.getList(c -> convertToDomainApe(c));
	}
	


	/**
	 * Find by companyId, aggrFrameCode and executionStatus
	 */
	@Override
	public List<AggrPeriodExcution> findExecutionStatus(String companyId, String aggrFrameCode, int executionStatus) {
		return this.queryProxy().query(FIND_EXECUTION_STATUS, KrcmtAggrPeriodExcution.class)
				.setParameter("companyId", companyId)
				.setParameter("aggrFrameCode", aggrFrameCode )
				.setParameter("executionStatus", executionStatus)
				.getList(c -> convertToDomainApe(c));
	}
	
	@Override
	public List<AggrPeriodExcution> findExecutionEmp(String companyId, String executionEmpId, String aggrFrameCode) {
		return this.queryProxy().query(FIND_EXECUTION_EMP, KrcmtAggrPeriodExcution.class)
				.setParameter("companyId", companyId)
				.setParameter("executionEmpId", executionEmpId)
				.setParameter("aggrFrameCode", aggrFrameCode )
				.getList(c -> convertToDomainApe(c));
	}

	/**
	 * 
	 * @param krcmtAggrPeriodExcution
	 * @return
	 */
	private AggrPeriodExcution convertToDomainApe(KrcmtAggrPeriodExcution krcmtAggrPeriodExcution) {
		AggrPeriodExcution aggrPeriodExcution = AggrPeriodExcution.createFromJavaType(
				krcmtAggrPeriodExcution.krcmtAggrPeriodExcutionPK.companyId, 
				krcmtAggrPeriodExcution.krcmtAggrPeriodExcutionPK.executionEmpId, 
				krcmtAggrPeriodExcution.aggrFrameCode,
				krcmtAggrPeriodExcution.krcmtAggrPeriodExcutionPK.aggrId, 
				krcmtAggrPeriodExcution.startDateTime, 
				krcmtAggrPeriodExcution.endDateTime, 
				krcmtAggrPeriodExcution.executionAtr, 
				krcmtAggrPeriodExcution.executionStatus, 
				krcmtAggrPeriodExcution.presenceOfError);
		return aggrPeriodExcution;
	}

	/**
	 * 
	 */
	@Override
	public List<AggrPeriodExcution> findExecutionPeriod(String companyId, GeneralDate start, GeneralDate end) {
		GeneralDateTime startP = GeneralDateTime.ymdhms(start.year(), start.month(), start.day(), 0, 0, 0);
		GeneralDateTime endP = GeneralDateTime.ymdhms(end.year(), end.month(), end.day(), 23, 59, 59);
		String sql = "SELECT e FROM KrcmtAggrPeriodExcution e WHERE e.krcmtAggrPeriodExcutionPK.companyId = :companyId AND e.startDateTime BETWEEN :start AND :end";
		return this.queryProxy().query(sql, KrcmtAggrPeriodExcution.class)
				.setParameter("companyId", companyId)
				.setParameter("start", startP)
				.setParameter("end", endP)
				.getList(c -> convertToDomainApe(c));
	}
	
	@Override
	public List<AggrPeriodExcution> findAggrCode(String companyId, String aggrFrameCode) {
		return this.queryProxy().query(FIND_AGGR_CODE, KrcmtAggrPeriodExcution.class)
				.setParameter("companyId", companyId)
				.setParameter("aggrFrameCode", aggrFrameCode)
				.getList(c -> convertToDomainApe(c));
	}
	

	/**
	 * 
	 */
	@Override
	public void addExcution(AggrPeriodExcution excution) {
		this.commandProxy().insert(convertToDbTypeApe(excution));

	}
	
	/**
	 * 
	 * @param excution
	 * @return
	 */
	private KrcmtAggrPeriodExcution convertToDbTypeApe(AggrPeriodExcution excution) {
		KrcmtAggrPeriodExcution entity = new KrcmtAggrPeriodExcution();
		entity.krcmtAggrPeriodExcutionPK = new KrcmtAggrPeriodExcutionPK(excution.getCompanyId(), excution.getExecutionEmpId(), excution.getAggrId());
		entity.aggrFrameCode = excution.getAggrFrameCode().v();
		entity.startDateTime = excution.getStartDateTime();
		entity.endDateTime = excution.getEndDateTime();
		entity.executionAtr = excution.getExecutionAtr().value;
		entity.executionStatus = excution.getExecutionStatus() != null ? excution.getExecutionStatus().get().value : null;
		entity.presenceOfError = excution.getPresenceOfError().value;
		return entity;
	}
	
	@Override
	public void updateExcution(AggrPeriodExcution excution) {
		KrcmtAggrPeriodExcutionPK primaryKey = new KrcmtAggrPeriodExcutionPK(excution.getCompanyId(),
				excution.getExecutionEmpId(), excution.getAggrId());
		KrcmtAggrPeriodExcution periodExcution = this.queryProxy().find(primaryKey, KrcmtAggrPeriodExcution.class).get();
		periodExcution.aggrFrameCode = excution.getAggrFrameCode().v();
		periodExcution.startDateTime = excution.getStartDateTime();
		periodExcution.endDateTime = excution.getEndDateTime();
		periodExcution.executionAtr = excution.getExecutionAtr().value;
		periodExcution.executionStatus = excution.getExecutionStatus() != null ? excution.getExecutionStatus().get().value : null;
		periodExcution.presenceOfError = excution.getPresenceOfError().value;
		

		this.commandProxy().update(periodExcution);
	}

	@Override
	public Optional<AggrPeriodExcution> findBy(String companyId, String aggrId, int status) {
		Optional<KrcmtAggrPeriodExcution> result = this.queryProxy().query(FIND_EXECUTION_INFOR, KrcmtAggrPeriodExcution.class)
				.setParameter("companyId", companyId)
				.setParameter("aggrId", aggrId)
				.getSingle();
		if(!result.isPresent()) return Optional.empty();
		result.get().executionStatus = status;
		this.commandProxy().update(result.get());
		return Optional.of(convertToDomainApe(result.get()));
	}

	@Override
	public Optional<AggrPeriodExcution> findByAggr(String companyId, String aggrId) {
		return this.queryProxy().query(FIND_EXECUTION, KrcmtAggrPeriodExcution.class)
				.setParameter("companyId", companyId)
				.setParameter("aggrId", aggrId )
				.getSingle(c -> convertToDomainApe(c));
	}
	@Override
	public Optional<AggrPeriodExcution> findExecution(String companyId, String executionEmpId, String aggrFrameCode) {
		return this.queryProxy().query(FIND_EXECUTION_AGGR, KrcmtAggrPeriodExcution.class)
				.setParameter("companyId", companyId)
				.setParameter("executionEmpId", executionEmpId )
				.setParameter("aggrFrameCode", aggrFrameCode )
				.getSingle(c -> convertToDomainApe(c));
	}
	
	@Override
	public void updateExe(AggrPeriodExcution excution, int executionStatus, GeneralDateTime endDateTime) {
		
		KrcmtAggrPeriodExcutionPK primaryKey = new KrcmtAggrPeriodExcutionPK(excution.getCompanyId(),
				excution.getExecutionEmpId(), excution.getAggrId());
		KrcmtAggrPeriodExcution periodExcution = this.queryProxy().find(primaryKey, KrcmtAggrPeriodExcution.class).get();
		//status is done
		periodExcution.aggrFrameCode = excution.getAggrFrameCode().v();
		periodExcution.startDateTime = excution.getStartDateTime();
		periodExcution.endDateTime = endDateTime;
		periodExcution.executionAtr = excution.getExecutionAtr().value;
		periodExcution.executionStatus = executionStatus;
		periodExcution.presenceOfError = excution.getPresenceOfError().value;

		this.commandProxy().update(periodExcution);
	}

	@Override
	public Optional<AggrPeriodExcution> findStatus(String companyId, String aggrFrameCode, int executionStatus) {
		return this.queryProxy().query(FIND_EXECUTION_STATUS, KrcmtAggrPeriodExcution.class)
				.setParameter("companyId", companyId)
				.setParameter("executionEmpId", aggrFrameCode )
				.setParameter("aggrFrameCode", executionStatus )
				.getSingle(c -> convertToDomainApe(c));
	}



	@Override
	public void updateStopState(String excuteId) {
		String sql = "Update KrcmtAggrPeriodExcution e Set e.executionStatus = 4 Where e.krcmtAggrPeriodExcutionPK.aggrId = :aggrId";
		this.queryProxy().query(sql).setParameter("aggrId", excuteId).getQuery().executeUpdate();
		
	}
	
	@Override
	public void updateAll(List<AggrPeriodExcution> domains) {
		this.commandProxy().updateAll(domains.stream()
				.map(this::convertToDbTypeApe)
				.collect(Collectors.toList()));
	}
	
}

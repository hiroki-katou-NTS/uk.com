package nts.uk.ctx.at.record.infra.repository.executionstatusmanage.optionalperiodprocess;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodExcution;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodExcutionRepository;
import nts.uk.ctx.at.record.infra.entity.executionstatusmanage.optionalperiodprocess.KrcmtAggrPeriodExcution;

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
	builderString.append(" AND e.krcmtAggrPeriodExcutionPK.aggrFrameCode = :aggrFrameCode");
	FIND_AGGR_CODE = builderString.toString();
	
	builderString = new StringBuilder();
	builderString.append("SELECT e");
	builderString.append(" FROM KrcmtAggrPeriodExcution e");
	builderString.append(" WHERE e.krcmtAggrPeriodExcutionPK.companyId = :companyId");
	builderString.append(" AND e.krcmtAggrPeriodExcutionPK.aggrFrameCode = :aggrFrameCode");
	builderString.append(" AND e.krcmtAggrPeriodExcutionPK.executionStatus = :executionStatus");
	FIND_EXECUTION_STATUS = builderString.toString();
	
	builderString = new StringBuilder();
	builderString.append("SELECT e");
	builderString.append(" FROM KrcmtAggrPeriodExcution e");
	builderString.append(" WHERE e.krcmtAggrPeriodExcutionPK.companyId = :companyId");
	builderString.append(" AND e.krcmtAggrPeriodExcutionPK.executionEmpId = :executionEmpId");
	builderString.append(" AND e.krcmtAggrPeriodExcutionPK.aggrFrameCode = :aggrFrameCode");
	FIND_EXECUTION_EMP = builderString.toString();
	}
	
	@Override
	public List<AggrPeriodExcution> findAll(String companyId) {
		return this.queryProxy().query(FIND_ALL, KrcmtAggrPeriodExcution.class)
				.setParameter("companyId", companyId)
				.getList(c -> convertToDomainApe(c));
	}
	
	@Override
	public List<AggrPeriodExcution> findAggrCode(String companyId, String aggrFrameCode) {
		return this.queryProxy().query(FIND_AGGR_CODE, KrcmtAggrPeriodExcution.class)
				.setParameter("companyId", companyId)
				.setParameter("aggrFrameCode", aggrFrameCode )
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
				krcmtAggrPeriodExcution.krcmtAggrPeriodExcutionPK.aggrFrameCode, 
				krcmtAggrPeriodExcution.aggrId, 
				krcmtAggrPeriodExcution.startDateTime, 
				krcmtAggrPeriodExcution.endDateTime, 
				krcmtAggrPeriodExcution.executionAtr, 
				krcmtAggrPeriodExcution.executionStatus, 
				krcmtAggrPeriodExcution.presenceOfError);
		return aggrPeriodExcution;
	}

	@Override
	public void deleteExcution(String companyId, String aggrFrameCode, int executionStatus) {
		// TODO Auto-generated method stub
		
	}

}

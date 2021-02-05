package nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

public interface AggrPeriodExcutionRepository {

	/**
	 * Find all Aggr Period Excution by companyId and aggrFrameCode
	 * @param companyId
	 * @param aggrFrameCode
	 * @return
	 */
	List<AggrPeriodExcution> findExecutionStatus(String companyId, String aggrFrameCode, int executionStatus);
	
	/**
	 * 
	 * @param companyId
	 * @param executionEmpId
	 * @param aggrFrameCode
	 * @return
	 */
	List<AggrPeriodExcution> findExecutionEmp(String companyId, String executionEmpId, String aggrFrameCode);

	/**
	 * 
	 * @param companyId
	 * @param aggrFrameCode
	 * @return
	 */
	List<AggrPeriodExcution> findAggrCode(String companyId, String aggrFrameCode);

	/**
	 * 
	 * @param companyId
	 * @return
	 */
	List<AggrPeriodExcution> findAll(String companyId);
	
	/**
	 * 
	 * @param companyId
	 * @param start
	 * @param end
	 * @return
	 */
	List<AggrPeriodExcution> findExecutionPeriod(String companyId, GeneralDate start, GeneralDate end);

	/**
	 * 
	 * @param excution
	 */
	void addExcution(AggrPeriodExcution excution);

	/**
	 * 
	 * @param excution
	 */
	void updateExcution(AggrPeriodExcution excution);
	/**
	 * 
	 * @param companyId
	 * @param executionId
	 * @return
	 */
	Optional<AggrPeriodExcution> findBy(String companyId, String aggrId, int status);
	
	Optional<AggrPeriodExcution> findByAggr(String companyId,String aggrId);
	
	Optional<AggrPeriodExcution> findExecution(String companyId, String executionEmpId, String aggrFrameCode);

	void updateExe(AggrPeriodExcution excutionPeriod, int executionStatus, GeneralDateTime endDateTime);
	
	Optional<AggrPeriodExcution> findStatus(String companyId, String aggrFrameCode, int executionStatus);
	
	void updateStopState(String excuteId);
	
	void updateAll(List<AggrPeriodExcution> domains);
	
}

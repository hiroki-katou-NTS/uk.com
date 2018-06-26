package nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface AggrPeriodExcutionRepository {

	/**
	 * Find all Aggr Period Excution by companyId and aggrFrameCode
	 * @param companyId
	 * @param aggrFrameCode
	 * @return
	 */
	List<AggrPeriodExcution> findExecutionStatus(String companyId, String aggrFrameCode, int executionStatus);
	
	/**
	 * Delete Excution by companyId, aggrFrameCode and executionStatus
	 * @param companyId
	 * @param aggrFrameCode
	 * @param executionStatus
	 */
	void deleteExcution(String companyId, String aggrFrameCode,int executionStatus);

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
	
}

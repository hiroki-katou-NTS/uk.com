package nts.uk.ctx.at.record.dom.workrecord.log;

import java.util.List;
import java.util.Optional;
/**
 * 
 * @author tutk
 *
 */
public interface EmpCalAndSumExeLogRepository {
	/**
	 * get all  EmpCalAndSumExeLog
	 * @param companyID
	 * @param operationCaseId
	 * @param employeeId
	 * @return
	 */
	List<EmpCalAndSumExeLog> getAllEmpCalAndSumExeLog(String companyID);
	
	/**
	 * get EmpCalAndSumExeLog by empCalAndSumExecLogId
	 * @param companyID
	 * @param empCalAndSumExecLogId
	 * @param operationCaseId
	 * @param employeeId
	 * @return
	 */
	Optional<EmpCalAndSumExeLog> getEmpCalAndSumExeLogByID(String companyID, long empCalAndSumExecLogID,String operationCaseID,String employeeID  );
	
	
	List<EmpCalAndSumExeLog> getByEmpCalAndSumExecLogID (String empCalAndSumExecLogID);

}

package nts.uk.ctx.at.record.dom.workrecord.log;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
/**
 * 
 * @author tutk
 *
 */
public interface EmpCalAndSumExeLogRepository {
	/**
	 * get all  EmpCalAndSumExeLog
	 * @param companyID
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
	Optional<EmpCalAndSumExeLog> getEmpCalAndSumExeLogByID(String companyID, String empCalAndSumExecLogID, String operationCaseID,String employeeID);
	
	List<EmpCalAndSumExeLog> getListByExecutionContent(String empCalAndSumExecLogID, int executionContent);	
	
	List<EmpCalAndSumExeLog> getByEmpCalAndSumExecLogID (String empCalAndSumExecLogID);



	/**
	 * get all EmpCalAndSumExeLog by startDate and endDate
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<EmpCalAndSumExeLog> getAllEmpCalAndSumExeLogByDate(String companyID,GeneralDate startDate,GeneralDate endDate);
	
}

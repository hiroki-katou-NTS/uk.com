package nts.uk.ctx.at.record.infra.repository.log;

import java.util.List;
import java.util.Optional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.workrecord.log.aggregateroot.EmpCalAndSumExeLog;
import nts.uk.ctx.at.record.dom.workrecord.log.aggregateroot.EmpCalAndSumExeLogRepository;
import nts.uk.ctx.at.record.infra.entity.log.KrcmtEmpExecutionLog;

public class JpaEmpCalAndSumExeLogRepository extends JpaRepository implements EmpCalAndSumExeLogRepository  {
	
	private final String SELECT_FROM_LOG = "SELECT c FROM KrcmtEmpExecutionLog c ";
	private final String SELECT_All_LOG = SELECT_FROM_LOG 
			+ " WHERE c.krcmtEmpExecutionLogPK.companyID = :companyID "
			+ " AND c.krcmtEmpExecutionLogPK.operationCaseID = :operationCaseID "
			+ " AND c.krcmtEmpExecutionLogPK.employeeID = :employeeID ";
	private final String SELECT_LOG_BY_CODE = SELECT_All_LOG
			+ " AND c.krcmtEmpExecutionLogPK.empCalAndSumExecLogID = :empCalAndSumExecLogID ";
	
	private static EmpCalAndSumExeLog toDomain(KrcmtEmpExecutionLog entity ) {
		return EmpCalAndSumExeLog.createFromJavaType (
					entity.krcmtEmpExecutionLogPK.companyID,
					entity.krcmtEmpExecutionLogPK.empCalAndSumExecLogID,
					entity.krcmtEmpExecutionLogPK.caseSpecExeContentID,
					entity.krcmtEmpExecutionLogPK.employeeID,
					entity.executedMenu,
					entity.executedStatus,
					entity.executedDate,
					entity.processingMonth,
					entity.closureID,
					null//list executionLogs( 実行ログ)
				);
		
	}
	
	/**
	 * get all EmpCalAndSumExeLog
	 */
	@Override
	public List<EmpCalAndSumExeLog> getAllEmpCalAndSumExeLog(String companyID, String operationCaseID,
			String employeeID) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * get EmpCalAndSumExeLog by code
	 */
	@Override
	public Optional<EmpCalAndSumExeLog> getEmpCalAndSumExeLogByID(String companyID, long empCalAndSumExecLogID,
			String operationCaseID, String employeeID) {
		// TODO Auto-generated method stub
		return null;
	}
	


}

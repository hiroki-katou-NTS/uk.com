package nts.uk.ctx.at.record.infra.repository.log;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.log.EmpCalAndSumExeLog;
import nts.uk.ctx.at.record.dom.workrecord.log.EmpCalAndSumExeLogRepository;
import nts.uk.ctx.at.record.infra.entity.log.KrcmtEmpExecutionLog;

@Stateless
public class JpaEmpCalAndSumExeLogRepository extends JpaRepository implements EmpCalAndSumExeLogRepository  {
	
	private final String SELECT_FROM_LOG = "SELECT c FROM KrcmtEmpExecutionLog c ";
	private final String SELECT_All_LOG = SELECT_FROM_LOG 
			+ " WHERE c.krcmtEmpExecutionLogPK.companyID = :companyID "
			+ " AND c.krcmtEmpExecutionLogPK.operationCaseID = :operationCaseID "
			+ " AND c.krcmtEmpExecutionLogPK.employeeID = :employeeID ";
	private final String SELECT_LOG_BY_CODE = SELECT_All_LOG
			+ " AND c.krcmtEmpExecutionLogPK.empCalAndSumExecLogID = :empCalAndSumExecLogID ";
	
	/**
	 * get all EmpCalAndSumExeLog
	 */
	@Override
	public List<EmpCalAndSumExeLog> getAllEmpCalAndSumExeLog(String companyID, String operationCaseID,
			String employeeID) {
		List<EmpCalAndSumExeLog> data = this.queryProxy().query(SELECT_All_LOG,KrcmtEmpExecutionLog.class)
				.setParameter("companyID", companyID)
				.setParameter("operationCaseID", operationCaseID)
				.setParameter("employeeID", employeeID)
				.getList(c -> c.toDomain());
		return data;
	}
	
	/**
	 * get EmpCalAndSumExeLog by code
	 */
	@Override
	public Optional<EmpCalAndSumExeLog> getEmpCalAndSumExeLogByID(String companyID, long empCalAndSumExecLogID,
			String operationCaseID, String employeeID) {
		Optional<EmpCalAndSumExeLog> data = this.queryProxy().query(SELECT_LOG_BY_CODE,KrcmtEmpExecutionLog.class)
				.setParameter("companyID", companyID)
				.setParameter("operationCaseID", operationCaseID)
				.setParameter("employeeID", employeeID)
				.setParameter("empCalAndSumExecLogID", empCalAndSumExecLogID)
				.getSingle(c -> c.toDomain());
		return data;
	}
}

package nts.uk.ctx.at.record.infra.repository.log;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.log.EmpCalAndSumExeLog;
import nts.uk.ctx.at.record.dom.workrecord.log.EmpCalAndSumExeLogRepository;
import nts.uk.ctx.at.record.infra.entity.log.KrcdtEmpExecutionLog;

@Stateless
public class JpaEmpCalAndSumExeLogRepository extends JpaRepository implements EmpCalAndSumExeLogRepository {

	private final String SELECT_FROM_LOG = "SELECT c FROM KrcmtEmpExecutionLog c ";
	private final String SELECT_All_LOG = SELECT_FROM_LOG + " WHERE c.krcmtEmpExecutionLogPK.companyID = :companyID ";

	private final String SELECT_LOG_BY_CODE = SELECT_All_LOG
			+ " AND c.krcmtEmpExecutionLogPK.operationCaseID = :operationCaseID "
			+ " AND c.krcmtEmpExecutionLogPK.employeeID = :employeeID "
			+ " AND c.krcmtEmpExecutionLogPK.empCalAndSumExecLogID = :empCalAndSumExecLogID ";

	private final String SELECT_BY_EXECUTION_LOG = "SELECT el FROM KrcmtEmpExecutionLog empl JOIN KrcmtExecutionLog el "
			+ " ON empl.krcmtEmpExecutionLogPK.empCalAndSumExecLogID = el.krcmtExecutionLogPK.empCalAndSumExecLogID "
			+ " WHERE " + ".krcmtEmpExecutionLogPK.companyID = :companyID " + " AND el.executionContent = 0";

//	private final String SELECT_BY_LOG_ID = "SELECT c FROM KrcmtEmpExecutionLog c "
//			+ "JOIN KrcmtExecutionLog d ON c.krcmtEmpExecutionLogPK.companyID = d.krcmtExecutionLogPK.companyID "
//			+ "AND c.krcmtEmpExecutionLogPK.empCalAndSumExecLogID = d.krcmtExecutionLogPK.empCalAndSumExecLogID "
//			+ "AND c.krcmtEmpExecutionLogPK.caseSpecExeContentID = d.krcmtExecutionLogPK.caseSpecExeContentID "
//			+ "AND c.krcmtEmpExecutionLogPK.employeeID = d.krcmtExecutionLogPK.employeeID "
//			+ "WHERE d.krcmtExecutionLogPK.empCalAndSumExecLogID = :empCalAndSumExecLogID ";

	private final String UPDATE_LOG_INFO = "UPDATE KrcmtExecutionLog a" + " SET a.processStatus = 1"
			+ " WHERE a.krcmtExecutionLogPK.empCalAndSumExecLogID = empCalAndSumExecLogID"
			+ " AND a.executionContent = 0 ";

//	private final String SELECT_LOG_BY_DATE = SELECT_All_LOG + " AND c.executedDate >= :startDate"
//			+ " ON empl.krcmtEmpExecutionLogPK.empCalAndSumExecLogID = el.krcmtExecutionLogPK.empCalAndSumExecLogID " 
//			+ " WHERE "
//			+ ".krcmtEmpExecutionLogPK.companyID = :companyID "
//			+ " AND el.executionContent = 0";

	private final String SELECT_BY_LOG_ID = "SELECT c FROM KrcmtEmpExecutionLog c " 		
			+" WHERE c.krcmtEmpExecutionLogPK.empCalAndSumExecLogID = :empCalAndSumExecLogID " ;

	private final String SELECT_LOG_BY_DATE = SELECT_All_LOG
			+ " AND c.executedDate >= :startDate"
			+ " AND c.executedDate <= :endDate";

	/**
	 * get all EmpCalAndSumExeLog
	 */
	@Override
	public List<EmpCalAndSumExeLog> getAllEmpCalAndSumExeLog(String companyID) {
		List<EmpCalAndSumExeLog> data = this.queryProxy().query(SELECT_All_LOG, KrcdtEmpExecutionLog.class)
				.setParameter("companyID", companyID).getList(c -> c.toDomain());
		return data;
	}

	/**
	 * get EmpCalAndSumExeLog by code
	 */
	@Override
	public Optional<EmpCalAndSumExeLog> getEmpCalAndSumExeLogByID(String companyID, String empCalAndSumExecLogID,
			String operationCaseID, String employeeID) {
		Optional<EmpCalAndSumExeLog> data = this.queryProxy().query(SELECT_LOG_BY_CODE, KrcdtEmpExecutionLog.class)
				.setParameter("companyID", companyID).setParameter("operationCaseID", operationCaseID)
				.setParameter("employeeID", employeeID).setParameter("empCalAndSumExecLogID", empCalAndSumExecLogID)
				.getSingle(c -> c.toDomain());
		return data;
	}

	@Override
	public Optional<EmpCalAndSumExeLog> getListByExecutionContent(String empCalAndSumExecLogID, int executionContent) {
		Optional<EmpCalAndSumExeLog> optional = this.queryProxy()
				.query(SELECT_BY_EXECUTION_LOG, KrcdtEmpExecutionLog.class)
				.setParameter("empCalAndSumExecLogID", empCalAndSumExecLogID)
				.setParameter("executionContent", executionContent).getSingle(f -> f.toDomain());

		return optional;
	}

	@Override
	public void updateLogInfo(String empCalAndSumExecLogID) {
		this.getEntityManager().createQuery(UPDATE_LOG_INFO)
				.setParameter("empCalAndSumExecLogID", empCalAndSumExecLogID).executeUpdate();
	}

	/**
	 * Get EmpCalAndSumExeLog by empCalAndSumExecLogID
	 */

	@Override
	public List<EmpCalAndSumExeLog> getByEmpCalAndSumExecLogID(String empCalAndSumExecLogID) {
		List<EmpCalAndSumExeLog> data = this.queryProxy().query(SELECT_BY_LOG_ID, KrcdtEmpExecutionLog.class)
				.setParameter("empCalAndSumExecLogID", empCalAndSumExecLogID).getList(c -> c.toDomain());
		return data;
	}

	/**
	 * get all EmpCalAndSumExeLog by date
	 */
	@Override
	public List<EmpCalAndSumExeLog> getAllEmpCalAndSumExeLogByDate(String companyID, GeneralDate startDate,
			GeneralDate endDate) {
		List<EmpCalAndSumExeLog> data = this.queryProxy().query(SELECT_LOG_BY_DATE, KrcdtEmpExecutionLog.class)
				.setParameter("companyID", companyID).setParameter("startDate", startDate)
				.setParameter("endDate", endDate).getList(c -> c.toDomain());
		return data;
	}
}

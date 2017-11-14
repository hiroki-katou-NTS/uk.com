package nts.uk.ctx.at.record.infra.repository.log;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.log.EmpCalAndSumExeLog;
import nts.uk.ctx.at.record.dom.workrecord.log.EmpCalAndSumExeLogRepository;
import nts.uk.ctx.at.record.infra.entity.log.KrcdtEmpExecutionLog;

@Stateless
public class JpaEmpCalAndSumExeLogRepository extends JpaRepository implements EmpCalAndSumExeLogRepository  {
	
	private final String SELECT_FROM_LOG = "SELECT c FROM KrcdtEmpExecutionLog c ";
	
	//Get all log by companyID and EmployeeID and empCalAndSumExecLogID DESC
	private final String SELECT_All_LOG_BY_EMPLOYEEID = SELECT_FROM_LOG 
			+ " WHERE c.krcmtEmpExecutionLogPK.companyID = :companyID "
			+ " AND c.krcmtEmpExecutionLogPK.employeeID =: emmployeeID"
			+ " ORDER BY c.krcmtEmpExecutionLogPK.empCalAndSumExecLogID DESC";
	
	private final String SELECT_All_LOG = SELECT_FROM_LOG 
			+ " WHERE c.companyID = :companyID ";
	                    

	private final String SELECT_BY_EXECUTION_LOG = "SELECT el FROM KrcdtEmpExecutionLog empl JOIN KrcdtExecutionLog el "
			+ " ON empl.krcdtEmpExecutionLogPK.empCalAndSumExecLogID = el.krcdtExecutionLogPK.empCalAndSumExecLogID "
			+ " WHERE " + ".krcdtEmpExecutionLogPK.companyID = :companyID " + " AND el.executionContent = 0";

	private final String UPDATE_LOG_INFO = "UPDATE KrcdtExecutionLog a" + " SET a.processStatus = 1"
			+ " WHERE a.krcdtExecutionLogPK.empCalAndSumExecLogID = empCalAndSumExecLogID"
			+ " AND a.executionContent = 0 ";

	private final String SELECT_BY_LOG_ID = "SELECT c FROM KrcdtEmpExecutionLog c " 		
			+" WHERE c.krcdtEmpExecutionLogPK.empCalAndSumExecLogID = :empCalAndSumExecLogID " ;

	private final String SELECT_LOG_BY_DATE = SELECT_All_LOG
			+ " AND c.executedDate >= :startDate"
			+ " AND c.executedDate <= :endDate";
	
	@Override
	public Optional<EmpCalAndSumExeLog> getEmpCalAndSumExeLogMaxByEmp(String companyID, String employeeID) {
		List<EmpCalAndSumExeLog> data = this.queryProxy().query(SELECT_All_LOG_BY_EMPLOYEEID,KrcdtEmpExecutionLog.class)
				.setParameter("companyID", companyID)
				.setParameter("employeeID", employeeID)
				.getList(c -> c.toDomain());
		return !data.isEmpty() ? Optional.of(data.get(0)) : Optional.empty();
	}
	
	@Override
	public List<EmpCalAndSumExeLog> getAllEmpCalAndSumExeLog(String companyID ) {
		List<EmpCalAndSumExeLog> data = this.queryProxy().query(SELECT_All_LOG, KrcdtEmpExecutionLog.class)
				.setParameter("companyID", companyID).getList(c -> c.toDomain());
		return data;
	}

	@Override
	public Optional<EmpCalAndSumExeLog> getByExecutionContent(String empCalAndSumExecLogID, int executionContent) {
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

	@Override
	public Optional<EmpCalAndSumExeLog> getByEmpCalAndSumExecLogID(String empCalAndSumExecLogID) {
		Optional<EmpCalAndSumExeLog> data = this.queryProxy()
				.query(SELECT_BY_LOG_ID, KrcdtEmpExecutionLog.class)
				.setParameter("empCalAndSumExecLogID", empCalAndSumExecLogID).getSingle(c -> c.toDomain());
		return data;
	}

	@Override
	public List<EmpCalAndSumExeLog> getAllEmpCalAndSumExeLogByDate(String companyID, GeneralDate startDate, GeneralDate endDate) {
		List<EmpCalAndSumExeLog> data = this.queryProxy()
				.query(SELECT_LOG_BY_DATE, KrcdtEmpExecutionLog.class)
				.setParameter("companyID", companyID).setParameter("startDate", startDate)
				.setParameter("endDate", endDate).getList(c -> c.toDomain());
		return data;
	}

	@Override
	public void add(EmpCalAndSumExeLog empCalAndSumExeLog) {
		this.commandProxy().insert(KrcdtEmpExecutionLog.toEntity(empCalAndSumExeLog));
	}
}

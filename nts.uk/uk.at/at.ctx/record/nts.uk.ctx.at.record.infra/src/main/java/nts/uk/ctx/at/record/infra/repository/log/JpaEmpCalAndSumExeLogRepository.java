package nts.uk.ctx.at.record.infra.repository.log;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import lombok.SneakyThrows;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.DbConsts;
//import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
//import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.YearMonth;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.EmpCalAndSumExeLog;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.EmpCalAndSumExeLogRepository;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ExecutionLog;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.enums.CalAndAggClassification;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExeStateOfCalAndSum;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutedMenu;
import nts.uk.ctx.at.record.infra.entity.log.KrcdtExec;
import nts.uk.ctx.at.record.infra.entity.log.KrcdtExecLog;
import nts.uk.shr.infra.data.jdbc.JDBCUtil;

@Stateless
public class JpaEmpCalAndSumExeLogRepository extends JpaRepository implements EmpCalAndSumExeLogRepository {

	private static final String SELECT_FROM_LOG = "SELECT c FROM KrcdtExec c ";

	// Get all log by companyID and EmployeeID and empCalAndSumExecLogID DESC
	private static final String SELECT_All_LOG_BY_EMPLOYEEID = SELECT_FROM_LOG + " WHERE c.companyID = :companyID "
			+ " AND c.employeeID = :employeeID ORDER BY c.krcdtExecPK.empCalAndSumExecLogID DESC";

	private static final String SELECT_All_LOG = SELECT_FROM_LOG + " WHERE c.companyID = :companyID ";

	private static final String SELECT_BY_EXECUTION_LOG = "SELECT el FROM KrcdtExecLog el "
			+ " WHERE el.krcdtExecLogPK.empCalAndSumExecLogID = :empCalAndSumExecLogID AND el.krcdtExecLogPK.executionContent = :executionContent";

//	private static final String UPDATE_LOG_INFO = "UPDATE KrcdtExecLog a" 
//			+ " SET a.processStatus = :processStatus"
//			+ " WHERE a.krcdtExecLogPK.empCalAndSumExecLogID = :empCalAndSumExecLogID"
//			+ " AND a.krcdtExecLogPK.executionContent = :executionContent ";

	private static final String SELECT_BY_LOG_ID = "SELECT c FROM KrcdtExec c "
			+ " WHERE c.krcdtExecPK.empCalAndSumExecLogID = :empCalAndSumExecLogID ";

	private static final String SELECT_LOG_BY_DATE = SELECT_All_LOG + " AND c.executedDate >= :startDate"
			+ " AND c.executedDate <= :endDate";

	@Override
	public Optional<EmpCalAndSumExeLog> getEmpCalAndSumExeLogMaxByEmp(String companyID, String employeeID) {
		List<EmpCalAndSumExeLog> data = this.queryProxy()
				.query(SELECT_All_LOG_BY_EMPLOYEEID, KrcdtExec.class).setParameter("companyID", companyID)
				.setParameter("employeeID", employeeID).getList(c -> c.toDomain());
		return !data.isEmpty() ? Optional.of(data.get(0)) : Optional.empty();
	}

	@Override
	public List<EmpCalAndSumExeLog> getAllEmpCalAndSumExeLog(String companyID) {
		List<EmpCalAndSumExeLog> data = this.queryProxy().query(SELECT_All_LOG, KrcdtExec.class)
				.setParameter("companyID", companyID).getList(c -> c.toDomain());
		return data;
	}

	@Override
	public Optional<ExecutionLog> getByExecutionContent(String empCalAndSumExecLogID, int executionContent) {
		Optional<ExecutionLog> optional = this.queryProxy()
				.query(SELECT_BY_EXECUTION_LOG, KrcdtExecLog.class)
				.setParameter("empCalAndSumExecLogID", empCalAndSumExecLogID)
				.setParameter("executionContent", executionContent).getSingle(f -> f.toDomain());

		return optional;
	}

	@Override
	public void updateLogInfo(String empCalAndSumExecLogID, int executionContent, int processStatus) {
		Optional<KrcdtExecLog> krcdtExecLog = this.queryProxy()
				.query(SELECT_BY_EXECUTION_LOG, KrcdtExecLog.class)
				.setParameter("empCalAndSumExecLogID", empCalAndSumExecLogID)
				.setParameter("executionContent", executionContent).getSingle();
		if(krcdtExecLog.isPresent()){
			krcdtExecLog.get().processStatus = processStatus;
			
			this.commandProxy().update(krcdtExecLog.get());
			this.getEntityManager().flush();
			
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Optional<EmpCalAndSumExeLog> getByEmpCalAndSumExecLogID(String empCalAndSumExecLogID) {
		Optional<EmpCalAndSumExeLog> data = this.queryProxy().query(SELECT_BY_LOG_ID, KrcdtExec.class)
				.setParameter("empCalAndSumExecLogID", empCalAndSumExecLogID).getSingle(c -> c.toDomain());
		return data;
	}
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public boolean checkStopByID(String empCalAndSumExecLogID) {
		Optional<EmpCalAndSumExeLog> data = Optional.empty();
		String sql = "select EMP_EXECUTION_LOG_ID,EXECUTED_STATUS from KRCDT_EXEC"
				+ " where EMP_EXECUTION_LOG_ID = ?";
		try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
			stmt.setString(1, empCalAndSumExecLogID);
			data = new NtsResultSet(stmt.executeQuery()).getSingle(rec -> {
				EmpCalAndSumExeLog ent = new EmpCalAndSumExeLog(rec.getString("EMP_EXECUTION_LOG_ID"),rec.getInt("EXECUTED_STATUS"));
				return ent;
			});
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		if (!data.isPresent() || !data.get().getExecutionStatus().isPresent()
				|| (data.get().getExecutionStatus().get() != ExeStateOfCalAndSum.START_INTERRUPTION)) {
			return false;
		}
		return true;
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@SneakyThrows
	@Override
	public Optional<EmpCalAndSumExeLog> getByEmpCalAndSumExecLogIDByJDBC(String empCalAndSumExecLogID) {
		String sql = "SELECT * FROM KRCDT_EXEC WHERE EMP_EXECUTION_LOG_ID = ? ";
		try (val stmt = this.connection().prepareStatement(sql)){
			stmt.setString(1, empCalAndSumExecLogID);
			return new NtsResultSet(stmt.executeQuery()).getSingle(c -> {
				return new EmpCalAndSumExeLog(
						c.getString("EMP_EXECUTION_LOG_ID"), 
						c.getString("CID"),
						YearMonth.of(c.getInt("PROCESSING_MONTH")),
						EnumAdaptor.valueOf(c.getInt("EXECUTED_MENU"), ExecutedMenu.class) ,
						c.getGeneralDateTime("EXECUTED_DATE"),
						c.getInt("EXECUTED_STATUS") ==null?null: EnumAdaptor.valueOf(c.getInt("EXECUTED_STATUS"),ExeStateOfCalAndSum.class),
						c.getString("SID"),
						c.getInt("CLOSURE_ID"),
						c.getString("OPERATION_CASE_ID"),
						EnumAdaptor.valueOf(c.getInt("CAL_AGG_CLASS"),CalAndAggClassification.class)
						);
			});
		}
	}

	@Override
	public List<EmpCalAndSumExeLog> getAllEmpCalAndSumExeLogByDate(String companyID, GeneralDateTime startDate,
			GeneralDateTime endDate) {
		startDate = GeneralDateTime.ymdhms(startDate.year(), startDate.month(), startDate.day(), 00, 00, 00);
		endDate = GeneralDateTime.ymdhms(endDate.year(), endDate.month(), endDate.day(), 23, 59, 59);
		List<EmpCalAndSumExeLog> data = this.queryProxy().query(SELECT_LOG_BY_DATE, KrcdtExec.class)
				.setParameter("companyID", companyID).setParameter("startDate", startDate)
				.setParameter("endDate", endDate).getList(c -> c.toDomain());
		return data;
	}

	@Override
	public void add(EmpCalAndSumExeLog empCalAndSumExeLog) {
		this.commandProxy().insert(KrcdtExec.toEntity(empCalAndSumExeLog));
		this.getEntityManager().flush();
	}
	
	@Override
	public void updateStatus(String empCalAndSumExecLogID, int executionStatus) {
		try {
			Connection con = this.getEntityManager().unwrap(Connection.class);

			String updateTableSQL = " UPDATE KRCDT_EXEC SET EXECUTED_STATUS = "
					+ executionStatus + " WHERE EMP_EXECUTION_LOG_ID = '" +empCalAndSumExecLogID+"'";
			Statement statementU = con.createStatement();
			statementU.executeUpdate(JDBCUtil.toUpdateWithCommonField(updateTableSQL));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
//		KrcdtExec krcdtExec = this.queryProxy()
//				.query(SELECT_BY_LOG_ID, KrcdtExec.class)
//				.setParameter("empCalAndSumExecLogID", empCalAndSumExecLogID).getSingle().get();
//		krcdtExec.executedStatus = executionStatus;
//		this.commandProxy().update(krcdtExec);
	}
}

package nts.uk.ctx.at.function.infra.repository.processexecution;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.EndStatus;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ExecutionTaskLog;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionTask;
import nts.uk.ctx.at.function.dom.processexecution.repository.ExecutionTaskLogRepository;
import nts.uk.ctx.at.function.infra.entity.processexecution.KfnmtExecutionTaskLog;
import nts.uk.ctx.at.function.infra.entity.processexecution.KfnmtExecutionTaskLogPK;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.jdbc.JDBCUtil;

//@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public class JpaExecutionTaskLogRepository extends JpaRepository implements ExecutionTaskLogRepository {
	/**
	 * Query strings
	 */
	private static final String SELECT_ALL = "SELECT etl FROM KfnmtExecutionTaskLog etl ";
	private static final String SELECT_LIST = SELECT_ALL + "WHERE etl.kfnmtExecTaskLogPK.companyId = :companyId "
			+ "AND etl.kfnmtExecTaskLogPK.execItemCd = :execItemCd " + "AND etl.kfnmtExecTaskLogPK.execId = :execId ";

	@Override
	public List<ExecutionTaskLog> getAllByCidExecCdExecId(String companyId, String execItemCd, String execId) {
//		String SELECT_LIST = "SELECT * FROM KFNMT_EXEC_TASK_LOG WHERE CID =? AND EXEC_ITEM_CD = ? AND EXEC_ID = ? ";
//		try (PreparedStatement statement = this.connection().prepareStatement(SELECT_LIST)) {
//			statement.setString(1, companyId);
//			statement.setString(2, execItemCd);
//			statement.setString(3, execId);
//			return new NtsResultSet(statement.executeQuery()).getList(rs -> ExecutionTaskLog.builder()
//					.procExecTask(EnumAdaptor.valueOf(rs.getInt("TASK_ID"), ProcessExecutionTask.class))
//					.status(Optional.ofNullable(rs.getInt("STATUS"))
//							.map(data -> EnumAdaptor.valueOf(data, EndStatus.class)))
//					.lastExecDateTime(Optional.ofNullable(rs.getGeneralDateTime("LAST_EXEC_DATETIME")))
//					.lastEndExecDateTime(Optional.ofNullable(rs.getGeneralDateTime("LAST_END_EXEC_DATETIME")))
//					.errorSystem(Optional
//							.ofNullable(rs.getString("ERROR_SYSTEM") == null ? null : rs.getInt("ERROR_SYSTEM") == 1))
//					.errorBusiness(Optional.ofNullable(
//							rs.getString("ERROR_BUSINESS") == null ? null : rs.getInt("ERROR_BUSINESS") == 1))
//					.systemErrorDetails(Optional.ofNullable(rs.getString("ERROR_SYSTEM_CONT"))).build());
//		} catch (SQLException e) {
//			throw new RuntimeException(e);
//		}
		return this.queryProxy().query(SELECT_LIST, KfnmtExecutionTaskLog.class)
				.setParameter("companyId", companyId)
				.setParameter("execItemCd", execItemCd)
				.setParameter("execId", execId)
				.getList(entity -> ExecutionTaskLog.builder()
						.execId(execId)
						.errorBusiness(Optional.ofNullable(entity.errorBusiness).map(data -> data == 1))
						.errorSystem(Optional.ofNullable(entity.errorSystem).map(data -> data == 1))
						.lastEndExecDateTime(Optional.ofNullable(entity.lastEndExecDateTime))
						.lastExecDateTime(Optional.ofNullable(entity.lastExecDateTime))
						.procExecTask(EnumAdaptor.valueOf(entity.kfnmtExecTaskLogPK.taskId, ProcessExecutionTask.class))
						.status(Optional.ofNullable(entity.status).map(data -> EnumAdaptor.valueOf(data, EndStatus.class)))
						.systemErrorDetails(Optional.ofNullable(entity.errorSystemDetail)).build());
	}

	/**
	 * insert task log list
	 */
	@Override
	public void insertAll(String companyId, String execItemCd, String execId, List<ExecutionTaskLog> taskLogList) {
//		try {
//			for (ExecutionTaskLog executionTaskLog : taskLogList) {
//				String updateTableSQL = " INSERT INTO KFNMT_EXEC_TASK_LOG "
//						+ " ( CID = ?,EXEC_ITEM_CD = ?,EXEC_ID = ?,TASK_ID = ?,STATUS = ?)";
//				try (PreparedStatement ps = this.connection()
//						.prepareStatement(JDBCUtil.toInsertWithCommonField(updateTableSQL))) {
//					ps.setString(1, companyId);
//					ps.setString(2, execItemCd);
//					ps.setString(3, execId);
//					ps.setInt(4, executionTaskLog.getProcExecTask().value);
//					ps.setString(5,
//							(executionTaskLog.getStatus() != null && executionTaskLog.getStatus().isPresent())
//									? String.valueOf(executionTaskLog.getStatus().get().value)
//									: null);
//					ps.executeUpdate();
//				}
//			}
//
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
		this.commandProxy()
				.insertAll(taskLogList.stream()
						.map(item -> KfnmtExecutionTaskLog.builder()
								.contractCode(AppContexts.user().contractCode())
								.kfnmtExecTaskLogPK(new KfnmtExecutionTaskLogPK(companyId, execItemCd, execId, item.getProcExecTask().value))
								.status((item.getStatus() != null && item.getStatus().isPresent())
										? item.getStatus().get().value
										: null)
								.build())
						.collect(Collectors.toList()));
	}

	/**
	 * update task log list
	 */
	@Override
	public void updateAll(String companyId, String execItemCd, String execId, List<ExecutionTaskLog> taskLogList) {
		try {
			for (ExecutionTaskLog executionTaskLog : taskLogList) {
				String updateTableSQL = " UPDATE KFNMT_EXEC_TASK_LOG SET" + " STATUS = ?"
						+ " WHERE CID = ? AND EXEC_ITEM_CD = ? AND EXEC_ID = ? AND TASK_ID = ? ";
				try (PreparedStatement ps = this.connection()
						.prepareStatement(JDBCUtil.toUpdateWithCommonField(updateTableSQL))) {
					ps.setString(1,
							(executionTaskLog.getStatus() != null && executionTaskLog.getStatus().isPresent())
									? String.valueOf(executionTaskLog.getStatus().get().value)
									: null);
					ps.setString(2, companyId);
					ps.setString(3, execItemCd);
					ps.setString(4, execId);
					ps.setInt(5, executionTaskLog.getProcExecTask().value);
					ps.executeUpdate();
				}
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void removeAll(String companyId, String execItemCd, String execId, List<ExecutionTaskLog> taskLogList) {
		List<Integer> taskLogIds = this.getAllByCidExecCdExecId(companyId, execItemCd, execId).stream()
				.map(data -> data.getProcExecTask().value).collect(Collectors.toList());
		taskLogList = taskLogList.stream().filter(data -> taskLogIds.contains(data.getProcExecTask().value))
				.collect(Collectors.toList());
//		this.commandProxy().removeAll(KfnmtExecutionTaskLog.class, taskLogList.stream()
//				.map(data -> new KfnmtExecutionTaskLogPK(companyId, execItemCd, execId, data.getProcExecTask().value))
//				.collect(Collectors.toList()));
		taskLogList.forEach(data -> this.commandProxy().remove(KfnmtExecutionTaskLog.class,
				new KfnmtExecutionTaskLogPK(companyId, execItemCd, execId, data.getProcExecTask().value)));
	}
}

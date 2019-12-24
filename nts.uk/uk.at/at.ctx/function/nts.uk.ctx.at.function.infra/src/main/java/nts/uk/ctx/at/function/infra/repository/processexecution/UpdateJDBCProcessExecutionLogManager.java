package nts.uk.ctx.at.function.infra.repository.processexecution;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.uk.ctx.at.function.dom.processexecution.ExecutionCode;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.CurrentExecutionStatus;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.EndStatus;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.OverallErrorDetail;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLogManage;
import nts.uk.shr.infra.data.jdbc.JDBCUtil;

@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Stateless
public class UpdateJDBCProcessExecutionLogManager extends JpaRepository {
	public void updateProcessExecutionLogManager (ProcessExecutionLogManage domain) {
		try {
			String updateTableSQL = " UPDATE KFNMT_PRO_EXE_LOG_MANAGE SET"
					+ " CURRENT_STATUS = ?,OVERALL_STATUS = ?,ERROR_DETAIL = ?, LAST_EXEC_DATETIME = ?, LAST_EXEC_DATETIME_EX = ?"
					+ " WHERE CID = ? AND EXEC_ITEM_CD = ? ";
			try (PreparedStatement ps = this.connection().prepareStatement(JDBCUtil.toUpdateWithCommonField(updateTableSQL))) {
				ps.setString(1, domain.getCurrentStatus() == null?null:String.valueOf(domain.getCurrentStatus().value));
				ps.setString(2, domain.getOverallStatus().isPresent()?String.valueOf(domain.getOverallStatus().get().value):null);
				ps.setString(3, domain.getOverallError() == null?null:String.valueOf(domain.getOverallError().value));
				ps.setString(4, domain.getLastExecDateTime() ==null?null:domain.getLastExecDateTime().toString());
				ps.setString(5, domain.getLastExecDateTimeEx()==null?null:domain.getLastExecDateTimeEx().toString());
				ps.setString(6, domain.getCompanyId());
				ps.setString(7, domain.getExecItemCd().v());
				ps.executeUpdate();
			}
			
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public Optional<ProcessExecutionLogManage> getLogByCIdAndExecCd(String companyId, String execItemCd) {
		try {
			String updateTableSQL = " SELECT * FROM KFNMT_PRO_EXE_LOG_MANAGE "
					+ " WHERE CID = ? AND EXEC_ITEM_CD = ? ";
			try (PreparedStatement statement = this.connection().prepareStatement(updateTableSQL)) {
				statement.setString(1, companyId);
				statement.setString(2, execItemCd);
				return new NtsResultSet(statement.executeQuery()).getSingle(rs -> {
					return new ProcessExecutionLogManage(
							new ExecutionCode(rs.getString("EXEC_ITEM_CD")), 
							rs.getString("CID"),
							rs.getString("ERROR_DETAIL") == null?null: EnumAdaptor.valueOf(rs.getInt("ERROR_DETAIL"),OverallErrorDetail.class),
							rs.getString("OVERALL_STATUS") == null?Optional.empty() : Optional.of(EnumAdaptor.valueOf(rs.getInt("OVERALL_STATUS"),EndStatus.class)),
							rs.getGeneralDateTime("LAST_EXEC_DATETIME"),
							rs.getString("CURRENT_STATUS") == null?null: EnumAdaptor.valueOf(rs.getInt("CURRENT_STATUS"),CurrentExecutionStatus.class),
							rs.getGeneralDateTime("LAST_EXEC_DATETIME_EX")
							);
				});
			}catch (SQLException e) {
				throw new RuntimeException(e);
			}
			
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

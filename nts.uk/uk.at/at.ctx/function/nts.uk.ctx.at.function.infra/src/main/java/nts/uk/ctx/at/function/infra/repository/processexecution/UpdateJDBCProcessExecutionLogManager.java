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
import nts.arc.time.GeneralDateTime;
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
					+ ", LAST_END_EXEC_DATETIME = ?, ERROR_SYSTEM = ?, ERROR_BUSINESS = ?"
					+ " WHERE CID = ? AND EXEC_ITEM_CD = ? ";
			try (PreparedStatement ps = this.connection().prepareStatement(JDBCUtil.toUpdateWithCommonField(updateTableSQL))) {			
				ps.setString(1, domain.getCurrentStatus().map(item -> String.valueOf(item.value)).orElse(null));
				ps.setString(2, domain.getOverallStatus().map(item -> String.valueOf(item.value)).orElse(null));
				ps.setString(3, domain.getOverallError().map(item -> String.valueOf(item.value)).orElse(null));
				ps.setString(4, domain.getLastExecDateTime().map(GeneralDateTime::toString).orElse(null));
				ps.setString(5, domain.getLastExecDateTimeEx().map(GeneralDateTime::toString).orElse(null));
				ps.setString(6, domain.getLastEndExecDateTime().map(GeneralDateTime::toString).orElse(null));
				ps.setString(7, domain.getErrorSystem().map(item -> item ? "1" : "0").orElse(null));
				ps.setString(8, domain.getErrorBusiness().map(item -> item ? "1" : "0").orElse(null));
				ps.setString(9, domain.getCompanyId());
				ps.setString(10, domain.getExecItemCd().v());
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
							Optional.ofNullable(rs.getInt("ERROR_DETAIL")).map(item -> EnumAdaptor.valueOf(item, OverallErrorDetail.class)),
							Optional.ofNullable(rs.getInt("OVERALL_STATUS")).map(item -> EnumAdaptor.valueOf(item, EndStatus.class)),
							Optional.ofNullable(rs.getGeneralDateTime("LAST_EXEC_DATETIME")),
							Optional.ofNullable(rs.getInt("CURRENT_STATUS")).map(item -> EnumAdaptor.valueOf(item, CurrentExecutionStatus.class)),
							Optional.ofNullable(rs.getGeneralDateTime("LAST_EXEC_DATETIME_EX")),
							Optional.ofNullable(rs.getGeneralDateTime("LAST_END_EXEC_DATETIME")),
							Optional.ofNullable(rs.getInt("ERROR_SYSTEM")).map(item -> item == 1),
							Optional.ofNullable(rs.getInt("ERROR_BUSINESS")).map(item -> item == 1)
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

package nts.uk.ctx.at.record.infra.repository.workrecord.actualsituation.createapproval.monthlyperformance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.monthlyperformance.AppInterrupMon;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.monthlyperformance.AppInterrupMonRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.actualsituation.createapproval.monthlyperformance.KrcmtAppInterrupMon;
import nts.uk.shr.infra.data.jdbc.JDBCUtil;

@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public class JpaAppInterrupMonRepository extends JpaRepository implements AppInterrupMonRepository {
	@Inject
	private AddAppInterrupMonth addAppInterrupMonth;
	
	private static final String SELECT_ALL_APP_INTERRUP_MON = " SELECT c FROM KrcmtAppInterrupMon c ";
	private static final String SELECT_APP_INTERRUP_MON_BY_ID = SELECT_ALL_APP_INTERRUP_MON 
			+ " WHERE c.executionId = :executionId";
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public Optional<AppInterrupMon> getAppInterrupMonByID(String executionId) {
//		Optional<AppInterrupMon> data = this.queryProxy().query(SELECT_APP_INTERRUP_MON_BY_ID,KrcmtAppInterrupMon.class)
//				.setParameter("executionId", executionId)
//				.getSingle(c->c.toDomain());
//		return data;
		String SELECT_APP_INTERRUP_MON_BY_ID_JDBC = "SELECT * FROM KRCMT_APP_INTERRUP_MON WHERE EXECUTION_ID = ? ";
		try (PreparedStatement statement = this.connection().prepareStatement(SELECT_APP_INTERRUP_MON_BY_ID_JDBC)) {
			statement.setString(1, executionId);
			return new NtsResultSet(statement.executeQuery()).getSingle(rs -> {
				return new AppInterrupMon(rs.getString("EXECUTION_ID"), rs.getBoolean("SUSPENDED_STATE"));
			});

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public void addAppInterrupMon(AppInterrupMon appInterrupMon) {
		addAppInterrupMonth.addAppInterrupMon(appInterrupMon);
//		this.commandProxy().insert(KrcmtAppInterrupMon.toEntity(appInterrupMon));
//		this.getEntityManager().flush();
	}
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public void updateAppInterrupMon(AppInterrupMon appInterrupMon) {
//		KrcmtAppInterrupMon newEntity = KrcmtAppInterrupMon.toEntity(appInterrupMon);
//		KrcmtAppInterrupMon updateEntity = this.queryProxy().find(newEntity.executionId, KrcmtAppInterrupMon.class).get();
//		updateEntity.suspendedState = newEntity.suspendedState;
//		this.commandProxy().update(updateEntity);
		try {
			Connection con = this.getEntityManager().unwrap(Connection.class);

			String updateTableSQL = " UPDATE KRCMT_APP_INTERRUP_MON SET SUSPENDED_STATE = "
					+ (appInterrupMon.isSuspendedState()?1:0) + " WHERE EXECUTION_ID = '" +appInterrupMon.getExecutionId()+"'";
			Statement statementU = con.createStatement();
			statementU.executeUpdate(JDBCUtil.toUpdateWithCommonField(updateTableSQL));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public void deleteAppInterrupMon(String executionId) {
		Optional<AppInterrupMon> newEntity = this.queryProxy().query(SELECT_APP_INTERRUP_MON_BY_ID,KrcmtAppInterrupMon.class)
				.setParameter("executionId", executionId)
				.getSingle(c->c.toDomain());
		this.commandProxy().remove(newEntity);
	}

}

package nts.uk.ctx.at.record.infra.repository.workrecord.actualsituation.createapproval.dailyperformance;

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
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.dailyperformance.AppInterrupDaily;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.dailyperformance.AppInterrupDailyRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.actualsituation.createapproval.dailyperformance.KrcmtAppInterrupDaily;
import nts.uk.shr.infra.data.jdbc.JDBCUtil;

@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public class JpaAppInterrupDailyRepository extends JpaRepository implements AppInterrupDailyRepository {
	@Inject 
	private AddAppInterrupDaily addAppInterrupDaily;
	private static final String SELECT_ALL_APP_INTERRUP_DAILY = " SELECT c FROM KrcmtAppInterrupDaily c ";
	private static final String SELECT_APP_INTERRUP_DAILY_BY_ID = SELECT_ALL_APP_INTERRUP_DAILY 
			+ " WHERE c.executionId = :executionId";
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public Optional<AppInterrupDaily> getAppInterrupDailyByID(String executionId) {
		return addAppInterrupDaily.getAppInterrupDailyByID(executionId);
//		Optional<AppInterrupDaily> data = this.queryProxy().query(SELECT_APP_INTERRUP_DAILY_BY_ID,KrcmtAppInterrupDaily.class)
//				.setParameter("executionId", executionId)
//				.getSingle(c->c.toDomain());
//		return data;
	}
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public void addAppInterrupDaily(AppInterrupDaily appInterrupDaily) {
		addAppInterrupDaily.addAppInterrupDaily(appInterrupDaily);
	}
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public void updateAppInterrupDaily(AppInterrupDaily appInterrupDaily) {
//		KrcmtAppInterrupDaily newEntity = KrcmtAppInterrupDaily.toEntity(appInterrupDaily);
//		KrcmtAppInterrupDaily updateEntity = this.queryProxy().find(newEntity.executionId, KrcmtAppInterrupDaily.class).get();
//		updateEntity.suspendedState = newEntity.suspendedState;
//		this.commandProxy().update(updateEntity);
		
		try {
			Connection con = this.getEntityManager().unwrap(Connection.class);

			String updateTableSQL = " UPDATE KRCMT_APP_INTERRUP_DAILY SET SUSPENDED_STATE = "
					+ (appInterrupDaily.isSuspendedState()?1:0) + " WHERE EXECUTION_ID = '" +appInterrupDaily.getExecutionId()+"'";
			Statement statementU = con.createStatement();
			statementU.executeUpdate(JDBCUtil.toUpdateWithCommonField(updateTableSQL));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public void deleteAppInterrupDaily(String executionId) {
		Optional<AppInterrupDaily> newEntity = this.queryProxy().query(SELECT_APP_INTERRUP_DAILY_BY_ID,KrcmtAppInterrupDaily.class)
				.setParameter("executionId", executionId)
				.getSingle(c->c.toDomain());
		this.commandProxy().remove(newEntity);
	}

}

package nts.uk.ctx.at.record.infra.repository.workrecord.actualsituation.createapproval.dailyperformance;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.dailyperformance.AppInterrupDaily;
import nts.uk.ctx.at.record.infra.entity.workrecord.actualsituation.createapproval.dailyperformance.KrcmtAppInterrupDaily;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class AddAppInterrupDaily extends JpaRepository{
	
	public void addAppInterrupDaily(AppInterrupDaily appInterrupDaily) {
		this.commandProxy().insert(KrcmtAppInterrupDaily.toEntity(appInterrupDaily));
	}
	
	public Optional<AppInterrupDaily> getAppInterrupDailyByID(String executionId) {
		String SEL_SELECT_APP_INTERRUP_DAILY_BY_ID_JDBC = "SELECT * FROM KRCMT_APP_INTERRUP_DAILY WHERE EXECUTION_ID = ? ";
		try (PreparedStatement statement = this.connection().prepareStatement(SEL_SELECT_APP_INTERRUP_DAILY_BY_ID_JDBC)) {
			statement.setString(1, executionId);
			return new NtsResultSet(statement.executeQuery()).getSingle(rs -> {
				return new AppInterrupDaily(rs.getString("EXECUTION_ID"), rs.getBoolean("SUSPENDED_STATE"));
			});

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}

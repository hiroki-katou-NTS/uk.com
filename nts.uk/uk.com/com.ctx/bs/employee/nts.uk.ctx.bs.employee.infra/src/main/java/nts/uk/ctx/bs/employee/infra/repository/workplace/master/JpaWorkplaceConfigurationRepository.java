package nts.uk.ctx.bs.employee.infra.repository.workplace.master;

import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.bs.employee.dom.workplace.master.WorkplaceConfiguration;
import nts.uk.ctx.bs.employee.dom.workplace.master.WorkplaceConfigurationRepository;
import nts.uk.ctx.bs.employee.infra.entity.workplace.master.BsymtWorkplaceConfig;
import nts.uk.ctx.bs.employee.infra.entity.workplace.master.BsymtWorkplaceConfigPk;
import nts.uk.shr.com.history.DateHistoryItem;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class JpaWorkplaceConfigurationRepository extends JpaRepository implements WorkplaceConfigurationRepository {

	private static final String FIND_BY_DATE;
	
	static {
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT CID, WKP_HIST_ID, START_DATE, END_DATE FROM BSYMT_WKP_CONFIG_2 ");
		builder.append("WHERE CID = 'companyID' AND START_DATE <= 'date' AND END_DATE >= 'date'");
		FIND_BY_DATE = builder.toString();
	}
	
	@Override
	public Optional<WorkplaceConfiguration> getWkpConfig(String companyId) {
		String query = "SELECT c FROM BsymtWorkplaceConfig c WHERE c.pk.companyId = :companyId";
		List<BsymtWorkplaceConfig> listEntities = this.queryProxy().query(query, BsymtWorkplaceConfig.class)
				.setParameter("companyId", companyId).getList();
		return Optional.ofNullable(BsymtWorkplaceConfig.toDomain(listEntities));
	}

	@Override
	public void addWorkplaceConfig(WorkplaceConfiguration workplaceConfig) {
		this.commandProxy().insertAll(BsymtWorkplaceConfig.fromDomain(workplaceConfig));
	}

	@Override
	public void updateWorkplaceConfig(WorkplaceConfiguration workplaceConfig) {
		this.commandProxy().updateAll(BsymtWorkplaceConfig.fromDomain(workplaceConfig));
	}

	@Override
	public void deleteWorkplaceConfig(String companyId, String workplaceHistoryId) {
		this.commandProxy().remove(BsymtWorkplaceConfig.class,
				new BsymtWorkplaceConfigPk(companyId, workplaceHistoryId));
	}

	@Override
	public Optional<WorkplaceConfiguration> findByDate(String companyID, GeneralDate date) {
		String sql = FIND_BY_DATE;
		sql.replaceAll("date", date.toString("yyyy-MM-dd"));
		sql.replaceAll("companyID", companyID);
		try (PreparedStatement pstatement = this.connection().prepareStatement(sql)) {
			return new NtsResultSet(pstatement.executeQuery())
			.getSingle(x -> new WorkplaceConfiguration(
					x.getString("CID"), 
					Arrays.asList(new DateHistoryItem(
							x.getString("WKP_HIST_ID"), 
							new DatePeriod(
									x.getGeneralDate("START_DATE"), 
									x.getGeneralDate("END_DATE"))))));
		} catch (Exception e) {
			throw new RuntimeException("setting error: workplace config");
		}
	}

}

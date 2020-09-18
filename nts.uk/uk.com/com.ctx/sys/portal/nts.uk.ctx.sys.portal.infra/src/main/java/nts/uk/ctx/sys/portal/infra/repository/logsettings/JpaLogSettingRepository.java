package nts.uk.ctx.sys.portal.infra.repository.logsettings;

import java.util.List;
import javax.ejb.Stateless;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.portal.dom.logsettings.LogSetting;
import nts.uk.ctx.sys.portal.dom.logsettings.LogSettingRepository;
import nts.uk.ctx.sys.portal.infra.entity.logsettings.SrcdtLogSetting;
import nts.uk.ctx.sys.portal.infra.entity.logsettings.SrcdtLogSettingPK;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaLogSettingRepository extends JpaRepository implements LogSettingRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT s FROM SrcdtLogSetting s ";
	private static final String SELECT_BY_SYSTEM_TYPE = SELECT_ALL_QUERY_STRING
			+ "WHERE s.srcdtLogSettingPK.system = :systemType " + "AND s.srcdtLogSettingPK.cid = :cid";
	private static final String DELETE_BY_SYSTEM = "DELETE FROM SrcdtLogSetting s "
			+ "WHERE s.srcdtLogSettingPK.system = :systemType " + "AND s.srcdtLogSettingPK.cid = :cid";

	@Override
	public List<LogSetting> findBySystem(String companyId, int systemType) {
		return this.queryProxy().query(SELECT_BY_SYSTEM_TYPE, SrcdtLogSetting.class)
				.setParameter("systemType", systemType).setParameter("cid", companyId)
				.getList(LogSetting::createFromMemento);
	}

	@Override
	public void deleteLogSetting(String companyId, int systemType) {
		this.getEntityManager().createQuery(DELETE_BY_SYSTEM, SrcdtLogSetting.class)
				.setParameter("systemType", systemType).setParameter("cid", companyId).executeUpdate();
	}

	@Override
	public void addLogSetting(LogSetting logSetting) {
		this.commandProxy().insert(insertToEntity(logSetting));
	}

	public SrcdtLogSetting insertToEntity(LogSetting domain) {
		SrcdtLogSettingPK srcdtLogSettingPK = new SrcdtLogSettingPK(domain.getCompanyId(), domain.getSystem(),
				domain.getProgramId());
		SrcdtLogSetting srcdtLogSetting = new SrcdtLogSetting();
		srcdtLogSetting.srcdtLogSettingPK = srcdtLogSettingPK;
		srcdtLogSetting.menuClassification = domain.getMenuClassification().value;
		srcdtLogSetting.loginLogUseAtr = domain.getLoginHistoryRecord().value;
		srcdtLogSetting.startupLogUseAtr = domain.getBootHistoryRecord().value;
		srcdtLogSetting.updateLogUseAtr = domain.getEditHistoryRecord().value;
		srcdtLogSetting.contractCd = AppContexts.user().contractCode();
		return srcdtLogSetting;
	}

}

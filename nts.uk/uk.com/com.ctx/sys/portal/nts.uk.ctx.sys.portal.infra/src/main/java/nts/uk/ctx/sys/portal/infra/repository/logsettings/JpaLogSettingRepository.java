package nts.uk.ctx.sys.portal.infra.repository.logsettings;

import java.util.List;
import javax.ejb.Stateless;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.portal.dom.logsettings.LogSetting;
import nts.uk.ctx.sys.portal.dom.logsettings.LogSettingRepository;
import nts.uk.ctx.sys.portal.infra.entity.logsettings.SrcdtLogSetting;

@Stateless
public class JpaLogSettingRepository extends JpaRepository implements LogSettingRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT s FROM SrcdtLogSetting s ";
	private static final String SELECT_BY_SYSTEM_TYPE = SELECT_ALL_QUERY_STRING
			+ "WHERE s.srcdtLogSettingPK.system = :systemType "
			+ "AND s.srcdtLogSettingPK.cid = :cid";

	@Override
	public List<LogSetting> findBySystem(String companyId, int systemType) {
		return this.queryProxy().query(SELECT_BY_SYSTEM_TYPE, SrcdtLogSetting.class)
				.setParameter("systemType", systemType)
				.setParameter("cid", companyId)
				.getList(LogSetting::createFromMemento);
	}

}

package nts.uk.ctx.hr.notice.infra.repository.report.registration.person;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.hr.notice.dom.report.registration.person.ReportStartSetting;
import nts.uk.ctx.hr.notice.dom.report.registration.person.ReportStartSettingRepository;
import nts.uk.ctx.hr.notice.infra.entity.report.registration.person.JhndtStartSetting;

@Stateless
public class JpaReportStartSettingRepository extends JpaRepository implements ReportStartSettingRepository {

	@Override
	public Optional<ReportStartSetting> getReportStartSettingByCid(String cid) {
		Optional<JhndtStartSetting> startSetting = this.queryProxy().find(cid, JhndtStartSetting.class);
		if (startSetting.isPresent()) {
			return Optional.ofNullable(startSetting.get().toDomain());
		}
		return Optional.empty();
	}

}

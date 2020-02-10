package nts.uk.ctx.hr.notice.dom.report.registration.person;

import java.util.Optional;

public interface ReportStartSettingRepository {
	
	Optional<ReportStartSetting> getReportStartSettingByCid(String cid);

}

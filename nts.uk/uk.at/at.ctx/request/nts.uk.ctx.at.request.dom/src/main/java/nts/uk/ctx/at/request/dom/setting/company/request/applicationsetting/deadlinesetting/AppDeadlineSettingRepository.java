package nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.deadlinesetting;

import java.util.Optional;

public interface AppDeadlineSettingRepository {
	/**
	 * get deadline setting by closureId
	 * @param companyId	
	 * @param closureId 締めＩＤ
	 * @return
	 */
	Optional<AppDeadlineSetting> getDeadlineByClosureId(String companyId, int closureId);
}

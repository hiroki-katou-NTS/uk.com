package nts.uk.ctx.at.request.dom.application.common.service.setting;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;

public interface PreBeforeApplicationService {
	
	void copyEmploymentSettingNew(String companyId, Optional<AppEmploymentSetting> sourceData1,
			List<String> targetEmploymentCodes, boolean overide);
}

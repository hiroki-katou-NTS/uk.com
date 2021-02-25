package nts.uk.ctx.at.request.dom.application.common.service.setting;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSet;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;

public interface PreBeforeApplicationService {
	
	void copyEmploymentSettingNew(String companyId, Optional<AppEmploymentSetting> sourceData1,
			List<String> targetEmploymentCodes, boolean overide);

	// refactor 4
	void copyAppEmploymentSet_New(String companyId, Optional<AppEmploymentSet> sourceData1, List<String> targetEmploymentCodes);
}

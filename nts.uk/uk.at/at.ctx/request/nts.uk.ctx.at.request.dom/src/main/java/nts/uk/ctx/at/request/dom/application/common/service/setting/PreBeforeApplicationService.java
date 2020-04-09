package nts.uk.ctx.at.request.dom.application.common.service.setting;

import java.util.List;

import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;

public interface PreBeforeApplicationService {
	
//	void copyEmploymentSetting(String companyId, List<AppEmploymentSetting> sourceData, List<String> targetEmploymentCodes, boolean isOveride);

	void copyEmploymentSettingNew(String companyId, List<AppEmploymentSetting> sourceData1,
			List<String> targetEmploymentCodes, boolean overide);
}

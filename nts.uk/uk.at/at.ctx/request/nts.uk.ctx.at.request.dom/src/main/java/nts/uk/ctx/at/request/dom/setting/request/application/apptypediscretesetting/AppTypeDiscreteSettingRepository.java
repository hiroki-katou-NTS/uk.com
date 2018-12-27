package nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting;

import java.util.Optional;

/*import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSetting;*/

public interface AppTypeDiscreteSettingRepository {
	/**
	 * @param companyId
	 * @param appType
	 * @return
	 */
	public Optional<AppTypeDiscreteSetting> getAppTypeDiscreteSettingByAppType(String companyID, int appType);
}

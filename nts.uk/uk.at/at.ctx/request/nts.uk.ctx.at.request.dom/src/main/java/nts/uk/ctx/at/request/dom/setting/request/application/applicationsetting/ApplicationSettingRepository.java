package nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting;

import java.util.List;
import java.util.Optional;

public interface ApplicationSettingRepository {

	/**
	 * @param companyId
	 * @param appType
	 * @return
	 */
	public Optional<ApplicationSetting> getApplicationSettingByComID(String companyID);

	/**
	 * @param applicationSetting
	 */
	void updateSingle(ApplicationSetting applicationSetting);

	/**
	 * @param lstApplicationSetting
	 */
	void updateList(List<ApplicationSetting> lstApplicationSetting);

}

package nts.uk.ctx.at.request.dom.setting.request.application;

import java.util.List;
import java.util.Optional;

public interface ApplicationSettingRepository {

	/**
	 * @param companyId
	 * @param appType
	 * @return
	 */
	public Optional<ApplicationSetting> getApplicationSettingByAppType(String companyID, int appType);

	/**
	 * @param companyID
	 * @return
	 */
	public List<ApplicationSetting> getApplicationSettingByCompany(String companyID);

	/**
	 * @param applicationSetting
	 */
	void updateSingle(ApplicationSetting applicationSetting);

	/**
	 * @param lstApplicationSetting
	 */
	void updateList(List<ApplicationSetting> lstApplicationSetting);

}

package nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon;

import java.util.Optional;

public interface GoBackDirectlyCommonSettingRepository {
	/***/
	/**
	 * @param companyID
	 * @param appID
	 * @return
	 */
	public Optional<GoBackDirectlyCommonSetting> findByAppID(String companyID, String appID);
	/**
	 * @param goBackDirectlyCommonSettingItem
	 */
	public void insert(GoBackDirectlyCommonSetting goBackDirectlyCommonSettingItem);
	
	/**
	 * @param goBackDirectlyCommonSettingItem
	 */
	public void update(GoBackDirectlyCommonSetting goBackDirectlyCommonSettingItem);
	
	/**
	 * @param goBackDirectlyCommonSettingItem
	 */
	public void delete(GoBackDirectlyCommonSetting goBackDirectlyCommonSettingItem);
}

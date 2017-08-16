package nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon;

import java.util.Optional;

public interface GoBackDirectlyCommonSettingRepository {
	/***/
	/**
	 * @param companyID
	 * @param appID
	 * @return
	 */
	public Optional<GoBackDirectlyCommonSettingItem> findByAppID(String companyID, String appID);
	/**
	 * @param goBackDirectlyCommonSettingItem
	 */
	public void insert(GoBackDirectlyCommonSettingItem goBackDirectlyCommonSettingItem);
	
	/**
	 * @param goBackDirectlyCommonSettingItem
	 */
	public void update(GoBackDirectlyCommonSettingItem goBackDirectlyCommonSettingItem);
	
	/**
	 * @param goBackDirectlyCommonSettingItem
	 */
	public void delete(GoBackDirectlyCommonSettingItem goBackDirectlyCommonSettingItem);
}

package nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.repository;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.item.GoBackDirectlyCommonSettingItem;

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

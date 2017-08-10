package nts.uk.ctx.at.request.dom.setting.applicationapproval.gobackdirectlycommon.repository;

import java.util.Optional;

import nts.uk.ctx.at.request.dom.setting.applicationapproval.gobackdirectlycommon.item.GoBackDirectlyCommonSettingItem;

public interface GoBackDirectlyCommonSettingRepository {
	/***/
	public Optional<GoBackDirectlyCommonSettingItem> findByAppID(String companyID, String appID);
}

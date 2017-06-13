/**
 * 9:51:17 AM Jun 6, 2017
 */
package nts.uk.ctx.at.shared.dom.bonuspay.repository;

import nts.uk.ctx.at.shared.dom.bonuspay.setting.CompanyBonusPaySetting;

/**
 * @author hungnm
 *
 */
public interface CPBonusPaySettingRepository {
	
	CompanyBonusPaySetting getSetting(String companyId);
	
	void addSetting(CompanyBonusPaySetting setting);
	
	void updateSetting(CompanyBonusPaySetting setting);
	
	void removeSetting(CompanyBonusPaySetting setting);
	
}

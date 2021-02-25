/**
 * 9:51:17 AM Jun 6, 2017
 */
package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.CompanyBonusPaySetting;



/**
 * @author hungnm
 *
 */
public interface CPBonusPaySettingRepository {

	Optional<CompanyBonusPaySetting> getSetting(String companyId);

	void saveSetting(CompanyBonusPaySetting setting);

	void removeSetting(CompanyBonusPaySetting setting);

}

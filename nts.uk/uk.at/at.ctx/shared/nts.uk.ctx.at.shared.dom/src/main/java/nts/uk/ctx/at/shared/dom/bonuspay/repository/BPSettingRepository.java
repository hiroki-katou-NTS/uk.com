/**
 * 9:49:36 AM Jun 6, 2017
 */
package nts.uk.ctx.at.shared.dom.bonuspay.repository;

import java.util.List;

import nts.uk.ctx.at.shared.dom.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.BonusPaySetting;

/**
 * @author hungnm
 *
 */
public interface BPSettingRepository {

	List<BonusPaySetting> getAllBonusPaySetting(String companyId);

	void addBonusPaySetting(BonusPaySetting domain);

	void updateBonusPaySetting(BonusPaySetting domain);

	void removeBonusPaySetting(String companyId, BonusPaySettingCode bonusPaySettingCode);
}

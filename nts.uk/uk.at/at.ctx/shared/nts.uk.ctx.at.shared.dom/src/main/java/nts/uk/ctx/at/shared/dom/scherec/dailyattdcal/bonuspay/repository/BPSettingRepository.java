/**
 * 9:49:36 AM Jun 6, 2017
 */
package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.BonusPaySetting;



/**
 * @author hungnm
 *
 */
public interface BPSettingRepository {

	List<BonusPaySetting> getAllBonusPaySetting(String companyId);
	
	Optional<BonusPaySetting> getBonusPaySetting(String companyId, BonusPaySettingCode bonusPaySettingCode);
	
	void addBonusPaySetting(BonusPaySetting domain);

	void updateBonusPaySetting(BonusPaySetting domain);

	void removeBonusPaySetting(String companyId, BonusPaySettingCode bonusPaySettingCode);

	boolean isExisted(String companyId, BonusPaySettingCode code);
	
	List<BonusPaySetting> findByCompanyAndCode(String company, List<String> bonusPaySettingCodeLst);
}

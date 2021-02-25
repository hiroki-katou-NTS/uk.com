/**
 * 9:53:14 AM Jun 6, 2017
 */
package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.WorkplaceBonusPaySetting;



/**
 * @author hungnm
 *
 */
public interface WPBonusPaySettingRepository {

	List<WorkplaceBonusPaySetting> getListSetting(String companyId, List<WorkplaceId> lstWorkplace);

	void addWPBPSetting(WorkplaceBonusPaySetting workplaceBonusPaySetting);

	void updateWPBPSetting(WorkplaceBonusPaySetting workplaceBonusPaySetting);

	void removeWPBPSetting(WorkplaceBonusPaySetting workplaceBonusPaySetting);
	
	Optional<WorkplaceBonusPaySetting> getWPBPSetting(String companyId, WorkplaceId WorkplaceId);

}

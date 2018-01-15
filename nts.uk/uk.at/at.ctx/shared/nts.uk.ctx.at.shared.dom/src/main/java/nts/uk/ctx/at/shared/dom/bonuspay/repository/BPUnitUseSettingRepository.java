/**
 * 9:59:50 AM Jun 6, 2017
 */
package nts.uk.ctx.at.shared.dom.bonuspay.repository;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.bonuspay.setting.BPUnitUseSetting;




/**
 * @author hungnm
 *
 */
public interface BPUnitUseSettingRepository {

	Optional<BPUnitUseSetting> getSetting(String companyId);

	void updateSetting(BPUnitUseSetting setting);
	
	void insertSetting(BPUnitUseSetting setting);

}

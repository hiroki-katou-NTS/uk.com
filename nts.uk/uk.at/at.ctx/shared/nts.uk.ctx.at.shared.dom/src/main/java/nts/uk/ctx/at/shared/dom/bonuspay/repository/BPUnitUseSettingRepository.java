/**
 * 9:59:50 AM Jun 6, 2017
 */
package nts.uk.ctx.at.shared.dom.bonuspay.repository;

import nts.uk.ctx.at.shared.dom.bonuspay.setting.BPUnitUseSetting;

/**
 * @author hungnm
 *
 */
public interface BPUnitUseSettingRepository {

	BPUnitUseSetting getSetting(String companyId);

	void updateSetting(BPUnitUseSetting setting);

}

/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.use;

import java.util.Optional;

/**
 * The Interface UseUnitAutoCalSettingRepository.
 */
public interface UseUnitAutoCalSettingRepository {

	
	/**
	 * Update.
	 *
	 * @param useUnitAutoCalSetting the use unit auto cal setting
	 */
    void update(UseUnitAutoCalSetting useUnitAutoCalSetting);
    


	/**
	 * Gets the all use unit auto cal setting.
	 *
	 * @param companyId the company id
	 * @return the all use unit auto cal setting
	 */
    Optional<UseUnitAutoCalSetting> getAllUseUnitAutoCalSetting(String companyId);
}

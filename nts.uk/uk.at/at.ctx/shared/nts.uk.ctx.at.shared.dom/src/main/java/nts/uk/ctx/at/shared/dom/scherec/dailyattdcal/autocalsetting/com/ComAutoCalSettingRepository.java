/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.com;

import java.util.Optional;

/**
 * The Interface ComAutoCalSettingRepository.
 */
public interface ComAutoCalSettingRepository {

	/**
	 * Update.
	 *
	 * @param comAutoCalSetting the com auto cal setting
	 */
    void update(ComAutoCalSetting comAutoCalSetting);
    
	/**
	 * Gets the all com auto cal setting.
	 *
	 * @param companyId the company id
	 * @return the all com auto cal setting
	 */
    Optional<ComAutoCalSetting> getAllComAutoCalSetting(String companyId);

	/**
	 * Gets the all com auto cal setting to export.
	 *
	 * @param cId the company id
	 * @return the all com auto cal setting
	 */

}

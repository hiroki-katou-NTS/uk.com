/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.pub.holidaymanagement.publicholiday;

import java.util.Optional;

/**
 * The Interface PublicHolidaySettingPub.
 */
public interface PublicHolidaySettingPub {
	
	/**
	 * Find public holiday setting.
	 *
	 * @return the public holiday setting dto
	 */
	Optional<PublicHolidaySettingDto> FindPublicHolidaySetting();
}

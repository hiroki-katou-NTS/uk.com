/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.pub.holidaymanagement.publicholiday;

import lombok.Data;

/**
 * The Class PublicHolidaySettingFindDto.
 */
@Data
public class PublicHolidaySettingDto{
	
	/** The company id. */
	private String companyId;
	
	/** The is manage com public hd. */
	private Integer isManageComPublicHd;
	
}

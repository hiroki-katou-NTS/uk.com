/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.pub.holidaysetting.configuration;

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

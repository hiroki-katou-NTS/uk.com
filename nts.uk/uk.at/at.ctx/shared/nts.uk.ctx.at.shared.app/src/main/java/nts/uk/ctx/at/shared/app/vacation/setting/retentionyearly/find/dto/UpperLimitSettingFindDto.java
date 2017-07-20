/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.vacation.setting.retentionyearly.find.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UpperLimitSettingFindDto {
	
	/** The retention years amount. */
	private Integer retentionYearsAmount;
	
	/** The max days cumulation. */
	private Integer maxDaysCumulation;
}

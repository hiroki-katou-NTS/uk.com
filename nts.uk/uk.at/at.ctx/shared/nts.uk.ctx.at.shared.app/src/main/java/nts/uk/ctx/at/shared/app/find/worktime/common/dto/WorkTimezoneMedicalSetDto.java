/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class WorkTimezoneMedicalSetDto.
 */
@Getter
@Setter
public class WorkTimezoneMedicalSetDto {
	
	/** The rounding set. */
	private TimeRoundingSettingDto roundingSet;

	/** The work system atr. */
	private Integer workSystemAtr;

	/** The application time. */
	private Integer applicationTime;

}

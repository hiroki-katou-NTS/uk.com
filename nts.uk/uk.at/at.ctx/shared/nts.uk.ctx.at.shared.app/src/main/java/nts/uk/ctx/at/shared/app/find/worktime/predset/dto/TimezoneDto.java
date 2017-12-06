/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.predset.dto;

import lombok.Builder;

/**
 * The Class TimezoneDto.
 */
@Builder
public class TimezoneDto {
	
	/** The use atr. */
	public boolean useAtr;

	/** The work no. */
	public Integer workNo;

	/** The start. */
	public Integer start;

	/** The end. */
	public Integer end;
}

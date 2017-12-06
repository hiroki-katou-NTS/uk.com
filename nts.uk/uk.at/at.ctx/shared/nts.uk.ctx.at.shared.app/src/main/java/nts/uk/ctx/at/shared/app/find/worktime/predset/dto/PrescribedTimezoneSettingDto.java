/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.predset.dto;

import java.util.List;

import lombok.Builder;

/**
 * The Class PrescribedTimezoneSettingDto.
 */
@Builder
public class PrescribedTimezoneSettingDto {
	/** The morning end time. */
	public Integer morningEndTime;
	
	/** The afternoon start time. */
	public Integer afternoonStartTime;
	
	/** The timezone. */
	public List<TimezoneDto> timezone;
}

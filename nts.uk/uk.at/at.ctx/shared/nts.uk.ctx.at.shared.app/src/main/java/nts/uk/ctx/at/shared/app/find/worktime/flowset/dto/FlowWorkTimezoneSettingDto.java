/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.flowset.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.TimeRoundingSettingDto;

/**
 * The Class FlowWorkTimezoneSettingDto.
 */
@Getter
@Setter
public class FlowWorkTimezoneSettingDto extends DomainObject {

	/** The work time rounding. */
	private TimeRoundingSettingDto workTimeRounding;
	
	/** The lst OT timezone. */
	private List<FlowOTTimezoneDto> lstOTTimezone;
}

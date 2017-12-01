/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.flowset.dto;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.TimeRoundingSettingDto;

/**
 * The Class FlowTimeSettingDto.
 */
@Getter
@Setter
public class FlowTimeSettingDto extends DomainObject {

	/** The rouding. */
	private TimeRoundingSettingDto rouding;

	/** The passage time. */
	private Integer passageTime;
}

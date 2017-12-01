/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class FlowRestTimezoneDto.
 */
@Getter
@Setter
public class FlowRestTimezoneDto {

	/** The flow rest set. */
	private List<FlowRestSettingDto> flowRestSet;

	/** The use here after rest set. */
	private boolean useHereAfterRestSet;

	/** The here after rest set. */
	private FlowRestSettingDto hereAfterRestSet;
}

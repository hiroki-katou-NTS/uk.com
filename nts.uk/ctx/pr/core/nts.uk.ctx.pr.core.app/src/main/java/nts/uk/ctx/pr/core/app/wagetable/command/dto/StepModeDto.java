/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable.command.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * The Class StepMode.
 */
@Getter
@Setter
@Builder
public class StepModeDto extends BaseModeDto {

	/** The upper limit. */
	private Double upperLimit;

	/** The lower limit. */
	private Double lowerLimit;

	/** The interval. */
	private Double interval;

	/** The items. */
	private List<RangeItemDto> items;

}

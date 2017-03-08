/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable.command.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class StepMode.
 */
@Getter
@Setter
public class StepModeDto extends BaseModeDto {

	/** The upper limit. */
	private BigDecimal upperLimit;

	/** The lower limit. */
	private BigDecimal lowerLimit;

	/** The interval. */
	private BigDecimal interval;

	/** The items. */
	private List<RangeItemDto> items;

}

/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class FlowRestSetDto.
 */
@Getter
@Setter
public class FlowRestSetDto {

	/** The use stamp. */
	private Boolean useStamp;

	/** The use stamp calc method. */
	private Integer useStampCalcMethod;

	/** The time manager set atr. */
	private Integer timeManagerSetAtr;

	/** The calculate method. */
	private Integer calculateMethod;
}

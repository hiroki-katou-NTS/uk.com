/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.ws.wagetable.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class SettingInfoInModel.
 */
@Getter
@Setter
public class SettingInfoInModel {

	/** The demension no. */
	private Integer demensionNo;

	/** The type. */
	private Integer type;

	/** The lower limit. */
	private BigDecimal lowerLimit;

	/** The upper limit. */
	private BigDecimal upperLimit;

	/** The interval. */
	private BigDecimal interval;

}

/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance;

import lombok.Data;

/**
 * The Class RoundingItem.
 */
@Data
public class RoundingItem {

	/** The company round atr. */
	private RoundingMethod companyRoundAtr;

	/** The personal round atr. */
	private RoundingMethod personalRoundAtr;

}

/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate;

import lombok.Getter;
import nts.uk.ctx.pr.core.dom.insurance.RoundingMethod;
import nts.uk.ctx.pr.core.dom.insurance.labor.businesstype.BusinessTypeEnum;

/**
 * The Class InsuBizRateItem.
 */
@Getter
public class InsuBizRateItem {

	/** The insu biz type. */
	private BusinessTypeEnum insuBizType;

	/** The insu rate. */
	private Double insuRate;

	/** The insu round. */
	private RoundingMethod insuRound;
}

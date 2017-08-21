/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.price;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimatedCondition;

/**
 * The Class EstimatedPrice.
 */
// 目安金額
@Getter
public class EstimatedPrice extends DomainObject{

	
	/** The estimated condition. */
	// 目安利用条件
	private EstimatedCondition estimatedCondition;
	
	
	/** The estimated price. */
	// 金額 
	private EstimatePrice estimatedPrice;
}

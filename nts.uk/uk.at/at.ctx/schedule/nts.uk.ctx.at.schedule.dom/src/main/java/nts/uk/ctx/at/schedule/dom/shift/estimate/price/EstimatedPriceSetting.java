/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.price;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.schedule.dom.shift.estimate.time.EstimateTargetClassification;

/**
 * The Class EstimatedPriceSetting.
 */
// 目安金額設定
@Getter
public class EstimatedPriceSetting {

	/** The target classification. */
	// 対象区分
	private EstimateTargetClassification targetClassification;
	
	
	/** The price setting. */
	// 金額設定
	private List<EstimatedPriceSetting> priceSetting;
}

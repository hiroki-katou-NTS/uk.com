/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.price;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateTargetClassification;

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
	private List<EstimatedPrice> priceSetting;
	
	
	/**
	 * Instantiates a new estimated price setting.
	 *
	 * @param memento the memento
	 */
	public EstimatedPriceSetting(EstimatedPriceSettingGetMemento memento){
		this.targetClassification = memento.getTargetClassification();
		this.priceSetting = memento.getPriceSetting();
	}
	
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(EstimatedPriceSettingSetMemento memento){
		memento.setTargetClassification(this.targetClassification);
		memento.setPriceSetting(this.priceSetting);
	}
}

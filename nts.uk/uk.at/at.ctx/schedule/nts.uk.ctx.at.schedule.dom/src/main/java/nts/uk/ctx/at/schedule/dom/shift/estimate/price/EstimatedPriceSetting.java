/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.price;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateTargetClassification;
import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimatedCondition;

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
	
	/** The next index. */
	private static final int NEXT_INDEX = 1;
	
	/** The Constant ZERO_VALUE. */
	private static final int ZERO_VALUE = 0;
	
	
	/**
	 * Instantiates a new estimated price setting.
	 *
	 * @param memento the memento
	 */
	public EstimatedPriceSetting(EstimatedPriceSettingGetMemento memento){
		this.targetClassification = memento.getTargetClassification();
		this.priceSetting = memento.getPriceSetting();

		// validate
		Map<Integer, EstimatedPrice> mapPrice = this.priceSetting.stream()
				.collect(Collectors.toMap((price) -> {
					return price.getEstimatedCondition().value;
				}, Function.identity()));

		// check validate of 1st to 4th
		for (int indexCondition = EstimatedCondition.CONDITION_1ST.value; 
				indexCondition <= EstimatedCondition.CONDITION_4TH.value; indexCondition++) {
			EstimatedPrice priceNow = mapPrice.get(indexCondition);
			EstimatedPrice priceNext = mapPrice.get(indexCondition + NEXT_INDEX);
			if (priceNow.getEstimatedPrice().v() != ZERO_VALUE
					&& priceNext.getEstimatedPrice().v() != ZERO_VALUE
					&& priceNext.getEstimatedPrice().v() < priceNow.getEstimatedPrice().v()) {
				throw new BusinessException("Msg_147", "KSM001_24");
			}
			if (priceNow.getEstimatedPrice().v() == ZERO_VALUE
					&& priceNext.getEstimatedPrice().v() > ZERO_VALUE) {
				throw new BusinessException("Msg_182", "KSM001_24");
			}
		}
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

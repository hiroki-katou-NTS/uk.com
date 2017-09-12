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
import nts.arc.i18n.I18NText;
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

	/** The Constant ZERO_VALUE. */
	private static final int ZERO_VALUE = 0;
	
	/** The Constant GUILDANCE_TEXT. */
	private static final String GUILDANCE_TEXT = "目安利用条件";

	/**
	 * Instantiates a new estimated price setting.
	 *
	 * @param memento
	 *            the memento
	 */
	public EstimatedPriceSetting(EstimatedPriceSettingGetMemento memento) {
		this.targetClassification = memento.getTargetClassification();
		this.priceSetting = memento.getPriceSetting();

		// validate
		Map<EstimatedCondition, EstimatedPrice> mapPrice = this.priceSetting.stream().collect(
				Collectors.toMap(EstimatedPrice::getEstimatedCondition, Function.identity()));

		// check validate of 1st to 5th
		for (EstimatedCondition cond : EstimatedCondition.values()) {
			EstimatedCondition nextCond = cond.nextOf(cond);

			if (nextCond == null) {
				return;
			}

			EstimatedPrice priceBase = mapPrice.get(cond);
			EstimatedPrice priceNext = mapPrice.get(nextCond);

			if (priceBase.getEstimatedPrice().v() != ZERO_VALUE
					&& priceNext.getEstimatedPrice().v() != ZERO_VALUE
					&& priceNext.getEstimatedPrice().v() >= priceBase.getEstimatedPrice().v()) {
				I18NText text = I18NText.main("Msg_147").addId("KSM001_24").addRaw(GUILDANCE_TEXT).build();
				throw new BusinessException(text);
			}
			if (priceBase.getEstimatedPrice().v() == ZERO_VALUE
					&& priceNext.getEstimatedPrice().v() > ZERO_VALUE) {
				I18NText text = I18NText.main("Msg_182").addId("KSM001_24").addRaw(GUILDANCE_TEXT).build();
				throw new BusinessException(text);
			}
		}
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(EstimatedPriceSettingSetMemento memento) {
		memento.setTargetClassification(this.targetClassification);
		memento.setPriceSetting(this.priceSetting);
	}
}

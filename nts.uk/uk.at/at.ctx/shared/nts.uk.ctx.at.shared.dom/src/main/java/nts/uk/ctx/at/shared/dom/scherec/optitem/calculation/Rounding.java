/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.optitem.calculation;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.common.amountrounding.AmountRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.numberrounding.NumberRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;

/**
 * The Class Rounding.
 */
// 別端数処理設定
@Getter
public class Rounding extends DomainObject {

	/** The number rounding. */
	// 数値丸め
	private NumberRoundingSetting numberRounding;

	/** The time rounding. */
	// 時間丸め
	private TimeRoundingSetting timeRounding;

	/** The amount rounding. */
	// 金額丸め
	private AmountRoundingSetting amountRounding;

	/**
	 * Instantiates a new rounding.
	 *
	 * @param memento the memento
	 */
	public Rounding(RoundingGetMemento memento) {
		this.numberRounding = memento.getNumberRounding();
		this.amountRounding = memento.getAmountRounding();
		this.timeRounding = memento.getTimeRoundingSetting();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(RoundingSetMemento memento) {
		memento.setAmountRounding(this.amountRounding);
		memento.setNumberRounding(this.numberRounding);
		memento.setTimeRoundingSetting(this.timeRounding);
	}
}

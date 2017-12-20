/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class ExtraordWorkOTFrameSet.
 */
// 臨時勤務時の残業枠設定
@Getter
public class ExtraordWorkOTFrameSet extends DomainObject {

	/** The OT frame no. */
	// 残業枠NO
	private OTFrameNo oTFrameNo;

	/** The in legal work frame no. */
	// 法内残業枠NO
	private OTFrameNo inLegalWorkFrameNo;

	/** The settlement order. */
	// 精算順序
	private SettlementOrder settlementOrder;

	/**
	 * Instantiates a new extraord work OT frame set.
	 *
	 * @param memento the memento
	 */
	public ExtraordWorkOTFrameSet (ExtraordWorkOTFrameSetGetMemento memento) {
		this.oTFrameNo = memento.getOTFrameNo();
		this.inLegalWorkFrameNo = memento.getInLegalWorkFrameNo();
		this.settlementOrder = memento.getSettlementOrder();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(ExtraordWorkOTFrameSetSetMemento memento) {
		memento.setOTFrameNo(this.oTFrameNo);
		memento.setInLegalWorkFrameNo(this.inLegalWorkFrameNo);
		memento.setSettlementOrder(this.settlementOrder);
	}
}

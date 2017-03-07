/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.history;

import lombok.Getter;
import nts.uk.ctx.pr.core.dom.wagetable.DemensionNo;
import nts.uk.ctx.pr.core.dom.wagetable.element.ElementMode;

/**
 * The Class WageTableDemensionDetail.
 */
@Getter
public class WageTableDemensionDetail {

	/** The demension no. */
	private DemensionNo demensionNo;

	/** The element mode setting. */
	private ElementMode elementModeSetting;

	// =================== Memento State Support Method ===================
	/**
	 * Instantiates a new wage table demension detail.
	 *
	 * @param memento
	 *            the memento
	 */
	public WageTableDemensionDetail(WageTableDetailGetMemento memento) {
		this.demensionNo = memento.getDemensionNo();
		this.elementModeSetting = memento.getElementModeSetting();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(WageTableDetailSetMemento memento) {
		memento.setDemensionNo(this.demensionNo);
		memento.setElementModeSetting(this.elementModeSetting);
	}

}

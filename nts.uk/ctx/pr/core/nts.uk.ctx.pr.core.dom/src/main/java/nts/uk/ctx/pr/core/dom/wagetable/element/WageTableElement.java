/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.element;

import lombok.Getter;
import nts.uk.ctx.pr.core.dom.wagetable.DemensionNo;

/**
 * The Class WageTableElement.
 */
@Getter
public class WageTableElement {

	/** The demension no. */
	private DemensionNo demensionNo;

	/** The element mode setting. */
	private ElementMode elementModeSetting;

	/**
	 * Instantiates a new wage table element.
	 *
	 * @param demensionNo
	 *            the demension no
	 * @param elementModeSetting
	 *            the element mode setting
	 */
	public WageTableElement(DemensionNo demensionNo, ElementMode elementModeSetting) {
		super();
		this.demensionNo = demensionNo;
		this.elementModeSetting = elementModeSetting;
	}

	// =================== Memento State Support Method ===================
	/**
	 * Instantiates a new wage table element.
	 *
	 * @param memento
	 *            the memento
	 */
	public WageTableElement(WageTableElementGetMemento memento) {
		this.demensionNo = memento.getDemensionNo();
		this.elementModeSetting = memento.getElementModeSetting();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(WageTableElementSetMemento memento) {
		memento.setDemensionNo(this.demensionNo);
		memento.setElementModeSetting(this.elementModeSetting);
	}
}

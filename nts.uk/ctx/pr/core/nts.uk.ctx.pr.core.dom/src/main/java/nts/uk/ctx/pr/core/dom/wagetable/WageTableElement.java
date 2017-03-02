/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable;

import lombok.Getter;
import nts.uk.ctx.pr.core.dom.wagetable.element.ElementMode;

/**
 * The Class WageTableElement.
 */
@Getter
public class WageTableElement {

	/** The demension no. */
	private DemensionOrder demensionNo;

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
	public WageTableElement(DemensionOrder demensionNo, ElementMode elementModeSetting) {
		super();
		this.demensionNo = demensionNo;
		this.elementModeSetting = elementModeSetting;
	}
}

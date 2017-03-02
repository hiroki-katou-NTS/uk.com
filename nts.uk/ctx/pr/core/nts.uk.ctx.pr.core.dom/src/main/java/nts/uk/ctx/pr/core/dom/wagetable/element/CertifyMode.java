/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.element;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.pr.core.dom.wagetable.ElementType;

/**
 * The Class CodeRefMode.
 */
@Getter
public class CertifyMode extends BaseMode {

	/** The items. */
	private List<CertifyItem> items;

	/**
	 * Instantiates a new code ref mode.
	 *
	 * @param companyCode
	 *            the company code
	 * @param refNo
	 *            the ref no
	 */
	public CertifyMode() {
		super(ElementType.Certification);

		// Create items
		this.items = null;
		// new CertifyItem(code, );
	}

}

/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.element;

import lombok.Getter;

/**
 * The Class CodeItem.
 */
@Getter
public class CodeItem {

	/** The reference code. */
	private String referenceCode;

	/** The uuid. */
	private String uuid;

	/**
	 * Instantiates a new code item.
	 *
	 * @param referenceCode
	 *            the reference code
	 * @param uuid
	 *            the uuid
	 */
	public CodeItem(String referenceCode, String uuid) {
		super();
		this.referenceCode = referenceCode;
		this.uuid = uuid;
	}
}

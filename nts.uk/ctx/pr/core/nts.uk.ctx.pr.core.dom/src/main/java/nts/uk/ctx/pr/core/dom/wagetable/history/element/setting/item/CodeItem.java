/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.history.element.setting.item;

import lombok.Getter;

/**
 * The Class CodeItem.
 */
@Getter
public class CodeItem extends BaseItem {

	/** The reference code. */
	private String referenceCode;

	/**
	 * Instantiates a new code item.
	 *
	 * @param referenceCode
	 *            the reference code
	 * @param uuid
	 *            the uuid
	 */
	public CodeItem(String referenceCode, String uuid) {
		super(uuid);
		this.referenceCode = referenceCode;
	}
}

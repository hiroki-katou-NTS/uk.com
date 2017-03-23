/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.reference;

import lombok.Getter;

/**
 * The Class DisplayCodeItem.
 */
@Getter
public class DemensionItem {

	/** The uuid. */
	private String uuid;

	/** The code. */
	private String code;

	/** The name. */
	private String displayName;

	public DemensionItem(String uuid, String code, String displayName) {
		super();
		this.uuid = uuid;
		this.code = code;
		this.displayName = displayName;
	}

}

/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.history.element.item;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.dom.wagetable.ElementId;

/**
 * The Class BaseItem.
 */

public class BaseItem implements Item {

	/** The uuid. */
	protected ElementId uuid;
	
	/** The display name. */
	@Getter
	@Setter
	private String displayName;

	/**
	 * Instantiates a new base item.
	 *
	 * @param uuid
	 *            the uuid
	 */
	public BaseItem(ElementId uuid) {
		super();
		this.uuid = uuid;
	}

	/**
	 * Gets the uuid.
	 *
	 * @return the uuid
	 */
	public ElementId getUuid() {
		return this.uuid;
	}
}

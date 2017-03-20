/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.history.element.setting.item;

/**
 * The Class BaseItem.
 */
public class BaseItem {

	/** The uuid. */
	protected String uuid;

	/**
	 * Instantiates a new base item.
	 *
	 * @param uuid the uuid
	 */
	public BaseItem(String uuid) {
		super();
		this.uuid = uuid;
	}

	/**
	 * Gets the uuid.
	 *
	 * @return the uuid
	 */
	public String getUuid() {
		return this.uuid;
	}
}

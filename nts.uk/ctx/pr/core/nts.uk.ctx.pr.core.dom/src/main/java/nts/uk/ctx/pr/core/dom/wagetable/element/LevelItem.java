/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.element;

import lombok.Getter;

/**
 * The Class LevelItem.
 */
@Getter
public class LevelItem extends CodeItem {

	/**
	 * Instantiates a new level item.
	 *
	 * @param levelCode the level code
	 * @param uuid the uuid
	 */
	public LevelItem(String levelCode, String uuid) {
		super(levelCode, uuid);
	}

}

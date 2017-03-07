/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.element;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.dom.wagetable.ElementType;

/**
 * The Class LevelMode.
 */
@Getter
public class LevelMode extends BaseMode {

	/** The items. */
	@Setter
	private List<CodeItem> items;

	/**
	 * Instantiates a new level mode.
	 */
	public LevelMode() {
		super(ElementType.LEVEL);
	}

	/**
	 * Instantiates a new level mode.
	 *
	 * @param type
	 *            the type
	 * @param items
	 *            the items
	 */
	public LevelMode(ElementType type, List<CodeItem> items) {
		super(type);
		this.items = items;
	}

}

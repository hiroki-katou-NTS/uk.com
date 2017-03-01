/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.element;

import nts.uk.ctx.pr.core.dom.wagetable.ElementType;

/**
 * The Class BaseMode.
 */
public abstract class BaseMode implements ElementMode {

	/** The type. */
	private ElementType type;
	
	/**
	 * Instantiates a new base mode.
	 *
	 * @param type the type
	 */
	public BaseMode(ElementType type) {
		this.type = type;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.core.dom.wagetable.element.ElementMode#getElementType()
	 */
	@Override
	public ElementType getElementType() {
		return this.type;
	}
}

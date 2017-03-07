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
 * The Class CertifyMode.
 */
@Getter
public class CertifyMode extends BaseMode {

	/** The items. */
	
	/**
	 * Sets the items.
	 *
	 * @param items the new items
	 */
	@Setter
	private List<CodeItem> items;

	/**
	 * Instantiates a new certify mode.
	 */
	public CertifyMode() {
		super(ElementType.CERTIFICATION);
	}

	/**
	 * Instantiates a new certify mode.
	 *
	 * @param type the type
	 * @param items the items
	 */
	public CertifyMode(ElementType type, List<CodeItem> items) {
		super(type);
		this.items = items;
	}
	
}

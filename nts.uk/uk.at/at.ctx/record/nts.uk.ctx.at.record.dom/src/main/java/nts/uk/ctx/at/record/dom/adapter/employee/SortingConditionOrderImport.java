/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.adapter.employee;

import lombok.Data;

/**
 * Instantiates a new sorting condition order import.
 */

/**
 * Instantiates a new sorting condition order import.
 */
@Data
public class SortingConditionOrderImport {
	/** The order. */
	public int order;
	
	/** The type. */
	public RegularSortingTypeImport type;

	public SortingConditionOrderImport(int order, RegularSortingTypeImport type) {
		super();
		this.order = order;
		this.type = type;
	}
	
}

/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.reference;

import java.util.List;

import lombok.Getter;

/**
 * The Class DisplayCodeItem.
 */
@Getter
public class DemensionTable {

	private List<DemensionDirection> directions;

	public DemensionTable(List<DemensionDirection> directions) {
		super();
		this.directions = directions;
	}

}

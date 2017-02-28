/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.history;

import java.math.BigDecimal;

import lombok.Getter;
import nts.uk.ctx.pr.core.dom.wagetable.ElementId;

/**
 * The Class WageTableAmount.
 */
@Getter
public class WageTableAmount {

	// Demension no 1.
	/** The element 1 id. */
	private ElementId element1Id;

	// Demension no 2.
	/** The element 2 id. */
	private ElementId element2Id;

	// Demension no 3.
	/** The element 3 id. */
	private ElementId element3Id;

	/** The amount. */
	private BigDecimal amount;

}

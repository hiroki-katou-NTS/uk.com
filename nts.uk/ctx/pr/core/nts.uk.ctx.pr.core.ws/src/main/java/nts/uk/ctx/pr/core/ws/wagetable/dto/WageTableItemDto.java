/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.ws.wagetable.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.dom.wagetable.ElementId;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableItemSetMemento;

/**
 * The Class WageTableItemDto.
 */
@Getter
@Setter
public class WageTableItemDto implements WageTableItemSetMemento {

	/** The element 1 id. */
	private String element1Id;

	/** The element 2 id. */
	private String element2Id;

	/** The element 3 id. */
	private String element3Id;

	/** The amount. */
	private Double amount;

	@Override
	public void setElement1Id(ElementId element1Id) {
		this.element1Id = element1Id.v();
	}

	@Override
	public void setElement2Id(ElementId element2Id) {
		this.element2Id = element2Id.v();
	}

	@Override
	public void setElement3Id(ElementId element3Id) {
		this.element3Id = element3Id.v();
	}

	@Override
	public void setAmount(BigDecimal amount) {
		this.amount = amount.doubleValue();
	}

}

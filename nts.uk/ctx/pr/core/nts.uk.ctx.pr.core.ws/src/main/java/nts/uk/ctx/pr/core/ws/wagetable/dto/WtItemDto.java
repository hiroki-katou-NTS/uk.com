/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.ws.wagetable.dto;

import java.math.BigDecimal;

import lombok.Data;
import nts.uk.ctx.pr.core.dom.wagetable.ElementId;
import nts.uk.ctx.pr.core.dom.wagetable.history.WtItemSetMemento;

/**
 * The Class WtItemDto.
 */
@Data
public class WtItemDto implements WtItemSetMemento {

	/** The element 1 id. */
	private String element1Id;

	/** The element 2 id. */
	private String element2Id;

	/** The element 3 id. */
	private String element3Id;

	/** The amount. */
	private Double amount;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.wagetable.history.WtItemSetMemento#setElement1Id(
	 * nts.uk.ctx.pr.core.dom.wagetable.ElementId)
	 */
	@Override
	public void setElement1Id(ElementId element1Id) {
		this.element1Id = element1Id.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.wagetable.history.WtItemSetMemento#setElement2Id(
	 * nts.uk.ctx.pr.core.dom.wagetable.ElementId)
	 */
	@Override
	public void setElement2Id(ElementId element2Id) {
		this.element2Id = element2Id.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.wagetable.history.WtItemSetMemento#setElement3Id(
	 * nts.uk.ctx.pr.core.dom.wagetable.ElementId)
	 */
	@Override
	public void setElement3Id(ElementId element3Id) {
		this.element3Id = element3Id.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.wagetable.history.WtItemSetMemento#setAmount(java.
	 * math.BigDecimal)
	 */
	@Override
	public void setAmount(BigDecimal amount) {
		this.amount = amount.doubleValue();
	}

}

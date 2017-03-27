/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable.command.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.dom.wagetable.ElementId;
import nts.uk.ctx.pr.core.dom.wagetable.history.WtItemGetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.history.WtItemSetMemento;

/**
 * The Class WageTableItemDto.
 */
@Getter
@Setter
public class WtItemDto implements WtItemGetMemento, WtItemSetMemento {

	/** The element 1 id. */
	private String element1Id;

	/** The element 2 id. */
	private String element2Id;

	/** The element 3 id. */
	private String element3Id;

	/** The amount. */
	private BigDecimal amount;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WageTableItemGetMemento#
	 * getElement1Id()
	 */
	@Override
	public ElementId getElement1Id() {
		return new ElementId(this.element1Id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WageTableItemGetMemento#
	 * getElement2Id()
	 */
	@Override
	public ElementId getElement2Id() {
		return new ElementId(this.element2Id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WageTableItemGetMemento#
	 * getElement3Id()
	 */
	@Override
	public ElementId getElement3Id() {
		return new ElementId(this.element3Id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WageTableItemGetMemento#
	 * getAmount()
	 */
	@Override
	public BigDecimal getAmount() {
		return amount;
	}

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
		this.amount = amount;
	}

}

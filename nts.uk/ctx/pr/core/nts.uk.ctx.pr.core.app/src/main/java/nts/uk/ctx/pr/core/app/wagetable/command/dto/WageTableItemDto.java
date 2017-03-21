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

/**
 * The Class WageTableItemDto.
 */
@Getter
@Setter
public class WageTableItemDto implements WtItemGetMemento {

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

}

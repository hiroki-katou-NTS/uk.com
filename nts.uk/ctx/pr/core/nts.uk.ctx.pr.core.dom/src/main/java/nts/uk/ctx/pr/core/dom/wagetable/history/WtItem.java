/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.history;

import lombok.Getter;
import nts.uk.ctx.pr.core.dom.wagetable.ElementId;
import nts.uk.ctx.pr.core.dom.wagetable.WtValue;

/**
 * The Class WageTableItem.
 */
@Getter
public class WtItem {

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
	private WtValue amount;

	/**
	 * Instantiates a new wage table item.
	 *
	 * @param element1Id
	 *            the element 1 id
	 * @param element2Id
	 *            the element 2 id
	 * @param element3Id
	 *            the element 3 id
	 * @param amount
	 *            the amount
	 */
	public WtItem(ElementId element1Id, ElementId element2Id, ElementId element3Id,
			WtValue amount) {
		super();
		this.element1Id = element1Id;
		this.element2Id = element2Id;
		this.element3Id = element3Id;
		this.amount = amount;
	}

	// =================== Memento State Support Method ===================
	/**
	 * Instantiates a new wage table item.
	 *
	 * @param memento
	 *            the memento
	 */
	public WtItem(WtItemGetMemento memento) {
		this.element1Id = memento.getElement1Id();
		this.element2Id = memento.getElement2Id();
		this.element3Id = memento.getElement3Id();
		this.amount = memento.getAmount();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(WtItemSetMemento memento) {
		memento.setElement1Id(this.element1Id);
		memento.setElement2Id(this.element2Id);
		memento.setElement3Id(this.element3Id);
		memento.setAmount(this.amount);
	}
}

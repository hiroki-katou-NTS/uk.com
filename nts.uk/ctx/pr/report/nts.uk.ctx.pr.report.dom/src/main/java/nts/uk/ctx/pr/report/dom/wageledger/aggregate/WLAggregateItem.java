/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.wageledger.aggregate;

import java.util.Set;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class WageLedgerAggregateItem.
 */
@Getter
public class WLAggregateItem extends AggregateRoot{
	
	/** The subject. */
	private WLItemSubject subject;
	
	/** The name. */
	private WLAggregateItemName name;
	
	/** The show name zero value. */
	private Boolean showNameZeroValue;
	
	/** The show value zero value. */
	private Boolean showValueZeroValue;
	
	/** The sub items. */
	private Set<String> subItems;
	
	/**
	 * Instantiates a new WL aggregate item.
	 *
	 * @param memento the memento
	 */
	public WLAggregateItem(WLAggregateItemGetMemento memento) {
		super();
		this.subject = memento.getSubject();
		this.name = memento.getName();
		this.showNameZeroValue = memento.getShowNameZeroValue();
		this.showValueZeroValue = memento.getShowValueZeroValue();
		this.subItems = memento.getSubItems();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(WLAggregateItemSetMemento memento) {
		memento.setSubject(this.subject);
		memento.setName(this.name);
		memento.setShowNameZeroValue(this.showNameZeroValue);
		memento.setShowValueZeroValue(this.showValueZeroValue);
		memento.setSubItems(this.subItems);
	}
}

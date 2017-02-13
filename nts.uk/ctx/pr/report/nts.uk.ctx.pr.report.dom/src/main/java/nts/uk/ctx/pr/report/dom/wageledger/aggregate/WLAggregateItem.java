/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.wageledger.aggregate;

import java.util.Set;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.pr.report.dom.company.CompanyCode;
import nts.uk.ctx.pr.report.dom.wageledger.PaymentType;
import nts.uk.ctx.pr.report.dom.wageledger.WLCategory;

/**
 * The Class WageLedgerAggregateItem.
 */
@Getter
public class WLAggregateItem extends AggregateRoot{
	
	/** The company code. */
	private CompanyCode companyCode;
	
	/** The category. */
	private WLCategory category;
	
	/** The payment type. */
	private PaymentType paymentType;
	
	/** The code. */
	private WLAggregateItemCode code;
	
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
		this.companyCode = memento.getCompanyCode();
		this.category = memento.getCategory();
		this.paymentType = memento.getPaymentType();
		this.code = memento.getCode();
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
		memento.setCategory(this.category);
		memento.setCode(this.code);
		memento.setCompanyCode(this.companyCode);
		memento.setName(this.name);
		memento.setPaymentType(this.paymentType);
		memento.setShowNameZeroValue(this.showNameZeroValue);
		memento.setShowValueZeroValue(this.showValueZeroValue);
		memento.setSubItems(this.subItems);
	}
}

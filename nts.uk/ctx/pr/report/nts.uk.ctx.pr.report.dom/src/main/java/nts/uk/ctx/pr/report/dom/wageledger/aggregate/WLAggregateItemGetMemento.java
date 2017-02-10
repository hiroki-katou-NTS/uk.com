/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.wageledger.aggregate;

import java.util.Set;

import nts.uk.ctx.pr.report.dom.company.CompanyCode;
import nts.uk.ctx.pr.report.dom.wageledger.PaymentType;
import nts.uk.ctx.pr.report.dom.wageledger.WLCategory;

/**
 * The Interface WLAggregateItemGetMemento.
 */
public interface WLAggregateItemGetMemento {
	
	/**
	 * Gets the company code.
	 *
	 * @return the company code
	 */
	CompanyCode getCompanyCode();
	
	/**
	 * Gets the category.
	 *
	 * @return the category
	 */
	WLCategory getCategory();
	
	/**
	 * Gets the payment type.
	 *
	 * @return the payment type
	 */
	PaymentType getPaymentType();
	
	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	WLAggregateItemCode getCode();
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	WLAggregateItemName getName();
	
	/**
	 * Gets the show name zero value.
	 *
	 * @return the show name zero value
	 */
	Boolean getShowNameZeroValue();
	
	/**
	 * Gets the show value zero value.
	 *
	 * @return the show value zero value
	 */
	Boolean getShowValueZeroValue();
	
	/**
	 * Gets the sub items.
	 *
	 * @return the sub items
	 */
	Set<String> getSubItems();

}

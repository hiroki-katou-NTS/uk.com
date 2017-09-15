/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.overtime.breakdown;

import nts.uk.ctx.at.shared.dom.overtime.ProductNumber;
import nts.uk.ctx.at.shared.dom.overtime.UseClassification;

/**
 * The Interface OvertimeBRDItemSetMemento.
 */
public interface OvertimeBRDItemSetMemento {

	/**
	 * Sets the use classification.
	 *
	 * @param useClassification the new use classification
	 */
	public void setUseClassification(UseClassification useClassification);
	
	
	/**
	 * Sets the breakdown item no.
	 *
	 * @param breakdownItemNo the new breakdown item no
	 */
	public void setBreakdownItemNo(BreakdownItemNo breakdownItemNo);
	
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(BreakdownItemName name);
	
	
	/**
	 * Sets the product number.
	 *
	 * @param productNumber the new product number
	 */
	public void setProductNumber(ProductNumber productNumber);
}

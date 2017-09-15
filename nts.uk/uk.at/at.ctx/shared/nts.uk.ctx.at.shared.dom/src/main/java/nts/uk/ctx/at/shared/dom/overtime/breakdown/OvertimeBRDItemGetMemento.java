/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.overtime.breakdown;

import nts.uk.ctx.at.shared.dom.overtime.ProductNumber;
import nts.uk.ctx.at.shared.dom.overtime.UseClassification;

/**
 * The Interface OvertimeBRDItemGetMemento.
 */
public interface OvertimeBRDItemGetMemento {
	
	/**
	 * Gets the use classification.
	 *
	 * @return the use classification
	 */
	public UseClassification getUseClassification();
	
	
	/**
	 * Gets the breakdown item no.
	 *
	 * @return the breakdown item no
	 */
	public BreakdownItemNo getBreakdownItemNo();
	
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public BreakdownItemName getName();
	
	
	/**
	 * Gets the product number.
	 *
	 * @return the product number
	 */
	public ProductNumber getProductNumber();

}

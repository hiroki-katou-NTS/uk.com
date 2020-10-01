/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown;

import java.util.List;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.UseClassification;

/**
 * The Interface OutsideOTBRDItemGetMemento.
 */
public interface OutsideOTBRDItemGetMemento {
	
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
	
	
	/**
	 * Gets the attendance item ids.
	 *
	 * @return the attendance item ids
	 */
	public List<Integer> getAttendanceItemIds();

}

/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.overtime.premium.extra;

import nts.uk.ctx.at.shared.dom.overtime.OvertimeNo;
import nts.uk.ctx.at.shared.dom.overtime.breakdown.BreakdownItemNo;
import nts.uk.ctx.at.shared.dom.overtime.premium.PremiumRate;

/**
 * The Interface PremiumExtra60HRateGetMemento.
 */
public interface PremiumExtra60HRateGetMemento {

	/**
	 * Gets the breakdown item no.
	 *
	 * @return the breakdown item no
	 */
	public BreakdownItemNo getBreakdownItemNo();
	
	
	/**
	 * Gets the premium rate.
	 *
	 * @return the premium rate
	 */
	public PremiumRate getPremiumRate();
	
	
	/**
	 * Gets the overtime no.
	 *
	 * @return the overtime no
	 */
	public OvertimeNo getOvertimeNo();
	
	
}

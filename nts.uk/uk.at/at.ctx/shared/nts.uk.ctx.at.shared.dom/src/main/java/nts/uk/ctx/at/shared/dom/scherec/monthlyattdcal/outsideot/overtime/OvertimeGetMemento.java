/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.UseClassification;

/**
 * The Interface OvertimeGetMemento.
 */
public interface OvertimeGetMemento {

	/**
	 * Gets the super holiday 60 H occurs.
	 *
	 * @return the super holiday 60 H occurs
	 */
	boolean getSuperHoliday60HOccurs();
	
	
	/**
	 * Gets the use classification.
	 *
	 * @return the use classification
	 */
	UseClassification getUseClassification();
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	OvertimeName getName();
	
	
	/**
	 * Gets the overtime.
	 *
	 * @return the overtime
	 */
	OvertimeValue getOvertime();
	
	
	/**
	 * Gets the overtime no.
	 *
	 * @return the overtime no
	 */
	OvertimeNo getOvertimeNo();
}

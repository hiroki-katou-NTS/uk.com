/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.UseClassification;

/**
 * The Interface OvertimeSetMemento.
 */
public interface OvertimeSetMemento {

	/**
	 * Sets the super holiday 60 H occurs.
	 *
	 * @param superHoliday60HOccurs the new super holiday 60 H occurs
	 */
	public void setSuperHoliday60HOccurs(boolean superHoliday60HOccurs);
	
	
	/**
	 * Sets the use classification.
	 *
	 * @param useClassification the new use classification
	 */
	public void setUseClassification(UseClassification useClassification);
	
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(OvertimeName name);
	
	
	/**
	 * Sets the overtime.
	 *
	 * @param overtime the new overtime
	 */
	public void setOvertime(OvertimeValue overtime);
	
	
	/**
	 * Sets the overtime no.
	 *
	 * @param overtimeNo the new overtime no
	 */
	public void setOvertimeNo(OvertimeNo overtimeNo);
}

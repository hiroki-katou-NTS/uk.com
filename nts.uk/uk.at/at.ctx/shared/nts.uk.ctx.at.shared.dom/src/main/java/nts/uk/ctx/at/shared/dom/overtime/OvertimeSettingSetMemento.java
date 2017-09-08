/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/

package nts.uk.ctx.at.shared.dom.overtime;
import java.util.List;

import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Interface OvertimeSettingSetMemento.
 */
public interface OvertimeSettingSetMemento {
	
	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	public void setCompanyId(CompanyId companyId);
	
	
	/**
	 * Sets the note.
	 *
	 * @param note the new note
	 */
	public void setNote(OvertimeNote note);
	
	
	/**
	 * Sets the calculation method.
	 *
	 * @param calculationMethod the new calculation method
	 */
	public void setCalculationMethod(OvertimeCalculationMethod calculationMethod);
	
	
	/**
	 * Sets the overtimes.
	 *
	 * @param overtimes the new overtimes
	 */
	public void setOvertimes(List<Overtime> overtimes);

}

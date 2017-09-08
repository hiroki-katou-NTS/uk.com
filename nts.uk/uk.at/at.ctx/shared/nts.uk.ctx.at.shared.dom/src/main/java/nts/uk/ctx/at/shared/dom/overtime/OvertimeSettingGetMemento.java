/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.overtime;

import java.util.List;

import nts.uk.ctx.at.shared.dom.common.CompanyId;

public interface OvertimeSettingGetMemento {

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	public CompanyId getCompanyId();
	
	
	/**
	 * Gets the note.
	 *
	 * @return the note
	 */
	public OvertimeNote getNote();
	
	
	/**
	 * Gets the calculation method.
	 *
	 * @return the calculation method
	 */
	public OvertimeCalculationMethod getCalculationMethod();
	
	
	/**
	 * Gets the overtimes.
	 *
	 * @return the overtimes
	 */
	public List<Overtime> getOvertimes();
}

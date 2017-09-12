/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.overtime.setting;

import java.util.List;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.overtime.Overtime;
import nts.uk.ctx.at.shared.dom.overtime.OvertimeCalculationMethod;
import nts.uk.ctx.at.shared.dom.overtime.OvertimeNote;
import nts.uk.ctx.at.shared.dom.overtime.breakdown.OvertimeBRDItem;

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
	 * Gets the breakdown items.
	 *
	 * @return the breakdown items
	 */
	public List<OvertimeBRDItem> getBreakdownItems();
	
	
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

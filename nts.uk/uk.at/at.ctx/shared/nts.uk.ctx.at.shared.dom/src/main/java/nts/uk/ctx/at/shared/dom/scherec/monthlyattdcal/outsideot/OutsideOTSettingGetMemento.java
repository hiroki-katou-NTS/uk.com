/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot;

import java.util.List;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.OutsideOTBRDItem;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.Overtime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeNote;

/**
 * OT = Overtime
 * The Interface OutsideOTSettingGetMemento.
 */
public interface OutsideOTSettingGetMemento {

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
	public List<OutsideOTBRDItem> getBreakdownItems();
	
	
	/**
	 * Gets the calculation method.
	 *
	 * @return the calculation method
	 */
	public OutsideOTCalMed getCalculationMethod();
	
	
	/**
	 * Gets the overtimes.
	 *
	 * @return the overtimes
	 */
	public List<Overtime> getOvertimes();
}

/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.workfixed;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;

/**
 * The Interface WorkFixedGetMemento.
 */
public interface WorkFixedGetMemento {

	/**
	 * Gets the closure id.
	 *
	 * @return the closure id
	 */
	Integer getClosureId();
	
	/**
	 * Gets the confirm P id.
	 *
	 * @return the confirm P id
	 */
	String getConfirmPId();
	
	/**
	 * Gets the work place id.
	 *
	 * @return the work place id
	 */
	String getWorkPlaceId();
	
	/**
	 * Gets the confirm cls status.
	 *
	 * @return the confirm cls status
	 */
	ConfirmClsStatus getConfirmClsStatus();
	
	
	/**
	 * Gets the fixed date.
	 *
	 * @return the fixed date
	 */
	GeneralDate getFixedDate();
	
	/**
	 * Gets the process date.
	 *
	 * @return the process date
	 */
	YearMonth getProcessYm();
	
	/**
	 * Gets the cid.
	 *
	 * @return the cid
	 */
	String getCid();
}

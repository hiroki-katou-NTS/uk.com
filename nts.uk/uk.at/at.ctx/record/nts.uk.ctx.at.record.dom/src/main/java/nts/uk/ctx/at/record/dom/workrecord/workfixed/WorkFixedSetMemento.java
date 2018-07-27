/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.workfixed;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;

/**
 * The Interface WorkFixedSetMemento.
 */
public interface WorkFixedSetMemento {

	/**
	 * Sets the closure id.
	 *
	 * @param closureId the new closure id
	 */
	void setClosureId(Integer closureId);
	
	/**
	 * Sets the confirm P id.
	 *
	 * @param confirmPid the new confirm P id
	 */
	void setConfirmPId(String confirmPid);
	
	/**
	 * Sets the workplace id.
	 *
	 * @param wkpId the new workplace id
	 */
	void setWorkplaceId(String wkpId);
	
	/**
	 * Sets the confirm cls status.
	 *
	 * @param confirmClsStatus the new confirm cls status
	 */
	void setConfirmClsStatus(ConfirmClsStatus confirmClsStatus);
		
	/**
	 * Sets the fixed date.
	 *
	 * @param fixedDate the new fixed date
	 */
	void setFixedDate(GeneralDate fixedDate);
	
	/**
	 * Sets the process date.
	 *
	 * @param processDate the new process date
	 */
	void setProcessYm(YearMonth processDate);
	
	/**
	 * Sets the cid.
	 *
	 * @param cid the new cid
	 */
	void setCid(String cid);
	
}

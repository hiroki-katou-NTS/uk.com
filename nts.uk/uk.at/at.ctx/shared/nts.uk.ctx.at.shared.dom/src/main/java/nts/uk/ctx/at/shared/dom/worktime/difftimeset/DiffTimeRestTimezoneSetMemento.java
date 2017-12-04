/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset;

import java.util.List;

/**
 * The Interface DiffTimeRestTimezoneSetMemento.
 */
public interface DiffTimeRestTimezoneSetMemento {
	
	/**
	 * Sets the rest timezone.
	 *
	 * @param restTimezone the new rest timezone
	 */
	public void setRestTimezone(List<DiffTimeDeductTimezone> restTimezone);
}

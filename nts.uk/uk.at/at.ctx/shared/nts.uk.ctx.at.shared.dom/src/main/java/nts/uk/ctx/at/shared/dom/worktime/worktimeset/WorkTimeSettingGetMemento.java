/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.worktimeset;

import nts.uk.ctx.at.shared.dom.worktime.common.AbolishAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.shr.com.primitive.Memo;

/**
 * The Interface WorkTimeSettingGetMemento.
 */
public interface WorkTimeSettingGetMemento {
	
	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	public String getCompanyId();

	/**
	 * Gets the worktime code.
	 *
	 * @return the worktime code
	 */
	public WorkTimeCode getWorktimeCode();

	/**
	 * Gets the work time division.
	 *
	 * @return the work time division
	 */
	public WorkTimeDivision getWorkTimeDivision();

	/**
	 * Gets the abolish atr.
	 *
	 * @return the abolish atr
	 */
	public AbolishAtr getAbolishAtr();

	/**
	 * Gets the color code.
	 *
	 * @return the color code
	 */
//	public ColorCode getColorCode();

	/**
	 * Gets the work time display name.
	 *
	 * @return the work time display name
	 */
	public WorkTimeDisplayName getWorkTimeDisplayName();

	/**
	 * Gets the memo.
	 *
	 * @return the memo
	 */
	public Memo getMemo();

	/**
	 * Gets the note.
	 *
	 * @return the note
	 */
	public WorkTimeNote getNote();

}

/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.worktimeset;

/**
 * The Interface WorkTimeDisplayNameSetMemento.
 */
public interface WorkTimeDisplayNameSetMemento {
	
	/**
	 * Sets the work time name.
	 *
	 * @param workTimeName the new work time name
	 */
	public void setWorkTimeName(WorkTimeName workTimeName);

	/**
	 * Sets the work time ab name.
	 *
	 * @param workTimeAbName the new work time ab name
	 */
	public void setWorkTimeAbName(WorkTimeAbName workTimeAbName);

	/**
	 * Sets the work time symbol.
	 *
	 * @param workTimeSymbol the new work time symbol
	 */
	public void setWorkTimeSymbol(WorkTimeSymbol workTimeSymbol);
}

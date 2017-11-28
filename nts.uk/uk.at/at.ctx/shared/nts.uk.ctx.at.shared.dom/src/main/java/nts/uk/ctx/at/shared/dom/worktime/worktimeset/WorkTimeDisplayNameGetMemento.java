/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.worktimeset;

/**
 * The Interface WorkTimeDisplayNameGetMemento.
 */
public interface WorkTimeDisplayNameGetMemento {
	
	/**
	 * Gets the work time name.
	 *
	 * @return the work time name
	 */
	public WorkTimeName getWorkTimeName();

    /**
     * Gets the work time ab name.
     *
     * @return the work time ab name
     */
    public WorkTimeAbName getWorkTimeAbName();

    /**
     * Gets the work time symbol.
     *
     * @return the work time symbol
     */
    public WorkTimeSymbol getWorkTimeSymbol();
}

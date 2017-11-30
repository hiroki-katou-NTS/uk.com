/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.worktimeset;

import lombok.Getter;

/**
 * The Class WorkTimeDisplayName.
 */
//就業時間帯の表示名
@Getter
public class WorkTimeDisplayName {
	
	/** The work time name. */
	//名称
	private WorkTimeName workTimeName;
	
	/** The work time ab name. */
	//略名
	private WorkTimeAbName workTimeAbName;
	
	/** The work time symbol. */
	//記号
	private WorkTimeSymbol workTimeSymbol;

	/**
	 * Instantiates a new work time display name.
	 *
	 * @param memento the memento
	 */
	public WorkTimeDisplayName(WorkTimeDisplayNameGetMemento memento) {
		this.workTimeName = memento.getWorkTimeName();
		this.workTimeAbName = memento.getWorkTimeAbName();
		this.workTimeSymbol = memento.getWorkTimeSymbol();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(WorkTimeDisplayNameSetMemento memento)
	{
		memento.setWorkTimeName(this.workTimeName);
		memento.setWorkTimeAbName(this.workTimeAbName);
		memento.setWorkTimeSymbol(this.workTimeSymbol);
	}
}

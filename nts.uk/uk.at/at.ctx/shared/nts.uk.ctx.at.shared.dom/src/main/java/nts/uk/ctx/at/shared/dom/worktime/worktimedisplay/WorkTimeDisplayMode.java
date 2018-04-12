/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.worktimedisplay;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeAggregateRoot;

/**
 * The Class WorkTimeDisplayMode.
 */
// 就業時間帯表示モード
@Getter
public class WorkTimeDisplayMode extends WorkTimeAggregateRoot {

	/** The company id. */
	// 会社ID
	private String companyId;

	/** The worktime code. */
	// 就業時間帯コード
	private WorkTimeCode worktimeCode;

	/** The display mode. */
	// 表示モード
	private DisplayMode displayMode;

	/**
	 * Instantiates a new work time display mode.
	 *
	 * @param comId the com id
	 * @param worktimeCode the worktime code
	 * @param displayMode the display mode
	 */
	public WorkTimeDisplayMode(String comId, String worktimeCode, int displayMode) {
		this.companyId = comId;
		this.worktimeCode = new WorkTimeCode(worktimeCode);
		this.displayMode = DisplayMode.valueOf(displayMode);
	}

	/**
	 * Instantiates a new work time display mode.
	 *
	 * @param memento
	 *            the memento
	 */
	public WorkTimeDisplayMode(WorkTimeDisplayModeGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.worktimeCode = memento.getWorktimeCode();
		this.displayMode = memento.getDisplayMode();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(WorkTimeDisplayModeSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setWorktimeCode(this.worktimeCode);
		memento.setWorkTimeDivision(this.displayMode);
	}

}

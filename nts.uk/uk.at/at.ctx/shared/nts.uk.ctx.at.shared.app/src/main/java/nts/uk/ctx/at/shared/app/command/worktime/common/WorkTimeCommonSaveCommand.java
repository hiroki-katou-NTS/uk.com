/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.common;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.command.worktime.predset.dto.PredetemineTimeSettingDto;
import nts.uk.ctx.at.shared.app.command.worktime.worktimeset.dto.WorkTimeDisplayModeDto;
import nts.uk.ctx.at.shared.app.command.worktime.worktimeset.dto.WorkTimeSettingDto;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.WorkTimeDisplayMode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;

/**
 * The Class WorkTimeCommonSaveCommand.
 */
@Getter
@Setter
public class WorkTimeCommonSaveCommand {

	/** The add mode. */
	private boolean addMode;

	/** The predseting. */
	private PredetemineTimeSettingDto predseting;

	/** The worktime setting. */
	private WorkTimeSettingDto worktimeSetting;

	/** The display mode. */
	private WorkTimeDisplayModeDto displayMode;

	/**
	 * To domain predetemine time setting.
	 *
	 * @return the predetemine time setting
	 */
	public PredetemineTimeSetting toDomainPredetemineTimeSetting() {
		return new PredetemineTimeSetting(this.predseting);
	}

	/**
	 * To domain work time setting.
	 *
	 * @return the work time setting
	 */
	public WorkTimeSetting toDomainWorkTimeSetting() {
		return new WorkTimeSetting(this.worktimeSetting);
	}

	/**
	 * To work time display mode.
	 *
	 * @return the work time display mode
	 */
	public WorkTimeDisplayMode toWorkTimeDisplayMode() {
		return new WorkTimeDisplayMode(this.displayMode);
	}
}

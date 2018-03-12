/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.find.worktime.predset.dto.PredetemineTimeSettingDto;
import nts.uk.ctx.at.shared.app.find.worktime.worktimeset.dto.WorkTimeDisplayModeDto;
import nts.uk.ctx.at.shared.app.find.worktime.worktimeset.dto.WorkTimeSettingDto;

@Getter
@Setter
public class WorkTimeCommonDto {

	/** The predseting. */
	private PredetemineTimeSettingDto predseting;

	/** The worktime setting. */
	private WorkTimeSettingDto worktimeSetting;

	/** The display mode. */
	private WorkTimeDisplayModeDto displayMode;

	/**
	 * Instantiates a new work time common dto.
	 *
	 * @param predseting
	 *            the predseting
	 * @param worktimeSetting
	 *            the worktime setting
	 */
	public WorkTimeCommonDto(PredetemineTimeSettingDto predseting, WorkTimeSettingDto worktimeSetting, WorkTimeDisplayModeDto displayMode) {
		this.predseting = predseting;
		this.worktimeSetting = worktimeSetting;
		this.displayMode = displayMode;
	}

	/**
	 * Instantiates a new work time common dto.
	 */
	public WorkTimeCommonDto() {
	}

}

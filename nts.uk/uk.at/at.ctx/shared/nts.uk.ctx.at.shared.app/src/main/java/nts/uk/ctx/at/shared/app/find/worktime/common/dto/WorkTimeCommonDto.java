/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.find.worktime.predset.dto.PredetemineTimeSettingDto;
import nts.uk.ctx.at.shared.app.find.worktime.worktimeset.dto.WorkTimeSettingDto;

@Getter
@Setter
public class WorkTimeCommonDto {


	/** The predseting. */
	private PredetemineTimeSettingDto predseting;
	
	/** The worktime setting. */
	private WorkTimeSettingDto worktimeSetting;

	/**
	 * @param predseting
	 * @param worktimeSetting
	 */
	public WorkTimeCommonDto(PredetemineTimeSettingDto predseting, WorkTimeSettingDto worktimeSetting) {
		this.predseting = predseting;
		this.worktimeSetting = worktimeSetting;
	}
	
	/**
	 * Instantiates a new work time common dto.
	 */
	public WorkTimeCommonDto() {
	}
	
}

/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.fixedset;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.command.worktime.common.WorkTimeCommonSaveCommand;
import nts.uk.ctx.at.shared.app.command.worktime.fixedset.dto.FixedWorkSettingDto;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;

/**
 * The Class FixedWorkSettingSaveCommand.
 */
@Getter
@Setter
public class FixedWorkSettingSaveCommand extends WorkTimeCommonSaveCommand {
	
	/** The fixed work setting. */
	private FixedWorkSettingDto fixedWorkSetting;
	
	/**
	 * To domain fixed work setting.
	 *
	 * @return the fixed work setting
	 */
	public FixedWorkSetting toDomainFixedWorkSetting(){
		return new FixedWorkSetting(this.fixedWorkSetting);
	}
}

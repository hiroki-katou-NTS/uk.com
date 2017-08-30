/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.basicworkregister;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.app.command.shift.basicworkregister.dto.BasicWorkSettingDto;

/**
 * The Class ClassifiBWSaveCommand.
 */
@Getter
@Setter
public class BaseBWSaveCommand {

	/** The basic work setting. */
	protected List<BasicWorkSettingDto> basicWorkSetting;

}

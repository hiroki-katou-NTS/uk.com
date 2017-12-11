/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.flexset;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.command.worktime.common.WorkTimeCommonSaveCommand;
import nts.uk.ctx.at.shared.app.command.worktime.flexset.dto.FlexWorkSettingDto;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;

/**
 * The Class FlexWorkSettingSaveCommand.
 */
@Getter
@Setter
public class FlexWorkSettingSaveCommand extends WorkTimeCommonSaveCommand {

	/** The flex work setting. */
	private FlexWorkSettingDto flexWorkSetting;
	
	/**
	 * To domain flex work setting.
	 *
	 * @return the flex work setting
	 */
	public FlexWorkSetting toDomainFlexWorkSetting(){
		return new FlexWorkSetting(this.flexWorkSetting);
	}
}

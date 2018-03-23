/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.flowset;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.command.worktime.common.WorkTimeCommonSaveCommand;
import nts.uk.ctx.at.shared.app.command.worktime.flowset.dto.FlWorkSettingDto;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;

/**
 * The Class FlowWorkSettingSaveCommand.
 */
@Getter
@Setter
public class FlowWorkSettingSaveCommand extends WorkTimeCommonSaveCommand {

	/** The flow work setting. */
	private FlWorkSettingDto flowWorkSetting;

	/**
	 * To domain flow work setting.
	 *
	 * @return the flow work setting
	 */
	public FlowWorkSetting toDomainFlowWorkSetting() {
		return new FlowWorkSetting(this.flowWorkSetting);
	}
}

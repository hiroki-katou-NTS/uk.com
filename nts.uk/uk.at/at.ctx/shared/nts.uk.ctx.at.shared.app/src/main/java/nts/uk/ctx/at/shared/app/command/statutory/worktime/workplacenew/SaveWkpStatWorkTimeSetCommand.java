/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.workplacenew;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.common.WkpDeforLaborSettingDto;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.common.WkpFlexSettingDto;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.common.WkpNormalSettingDto;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.common.WkpWorkingTimeSettingDto;

/**
 * The Class SaveComStatWorkTimeSetCommand.
 */
@Getter
@Setter
@NoArgsConstructor
public class SaveWkpStatWorkTimeSetCommand{

	/** The year. */
	private int year;
	
	/** The employee id. */
	private String workplaceId;

	/** The normal setting. */
	private WkpNormalSettingDto normalSetting;

	/** The flex setting. */
	private WkpFlexSettingDto flexSetting;

	/** The defor labor setting. */
	private WkpDeforLaborSettingDto deforLaborSetting;

	/** The regular labor time. */
	private WkpWorkingTimeSettingDto regularLaborTime;

	/** The trans labor time. */
	private WkpWorkingTimeSettingDto transLaborTime;
}

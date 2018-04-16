/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.companyNew;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.common.DeforLaborSettingDto;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.common.FlexSettingDto;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.common.NormalSettingDto;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.common.WorkingTimeSettingDto;

/**
 * The Class SaveComStatWorkTimeSetCommand.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveComStatWorkTimeSetCommand{

	/** The year. */
	private int year;

	/** The normal setting. */
	private NormalSettingDto normalSetting;

	/** The flex setting. */
	private FlexSettingDto flexSetting;

	/** The defor labor setting. */
	private DeforLaborSettingDto deforLaborSetting;

	/** The regular labor time. */
	private WorkingTimeSettingDto regularLaborTime;

	/** The trans labor time. */
	private WorkingTimeSettingDto transLaborTime;
}

/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.companyNew;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.common.WorkingTimeSettingDto;

/**
 * The Class ComStatWorkTimeSetDto.
 */
@Getter
@Setter
@Builder
public class ComStatWorkTimeSetDto {
	
	/** The year. */
	private int year;

	/** The defor labor setting. */
	private ComDeforLaborSettingDto deforLaborSetting;

	/** The flex setting. */
	private ComFlexSettingDto flexSetting;

	/** The normal setting. */
	private ComNormalSettingDto normalSetting;

	/** The regular labor time. */
	private WorkingTimeSettingDto regularLaborTime;

	/** The trans labor time. */
	private WorkingTimeSettingDto transLaborTime;

}

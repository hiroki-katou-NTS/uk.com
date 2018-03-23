/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.companyNew;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * The Class ComStatWorkTimeSetDto.
 */
@Getter
@Setter
@Builder
public class ComStatWorkTimeSetDto {

	/** The defor labor setting. */
	private ComDeforLaborSettingDto deforLaborSetting;

	/** The flex setting. */
	private ComFlexSettingDto flexSetting;

	/** The normal setting. */
	private ComNormalSettingDto normalSetting;

	/** The regular labor time. */
	private ComRegularLaborTimeDto regularLaborTime;

	/** The trans labor time. */
	private ComTransLaborTimeDto transLaborTime;

}

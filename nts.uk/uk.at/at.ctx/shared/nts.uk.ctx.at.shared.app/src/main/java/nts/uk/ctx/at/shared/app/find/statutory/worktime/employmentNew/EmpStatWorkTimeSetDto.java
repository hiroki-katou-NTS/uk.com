/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.employmentNew;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * The Class ComStatWorkTimeSetDto.
 */
@Getter
@Setter
@Builder
public class EmpStatWorkTimeSetDto {

	/** The defor labor setting. */
	private EmpDeforLaborSettingDto deforLaborSetting;

	/** The flex setting. */
	private EmpFlexSettingDto flexSetting;

	/** The normal setting. */
	private EmpNormalSettingDto normalSetting;

	/** The regular labor time. */
	private EmpRegularWorkHourDto regularLaborTime;

	/** The trans labor time. */
	private EmpTransLaborHourDto transLaborTime;

}

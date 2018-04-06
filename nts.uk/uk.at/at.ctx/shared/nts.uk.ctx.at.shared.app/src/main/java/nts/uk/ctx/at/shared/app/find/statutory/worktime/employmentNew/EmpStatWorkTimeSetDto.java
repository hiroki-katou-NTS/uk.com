/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.employmentNew;

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
public class EmpStatWorkTimeSetDto {
	
	/** The year. */
	private int year;
	
	/** The employee id. */
	private String employmentCode;

	/** The defor labor setting. */
	private EmpDeforLaborSettingDto deforLaborSetting;

	/** The flex setting. */
	private EmpFlexSettingDto flexSetting;

	/** The normal setting. */
	private EmpNormalSettingDto normalSetting;

	/** The regular labor time. */
	private WorkingTimeSettingDto regularLaborTime;

	/** The trans labor time. */
	private WorkingTimeSettingDto transLaborTime;

}

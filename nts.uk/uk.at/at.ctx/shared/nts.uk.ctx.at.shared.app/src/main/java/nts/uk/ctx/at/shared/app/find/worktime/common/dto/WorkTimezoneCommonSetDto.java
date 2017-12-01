/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class WorkTimezoneCommonSetDto.
 */
@Getter
@Setter
public class WorkTimezoneCommonSetDto {
	
	/** The Zero H stradd calculate set. */
	private boolean ZeroHStraddCalculateSet;

	/** The interval set. */
	private IntervalTimeSettingDto intervalSet;

	/** The sub hol time set. */
	private WorkTimezoneOtherSubHolTimeSetDto subHolTimeSet;

	/** The raising salary set. */
	private String raisingSalarySet;

	/** The medical set. */
	private List<WorkTimezoneMedicalSetDto> medicalSet;

	/** The go out set. */
	private WorkTimezoneGoOutSetDto goOutSet;

	/** The stamp set. */
	private WorkTimezoneStampSetDto stampSet;

	/** The late night time set. */
	private TimeRoundingSettingDto lateNightTimeSet;

	/** The short time work set. */
	private WorkTimezoneShortTimeWorkSetDto shortTimeWorkSet;

	/** The extraord time set. */
	private WorkTimezoneExtraordTimeSetDto extraordTimeSet;

	/** The late early set. */
	private WorkTimezoneLateEarlySetDto lateEarlySet;

}

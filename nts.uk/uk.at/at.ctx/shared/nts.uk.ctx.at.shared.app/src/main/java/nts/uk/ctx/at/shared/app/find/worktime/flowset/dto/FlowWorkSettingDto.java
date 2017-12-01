/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.flowset.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.FlowWorkRestSettingDto;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.WorkTimezoneCommonSetDto;

/**
 * The Class FlowWorkSettingDto.
 */
@Getter
@Setter
public class FlowWorkSettingDto {

	/** The working code. */
	private String workingCode;

	/** The rest setting. */
	private FlowWorkRestSettingDto restSetting;

	/** The offday work timezone. */
	private FlowOffdayWorkTimezoneDto offdayWorkTimezone;

	/** The common setting. */
	private WorkTimezoneCommonSetDto commonSetting;

	/** The half day work timezone. */
	private FlowHalfDayWorkTimezoneDto halfDayWorkTimezone;

	/** The stamp reflect timezone. */
	private FlowStampReflectTimezoneDto stampReflectTimezone;

	/** The designated setting. */
	private Integer designatedSetting;

	/** The flow setting. */
	private FlowWorkDedicateSettingDto flowSetting;
}

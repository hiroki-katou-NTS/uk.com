/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.flexset.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.FlowWorkRestSettingDto;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.WorkTimezoneCommonSetDto;

@Getter
@Setter
public class FlexWorkSettingDto {

	/** The work time code. */
	private String workTimeCode;

	/** The core time setting. */
	private CoreTimeSettingDto coreTimeSetting;

	/** The rest setting. */
	private FlowWorkRestSettingDto restSetting;

	/** The offday work time. */
	private FlexOffdayWorkTimeDto offdayWorkTime;

	/** The common setting. */
	private WorkTimezoneCommonSetDto commonSetting;

	/** The use half day shift. */
	private boolean useHalfDayShift;

	/** The half day work timezone. */
	private List<FlexHalfDayWorkTimeDto> halfDayWorkTimezone;

	/** The stamp reflect timezone. */
	private List<StampReflectTimezoneDto> stampReflectTimezone;

	/** The calculate setting. */
	private FlexCalcSettingDto calculateSetting;
}

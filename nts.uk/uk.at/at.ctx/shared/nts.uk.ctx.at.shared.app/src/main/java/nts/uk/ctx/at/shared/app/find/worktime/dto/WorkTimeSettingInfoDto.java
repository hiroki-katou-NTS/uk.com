/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.WorkTimeCommonDto;
import nts.uk.ctx.at.shared.app.find.worktime.difftimeset.dto.DiffTimeWorkSettingDto;
import nts.uk.ctx.at.shared.app.find.worktime.fixedset.dto.FixedWorkSettingDto;
import nts.uk.ctx.at.shared.app.find.worktime.flexset.dto.FlexWorkSettingDto;
import nts.uk.ctx.at.shared.app.find.worktime.flowset.dto.FlWorkSettingDto;

/**
 * The Class WorkTimeSettingInfoDto.
 */

@Getter
@Setter
public class WorkTimeSettingInfoDto extends WorkTimeCommonDto{

	/** The flex work setting. */
	private FlexWorkSettingDto flexWorkSetting;
	
	/** The fixed work setting. */
	private FixedWorkSettingDto fixedWorkSetting;
	
	/** The flow work setting. */
	private FlWorkSettingDto flowWorkSetting;
	
	/** The diff time work setting. */
	private DiffTimeWorkSettingDto diffTimeWorkSetting;
}

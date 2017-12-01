/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.flexset.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.FlowWorkRestTimezoneDto;
import nts.uk.ctx.at.shared.app.find.worktime.fixedset.dto.HDWorkTimeSheetSettingDto;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.HDWorkTimeSheetSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexOffdayWorkTimeSetMemento;

/**
 * The Class FlexOffdayWorkTimeDto.
 */
@Getter
@Setter
public class FlexOffdayWorkTimeDto implements FlexOffdayWorkTimeSetMemento{

	/** The work timezone. */
	private List<HDWorkTimeSheetSettingDto> workTimezone;

	/** The rest timezone. */
	private FlowWorkRestTimezoneDto restTimezone;

	@Override
	public void setWorkTimezone(List<HDWorkTimeSheetSetting> workTimezone) {
		
	}

	@Override
	public void setRestTimezone(FlowWorkRestTimezone restTimezone) {
		// TODO Auto-generated method stub
		
	}
}

/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.flexset.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.app.command.worktime.common.dto.FlowWorkRestTimezoneDto;
import nts.uk.ctx.at.shared.app.command.worktime.common.dto.HDWorkTimeSheetSettingDto;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexOffdayWorkTimeGetMemento;

/**
 * The Class FlexOffdayWorkTimeDto.
 */
@Getter
@Setter
public class FlexOffdayWorkTimeDto implements FlexOffdayWorkTimeGetMemento{

	/** The lst work timezone. */
	private List<HDWorkTimeSheetSettingDto> lstWorkTimezone;

	/** The rest timezone. */
	private FlowWorkRestTimezoneDto restTimezone;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexOffdayWorkTimeGetMemento#getLstWorkTimezone()
	 */
	@Override
	public List<HDWorkTimeSheetSetting> getLstWorkTimezone() {
		if(CollectionUtil.isEmpty(this.lstWorkTimezone)){
			return new ArrayList<>();
		}
		return this.lstWorkTimezone.stream().map(worktimezone -> new HDWorkTimeSheetSetting(worktimezone))
				.collect(Collectors.toList());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexOffdayWorkTimeGetMemento#getRestTimezone()
	 */
	@Override
	public FlowWorkRestTimezone getRestTimezone() {
		return new FlowWorkRestTimezone(this.restTimezone);
	}
	

}

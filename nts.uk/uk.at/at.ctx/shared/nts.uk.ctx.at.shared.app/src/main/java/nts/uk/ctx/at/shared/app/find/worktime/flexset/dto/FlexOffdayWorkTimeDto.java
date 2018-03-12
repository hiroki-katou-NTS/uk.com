/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.flexset.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.FlowWorkRestTimezoneDto;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.HDWorkTimeSheetSettingDto;
import nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexOffdayWorkTimeSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestTimezone;

/**
 * The Class FlexOffdayWorkTimeDto.
 */
@Getter
@Setter
public class FlexOffdayWorkTimeDto implements FlexOffdayWorkTimeSetMemento {

	/** The lst work timezone. */
	private List<HDWorkTimeSheetSettingDto> lstWorkTimezone;

	/** The rest timezone. */
	private FlowWorkRestTimezoneDto restTimezone;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flexset.FlexOffdayWorkTimeSetMemento#
	 * setLstWorkTimezone(java.util.List)
	 */
	@Override
	public void setLstWorkTimezone(List<HDWorkTimeSheetSetting> lstWorkTimezone) {
		if (CollectionUtil.isEmpty(lstWorkTimezone)) {
			this.lstWorkTimezone = new ArrayList<>();
		} else {
			this.lstWorkTimezone = lstWorkTimezone.stream().map(domain -> {
				HDWorkTimeSheetSettingDto dto = new HDWorkTimeSheetSettingDto();
				domain.saveToMemento(dto);
				return dto;
			}).collect(Collectors.toList());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flexset.FlexOffdayWorkTimeSetMemento#
	 * setRestTimezone(nts.uk.ctx.at.shared.dom.worktime.common.
	 * FlowWorkRestTimezone)
	 */
	@Override
	public void setRestTimezone(FlowWorkRestTimezone restTimezone) {
		if (restTimezone != null) {
			this.restTimezone = new FlowWorkRestTimezoneDto();
			restTimezone.saveToMemento(this.restTimezone);
		}
	}

}

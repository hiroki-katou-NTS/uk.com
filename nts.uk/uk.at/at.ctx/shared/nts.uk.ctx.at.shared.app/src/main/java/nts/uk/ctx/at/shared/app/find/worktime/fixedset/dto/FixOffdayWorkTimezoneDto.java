/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.fixedset.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.HDWorkTimeSheetSettingDto;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.TimezoneOfFixedRestTimeSetDto;
import nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixOffdayWorkTimezoneSetMemento;

/**
 * The Class FixOffdayWorkTimezoneDto.
 */
@Getter
@Setter
public class FixOffdayWorkTimezoneDto implements FixOffdayWorkTimezoneSetMemento {

	/** The rest timezone. */
	private TimezoneOfFixedRestTimeSetDto restTimezone;

	/** The lst work timezone. */
	private List<HDWorkTimeSheetSettingDto> lstWorkTimezone;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * FixOffdayWorkTimezoneSetMemento#setRestTimezone(nts.uk.ctx.at.shared.dom.
	 * worktime.fixedset.FixRestTimezoneSet)
	 */
	@Override
	public void setRestTimezone(TimezoneOfFixedRestTimeSet restTimezone) {
		if (restTimezone != null) {
			this.restTimezone = new TimezoneOfFixedRestTimeSetDto();
			restTimezone.saveToMemento(this.restTimezone);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * FixOffdayWorkTimezoneSetMemento#setLstWorkTimezone(java.util.List)
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

}

/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.fixedset.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.app.command.worktime.common.dto.HDWorkTimeSheetSettingDto;
import nts.uk.ctx.at.shared.app.command.worktime.common.dto.TimezoneOfFixedRestTimeSetDto;
import nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixOffdayWorkTimezoneGetMemento;

/**
 * The Class FixOffdayWorkTimezoneDto.
 */
@Getter
@Setter
public class FixOffdayWorkTimezoneDto implements FixOffdayWorkTimezoneGetMemento {

	/** The rest timezone. */
	private TimezoneOfFixedRestTimeSetDto restTimezone;

	/** The lst work timezone. */
	private List<HDWorkTimeSheetSettingDto> lstWorkTimezone;

	/** The work time no. */
	private int workTimeNo = 0;
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * FixOffdayWorkTimezoneGetMemento#getRestTimezone()
	 */
	@Override
	public TimezoneOfFixedRestTimeSet getRestTimezone() {
		return new TimezoneOfFixedRestTimeSet(this.restTimezone);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * FixOffdayWorkTimezoneGetMemento#getLstWorkTimezone()
	 */
	@Override
	public List<HDWorkTimeSheetSetting> getLstWorkTimezone() {
		if (CollectionUtil.isEmpty(this.lstWorkTimezone)) {
			return new ArrayList<>();
		}
		this.lstWorkTimezone = this.lstWorkTimezone.stream().sorted((timezone1, timezone2) -> timezone1.getTimezone()
				.getStart().compareTo(timezone2.getTimezone().getStart())).collect(Collectors.toList());

		workTimeNo = 0;
		this.lstWorkTimezone.forEach(timezone -> {
			workTimeNo++;
			timezone.setWorkTimeNo(workTimeNo);
		});
		return this.lstWorkTimezone.stream().map(item -> new HDWorkTimeSheetSetting(item)).collect(Collectors.toList());
	}

}

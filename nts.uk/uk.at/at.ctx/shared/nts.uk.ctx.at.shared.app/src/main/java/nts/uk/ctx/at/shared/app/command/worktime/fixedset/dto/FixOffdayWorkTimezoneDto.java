/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.fixedset.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Value;
import nts.uk.ctx.at.shared.app.command.worktime.common.dto.HDWorkTimeSheetSettingDto;
import nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixOffdayWorkTimezoneGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixRestTimezoneSet;

/**
 * The Class FixOffdayWorkTimezoneDto.
 */
@Value
public class FixOffdayWorkTimezoneDto implements FixOffdayWorkTimezoneGetMemento {

	/** The rest timezone. */
	private FixRestTimezoneSetDto restTimezone;

	/** The lst work timezone. */
	private List<HDWorkTimeSheetSettingDto> lstWorkTimezone;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * FixOffdayWorkTimezoneGetMemento#getRestTimezone()
	 */
	@Override
	public FixRestTimezoneSet getRestTimezone() {
		return new FixRestTimezoneSet(this.restTimezone);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * FixOffdayWorkTimezoneGetMemento#getLstWorkTimezone()
	 */
	@Override
	public List<HDWorkTimeSheetSetting> getLstWorkTimezone() {
		return this.lstWorkTimezone.stream().map(item -> new HDWorkTimeSheetSetting(item)).collect(Collectors.toList());
	}

}

/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.difftimeset.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.StampReflectTimezoneDto;
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeStampReflectSetMemento;

/**
 * The Class DiffTimeWorkStampReflectTimezone.
 */
@Getter
@Setter
public class DiffTimeWorkStampReflectTimezoneDto implements DiffTimeStampReflectSetMemento {

	/** The stamp reflect timezone. */
	private List<StampReflectTimezoneDto> stampReflectTimezone;

	/** The is update start time. */
	private boolean isUpdateStartTime;

	@Override
	public void setStampReflectTimezone(List<StampReflectTimezone> stampReflectTimezone) {
		this.stampReflectTimezone = new ArrayList<>();
		stampReflectTimezone.stream().forEach(item -> {
			StampReflectTimezoneDto dto = new StampReflectTimezoneDto();
			item.saveToMemento(dto);
			this.stampReflectTimezone.add(dto);
		});
	}

	@Override
	public void setIsUpdateStartTime(boolean isUpdateStartTime) {
		this.isUpdateStartTime = isUpdateStartTime;
	}
}

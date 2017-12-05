/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.difftimeset.dto;

import lombok.Getter;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.StampReflectTimezoneDto;
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeStampReflectSetMemento;

/**
 * The Class DiffTimeWorkStampReflectTimezone.
 */
@Getter
public class DiffTimeWorkStampReflectTimezoneDto implements DiffTimeStampReflectSetMemento{

	/** The stamp reflect timezone. */
	private StampReflectTimezoneDto stampReflectTimezone;

	/** The is update start time. */
	private boolean isUpdateStartTime;
	
	@Override
	public void setStampReflectTimezone(StampReflectTimezone stampReflectTimezone) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setIsUpdateStartTime(boolean isUpdateStartTime) {
		// TODO Auto-generated method stub
		
	}
}

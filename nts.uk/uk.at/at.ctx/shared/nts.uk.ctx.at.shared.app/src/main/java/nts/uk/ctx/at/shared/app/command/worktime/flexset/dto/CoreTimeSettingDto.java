/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.flexset.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.usecls.ApplyAtr;
import nts.uk.ctx.at.shared.dom.worktime.flexset.CoreTimeSettingGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.flexset.OutingCalcWithinCoreTime;
import nts.uk.ctx.at.shared.dom.worktime.flexset.TimeSheet;

/**
 * The Class CoreTimeSettingDto.
 */
@Getter
@Setter
public class CoreTimeSettingDto implements CoreTimeSettingGetMemento{
	

	/** The core time sheet. */
	private TimeSheetDto coreTimeSheet;
	
	/** The timesheet. */
	private Integer timesheet;
	
	/** The min work time. */
	private Integer minWorkTime;

	/** The go out calc. */
	private OutingCalcWithinCoreTimeDto goOutCalc;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.CoreTimeSettingGetMemento#
	 * getCoreTimeSheet()
	 */
	@Override
	public TimeSheet getCoreTimeSheet() {
		return new TimeSheet(this.coreTimeSheet);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.CoreTimeSettingGetMemento#
	 * getTimesheet()
	 */
	@Override
	public ApplyAtr getTimesheet() {
		return ApplyAtr.valueOf(this.timesheet);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.CoreTimeSettingGetMemento#
	 * getMinWorkTime()
	 */
	@Override
	public AttendanceTime getMinWorkTime() {
		return this.minWorkTime == null ? null : new AttendanceTime(this.minWorkTime);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.CoreTimeSettingGetMemento#
	 * getGoOutCalc()
	 */
	@Override
	public OutingCalcWithinCoreTime getGoOutCalc() {
		return new OutingCalcWithinCoreTime(this.goOutCalc);
	}
}

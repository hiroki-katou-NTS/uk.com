/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestSettingGetMemento;

/**
 * The Class FlowRestSettingDto.
 */
@Getter
@Setter
public class FlowRestSettingDto implements FlowRestSettingGetMemento {

	/** The flow rest time. */
	private Integer flowRestTime;

	/** The flow passage time. */
	private Integer flowPassageTime;
	
	/** The Constant DEFAULT_ZERO. */
	private static final Integer DEFAULT_ZERO = 0;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowRestSettingGetMemento#getFlowRestTime()
	 */
	@Override
	public AttendanceTime getFlowRestTime() {
		return new AttendanceTime(this.flowRestTime == null ? DEFAULT_ZERO : this.flowRestTime);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowRestSettingGetMemento#getFlowPassageTime()
	 */
	@Override
	public AttendanceTime getFlowPassageTime() {
		return new AttendanceTime(this.flowPassageTime == null ? DEFAULT_ZERO : this.flowPassageTime);
	}
	

}

/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.TimeRoundingSettingDto;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowFixedRestSet;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestSet;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestSettingDetailGetMemento;

/**
 * The Class FlowWorkRestSettingDetailDto.
 */
@Getter
@Setter
public class FlowWorkRestSettingDetailDto implements FlowWorkRestSettingDetailGetMemento {

	/** The flow rest setting. */
	private FlowRestSetDto flowRestSetting;

	/** The flow fixed rest setting. */
	private FlowFixedRestSetDto flowFixedRestSetting;

	/** The use plural work rest time. */
	private boolean usePluralWorkRestTime;

	/** The rounding break multiple work. */
	private TimeRoundingSettingDto roundingBreakMultipleWork;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * FlowWorkRestSettingDetailGetMemento#getFlowRestSetting()
	 */
	@Override
	public FlowRestSet getFlowRestSetting() {
		return new FlowRestSet(this.flowRestSetting);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * FlowWorkRestSettingDetailGetMemento#getFlowFixedRestSetting()
	 */
	@Override
	public FlowFixedRestSet getFlowFixedRestSetting() {
		return new FlowFixedRestSet(this.flowFixedRestSetting);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * FlowWorkRestSettingDetailGetMemento#getUsePluralWorkRestTime()
	 */
	@Override
	public boolean getUsePluralWorkRestTime() {
		return this.usePluralWorkRestTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.
	 * FlowWorkRestSettingDetailGetMemento#getRoundingBreakMultipleWork()
	 */
	@Override
	public TimeRoundingSetting getRoundingBreakMultipleWork() {
		return new TimeRoundingSetting(this.roundingBreakMultipleWork.getRoundingTime(),
				this.roundingBreakMultipleWork.getRounding());
	}

}

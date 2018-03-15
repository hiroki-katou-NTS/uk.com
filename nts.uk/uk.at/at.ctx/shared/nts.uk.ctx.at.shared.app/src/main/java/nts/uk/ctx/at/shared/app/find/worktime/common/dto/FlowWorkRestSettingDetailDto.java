/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowFixedRestSet;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestSet;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestSettingDetailSetMemento;

/**
 * The Class FlowWorkRestSettingDetailDto.
 */
@Getter
@Setter
public class FlowWorkRestSettingDetailDto implements FlowWorkRestSettingDetailSetMemento {

	/** The flow rest setting. */
	private FlowRestSetDto flowRestSetting;

	/** The flow fixed rest setting. */
	private FlowFixedRestSetDto flowFixedRestSetting;

	/** The use plural work rest time. */
	private boolean usePluralWorkRestTime;

	/** The rounding break multiple work. */
	private TimeRoundingSettingDto roundingBreakMultipleWork;

	/**
	 * Instantiates a new flow work rest setting detail dto.
	 */
	public FlowWorkRestSettingDetailDto() {
		this.flowRestSetting = new FlowRestSetDto();
		this.flowFixedRestSetting = new FlowFixedRestSetDto();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * FlowWorkRestSettingDetailSetMemento#setFlowRestSetting(nts.uk.ctx.at.
	 * shared.dom.worktime.common.FlowRestSet)
	 */
	@Override
	public void setFlowRestSetting(FlowRestSet set) {
		if (set != null) {
			this.flowRestSetting = new FlowRestSetDto();
			set.saveToMemento(this.flowRestSetting);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * FlowWorkRestSettingDetailSetMemento#setFlowFixedRestSetting(nts.uk.ctx.at
	 * .shared.dom.worktime.common.FlowFixedRestSet)
	 */
	@Override
	public void setFlowFixedRestSetting(FlowFixedRestSet set) {
		if (set != null) {
			this.flowFixedRestSetting = new FlowFixedRestSetDto();
			set.saveToMemento(this.flowFixedRestSetting);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * FlowWorkRestSettingDetailSetMemento#setUsePluralWorkRestTime(boolean)
	 */
	@Override
	public void setUsePluralWorkRestTime(boolean val) {
		this.usePluralWorkRestTime = val;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.
	 * FlowWorkRestSettingDetailSetMemento#setRoundingBreakMultipleWork(nts.uk.
	 * ctx.at.shared.dom.common.timerounding.TimeRoundingSetting)
	 */
	@Override
	public void setRoundingBreakMultipleWork(TimeRoundingSetting val) {
		this.roundingBreakMultipleWork = new TimeRoundingSettingDto(val.getRoundingTime().value,
				val.getRounding().value);
	}

}

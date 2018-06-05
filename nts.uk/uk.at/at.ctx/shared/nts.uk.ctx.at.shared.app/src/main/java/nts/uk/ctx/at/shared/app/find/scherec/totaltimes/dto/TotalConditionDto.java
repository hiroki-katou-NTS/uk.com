/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.scherec.totaltimes.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.ConditionThresholdLimit;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalConditionSetMemento;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.UseAtr;

/**
 * The Class TotalConditionDto.
 */
@Getter
@Setter
public class TotalConditionDto implements TotalConditionSetMemento {

	/** The upper limit setting atr. */
	// 上限設定区分
	private Integer upperLimitSettingAtr;

	/** The lower limit setting atr. */
	// 下限設定区分
	private Integer lowerLimitSettingAtr;

	/** The thresold upper limit. */
	// 閾値上限
	private Long thresoldUpperLimit;

	/** The thresold lower limit. */
	// 閾値下限
	private Long thresoldLowerLimit;

	/** The attendance item id. */
	// 勤怠項目ID
	private Integer attendanceItemId;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.shift.total.times.TotalConditionSetMemento#
	 * setThresoldUpperLimit(nts.uk.ctx.at.schedule.dom.shift.total.times.
	 * setting.ConditionThresholdLimit)
	 */
	@Override
	public void setThresoldUpperLimit(ConditionThresholdLimit setThresoldUpperLimit) {
		this.thresoldUpperLimit = setThresoldUpperLimit.v().longValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.shift.total.times.TotalConditionSetMemento#
	 * setThresoldLowerLimit(nts.uk.ctx.at.schedule.dom.shift.total.times.
	 * setting.ConditionThresholdLimit)
	 */
	@Override
	public void setThresoldLowerLimit(ConditionThresholdLimit setThresoldLowerLimit) {
		this.thresoldLowerLimit = setThresoldLowerLimit.v().longValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalConditionSetMemento#
	 * setUpperLimitSettingAtr(nts.uk.ctx.at.shared.dom.scherec.totaltimes.
	 * UseAtr)
	 */
	@Override
	public void setUpperLimitSettingAtr(UseAtr upperLimitSettingAtr) {
		this.upperLimitSettingAtr = upperLimitSettingAtr.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalConditionSetMemento#
	 * setLowerLimitSettingAtr(nts.uk.ctx.at.shared.dom.scherec.totaltimes.
	 * UseAtr)
	 */
	@Override
	public void setLowerLimitSettingAtr(UseAtr lowerLimitSettingAtr) {
		this.lowerLimitSettingAtr = lowerLimitSettingAtr.value;
	}

	
	
	/**
	 * Sets the attendance item id.
	 *
	 * @param attendanceItemId the new attendance item id
	 */
	@Override
	public void setAttendanceItemId(Integer attendanceItemId) {
		this.attendanceItemId = attendanceItemId;
	}

}

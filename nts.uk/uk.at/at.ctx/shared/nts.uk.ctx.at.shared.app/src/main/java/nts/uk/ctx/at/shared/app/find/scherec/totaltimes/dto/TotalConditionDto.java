/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.scherec.totaltimes.dto;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.ConditionThresholdLimit;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.UseAtr;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.memento.TotalConditionSetMemento;

/**
 * The Class TotalConditionDto.
 */
@Getter
@Setter
public class TotalConditionDto implements TotalConditionSetMemento {

	/** 上限設定区分 */
	private Integer upperLimitSettingAtr;

	/** 下限設定区分 */
	private Integer lowerLimitSettingAtr;

	/** 閾値上限 */
	private Long thresoldUpperLimit;

	/** 閾値下限 */
	private Long thresoldLowerLimit;

	/** 勤怠項目ID */
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
	public void setThresoldUpperLimit(Optional<ConditionThresholdLimit> setThresoldUpperLimit) {
		this.thresoldUpperLimit = setThresoldUpperLimit.map(c -> c.v().longValue()).orElse(null);
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
	public void setThresoldLowerLimit(Optional<ConditionThresholdLimit> setThresoldLowerLimit) {
		this.thresoldLowerLimit = setThresoldLowerLimit.map(c -> c.v().longValue()).orElse(null);
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
	public void setAttendanceItemId(Optional<Integer> attendanceItemId) {
		this.attendanceItemId = attendanceItemId.orElse(null);
	}

}

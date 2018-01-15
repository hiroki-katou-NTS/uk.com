/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.shift.estimate.commonset.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import nts.uk.ctx.at.schedule.app.command.shift.estimate.guideline.dto.EstimatedAlarmColorDto;
import nts.uk.ctx.at.schedule.app.command.shift.estimate.guideline.dto.ReferenceConditionDto;
import nts.uk.ctx.at.schedule.dom.shift.estimate.commonset.CommonGuidelineSettingSetMemento;
import nts.uk.ctx.at.schedule.dom.shift.estimate.commonset.EstimatedAlarmColor;
import nts.uk.ctx.at.schedule.dom.shift.estimate.commonset.ReferenceCondition;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class CommonGuidelineSettingDto.
 */
@Data
public class CommonGuidelineSettingDto implements CommonGuidelineSettingSetMemento {

	/** The alarm colors. */
	private List<EstimatedAlarmColorDto> alarmColors;

	/** The estimate time. */
	private ReferenceConditionDto estimateTime;

	/** The estimate price. */
	private ReferenceConditionDto estimatePrice;

	/** The estimate number of days. */
	private ReferenceConditionDto estimateNumberOfDays;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.guideline.
	 * CommonGuidelineSettingSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.
	 * common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		// Do nothing.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.guideline.
	 * CommonGuidelineSettingSetMemento#setAlarmColors(java.util.List)
	 */
	@Override
	public void setAlarmColors(List<EstimatedAlarmColor> alarmColors) {
		this.alarmColors = alarmColors.stream()
				.map(item -> new EstimatedAlarmColorDto(item.getGuidelineCondition().value,
						item.getColor().v()))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.guideline.
	 * CommonGuidelineSettingSetMemento#setEstimateTime(nts.uk.ctx.at.schedule.
	 * dom.shift.estimate.guideline.ReferenceCondition)
	 */
	@Override
	public void setEstimateTime(ReferenceCondition estimateTime) {
		this.estimateTime = new ReferenceConditionDto(
				estimateTime.getYearlyDisplayCondition().value,
				estimateTime.getMonthlyDisplayCondition().value,
				estimateTime.getYearlyAlarmCkCondition().value,
				estimateTime.getMonthlyAlarmCkCondition().value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.guideline.
	 * CommonGuidelineSettingSetMemento#setEstimatePrice(nts.uk.ctx.at.schedule.
	 * dom.shift.estimate.guideline.ReferenceCondition)
	 */
	@Override
	public void setEstimatePrice(ReferenceCondition estimatePrice) {
		this.estimatePrice = new ReferenceConditionDto(
				estimatePrice.getYearlyDisplayCondition().value,
				estimatePrice.getMonthlyDisplayCondition().value,
				estimatePrice.getYearlyAlarmCkCondition().value,
				estimatePrice.getMonthlyAlarmCkCondition().value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.guideline.
	 * CommonGuidelineSettingSetMemento#setEstimateNumberOfDays(nts.uk.ctx.at.
	 * schedule.dom.shift.estimate.guideline.ReferenceCondition)
	 */
	@Override
	public void setEstimateNumberOfDays(ReferenceCondition estimateNumberOfDays) {
		this.estimateNumberOfDays = new ReferenceConditionDto(
				estimateNumberOfDays.getYearlyDisplayCondition().value,
				estimateNumberOfDays.getMonthlyDisplayCondition().value,
				estimateNumberOfDays.getYearlyAlarmCkCondition().value,
				estimateNumberOfDays.getMonthlyAlarmCkCondition().value);
	}

}

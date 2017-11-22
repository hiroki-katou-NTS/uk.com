/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.estimate.guideline;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import nts.uk.ctx.at.schedule.app.command.shift.estimate.guideline.dto.EstimatedAlarmColorDto;
import nts.uk.ctx.at.schedule.app.command.shift.estimate.guideline.dto.ReferenceConditionDto;
import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimatedCondition;
import nts.uk.ctx.at.schedule.dom.shift.estimate.commonset.CommonGuidelineSetting;
import nts.uk.ctx.at.schedule.dom.shift.estimate.commonset.CommonGuidelineSettingGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.estimate.commonset.EstimatedAlarmColor;
import nts.uk.ctx.at.schedule.dom.shift.estimate.commonset.ReferenceCondition;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;

/**
 * The Class CommonGuidelineSettingSaveCommand.
 */
@Data
public class CommonGuidelineSettingSaveCommand {

	/** The alarm colors. */
	private List<EstimatedAlarmColorDto> alarmColors;

	/** The estimate time. */
	private ReferenceConditionDto estimateTime;

	/** The estimate price. */
	private ReferenceConditionDto estimatePrice;

	/** The estimate number of days. */
	private ReferenceConditionDto estimateNumberOfDays;

	/**
	 * To domain.
	 *
	 * @return the common guideline setting
	 */
	public CommonGuidelineSetting toDomain(String companyId) {
		return new CommonGuidelineSetting(new JpaGetMemento(companyId, this));
	}

	/**
	 * The Class JpaGetMemento.
	 */
	private class JpaGetMemento implements CommonGuidelineSettingGetMemento {

		/** The command. */
		private CommonGuidelineSettingSaveCommand command;

		/** The company id. */
		private String companyId;

		/**
		 * Jpa pattern calendar setting get memento.
		 *
		 * @param command
		 *            the command
		 */
		public JpaGetMemento(String companyId, CommonGuidelineSettingSaveCommand command) {
			this.command = command;
			this.companyId = companyId;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.guideline.
		 * CommonGuidelineSettingGetMemento#getCompanyId()
		 */
		@Override
		public CompanyId getCompanyId() {
			return new CompanyId(this.companyId);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.guideline.
		 * CommonGuidelineSettingGetMemento#getAlarmColors()
		 */
		@Override
		public List<EstimatedAlarmColor> getAlarmColors() {
			return this.command.getAlarmColors().stream()
					.map(item -> new EstimatedAlarmColor(
							EstimatedCondition.valueOf(item.getGuidelineCondition()),
							new ColorCode(item.getColor())))
					.collect(Collectors.toList());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.guideline.
		 * CommonGuidelineSettingGetMemento#getEstimateTime()
		 */
		@Override
		public ReferenceCondition getEstimateTime() {
			ReferenceConditionDto dto = this.command.getEstimateTime();
			return new ReferenceCondition(
					EstimatedCondition.valueOf(dto.getYearlyDisplayCondition()),
					EstimatedCondition.valueOf(dto.getMonthlyDisplayCondition()),
					EstimatedCondition.valueOf(dto.getYearlyAlarmCkCondition()),
					EstimatedCondition.valueOf(dto.getMonthlyAlarmCkCondition()));
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.guideline.
		 * CommonGuidelineSettingGetMemento#getEstimatePrice()
		 */
		@Override
		public ReferenceCondition getEstimatePrice() {
			ReferenceConditionDto dto = this.command.getEstimatePrice();
			return new ReferenceCondition(
					EstimatedCondition.valueOf(dto.getYearlyDisplayCondition()),
					EstimatedCondition.valueOf(dto.getMonthlyDisplayCondition()),
					EstimatedCondition.valueOf(dto.getYearlyAlarmCkCondition()),
					EstimatedCondition.valueOf(dto.getMonthlyAlarmCkCondition()));
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.guideline.
		 * CommonGuidelineSettingGetMemento#getEstimateNumberOfDays()
		 */
		@Override
		public ReferenceCondition getEstimateNumberOfDays() {
			ReferenceConditionDto dto = this.command.getEstimateNumberOfDays();
			return new ReferenceCondition(
					EstimatedCondition.valueOf(dto.getYearlyDisplayCondition()),
					EstimatedCondition.valueOf(dto.getMonthlyDisplayCondition()),
					EstimatedCondition.valueOf(dto.getYearlyAlarmCkCondition()),
					EstimatedCondition.valueOf(dto.getMonthlyAlarmCkCondition()));
		}

	}

}

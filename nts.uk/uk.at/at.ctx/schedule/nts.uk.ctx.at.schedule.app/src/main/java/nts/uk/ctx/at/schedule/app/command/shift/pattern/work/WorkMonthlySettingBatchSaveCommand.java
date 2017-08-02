/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.pattern.work;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.app.find.shift.pattern.dto.WorkMonthlySettingDto;
import nts.uk.ctx.at.schedule.dom.shift.pattern.WorkTypeCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.WorkingCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySetting;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySettingGetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class WorkMonthlySettingBatchSaveCommand.
 */

@Getter
@Setter
public class WorkMonthlySettingBatchSaveCommand {
	
	/** The work monthly setting. */
	private List<WorkMonthlySettingDto> workMonthlySetting;

	/**
	 * To domain month.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	public List<WorkMonthlySetting> toDomainMonth(String companyId){
		return this.workMonthlySetting.stream()
				.map(dto -> new WorkMonthlySetting(
						new WorkMonthlySettingBatchGetMementoImpl(companyId, dto)))
				.collect(Collectors.toList());
	}
	/**
	 * The Class WorkMonthlySettingBatchGetMementoImpl.
	 */
	class WorkMonthlySettingBatchGetMementoImpl implements WorkMonthlySettingGetMemento{

		/** The company id. */
		private String companyId;
		
		/** The dto. */
		private WorkMonthlySettingDto dto;
		
		/**
		 * Instantiates a new work monthly setting batch get memento impl.
		 *
		 * @param companyId the company id
		 * @param dto the dto
		 */
		public WorkMonthlySettingBatchGetMementoImpl(String companyId, WorkMonthlySettingDto dto) {
			this.companyId = companyId;
			this.dto = dto;
		}
		
		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.work.
		 * WorkMonthlySettingGetMemento#getCompanyId()
		 */
		@Override
		public CompanyId getCompanyId() {
			return new CompanyId(this.companyId);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.work.
		 * WorkMonthlySettingGetMemento#getWorkTypeCode()
		 */
		@Override
		public WorkTypeCode getWorkTypeCode() {
			return new WorkTypeCode(this.dto.getWorkTypeCode());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.work.
		 * WorkMonthlySettingGetMemento#getWorkingCode()
		 */
		@Override
		public WorkingCode getWorkingCode() {
			return new WorkingCode(this.dto.getWorkingCode());
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySettingGetMemento#getYmdK()
		 */
		@Override
		public BigDecimal getYmdK() {
			return BigDecimal.valueOf(this.dto.getYmdk());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.work.
		 * WorkMonthlySettingGetMemento#getMonthlyPatternCode()
		 */
		@Override
		public MonthlyPatternCode getMonthlyPatternCode() {
			return new MonthlyPatternCode(this.dto.getMonthlyPatternCode());
		}
		
	}
}

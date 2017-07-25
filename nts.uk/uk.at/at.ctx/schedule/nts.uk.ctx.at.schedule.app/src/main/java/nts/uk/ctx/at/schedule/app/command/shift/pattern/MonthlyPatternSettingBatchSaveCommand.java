/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.pattern;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.app.find.shift.pattern.dto.MonthlyPatternSettingBatchDto;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySetting;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySettingGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkTypeCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkingCode;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class MonthlyPatternSettingBatchSaveCommand.
 */

/**
 * Gets the end year month.
 *
 * @return the end year month
 */

/**
 * Gets the end year month.
 *
 * @return the end year month
 */
@Getter

/**
 * Sets the end year month.
 *
 * @param endYearMonth the new end year month
 */

/**
 * Sets the end year month.
 *
 * @param endYearMonth the new end year month
 */
@Setter
public class MonthlyPatternSettingBatchSaveCommand {

	/** The setting work days. */
	private MonthlyPatternSettingBatchDto settingWorkDays;
	
	/** The setting statutory holidays. */
	private MonthlyPatternSettingBatchDto settingStatutoryHolidays;
	
	/** The setting none statutory holidays. */
	private MonthlyPatternSettingBatchDto settingNoneStatutoryHolidays;
	
	/** The setting public holidays. */
	private MonthlyPatternSettingBatchDto settingPublicHolidays;
	
	/** The monthly pattern code. */
	private String monthlyPatternCode;

	/** The overwrite. */
	private boolean overwrite;
	
	/** The start year month. */
	private int startYearMonth;
	
	/** The end year month. */
	private int endYearMonth;

	/**
	 * To domain work days.
	 *
	 * @param companyId the company id
	 * @param baseDate the base date
	 * @return the work monthly setting
	 */
	public WorkMonthlySetting toDomainWorkDays(String companyId, GeneralDate baseDate) {
		return new WorkMonthlySetting(new WorkMonthlySettingGetMementoImpl(settingWorkDays,
				companyId, baseDate, monthlyPatternCode));
	}

	/**
	 * To domain statutory holidays.
	 *
	 * @param companyId the company id
	 * @param baseDate the base date
	 * @return the work monthly setting
	 */
	public WorkMonthlySetting toDomainStatutoryHolidays(String companyId, GeneralDate baseDate) {
		return new WorkMonthlySetting(new WorkMonthlySettingGetMementoImpl(settingStatutoryHolidays,
				companyId, baseDate, monthlyPatternCode));
	}

	/**
	 * To domain none statutory holidays.
	 *
	 * @param companyId the company id
	 * @param baseDate the base date
	 * @return the work monthly setting
	 */
	public WorkMonthlySetting toDomainNoneStatutoryHolidays(String companyId,
			GeneralDate baseDate) {
		return new WorkMonthlySetting(new WorkMonthlySettingGetMementoImpl(
				settingNoneStatutoryHolidays, companyId, baseDate, monthlyPatternCode));
	}

	/**
	 * To domain public holidays.
	 *
	 * @param companyId the company id
	 * @param baseDate the base date
	 * @return the work monthly setting
	 */
	public WorkMonthlySetting toDomainPublicHolidays(String companyId, GeneralDate baseDate) {
		return new WorkMonthlySetting(new WorkMonthlySettingGetMementoImpl(settingPublicHolidays,
				companyId, baseDate, monthlyPatternCode));
	}
	
		/**
		 * The Class WorkMonthlySettingGetMementoImpl.
		 */
	class WorkMonthlySettingGetMementoImpl implements WorkMonthlySettingGetMemento{
		
		/** The dto. */
		private MonthlyPatternSettingBatchDto dto;
		
		/** The company id. */
		private String companyId;
		
		/** The base date. */
		private GeneralDate baseDate;
		
		/** The monthly pattern code. */
		private String monthlyPatternCode;

		/**
		 * Instantiates a new work monthly setting get memento impl.
		 *
		 * @param dto the dto
		 * @param companyId the company id
		 * @param baseDate the base date
		 * @param monthlyPatternCode the monthly pattern code
		 */
		public WorkMonthlySettingGetMementoImpl(MonthlyPatternSettingBatchDto dto, String companyId,
				GeneralDate baseDate, String monthlyPatternCode) {
			this.dto = dto;
			this.companyId = companyId;
			this.baseDate = baseDate;
			this.monthlyPatternCode = monthlyPatternCode;
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

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.work.
		 * WorkMonthlySettingGetMemento#getDate()
		 */
		@Override
		public GeneralDate getDate() {
			return this.baseDate;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.work.
		 * WorkMonthlySettingGetMemento#getMonthlyPatternCode()
		 */
		@Override
		public MonthlyPatternCode getMonthlyPatternCode() {
			return new MonthlyPatternCode(this.monthlyPatternCode);
		}
		
		
	}
	
}

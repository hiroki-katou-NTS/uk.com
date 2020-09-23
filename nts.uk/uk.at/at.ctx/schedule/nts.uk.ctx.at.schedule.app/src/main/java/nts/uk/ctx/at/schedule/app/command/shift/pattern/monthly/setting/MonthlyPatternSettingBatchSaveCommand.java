/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.pattern.monthly.setting;

//import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.schedule.app.find.shift.pattern.dto.MonthlyPatternSettingBatchDto;
import nts.uk.ctx.at.schedule.dom.shift.pattern.WorkTypeCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.WorkingCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPattern;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternName;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySetting;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySettingGetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class MonthlyPatternSettingBatchSaveCommand.
 */

@Getter
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

	/** The monthly pattern name. */
	private String monthlyPatternName;

	/** The overwrite. */
	private boolean overwrite;

	/** The start year month. */
	private int startYearMonth;

	/** The end year month. */
	private int endYearMonth;

	/**
	 * To domain work days.
	 *
	 * @param companyId
	 *            the company id
	 * @param ymdk
	 *            the ymdk
	 * @return the work monthly setting
	 */
	public WorkMonthlySetting toDomainWorkDays(String companyId, GeneralDate ymdk) {
		if (settingWorkDays == null
				|| StringUtil.isNullOrEmpty(settingWorkDays.getWorkTypeCode(),false)){
			return null;
		}
		return new WorkMonthlySetting(new WorkMonthlySettingGetMementoImpl(settingWorkDays,
				companyId, ymdk, monthlyPatternCode));
	}

	/**
	 * To domain statutory holidays.
	 *
	 * @param companyId
	 *            the company id
	 * @param ymdk
	 *            the ymdk
	 * @return the work monthly setting
	 */
	public WorkMonthlySetting toDomainStatutoryHolidays(String companyId, GeneralDate ymdk) {
		if (settingStatutoryHolidays == null
				|| StringUtil.isNullOrEmpty(settingStatutoryHolidays.getWorkTypeCode(),false)){
			return null;
		}
		return new WorkMonthlySetting(new WorkMonthlySettingGetMementoImpl(settingStatutoryHolidays,
				companyId, ymdk, monthlyPatternCode));
	}

	/**
	 * To domain none statutory holidays.
	 *
	 * @param companyId
	 *            the company id
	 * @param ymdk
	 *            the ymdk
	 * @return the work monthly setting
	 */
	public WorkMonthlySetting toDomainNoneStatutoryHolidays(String companyId, GeneralDate ymdk) {
		if (settingNoneStatutoryHolidays == null
				|| StringUtil.isNullOrEmpty(settingNoneStatutoryHolidays.getWorkTypeCode(),false)){
			return null;
		}
		return new WorkMonthlySetting(new WorkMonthlySettingGetMementoImpl(
				settingNoneStatutoryHolidays, companyId, ymdk, monthlyPatternCode));
	}

	/**
	 * To domain public holidays.
	 *
	 * @param companyId
	 *            the company id
	 * @param ymdk
	 *            the ymdk
	 * @return the work monthly setting
	 */
	public WorkMonthlySetting toDomainPublicHolidays(String companyId, GeneralDate ymdk) {
		if (settingPublicHolidays == null
				|| StringUtil.isNullOrEmpty(settingPublicHolidays.getWorkTypeCode(),false)){
			return null;
		}
		return new WorkMonthlySetting(new WorkMonthlySettingGetMementoImpl(this.settingPublicHolidays,
				companyId, ymdk, this.monthlyPatternCode));
	}

	public MonthlyPattern toDomainMonthlyPattern(String companyId) {
		return new MonthlyPattern(new MonthlyPatternGetMementoImpl(monthlyPatternCode,
				monthlyPatternName, companyId));
	}

	/**
	 * The Class WorkMonthlySettingGetMementoImpl.
	 */
	class WorkMonthlySettingGetMementoImpl implements WorkMonthlySettingGetMemento {

		/** The dto. */
		private MonthlyPatternSettingBatchDto dto;

		/** The company id. */
		private String companyId;

		/** The ymdk. */
		private GeneralDate ymdk;

		/** The monthly pattern code. */
		private String monthlyPatternCode;

		/**
		 * Instantiates a new work monthly setting get memento impl.
		 *
		 * @param dto
		 *            the dto
		 * @param companyId
		 *            the company id
		 * @param ymdk
		 *            the ymdk
		 * @param monthlyPatternCode
		 *            the monthly pattern code
		 */
		public WorkMonthlySettingGetMementoImpl(MonthlyPatternSettingBatchDto dto, String companyId,
				GeneralDate ymdk, String monthlyPatternCode) {
			this.dto = dto;
			this.companyId = companyId;
			this.ymdk = ymdk;
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
		public GeneralDate getYmdK() {
			return this.ymdk;
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

	/**
	 * The Class MonthlyPatternGetMementoImpl.
	 */
	class MonthlyPatternGetMementoImpl implements MonthlyPatternGetMemento {
		/** The monthly pattern code. */
		private String monthlyPatternCode;

		/** The monthly pattern name. */
		private String monthlyPatternName;

		/** The company id. */
		private String companyId;

		/** The contract code. */
		private String contractCd = AppContexts.user().contractCode();

		/**
		 * Instantiates a new monthly pattern get memento impl.
		 *
		 * @param monthlyPatternCode
		 *            the monthly pattern code
		 * @param monthlyPatternName
		 *            the monthly pattern name
		 */
		public MonthlyPatternGetMementoImpl(String monthlyPatternCode, String monthlyPatternName,
				String companyId) {
			this.monthlyPatternCode = monthlyPatternCode;
			this.monthlyPatternName = monthlyPatternName;
			this.companyId = companyId;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.
		 * MonthlyPatternGetMemento#getCompanyId()
		 */
		@Override
		public CompanyId getCompanyId() {
			return new CompanyId(this.companyId);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.
		 * MonthlyPatternGetMemento#getMonthlyPatternCode()
		 */
		@Override
		public MonthlyPatternCode getMonthlyPatternCode() {
			return new MonthlyPatternCode(this.monthlyPatternCode);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.
		 * MonthlyPatternGetMemento#getMonthlyPatternName()
		 */
		@Override
		public MonthlyPatternName getMonthlyPatternName() {
			return new MonthlyPatternName(this.monthlyPatternName);
		}

		@Override
		public String getContractCd() {
			return this.contractCd;
		}

	}

}

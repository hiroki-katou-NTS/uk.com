/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.common;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.common.Month;
import nts.uk.ctx.at.shared.dom.common.MonthlyEstimateTime;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComFlexSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComFlexSettingGetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainFlexSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainFlexSettingGetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpFlexSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpFlexSettingGetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthlyUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class FlexSettingDto.
 */
@Getter
@Setter
public class FlexSettingDto {

	/** The statutory setting. */
	protected List<MonthlyUnitDto> statutorySetting;

	/** The specified setting. */
	protected List<MonthlyUnitDto> specifiedSetting;

	/** The week average setting. */
	protected List<MonthlyUnitDto> weekAveSetting;
	
	public FlexSettingDto() {
		super();
	}
	
	/**
	 * To domain.
	 *
	 * @param year the year
	 * @return the com flex setting
	 */
	public ComFlexSetting toDomain(int year) {
		return new ComFlexSetting(new ComFlexSettingMemento(year,
				this.statutorySetting, this.specifiedSetting, this.weekAveSetting));
	}
	
	/**
	 * To shain domain.
	 *
	 * @param year the year
	 * @param employeeId the employee id
	 * @return the shain flex setting
	 */
	public ShainFlexSetting toShainDomain(int year, String employeeId) {
		return new ShainFlexSetting(new ShainFlexSettingMemento(year, employeeId,
				this.statutorySetting, this.specifiedSetting, this.weekAveSetting));
	}
	
	/**
	 * To emp domain.
	 *
	 * @param year the year
	 * @param employeeId the employee id
	 * @return the emp flex setting
	 */
	public EmpFlexSetting toEmpDomain(int year, String employeeId) {
		return new EmpFlexSetting(new EmpFlexSettingMemento(year, employeeId,
				this.statutorySetting, this.specifiedSetting, this.weekAveSetting));
	}
	
	/**
	 * The Class EmpFlexSettingMemento.
	 */
	private class EmpFlexSettingMemento implements EmpFlexSettingGetMemento {

		/** The year. */
		private int year;
		
		/** The empl code. */
		private String emplCode;
		
		/** The statutory setting. */
		private List<MonthlyUnitDto> statutorySetting;
		
		/** The specified setting. */
		private List<MonthlyUnitDto> specifiedSetting;
		
		/** The week average setting. */
		private List<MonthlyUnitDto> weekAveSetting;
		
		/**
		 * Instantiates a new emp flex setting memento.
		 *
		 * @param year the year
		 * @param emplCode the empl code
		 * @param statutorySetting the statutory setting
		 * @param specifiedSetting the specified setting
		 * @param weekAveSetting the week average setting
		 */
		public EmpFlexSettingMemento(int year, String emplCode, List<MonthlyUnitDto> statutorySetting,
				List<MonthlyUnitDto> specifiedSetting, List<MonthlyUnitDto> weekAveSetting) {
			this.year = year;
			this.emplCode = emplCode;
			this.statutorySetting = statutorySetting;
			this.specifiedSetting = specifiedSetting;
			this.weekAveSetting = weekAveSetting;
		}

		/* 
		 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.FlexSettingGetMemento#getYear()
		 */
		@Override
		public Year getYear() {
			return new Year(this.year);
		}

		/* 
		 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.FlexSettingGetMemento#getStatutorySetting()
		 */
		@Override
		public List<MonthlyUnit> getStatutorySetting() {
			return this.statutorySetting.stream().map(funcMap).collect(Collectors.toList());
		}

		/* 
		 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.FlexSettingGetMemento#getSpecifiedSetting()
		 */
		@Override
		public List<MonthlyUnit> getSpecifiedSetting() {
			return this.specifiedSetting.stream().map(funcMap).collect(Collectors.toList());
		}

		/*
		 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.FlexSettingGetMemento#getWeekAveSetting()
		 */
		@Override
		public List<MonthlyUnit> getWeekAveSetting() {
			return this.weekAveSetting.stream().map(funcMap).collect(Collectors.toList());
		}
		
		/* 
		 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpFlexSettingGetMemento#getCompanyId()
		 */
		@Override
		public CompanyId getCompanyId() {
			return new CompanyId(AppContexts.user().companyId());
		}

		/* 
		 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpFlexSettingGetMemento#getEmploymentCode()
		 */
		@Override
		public EmploymentCode getEmploymentCode() {
			return new EmploymentCode(this.emplCode);
		}
		
		/** The func map. */
		private Function<MonthlyUnitDto, MonthlyUnit> funcMap = dto -> {
			return new MonthlyUnit(new Month(dto.getMonth()), new MonthlyEstimateTime(dto.getMonthlyTime()));
		};
		
	}
	
	/**
	 * The Class ComFlexSettingMemento.
	 */
	private class ComFlexSettingMemento implements ComFlexSettingGetMemento {

		/** The year. */
		private int year;
		
		/** The statutory setting. */
		private List<MonthlyUnitDto> statutorySetting;
		
		/** The specified setting. */
		private List<MonthlyUnitDto> specifiedSetting;
		
		/** The week average setting. */
		private List<MonthlyUnitDto> weekAveSetting;

		/**
		 * Instantiates a new com flex setting memento.
		 *
		 * @param year the year
		 * @param statutorySetting the statutory setting
		 * @param specifiedSetting the specified setting
		 * @param weekAveSetting the week average setting
		 */
		public ComFlexSettingMemento(int year, List<MonthlyUnitDto> statutorySetting,
				List<MonthlyUnitDto> specifiedSetting, List<MonthlyUnitDto> weekAveSetting) {
			this.year = year;
			this.statutorySetting = statutorySetting;
			this.specifiedSetting = specifiedSetting;
			this.weekAveSetting = weekAveSetting;
		}

		/* 
		 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.FlexSettingGetMemento#getYear()
		 */
		@Override
		public Year getYear() {
			return new Year(this.year);
		}

		/* 
		 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.FlexSettingGetMemento#getStatutorySetting()
		 */
		@Override
		public List<MonthlyUnit> getStatutorySetting() {
			return this.statutorySetting.stream().map(funcMap).collect(Collectors.toList());
		}

		/* 
		 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.FlexSettingGetMemento#getSpecifiedSetting()
		 */
		@Override
		public List<MonthlyUnit> getSpecifiedSetting() {
			return this.specifiedSetting.stream().map(funcMap).collect(Collectors.toList());
		}

		/*
		 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.FlexSettingGetMemento#getWeekAveSetting()
		 */
		@Override
		public List<MonthlyUnit> getWeekAveSetting() {
			return this.weekAveSetting.stream().map(funcMap).collect(Collectors.toList());
		}
		
		/* 
		 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComFlexSettingGetMemento#getCompanyId()
		 */
		@Override
		public CompanyId getCompanyId() {
			return new CompanyId(AppContexts.user().companyId());
		}
		
		/** The func map. */
		private Function<MonthlyUnitDto, MonthlyUnit> funcMap = dto -> {
			return new MonthlyUnit(new Month(dto.getMonth()), new MonthlyEstimateTime(dto.getMonthlyTime()));
		};
		
	}
	
	/**
	 * The Class ShainFlexSettingMemento.
	 */
	private class ShainFlexSettingMemento implements ShainFlexSettingGetMemento {

		/** The year. */
		private int year;
		
		/** The employee id. */
		private String employeeId;
		
		/** The statutory setting. */
		private List<MonthlyUnitDto> statutorySetting;
		
		/** The specified setting. */
		private List<MonthlyUnitDto> specifiedSetting;

		/** The week average setting. */
		private List<MonthlyUnitDto> weekAveSetting;
		
		/**
		 * Instantiates a new shain flex setting memento.
		 *
		 * @param year the year
		 * @param employeeId the employee id
		 * @param statutorySetting the statutory setting
		 * @param specifiedSetting the specified setting
		 * @param weekAveSetting the week average setting
		 */
		public ShainFlexSettingMemento(int year, String employeeId, List<MonthlyUnitDto> statutorySetting,
				List<MonthlyUnitDto> specifiedSetting, List<MonthlyUnitDto> weekAveSetting) {
			this.year = year;
			this.employeeId = employeeId;
			this.statutorySetting = statutorySetting;
			this.specifiedSetting = specifiedSetting;
			this.weekAveSetting = weekAveSetting;
		}

		/* 
		 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.FlexSettingGetMemento#getYear()
		 */
		@Override
		public Year getYear() {
			return new Year(this.year);
		}

		/* 
		 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.FlexSettingGetMemento#getStatutorySetting()
		 */
		@Override
		public List<MonthlyUnit> getStatutorySetting() {
			return this.statutorySetting.stream().map(funcMap).collect(Collectors.toList());
		}

		/* 
		 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.FlexSettingGetMemento#getSpecifiedSetting()
		 */
		@Override
		public List<MonthlyUnit> getSpecifiedSetting() {
			return this.specifiedSetting.stream().map(funcMap).collect(Collectors.toList());
		}
		
		/*
		 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.FlexSettingGetMemento#getWeekAveSetting()
		 */
		@Override
		public List<MonthlyUnit> getWeekAveSetting() {
			return this.weekAveSetting.stream().map(funcMap).collect(Collectors.toList());
		}

		/* 
		 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainFlexSettingGetMemento#getCompanyId()
		 */
		@Override
		public CompanyId getCompanyId() {
			return new CompanyId(AppContexts.user().companyId());
		}
		
		/** The func map. */
		private Function<MonthlyUnitDto, MonthlyUnit> funcMap = dto -> {
			return new MonthlyUnit(new Month(dto.getMonth()), new MonthlyEstimateTime(dto.getMonthlyTime()));
		};

		/* 
		 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainFlexSettingGetMemento#getEmployeeId()
		 */
		@Override
		public EmployeeId getEmployeeId() {
			return new EmployeeId(this.employeeId);
		}
		
	}

}

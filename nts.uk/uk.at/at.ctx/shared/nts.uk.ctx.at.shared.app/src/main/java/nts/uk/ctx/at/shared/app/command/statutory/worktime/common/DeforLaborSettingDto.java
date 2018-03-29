/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.common;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.common.Month;
import nts.uk.ctx.at.shared.dom.common.MonthlyEstimateTime;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComDeforLaborSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComDeforLaborSettingGetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainDeforLaborSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainDeforLaborSettingGetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpDeforLaborSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpDeforLaborSettingGetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthlyUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class DeforLaborSettingDto.
 */
@Getter
@Setter
public class DeforLaborSettingDto {

	/** The year. */
	protected Integer year;

	/** The statutory setting. */
	protected List<MonthlyUnitDto> statutorySetting;

	/**
	 * To domain.
	 *
	 * @param year the year
	 * @return the com defor labor setting
	 */
	public ComDeforLaborSetting toDomain(int year) {
		return new ComDeforLaborSetting(new ComDeforLaborSettingMemento(year, this.statutorySetting));
	}

	/**
	 * To shain domain.
	 *
	 * @param year the year
	 * @param employeeId the employee id
	 * @return the shain defor labor setting
	 */
	public ShainDeforLaborSetting toShainDomain(int year, String employeeId) {
		return new ShainDeforLaborSetting(new ShainDeforLaborSettingMemento(year, employeeId, this.statutorySetting));
	}
	
	/**
	 * To emp domain.
	 *
	 * @param year the year
	 * @param employeeId the employee id
	 * @return the emp defor labor setting
	 */
	public EmpDeforLaborSetting toEmpDomain(int year, String employeeId) {
		return new EmpDeforLaborSetting(new EmpDeforLaborSettingMemento(year, employeeId, this.statutorySetting));
	}
	
	/**
	 * The Class EmpDeforLaborSettingMemento.
	 */
	private class EmpDeforLaborSettingMemento implements EmpDeforLaborSettingGetMemento {
		
		/** The year. */
		private Integer year;
		
		/** The empl code. */
		private String emplCode;
		
		/** The statutory setting. */
		private List<MonthlyUnitDto> statutorySetting;

		/**
		 * Instantiates a new emp defor labor setting memento.
		 *
		 * @param year the year
		 * @param emplCode the empl code
		 * @param statutorySetting the statutory setting
		 */
		public EmpDeforLaborSettingMemento(Integer year, String emplCode, List<MonthlyUnitDto> statutorySetting) {
			this.year = year;
			this.emplCode = emplCode;
			this.statutorySetting = statutorySetting;
		}

		/* 
		 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.DeforLaborSettingGetMemento#getYear()
		 */
		@Override
		public Year getYear() {
			return new Year(this.year);
		}

		/* 
		 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.DeforLaborSettingGetMemento#getStatutorySetting()
		 */
		@Override
		public List<MonthlyUnit> getStatutorySetting() {
			return this.statutorySetting.stream().map(dto -> {
				return new MonthlyUnit(new Month(dto.getMonth()), new MonthlyEstimateTime(dto.getMonthlyTime()));
			}).collect(Collectors.toList());
		}

		/* 
		 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpDeforLaborSettingGetMemento#getCompanyId()
		 */
		@Override
		public CompanyId getCompanyId() {
			return new CompanyId(AppContexts.user().companyId());
		}

		/* 
		 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpDeforLaborSettingGetMemento#getEmploymentCode()
		 */
		@Override
		public EmploymentCode getEmploymentCode() {
			return new EmploymentCode(this.emplCode);
		}
		
	}

	/**
	 * The Class ComDeforLaborSettingMemento.
	 */
	private class ComDeforLaborSettingMemento implements ComDeforLaborSettingGetMemento {

		/** The year. */
		private Integer year;
		
		/** The statutory setting. */
		private List<MonthlyUnitDto> statutorySetting;

		/**
		 * Instantiates a new com defor labor setting memento.
		 *
		 * @param year the year
		 * @param statutorySetting the statutory setting
		 */
		public ComDeforLaborSettingMemento(Integer year, List<MonthlyUnitDto> statutorySetting) {
			this.year = year;
			this.statutorySetting = statutorySetting;
		}

		/* 
		 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.DeforLaborSettingGetMemento#getYear()
		 */
		@Override
		public Year getYear() {
			return new Year(this.year);
		}

		/* 
		 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.DeforLaborSettingGetMemento#getStatutorySetting()
		 */
		@Override
		public List<MonthlyUnit> getStatutorySetting() {
			return this.statutorySetting.stream().map(dto -> {
				return new MonthlyUnit(new Month(dto.getMonth()), new MonthlyEstimateTime(dto.getMonthlyTime()));
			}).collect(Collectors.toList());
		}

		/* 
		 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComDeforLaborSettingGetMemento#getCompanyId()
		 */
		@Override
		public CompanyId getCompanyId() {
			return new CompanyId(AppContexts.user().companyId());
		}
	}

	/**
	 * The Class ShainDeforLaborSettingMemento.
	 */
	private class ShainDeforLaborSettingMemento implements ShainDeforLaborSettingGetMemento {

		/** The year. */
		private Integer year;
		
		/** The employee id. */
		private String employeeId;
		
		/** The statutory setting. */
		private List<MonthlyUnitDto> statutorySetting;

		/**
		 * Instantiates a new shain defor labor setting memento.
		 *
		 * @param year the year
		 * @param employeeId the employee id
		 * @param statutorySetting the statutory setting
		 */
		public ShainDeforLaborSettingMemento(Integer year, String employeeId, List<MonthlyUnitDto> statutorySetting) {
			this.year = year;
			this.employeeId = employeeId;
			this.statutorySetting = statutorySetting;
		}

		/* 
		 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.DeforLaborSettingGetMemento#getYear()
		 */
		@Override
		public Year getYear() {
			return new Year(this.year);
		}

		/* 
		 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.DeforLaborSettingGetMemento#getStatutorySetting()
		 */
		@Override
		public List<MonthlyUnit> getStatutorySetting() {
			return this.statutorySetting.stream().map(dto -> {
				return new MonthlyUnit(new Month(dto.getMonth()), new MonthlyEstimateTime(dto.getMonthlyTime()));
			}).collect(Collectors.toList());
		}

		/* 
		 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainDeforLaborSettingGetMemento#getCompanyId()
		 */
		@Override
		public CompanyId getCompanyId() {
			return new CompanyId(AppContexts.user().companyId());
		}

		/* 
		 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainDeforLaborSettingGetMemento#getEmployeeId()
		 */
		@Override
		public EmployeeId getEmployeeId() {
			return new EmployeeId(this.employeeId);
		}
	}
}

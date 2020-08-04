/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.common;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.common.TimeOfDay;
import nts.uk.ctx.at.shared.dom.common.WeeklyTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComRegularLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComRegularLaborTimeGetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComTransLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComTransLaborTimeGetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainRegularLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainRegularLaborTimeGetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainTransLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainTransLaborTimeGetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpRegularLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpRegularLaborTimeGetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransLaborTimeGetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.WeekStart;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.DailyUnit;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WeeklyUnit;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class WorkingTimeSettingDto.
 */

@Getter
@Setter
public class WorkingTimeSettingDto {

	/** The weekly time. */
	protected WeeklyUnitDto weeklyTime;

	/** The daily time. */
	protected DailyUnitDto dailyTime;

	/**
	 * To com regular labor time domain.
	 *
	 * @return the com regular labor time
	 */
	public ComRegularLaborTime toComRegularLaborTimeDomain() {
		return new ComRegularLaborTime(new ComRegularLaborTimeDtoMemento(weeklyTime, dailyTime));
	}
	
	/**
	 * To com trans labor time domain.
	 *
	 * @return the com trans labor time
	 */
	public ComTransLaborTime toComTransLaborTimeDomain() {
		return new ComTransLaborTime(new ComTransLaborTimeDtoMemento(weeklyTime, dailyTime));
	}
	
	/**
	 * To shain regular time domain.
	 *
	 * @param employeeId the employee id
	 * @return the shain regular work time
	 */
	public ShainRegularLaborTime toShainRegularTimeDomain(String employeeId) {
		return new ShainRegularLaborTime(new ShainRegularWorkTimeDtoMemento(employeeId, weeklyTime, dailyTime));
	}
	
	/**
	 * To shain spe time domain.
	 *
	 * @param employeeId the employee id
	 * @return the shain spe defor labor time
	 */
	public ShainTransLaborTime toShainSpeTimeDomain(String employeeId) {
		return new ShainTransLaborTime(new ShainSpeDeforLaborTimeDtoMemento(employeeId, weeklyTime, dailyTime));
	}
	
	/**
	 * To emp spe time domain.
	 *
	 * @param employeeId the employee id
	 * @return the emp trans work time
	 */
	public EmpTransLaborTime toEmpTransTimeDomain(String emplCode) {
		return new EmpTransLaborTime(new EmpTransWorkTimeDtoMemento(emplCode, weeklyTime, dailyTime));
	}
	
	public EmpRegularLaborTime toEmpRegularTimeDomain(String emplCode) {
		return new EmpRegularLaborTime(new EmpRegularWorkTimeDtoMemento(emplCode, weeklyTime, dailyTime));
	}
	
	private class EmpRegularWorkTimeDtoMemento implements EmpRegularLaborTimeGetMemento {
		
		/** The empl code. */
		private String emplCode;
		
		/** The weekly time. */
		private WeeklyUnitDto weeklyTime;
		
		/** The daily time. */
		private DailyUnitDto dailyTime;
		
		/**
		 * Instantiates a new emp trans work time dto memento.
		 *
		 * @param emplCode the empl code
		 * @param weeklyTime the weekly time
		 * @param dailyTime the daily time
		 */
		public EmpRegularWorkTimeDtoMemento(String emplCode, WeeklyUnitDto weeklyTime, DailyUnitDto dailyTime) {
			this.emplCode = emplCode;
			this.weeklyTime = weeklyTime;
			this.dailyTime = dailyTime;
		}

		/* 
		 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransWorkTimeGetMemento#getCompanyId()
		 */
		@Override
		public CompanyId getCompanyId() {
			return new CompanyId(AppContexts.user().companyId());
		}

		/* 
		 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransWorkTimeGetMemento#getEmploymentCode()
		 */
		@Override
		public EmploymentCode getEmploymentCode() {
			return new EmploymentCode(this.emplCode);
		}

		/* 
		 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransWorkTimeGetMemento#getWorkingTimeSet()
		 */
		@Override
		public WorkingTimeSetting getWorkingTimeSet() {
			WeeklyUnit weeklyUnit = new WeeklyUnit(new WeeklyTime(this.weeklyTime.getTime()), WeekStart.valueOf(this.weeklyTime.getStart()));
			DailyUnit dailyUnit = new DailyUnit(new TimeOfDay(this.dailyTime.getDailyTime()));
			return new WorkingTimeSetting(weeklyUnit, dailyUnit);
		}
	}
	
	/**
	 * The Class EmpTransWorkTimeDtoMemento.
	 */
	private class EmpTransWorkTimeDtoMemento implements EmpTransLaborTimeGetMemento {
		
		/** The empl code. */
		private String emplCode;
		
		/** The weekly time. */
		private WeeklyUnitDto weeklyTime;
		
		/** The daily time. */
		private DailyUnitDto dailyTime;
		
		/**
		 * Instantiates a new emp trans work time dto memento.
		 *
		 * @param emplCode the empl code
		 * @param weeklyTime the weekly time
		 * @param dailyTime the daily time
		 */
		public EmpTransWorkTimeDtoMemento(String emplCode, WeeklyUnitDto weeklyTime, DailyUnitDto dailyTime) {
			this.emplCode = emplCode;
			this.weeklyTime = weeklyTime;
			this.dailyTime = dailyTime;
		}

		/* 
		 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransWorkTimeGetMemento#getCompanyId()
		 */
		@Override
		public CompanyId getCompanyId() {
			return new CompanyId(AppContexts.user().companyId());
		}

		/* 
		 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransWorkTimeGetMemento#getEmploymentCode()
		 */
		@Override
		public EmploymentCode getEmploymentCode() {
			return new EmploymentCode(this.emplCode);
		}

		/* 
		 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransWorkTimeGetMemento#getWorkingTimeSet()
		 */
		@Override
		public WorkingTimeSetting getWorkingTimeSet() {
			WeeklyUnit weeklyUnit = new WeeklyUnit(new WeeklyTime(this.weeklyTime.getTime()), WeekStart.valueOf(this.weeklyTime.getStart()));
			DailyUnit dailyUnit = new DailyUnit(new TimeOfDay(this.dailyTime.getDailyTime()));
			return new WorkingTimeSetting(weeklyUnit, dailyUnit);
		}
	}
	
	/**
	 * The Class ComRegularLaborTimeDtoMemento.
	 */
	private class ComRegularLaborTimeDtoMemento implements ComRegularLaborTimeGetMemento{
		
		/** The weekly time. */
		private WeeklyUnitDto weeklyTime;
		
		/** The daily time. */
		private DailyUnitDto dailyTime;
		
		/**
		 * Instantiates a new com regular labor time dto memento.
		 *
		 * @param weeklyTime the weekly time
		 * @param dailyTime the daily time
		 */
		public ComRegularLaborTimeDtoMemento(WeeklyUnitDto weeklyTime, DailyUnitDto dailyTime) {
			this.weeklyTime = weeklyTime;
			this.dailyTime = dailyTime;
		}

		/* 
		 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComRegularLaborTimeGetMemento#getCompanyId()
		 */
		@Override
		public CompanyId getCompanyId() {
			return new CompanyId(AppContexts.user().companyId());
		}

		/* 
		 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComRegularLaborTimeGetMemento#getWorkingTimeSet()
		 */
		@Override
		public WorkingTimeSetting getWorkingTimeSet() {
			WeeklyUnit weeklyUnit = new WeeklyUnit(new WeeklyTime(this.weeklyTime.getTime()), WeekStart.valueOf(this.weeklyTime.getStart()));
			DailyUnit dailyUnit = new DailyUnit(new TimeOfDay(this.dailyTime.getDailyTime()));
			return new WorkingTimeSetting(weeklyUnit, dailyUnit);
		}
		
	}
	
	/**
	 * The Class ComTransLaborTimeDtoMemento.
	 */
	private class ComTransLaborTimeDtoMemento implements ComTransLaborTimeGetMemento {

		/** The weekly time. */
		private WeeklyUnitDto weeklyTime;
		
		/** The daily time. */
		private DailyUnitDto dailyTime;

		/**
		 * Instantiates a new com trans labor time dto memento.
		 *
		 * @param weeklyTime the weekly time
		 * @param dailyTime the daily time
		 */
		public ComTransLaborTimeDtoMemento(WeeklyUnitDto weeklyTime, DailyUnitDto dailyTime) {
			super();
			this.weeklyTime = weeklyTime;
			this.dailyTime = dailyTime;
		}

		/* 
		 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComTransLaborTimeGetMemento#getCompanyId()
		 */
		@Override
		public CompanyId getCompanyId() {
			return new CompanyId(AppContexts.user().companyId());
		}

		/* 
		 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComTransLaborTimeGetMemento#getWorkingTimeSet()
		 */
		@Override
		public WorkingTimeSetting getWorkingTimeSet() {
			WeeklyUnit weeklyUnit = new WeeklyUnit(new WeeklyTime(this.weeklyTime.getTime()), WeekStart.valueOf(this.weeklyTime.getStart()));
			DailyUnit dailyUnit = new DailyUnit(new TimeOfDay(this.dailyTime.getDailyTime()));
			return new WorkingTimeSetting(weeklyUnit, dailyUnit);
		}
		
	}
	
	/**
	 * The Class ShainRegularWorkTimeDtoMemento.
	 */
	private class ShainRegularWorkTimeDtoMemento implements ShainRegularLaborTimeGetMemento {

		/** The weekly time. */
		private WeeklyUnitDto weeklyTime;
		
		/** The daily time. */
		private DailyUnitDto dailyTime;
		
		/** The employee id. */
		private String employeeId;

		/**
		 * Instantiates a new shain regular work time dto memento.
		 *
		 * @param employeeId the employee id
		 * @param weeklyTime the weekly time
		 * @param dailyTime the daily time
		 */
		public ShainRegularWorkTimeDtoMemento(String employeeId, WeeklyUnitDto weeklyTime, DailyUnitDto dailyTime) {
			this.weeklyTime = weeklyTime;
			this.dailyTime = dailyTime;
			this.employeeId = employeeId;
		}

		/* 
		 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainRegularWorkTimeGetMemento#getCompanyId()
		 */
		@Override
		public CompanyId getCompanyId() {
			return new CompanyId(AppContexts.user().companyId());
		}

		/* 
		 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainRegularWorkTimeGetMemento#getWorkingTimeSet()
		 */
		@Override
		public WorkingTimeSetting getWorkingTimeSet() {
			WeeklyUnit weeklyUnit = new WeeklyUnit(new WeeklyTime(this.weeklyTime.getTime()), WeekStart.valueOf(this.weeklyTime.getStart()));
			DailyUnit dailyUnit = new DailyUnit(new TimeOfDay(this.dailyTime.getDailyTime()));
			return new WorkingTimeSetting(weeklyUnit, dailyUnit);
		}

		/* 
		 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainRegularWorkTimeGetMemento#getEmployeeId()
		 */
		@Override
		public EmployeeId getEmployeeId() {
			return new EmployeeId(this.employeeId);
		}
		
	}
	
	/**
	 * The Class ShainSpeDeforLaborTimeDtoMemento.
	 */
	private class ShainSpeDeforLaborTimeDtoMemento implements ShainTransLaborTimeGetMemento {

		/** The weekly time. */
		private WeeklyUnitDto weeklyTime;
		
		/** The daily time. */
		private DailyUnitDto dailyTime;
		
		/** The employee id. */
		private String employeeId;

		/**
		 * Instantiates a new shain spe defor labor time dto memento.
		 *
		 * @param employeeId the employee id
		 * @param weeklyTime the weekly time
		 * @param dailyTime the daily time
		 */
		public ShainSpeDeforLaborTimeDtoMemento(String employeeId, WeeklyUnitDto weeklyTime, DailyUnitDto dailyTime) {
			this.weeklyTime = weeklyTime;
			this.dailyTime = dailyTime;
			this.employeeId = employeeId;
		}

		/* 
		 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainSpeDeforLaborTimeGetMemento#getCompanyId()
		 */
		@Override
		public CompanyId getCompanyId() {
			return new CompanyId(AppContexts.user().companyId());
		}

		/* 
		 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainSpeDeforLaborTimeGetMemento#getWorkingTimeSet()
		 */
		@Override
		public WorkingTimeSetting getWorkingTimeSet() {
			WeeklyUnit weeklyUnit = new WeeklyUnit(new WeeklyTime(this.weeklyTime.getTime()), WeekStart.valueOf(this.weeklyTime.getStart()));
			DailyUnit dailyUnit = new DailyUnit(new TimeOfDay(this.dailyTime.getDailyTime()));
			return new WorkingTimeSetting(weeklyUnit, dailyUnit);
		}

		/* 
		 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainSpeDeforLaborTimeGetMemento#getEmployeeId()
		 */
		@Override
		public EmployeeId getEmployeeId() {
			return new EmployeeId(this.employeeId);
		}
		
	}

	/**
	 * From domain.
	 *
	 * @param domain the domain
	 * @return the working time setting dto
	 */
	public static WorkingTimeSettingDto fromDomain(WorkingTimeSetting domain) {
		WorkingTimeSettingDto dto = new WorkingTimeSettingDto();
		WeeklyUnitDto weeklyTime = new WeeklyUnitDto(domain.getWeeklyTime().getTime().v(), domain.getWeeklyTime().getStart().value);
		DailyUnitDto dailyTime = new DailyUnitDto(domain.getDailyTime().getDailyTime().v());
		dto.setWeeklyTime(weeklyTime);
		dto.setDailyTime(dailyTime);
		return dto;
	}
	
}

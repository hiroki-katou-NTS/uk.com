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
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComNormalSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComNormalSettingGetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainNormalSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainNormalSettingGetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpNormalSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpNormalSettingGetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthlyUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class NormalSettingDto.
 */
@Getter
@Setter
public class NormalSettingDto {

	/** The statutory setting. */
	protected List<MonthlyUnitDto> statutorySetting;
	
	public ComNormalSetting toDomain(int year) {
		return new ComNormalSetting(new ComNormalSettingMemento(year, this.statutorySetting));
	}
	
	public ShainNormalSetting toShainDomain(int year, String employeeId) {
		return new ShainNormalSetting(new ShainNormalSettingMemento(year,employeeId, this.statutorySetting));
	}
	
	public EmpNormalSetting toEmpDomain(int year, String emplCode) {
		return new EmpNormalSetting(new EmpNormalSettingMemento(year, emplCode, this.statutorySetting));
	}
	
	private class EmpNormalSettingMemento implements EmpNormalSettingGetMemento {

		private List<MonthlyUnitDto> statutorySetting;
		private int year;
		private String emplCode;
		
		public EmpNormalSettingMemento(int year, String emplCode, List<MonthlyUnitDto> statutorySetting) {
			this.statutorySetting = statutorySetting;
			this.year = year;
			this.emplCode = emplCode;
		}

		@Override
		public Year getYear() {
			return new Year(this.year);
		}

		@Override
		public List<MonthlyUnit> getStatutorySetting() {
			return this.statutorySetting.stream().map(dto -> {
				return new MonthlyUnit(new Month(dto.getMonth()), new MonthlyEstimateTime(dto.getMonthlyTime()));
			}).collect(Collectors.toList());
		}

		@Override
		public CompanyId getCompanyId() {
			return new CompanyId(AppContexts.user().companyId());
		}

		@Override
		public EmploymentCode getEmploymentCode() {
			return new EmploymentCode(this.emplCode);
		}

		
	}
	
	private class ComNormalSettingMemento implements ComNormalSettingGetMemento {

		private List<MonthlyUnitDto> statutorySetting;
		private int year;
		
		public ComNormalSettingMemento(int year, List<MonthlyUnitDto> statutorySetting) {
			this.statutorySetting = statutorySetting;
			this.year = year;
		}

		@Override
		public Year getYear() {
			return new Year(this.year);
		}

		@Override
		public List<MonthlyUnit> getStatutorySetting() {
			return this.statutorySetting.stream().map(dto -> {
				return new MonthlyUnit(new Month(dto.getMonth()), new MonthlyEstimateTime(dto.getMonthlyTime()));
			}).collect(Collectors.toList());
		}

		@Override
		public CompanyId getCompanyId() {
			return new CompanyId(AppContexts.user().companyId());
		}
		
	}
	
	private class ShainNormalSettingMemento implements ShainNormalSettingGetMemento {

		private List<MonthlyUnitDto> statutorySetting;
		private int year;
		private String employeeId;
		
		public ShainNormalSettingMemento(int year, String employeeId, List<MonthlyUnitDto> statutorySetting) {
			this.statutorySetting = statutorySetting;
			this.year = year;
			this.employeeId = employeeId;
		}

		@Override
		public Year getYear() {
			return new Year(this.year);
		}

		@Override
		public List<MonthlyUnit> getStatutorySetting() {
			return this.statutorySetting.stream().map(dto -> {
				return new MonthlyUnit(new Month(dto.getMonth()), new MonthlyEstimateTime(dto.getMonthlyTime()));
			}).collect(Collectors.toList());
		}

		@Override
		public CompanyId getCompanyId() {
			return new CompanyId(AppContexts.user().companyId());
		}

		@Override
		public EmployeeId getEmployeeId() {
			return new EmployeeId(this.employeeId);
		}
		
	}
}
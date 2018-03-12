package nts.uk.ctx.at.shared.app.find.holidaysetting.employee;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import nts.uk.ctx.at.shared.app.find.holidaysetting.common.dto.PublicHolidayMonthSettingDto;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.PublicHolidayMonthSetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.Year;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employee.EmployeeMonthDaySettingSetMemento;

/**
 * The Class EmployeeMonthDaySettingDto.
 */
@Data
public class EmployeeMonthDaySettingDto implements EmployeeMonthDaySettingSetMemento {
	
	/** The year. */
	private int year;
	
	/** The s id. */
	private String sId; 
	
	/** The public holiday month settings. */
	private List<PublicHolidayMonthSettingDto> publicHolidayMonthSettings;
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.employee.EmployeeMonthDaySettingSetMemento#setCompanyId(nts.uk.ctx.bs.employee.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		// Nothing code
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.employee.EmployeeMonthDaySettingSetMemento#setEmployeeId(java.lang.String)
	 */
	@Override
	public void setEmployeeId(String employeeId) {
		this.sId = employeeId;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.employee.EmployeeMonthDaySettingSetMemento#setManagementYear(nts.uk.ctx.bs.employee.dom.holidaysetting.common.Year)
	 */
	@Override
	public void setManagementYear(Year managementYear) {
		this.year = managementYear.v();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.employee.EmployeeMonthDaySettingSetMemento#setPublicHolidayMonthSettings(java.util.List)
	 */
	@Override
	public void setPublicHolidayMonthSettings(List<PublicHolidayMonthSetting> publicHolidayMonthSettings) {
		this.publicHolidayMonthSettings = publicHolidayMonthSettings.stream().map(e -> {
			PublicHolidayMonthSettingDto dto = new PublicHolidayMonthSettingDto(e.getPublicHdManagementYear().v(), e.getMonth().intValue(),
																				e.getInLegalHoliday().v().doubleValue());
			return dto;
		}).collect(Collectors.toList());
	}
}

package nts.uk.ctx.at.shared.app.find.holidaysetting.company;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import nts.uk.ctx.at.shared.app.find.holidaysetting.common.dto.PublicHolidayMonthSettingDto;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.PublicHolidayMonthSetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.Year;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.company.CompanyMonthDaySettingSetMemento;


/**
 * The Class CompanyMonthDaySettingDto.
 */
@Data
public class CompanyMonthDaySettingDto implements CompanyMonthDaySettingSetMemento {
	
	/** The year. */
	private int year;
	
	/** The public holiday month settings. */
	private List<PublicHolidayMonthSettingDto> publicHolidayMonthSettings;
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.company.CompanyMonthDaySettingSetMemento#setCompanyId(nts.uk.ctx.bs.employee.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		// Nothing code
		
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.company.CompanyMonthDaySettingSetMemento#setManagementYear(nts.uk.ctx.bs.employee.dom.holidaysetting.common.Year)
	 */
	@Override
	public void setManagementYear(Year managementYear) {
		this.year = managementYear.v();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.company.CompanyMonthDaySettingSetMemento#setPublicHolidayMonthSettings(java.util.List)
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

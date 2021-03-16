package nts.uk.screen.at.app.ksus01.a;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHoliday;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PublicHolidayDto {

	private String companyId;
	
	private String date;
	
	private String holidayName;
	
	public static PublicHolidayDto toDto(PublicHoliday domain) {
		
		return new PublicHolidayDto(domain.getCompanyId(), domain.getDate().toString(), domain.getHolidayName().v());
	}
}

package nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.holiday;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHoliday;

import nts.uk.shr.com.context.AppContexts;

/**
 * @author hieult
 */

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePublicHolidayCommand {
	
	private BigDecimal date;
	
	private String holidayName;
	

	public PublicHoliday toDomain() {
		return PublicHoliday.createFromJavaType(AppContexts.user().companyId(), this.date, this.holidayName);
	}
}

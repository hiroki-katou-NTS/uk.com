package nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.holiday;

//import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHoliday;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author hieult
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePublicHolidayCommand {

	private String date;

	private String holidayName;
	
	final String DATE_FORMAT = "yyyy/MM/dd";

	public PublicHoliday toDomain() {
		return PublicHoliday.createFromJavaType(AppContexts.user().companyId(), GeneralDate.fromString(this.date, DATE_FORMAT), this.holidayName);
	}

}

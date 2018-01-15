/**
 * 2:08:52 PM Jun 13, 2017
 */
package nts.uk.ctx.at.schedule.app.find.shift.businesscalendar.holiday;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHoliday;

/**
 * @author hungnm
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PublicHolidayDto {

	private GeneralDate date;

	private String holidayName;

	public static PublicHolidayDto fromDomain(PublicHoliday domain) {
		return new PublicHolidayDto(domain.getDate(), domain.getHolidayName().v());
	}

}

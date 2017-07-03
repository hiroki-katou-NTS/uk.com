/**
 * 2:08:52 PM Jun 13, 2017
 */
package nts.uk.ctx.at.schedule.app.find.holiday;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.holiday.PublicHoliday;

/**
 * @author hungnm
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PublicHolidayDto {

	private int date;

	private String name;

	public static PublicHolidayDto fromDomain(PublicHoliday domain) {
		return new PublicHolidayDto(domain.getDate().intValue(), domain.getHolidayName().v());
	}

}

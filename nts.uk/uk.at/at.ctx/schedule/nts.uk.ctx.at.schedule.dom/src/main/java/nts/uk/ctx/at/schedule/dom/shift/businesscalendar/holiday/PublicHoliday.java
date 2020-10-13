/**
 * 4:16:26 PM Jun 12, 2017
 */
package nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday;

//import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;

/**
 * 祝日
 * @author hungnm
 *
 */
@Getter
public class PublicHoliday extends AggregateRoot {

	private String companyId;

	private GeneralDate date;

	private HolidayName holidayName;

	private PublicHoliday(String companyId, GeneralDate date, HolidayName holidayName) {
		super();
		this.companyId = companyId;
		this.date = date;
		this.holidayName = holidayName;
	}

	private PublicHoliday() {
		super();
	}

	public static PublicHoliday createFromJavaType(String companyId, GeneralDate date, String holidayName) {
		return new PublicHoliday(companyId, date, new HolidayName(holidayName));
	}
}

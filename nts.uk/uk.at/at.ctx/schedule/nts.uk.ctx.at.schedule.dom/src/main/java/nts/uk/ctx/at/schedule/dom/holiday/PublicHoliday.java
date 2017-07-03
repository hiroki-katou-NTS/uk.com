/**
 * 4:16:26 PM Jun 12, 2017
 */
package nts.uk.ctx.at.schedule.dom.holiday;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * @author hungnm
 *
 */
@Getter
public class PublicHoliday extends AggregateRoot {

	private String companyId;

	private BigDecimal date;

	private HolidayName holidayName;

	private PublicHoliday(String companyId, BigDecimal date, HolidayName holidayName) {
		super();
		this.companyId = companyId;
		this.date = date;
		this.holidayName = holidayName;
	}

	private PublicHoliday() {
		super();
	}

	public static PublicHoliday createFromJavaType(String companyId, BigDecimal date, String holidayName) {
		return new PublicHoliday(companyId, date, new HolidayName(holidayName));
	}
}

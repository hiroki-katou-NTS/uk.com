/**
 * 4:16:26 PM Jun 12, 2017
 */
package nts.uk.ctx.at.schedule.dom.holiday;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * @author hungnm
 *
 */
@Getter
public class PublicHoliday extends AggregateRoot {

	CompanyId companyId;

	GeneralDate date;

	HolidayName holidayName;

	private PublicHoliday(CompanyId companyId, GeneralDate date, HolidayName holidayName) {
		super();
		this.companyId = companyId;
		this.date = date;
		this.holidayName = holidayName;
	}

	private PublicHoliday() {
		super();
	}

	public static PublicHoliday createFromJavaType(String companyId, GeneralDate date, String holidayName) {
		return new PublicHoliday(new CompanyId(companyId), date, new HolidayName(holidayName));
	}
}

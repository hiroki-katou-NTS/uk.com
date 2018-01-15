package nts.uk.ctx.at.schedule.app.find.shift.businesscalendar.businesscalendar;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.businesscalendar.StartDayItem;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StartDayDto {
	private String companyId;
	private int startDay;

	/**
	 * 
	 * @param domain
	 * @return
	 */
	public static StartDayDto fromDomain(StartDayItem domain) {
		return new StartDayDto(domain.getCompanyId(), domain.getStartDay().value);
	}
}

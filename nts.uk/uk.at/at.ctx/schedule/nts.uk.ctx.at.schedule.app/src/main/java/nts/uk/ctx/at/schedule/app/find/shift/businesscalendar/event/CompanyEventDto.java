/**
 * 2:10:58 PM Jun 13, 2017
 */
package nts.uk.ctx.at.schedule.app.find.shift.businesscalendar.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.CompanyEvent;

/**
 * @author hungnm
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CompanyEventDto {

	private GeneralDate date;

	private String name;

	public static CompanyEventDto fromDomain(CompanyEvent domain) {
		return new CompanyEventDto(domain.getDate(), domain.getEventName().v());
	}

}

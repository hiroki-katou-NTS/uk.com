/**
 * 2:10:58 PM Jun 13, 2017
 */
package nts.uk.ctx.at.schedule.app.find.shift.businesscalendar.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.WorkplaceEvent;

/**
 * @author hungnm
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WorkplaceEventDto {

	private GeneralDate date;

	private String name;

	public static WorkplaceEventDto fromDomain(WorkplaceEvent domain) {
		return new WorkplaceEventDto(domain.getDate(), domain.getEventName().v());
	}

}

/**
 * 2:10:58 PM Jun 13, 2017
 */
package nts.uk.ctx.at.schedule.app.find.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.event.WorkplaceEvent;

/**
 * @author hungnm
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WorkplaceEventDto {

	private int date;

	private String name;

	public static WorkplaceEventDto fromDomain(WorkplaceEvent domain) {
		return new WorkplaceEventDto(domain.getDate().intValue(), domain.getEventName().v());
	}

}
